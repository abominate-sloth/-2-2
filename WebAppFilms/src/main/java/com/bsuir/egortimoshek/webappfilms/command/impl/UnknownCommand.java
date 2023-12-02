package com.bsuir.kirillpastukhou.webappfilms.command.impl;

import java.util.ResourceBundle;

import com.bsuir.kirillpastukhou.webappfilms.beans.LangBundle;
import com.bsuir.kirillpastukhou.webappfilms.command.Command;
import com.bsuir.kirillpastukhou.webappfilms.controller.Page;
import com.bsuir.kirillpastukhou.webappfilms.exception.CommandException;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Command that handles unknown commands.
 * This command is executed when the requested command name is not recognized.
 * It sets an error message in the request attribute and returns the error page.
 */
public class UnknownCommand implements Command {
	
    /**
     * Executes the UnknownCommand logic.
     * Sets an error message in the request attribute and returns the error page.
     *
     * @param request The HttpServletRequest object representing the current request.
     * @return The String representing the path to the error page.
     * @throws CommandException If an error occurs while executing the command.
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        ResourceBundle bundle = LangBundle.getBundle(request.getSession());

        // Set error message in the request attribute using the language bundle
        request.setAttribute("errorMessage", bundle.getString("unknown_command"));
        return Page.ERROR;
    }

}