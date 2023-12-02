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
 * The ToCartCommand class implements the Command interface and is responsible for executing a command
 * to retrieve a user's cart information.
 */
public class ToCartCommand implements Command {
	private static Logger logger = LogManager.getLogger(ToCartCommand.class.getName());

    /**
     * Executes the command to retrieve a user's cart information.
     *
     * @param request the HttpServletRequest object that contains the request parameters and attributes
     * @return a string representing the path to the cart page
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
            
            // Get the user ID from the session
            Integer userId = ((User) request.getSession().getAttribute("user")).getId(); 
            
            // Get the list of films in the user's cart
            ArrayList<Film> filmList = dao.getFilmsFromCart(userId);
            
            // Set the film list as an attribute in the request
            request.setAttribute("filmList", filmList);
        } catch (DatabaseDAOException e) {
        	logger.error(e.getMessage());
            // If an exception occurs, throw a CommandException with the localized error message
            throw new CommandException(bundle.getString("to_cart_error"));
        }
        
        // Return the path to the cart page
        return Page.CART;
    }

}