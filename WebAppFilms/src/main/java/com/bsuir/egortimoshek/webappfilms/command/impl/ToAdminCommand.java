package com.bsuir.kirillpastukhou.webappfilms.command.impl;

import java.util.ArrayList;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
 * The ToAdminCommand class implements the Command interface and represents a command to navigate to the admin page.
 */
public class ToAdminCommand implements Command {
	private static Logger logger = LogManager.getLogger(ToAdminCommand.class.getName());
    
    private static final Integer filmsOnPage = 5;
    private static final Integer usersOnPage = 5;
    
    /**
     * Executes the command to navigate to the admin page based on the given HttpServletRequest.
     *
     * @param request the HttpServletRequest object that contains the request parameters and attributes
     * @return a string representing the destination page
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
            
            // Get the total number of films in the database
            Integer filmsNumber = dao.getFilmsNumber();
            request.setAttribute("filmsNumber", filmsNumber);
            request.setAttribute("filmsOnPage", filmsOnPage);
            
            // Get the current page number from the request parameter
            Integer currentPage = Integer.parseInt(request.getParameter("page"));
            request.setAttribute("page", currentPage);
            
            // Get the list of films for the current page
            ArrayList<Film> filmList = dao.getFilms(currentPage);
            request.setAttribute("filmList", filmList);
            
            // Get the total number of users in the database
            Integer usersNumber = dao.getUsersNumber();
            request.setAttribute("usersNumber", usersNumber);
            request.setAttribute("usersOnPage", usersOnPage);   
            
            // Get the current page number for the users from the request parameter
            Integer currentPageUser = Integer.parseInt(request.getParameter("pageUser"));
            request.setAttribute("pageUser", currentPageUser);
            
            // Get the list of users for the current page
            ArrayList<User> userList = dao.getUsers(currentPageUser);
            request.setAttribute("userList", userList);
        } catch (DatabaseDAOException e) {
        	logger.error(e.getMessage());
            // If an exception occurs, throw a CommandException with the localized error message
            throw new CommandException(bundle.getString("to_admin_error"));
        }

        // Return the destination page
        return Page.ADMIN;
    }
}