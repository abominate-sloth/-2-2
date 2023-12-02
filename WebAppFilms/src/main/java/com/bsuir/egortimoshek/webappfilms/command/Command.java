package com.bsuir.kirillpastukhou.webappfilms.command;

import com.bsuir.kirillpastukhou.webappfilms.exception.CommandException;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Interface for implementing command classes.
 * Each command class must implement this interface and provide an implementation
 * for the execute method, which executes the specific command logic.
 */
public interface Command {

    /**
     * Executes the command logic based on the provided HttpServletRequest.
     * The method takes the request as a parameter and returns a string result.
     * Implementations of this method should handle the command logic and return
     * the result or an appropriate error message.
     *
     * @param request The HttpServletRequest object representing the current request
     * @return A string result of the command execution
     * @throws CommandException if there is an error executing the command
     */
    String execute(HttpServletRequest request) throws CommandException;
}