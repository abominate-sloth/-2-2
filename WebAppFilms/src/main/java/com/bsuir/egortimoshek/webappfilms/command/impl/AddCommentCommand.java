package com.bsuir.kirillpastukhou.webappfilms.command.impl;

import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.bsuir.kirillpastukhou.webappfilms.beans.LangBundle;
import com.bsuir.kirillpastukhou.webappfilms.beans.User;
import com.bsuir.kirillpastukhou.webappfilms.command.Command;
import com.bsuir.kirillpastukhou.webappfilms.command.CommandHelper;
import com.bsuir.kirillpastukhou.webappfilms.controller.Controller;
import com.bsuir.kirillpastukhou.webappfilms.dao.DBDao;
import com.bsuir.kirillpastukhou.webappfilms.dao.DBDaoFactory;
import com.bsuir.kirillpastukhou.webappfilms.exception.CommandException;
import com.bsuir.kirillpastukhou.webappfilms.exception.DatabaseDAOException;

import jakarta.servlet.http.HttpServletRequest;

/**
 * The AddCommentCommand class implements the Command interface and represents a command to add a comment to a film.
 */
public class AddCommentCommand implements Command {
	private static Logger logger = LogManager.getLogger(AddCommentCommand.class.getName());
	/**
     * Executes the command to add a comment to a film based on the given HttpServletRequest.
     *
     * @param request the HttpServletRequest object that contains the request parameters and attributes
     * @return a string representing the destination page
     * @throws CommandException if an error occurs while executing the command
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        ResourceBundle bundle = LangBundle.getBundle(request.getSession());
        
        // Get the DBDaoFactory instance
        DBDaoFactory daoFactory = DBDaoFactory.getInstance();
        try {
            // Get the DAO for accessing the database
            DBDao dao = daoFactory.getDao(DBDaoFactory.DaoType.MySQL);
            
            // Get the user information from the session
            String username = ((User)request.getSession().getAttribute("user")).getLogin();
            
            // Get the film ID, stars, and comment from the request parameters
            Integer filmId = Integer.parseInt(request.getParameter("film"));
            Integer stars = Integer.parseInt(request.getParameter("stars"));
            String comment = request.getParameter("comment");
            
            // Add the comment to the film in the database
            dao.addComment(filmId, username, stars, comment);
        } catch (DatabaseDAOException e) {
        	logger.error(e.getMessage());
            // Throw a command exception with a localized error message
            throw new CommandException(bundle.getString("add_comment_error"));
        }
        
        // Redirect to the film page after adding the comment
        Command command = CommandHelper.getInstance().getCommand("to_film");
        return command.execute(request);
    }
}