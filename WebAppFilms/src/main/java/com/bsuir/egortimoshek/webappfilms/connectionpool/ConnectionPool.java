package com.bsuir.kirillpastukhou.webappfilms.connectionpool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.bsuir.kirillpastukhou.webappfilms.controller.Controller;

/**
 * The ConnectionPool class represents a connection pool for managing database connections.
 */
public class ConnectionPool {
    private static ConnectionPool instance = null;
    private final String url;
    private final String user;
    private final String password;
    private final int maxConnections = 10;
    private final List<Connection> connectionPool;
    private final List<Connection> usedConnections = new ArrayList<>();
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private static Logger logger = LogManager.getLogger(Controller.class.getName());

    /**
     * Retrieves the instance of the ConnectionPool.
     *
     * @return the instance of the ConnectionPool
     */
    public synchronized static ConnectionPool getInstance() {
        if (instance == null) {
            synchronized (ConnectionPool.class) {
                if (instance == null) {
                    instance = new ConnectionPool();
                }
            }
        }
        return instance;
    }
    
    /**
     * Creates a new ConnectionPool instance.
     * Reads the database configuration from the resource bundle and initializes the connection pool.
     */
    private ConnectionPool() {
        ResourceBundle bundle = ResourceBundle.getBundle("database");
        url = bundle.getString("db.url") + bundle.getString("db.name");
        user = bundle.getString("db.user");
        password = bundle.getString("db.password");
        this.connectionPool = new ArrayList<>(maxConnections);
    }

    /**
     * Retrieves a database connection from the connection pool.
     *
     * @return a Connection object representing a database connection
     * @throws SQLException if an error occurs while getting a connection
     */
    public synchronized Connection getConnection() throws SQLException {
        lock.readLock().lock();
        try {
            if (connectionPool.isEmpty()) {
                if (usedConnections.size() < maxConnections) {
                    Connection connection = createConnection();
                    usedConnections.add(connection);
                    return connection;
                } else {
                    throw new SQLException("Reached maximum connections limit");
                }
            } else {
                Connection connection = connectionPool.remove(connectionPool.size() - 1);
                usedConnections.add(connection);
                return connection;
            }
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * Releases a database connection and returns it to the connection pool.
     *
     * @param connection the Connection object representing the database connection to be released
     */
    public synchronized void releaseConnection(Connection connection) {
        lock.writeLock().lock();
        try {
            usedConnections.remove(connection);
            connectionPool.add(connection);
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * Creates a new database connection.
     *
     * @return a Connection object representing a new database connection
     * @throws SQLException if an error occurs while creating a connection
     */
    private Connection createConnection() throws SQLException {
        Properties properties = new Properties();
        properties.setProperty("user", user);
        properties.setProperty("password", password);
        return DriverManager.getConnection(url, properties);
    }

    /**
     * Closes all the connections in the connection pool and clears the pool.
     *
     * @throws SQLException if an error occurs while closing the connections
     */
    public void closeAllConnections() throws SQLException {
        lock.writeLock().lock();
        try {
            for (Connection connection : connectionPool) {
                connection.close();
            }
            connectionPool.clear();
            for (Connection connection : usedConnections) {
                connection.close();
            }
            usedConnections.clear();
        } finally {
            lock.writeLock().unlock();
        }
    }
}	