package com.bsuir.kirillpastukhou.webappfilms.command.impl;

import com.bsuir.kirillpastukhou.webappfilms.command.Command;
import com.bsuir.kirillpastukhou.webappfilms.controller.Page;
import com.bsuir.kirillpastukhou.webappfilms.exception.CommandException;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Command that handles navigation to the registration page.
 * This command is executed when the user wants to navigate to the registration page.
 * It simply returns the path to the registration page.
 */
public class ToRegistrationCommand implements Command {

    /**
     * Executes the ToRegistrationCommand logic.
     * Returns the path to the registration page.
     *
     * @param request The HttpServletRequest object representing the current request.
     * @return The String representing the path to the registration page.
     * @throws CommandException If an error occurs while executing the command.
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        return Page.REGISTRATION;
    }

}