package com.bsuir.kirillpastukhou.webappfilms.exception;

/**
 * The CommandException class represents an exception that occurs during command processing.
 */
public class CommandException extends ProjectException {
    private static final long serialVersionUID = -1711969327267136978L;

    /**
     * Constructs a new CommandException with no detail message.
     */
    public CommandException() {
        super();
    }

    /**
     * Constructs a new CommandException with the specified detail message.
     *
     * @param msg the detail message
     */
    public CommandException(String msg) {
        super(msg);
    }

    /**
     * Constructs a new CommandException with the specified detail message and cause.
     *
     * @param msg   the detail message
     * @param cause the cause
     */
    public CommandException(String msg, Throwable cause) {
        super(msg, cause);
    }
}