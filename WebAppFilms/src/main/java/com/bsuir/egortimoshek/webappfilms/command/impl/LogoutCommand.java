package com.bsuir.kirillpastukhou.webappfilms.command.impl;

import com.bsuir.kirillpastukhou.webappfilms.command.Command;
import com.bsuir.kirillpastukhou.webappfilms.controller.Page;
import com.bsuir.kirillpastukhou.webappfilms.exception.CommandException;

import jakarta.servlet.http.HttpServletRequest;

/**
 * The LogoutCommand class represents a command to log out a user from the system.
 * It implements the Command interface.
 */
public class LogoutCommand implements Command {

    /**
     * Executes the command to log out a user based on the given HttpServletRequest.
     * It removes the user attribute from the session and redirects the user to the index page.
     *
     * @param request the HttpServletRequest object that contains the request parameters and attributes
     * @return a string representing the destination page
     * @throws CommandException if an error occurs while executing the command
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        // Removing the user attribute from the session
        request.getSession().setAttribute("user", null);
        
        // Redirecting to the index page after logging out
        return Page.INDEX;
    }

}