package com.bsuir.kirillpastukhou.webappfilms.command.impl;

import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.bsuir.kirillpastukhou.webappfilms.beans.LangBundle;
import com.bsuir.kirillpastukhou.webappfilms.command.Command;
import com.bsuir.kirillpastukhou.webappfilms.command.CommandHelper;
import com.bsuir.kirillpastukhou.webappfilms.controller.Controller;
import com.bsuir.kirillpastukhou.webappfilms.dao.DBDao;
import com.bsuir.kirillpastukhou.webappfilms.dao.DBDaoFactory;
import com.bsuir.kirillpastukhou.webappfilms.exception.CommandException;
import com.bsuir.kirillpastukhou.webappfilms.exception.DatabaseDAOException;

import jakarta.servlet.http.HttpServletRequest;

/**
 * The EditCommentCommand class implements the Command interface
 * and represents a command to edit a comment.
 */
public class EditCommentCommand implements Command {
	private static Logger logger = LogManager.getLogger(EditCommentCommand.class.getName());
    /**
     * Executes the command to edit a comment based on the given HttpServletRequest.
     *
     * @param request the HttpServletRequest object that contains the request parameters and attributes
     * @return a string representing the destination page
     * @throws CommandException if an error occurs while executing the command
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        // Obtaining the language bundle for localization
        ResourceBundle bundle = LangBundle.getBundle(request.getSession());

        // Getting the DBDaoFactory instance
        DBDaoFactory daoFactory = DBDaoFactory.getInstance();
        try {
            // Getting the DBDao instance for MySQL implementation
            DBDao dao = daoFactory.getDao(DBDaoFactory.DaoType.MySQL);

            // Obtaining the review ID, rating of stars, and comment text from the request parameters
            Integer reviewId = Integer.parseInt(request.getParameter("reviewId"));
            Integer stars = Integer.parseInt(request.getParameter("stars"));
            String comment = request.getParameter("comment");

            // Updating the comment in the database by calling the DAO method
            dao.updateComment(reviewId, stars, comment);
        } catch (DatabaseDAOException e) {
        	logger.error(e.getMessage());
            // Throw a command exception with a localized error message
            throw new CommandException(bundle.getString("edit_comment_error"));
        }

        // Redirecting to the film page after editing the comment
        Command command = CommandHelper.getInstance().getCommand("to_film");
        return command.execute(request);
    }
}