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
 * Command that handles navigation to the main page.
 * This command is executed when the user wants to navigate to the main page.
 * It retrieves the necessary data from the database and sets the appropriate attributes in the request.
 * If a user is logged in, it returns the path to the user page.
 * If no user is logged in, it returns the path to the index page.
 */
public class ToMainCommand implements Command {
    // The number of films to display per page
    private static final Integer filmsOnPage = 5;
    private static Logger logger = LogManager.getLogger(ToMainCommand.class.getName());
    

    /**
     * Executes the ToMainCommand logic.
     * Retrieves the necessary data from the database and sets the appropriate attributes in the request.
     * If a user is logged in, it returns the path to the user page.
     * If no user is logged in, it returns the path to the index page.
     *
     * @param request The HttpServletRequest object representing the current request.
     * @return The path to the main page (user page or index page) as a String.
     * @throws CommandException If an error occurs while executing the command.
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
    	ResourceBundle bundle = LangBundle.getBundle(request.getSession());

        // Check if a user is logged in
        User logged = (User) request.getSession().getAttribute("user");
        if (logged != null) {
            DBDaoFactory daoFactory = DBDaoFactory.getInstance();
            try {
                DBDao dao = daoFactory.getDao(DBDaoFactory.DaoType.MySQL);

                // Retrieve the total number of films
                Integer filmsNumber = dao.getFilmsNumber();
                request.setAttribute("filmsNumber", filmsNumber);

                // Set the number of films to display per page
                request.setAttribute("filmsOnPage", filmsOnPage);

                // Set the current page to 1
                request.setAttribute("page", 1);

                // Retrieve the list of films for the first page
                ArrayList < Film > filmList = dao.getFilms(1);
                request.setAttribute("filmList", filmList);
            } catch (DatabaseDAOException e) {
            	logger.error(e.getMessage());
                throw new CommandException(bundle.getString("to_main_error"));
            }

            // Return the path to the user page
            return Page.USER;
        } else {
            // Return the path to the index page
            return Page.INDEX;
        }
    }
}