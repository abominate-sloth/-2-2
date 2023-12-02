package com.bsuir.kirillpastukhou.webappfilms.dao;

import com.bsuir.kirillpastukhou.webappfilms.dao.impl.MySqlDBDao;
import com.bsuir.kirillpastukhou.webappfilms.exception.DatabaseDAOException;

/**
 * Factory class for creating instances of DBDao implementations.
 */
public class DBDaoFactory {

    private final static DBDaoFactory instance = new DBDaoFactory();

    private DBDaoFactory() {}

    /**
     * Returns the singleton instance of DBDaoFactory.
     *
     * @return the singleton instance of DBDaoFactory
     */
    public static DBDaoFactory getInstance() {
        return instance;
    }

    /**
     * Creates and returns an instance of the requested DBDao implementation based on the provided DaoType.
     *
     * @param type the DaoType indicating the requested DBDao implementation
     * @return an instance of the requested DBDao implementation
     * @throws DatabaseDAOException if no matching DBDao implementation is found
     */
    public DBDao getDao(DaoType type) throws DatabaseDAOException {
        switch (type) {
            case MySQL:
                return MySqlDBDao.getInstance();
            default:
                throw new DatabaseDAOException("No such DAO");
        }
    }

    /**
     * Enumeration of available DaoTypes.
     */
    public enum DaoType {
        MySQL
    }
}