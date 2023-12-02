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
 * The LikeCommand class represents a command to add or update the likes of a comment/review in the system.
 * It implements the Command interface.
 */
public class LikeCommand implements Command {
	private static Logger logger = LogManager.getLogger(LikeCommand.class.getName());

    /**
     * Executes the command to add or update the likes of a comment/review based on the given HttpServletRequest.
     *
     * @param request the HttpServletRequest object that contains the request parameters and attributes
     * @return a string representing the destination page
     * @throws CommandException if an error occurs while executing the command
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        ResourceBundle bundle = LangBundle.getBundle(request.getSession());
  
        // Getting the DBDaoFactory instance
        DBDaoFactory daoFactory = DBDaoFactory.getInstance();
        try {
            // Getting the DBDao instance for MySQL implementation
            DBDao dao = daoFactory.getDao(DBDaoFactory.DaoType.MySQL);
            
            // Obtaining the review ID, likes, and user ID from the request parameters and session attribute
            Integer reviewId = Integer.parseInt(request.getParameter("reviewId"));
            Integer likes = Integer.parseInt(request.getParameter("likes"));
            Integer userId = ((User) request.getSession().getAttribute("user")).getId(); 
   
            // Updating the likes of the comment/review in the system by calling the DAO method
            dao.updateCommentLikes(userId, reviewId, likes);
        } catch (DatabaseDAOException e) {
        	logger.error(e.getMessage());
            // Throw a command exception with a localized error message
            throw new CommandException(bundle.getString("like_error"));
        }

        // Redirecting to the film details page after successfully adding/updating the likes
        Command command = CommandHelper.getInstance().getCommand("to_film");
        return command.execute(request);   
    }
}