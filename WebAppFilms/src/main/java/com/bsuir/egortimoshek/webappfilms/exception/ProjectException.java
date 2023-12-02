package com.bsuir.kirillpastukhou.webappfilms.exception;

/**
 * The ProjectException class represents a generic exception for the project.
 * This exception is used to handle general errors within the project.
 */
public class ProjectException extends Exception {
    private static final long serialVersionUID = -1711967327267136978L;

    /**
     * Constructs a new ProjectException with no detail message.
     */
    public ProjectException() {
        super();
    }

    /**
     * Constructs a new ProjectException with the specified detail message.
     *
     * @param msg the detail message
     */
    public ProjectException(String msg) {
        super(msg);
    }

    /**
     * Constructs a new ProjectException with the specified detail message and cause.
     *
     * @param msg   the detail message
     * @param cause the cause of the exception
     */
    public ProjectException(String msg, Throwable cause) {
        super(msg, cause);
    }
}