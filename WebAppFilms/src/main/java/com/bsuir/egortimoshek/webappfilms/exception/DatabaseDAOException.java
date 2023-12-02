package com.bsuir.kirillpastukhou.webappfilms.exception;

/**
 * The DBDaoException class represents an exception that occurs during database DAO (Data Access Object) operations.
 * This exception is used to handle errors specifically related to database operations in the DAO layer.
 */
public class DatabaseDAOException extends ProjectException {
    private static final long serialVersionUID = -2140677818975799557L;

    /**
     * Constructs a new DBDaoException with no detail message.
     */
    public DatabaseDAOException() {
        super();
    }

    /**
     * Constructs a new DBDaoException with the specified detail message.
     *
     * @param msg the detail message
     */
    public DatabaseDAOException(String msg) {
        super(msg);
    }

    /**
     * Constructs a new DBDaoException with the specified detail message and cause.
     *
     * @param msg   the detail message
     * @param cause the cause of the exception
     */
    public DatabaseDAOException(String msg, Throwable cause) {
        super(msg, cause);
    }
}