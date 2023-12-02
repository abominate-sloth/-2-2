package com.bsuir.kirillpastukhou.webappfilms.command.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.bsuir.kirillpastukhou.webappfilms.command.Command;
import com.bsuir.kirillpastukhou.webappfilms.command.CommandHelper;
import com.bsuir.kirillpastukhou.webappfilms.controller.Controller;
import com.bsuir.kirillpastukhou.webappfilms.exception.CommandException;

import jakarta.servlet.http.HttpServletRequest;

/**
 * The ShowEditCommentCommand class represents a command to show the form for editing a comment.
 * It implements the Command interface.
 */
public class ShowEditCommentCommand implements Command {
    /**
     * Executes the command to show the form for editing a comment based on the given HttpServletRequest.
     * It sets an attribute for the request to indicate which comment should be edited.
     * Then, it retrieves the "to_film" command and executes it.
     *
     * @param request the HttpServletRequest object that contains the request parameters and attributes
     * @return a string representing the destination page returned by the executed "to_film" command
     * @throws CommandException if an error occurs while executing the command
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        // Setting the attribute to indicate which comment should be edited
        request.setAttribute("editFlag", Integer.parseInt(request.getParameter("reviewId")));
        
        // Retrieving and executing the "to_film" command
        Command command = CommandHelper.getInstance().getCommand("to_film");
        return command.execute(request);
    }
}