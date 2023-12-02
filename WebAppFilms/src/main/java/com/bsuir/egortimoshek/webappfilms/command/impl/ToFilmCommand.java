package com.bsuir.kirillpastukhou.webappfilms.command.impl;

import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.bsuir.kirillpastukhou.webappfilms.beans.Comment;
import com.bsuir.kirillpastukhou.webappfilms.beans.Film;
import com.bsuir.kirillpastukhou.webappfilms.beans.LangBundle;
import com.bsuir.kirillpastukhou.webappfilms.beans.User;
import com.bsuir.kirillpastukhou.webappfilms.command.Command;
import com.bsuir.kirillpastukhou.webappfilms.controller.Controller;
import com.bsuir.kirillpastukhou.webappfilms.controller.Page;
import com.bsuir.kirillpastukhou.webappfilms.dao.DBDao;
import com.bsuir.kirillpastukhou.webappfilms.dao.DBDaoFactory;
import com.bsuir.kirillpastukhou.webappfilms.exception.CommandException;
import com.bsuir.kirillpastukhou.webappfilms.exception.DatabaseDAOException;

import jakarta.servlet.http.HttpServletRequest;

/**
 * The ToFilmCommand class implements the Command interface and is responsible for executing a command
 * to retrieve film information and comments.
 */
public class ToFilmCommand implements Command {
	private static Logger logger = LogManager.getLogger(ToFilmCommand.class.getName());

    /**
     * Executes the command to retrieve film information and comments.
     *
     * @param request the HttpServletRequest object that contains the request parameters and attributes
     * @return a string representing the path to the film page
     * @throws CommandException if an error occurs while executing the command
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        // Get the resource bundle for localization
        ResourceBundle bundle = LangBundle.getBundle(request.getSession());
        
        // Get the DAO factory instance
        DBDaoFactory daoFactory = DBDaoFactory.getInstance();
        try {
            // Get the DAO for dealing with database operations
            DBDao dao = daoFactory.getDao(DBDaoFactory.DaoType.MySQL);
            
            // Get the film ID from the request parameters
            Integer filmId = Integer.parseInt(request.getParameter("film"));
            
            // Get the user ID from the session
            Integer userId = ((User) request.getSession().getAttribute("user")).getId(); 
            
            // Get the comment statuses for the specified film and user
            Map<Integer, Integer> commentStatus = dao.getCommentStatuses(filmId, userId);
            
            // Set the comment statuses as an attribute in the request
            request.setAttribute("commentStatus", commentStatus);
            
            // Get the details of the current film
            Film currentFilm = dao.getFilm(filmId);
            
            // Set the current film as an attribute in the request
            request.setAttribute("film", currentFilm);
            
            // Get the film status for the current user
            request.setAttribute("filmStatus", dao.getFilmStatus(filmId, userId));
            
            // Get the list of comments/reviews for the current film
            ArrayList<Comment> reviewsList = dao.getComments(filmId);
            
            // Set the list of comments as an attribute in the request
            request.setAttribute("reviewsList", reviewsList);
        } catch (DatabaseDAOException e) {
        	logger.error(e.getMessage());
            // If an exception occurs, throw a CommandException with the localized error message
            throw new CommandException(bundle.getString("to_film_error"));
        }
        
        // Return the path to the film page
        return Page.FILM;
    }

}