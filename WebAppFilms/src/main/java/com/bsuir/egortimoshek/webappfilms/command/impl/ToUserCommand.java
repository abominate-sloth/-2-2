package com.bsuir.kirillpastukhou.webappfilms.command.impl;

import java.util.ArrayList;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.bsuir.kirillpastukhou.webappfilms.beans.Film;
import com.bsuir.kirillpastukhou.webappfilms.beans.LangBundle;
import com.bsuir.kirillpastukhou.webappfilms.command.Command;
import com.bsuir.kirillpastukhou.webappfilms.controller.Controller;
import com.bsuir.kirillpastukhou.webappfilms.controller.Page;
import com.bsuir.kirillpastukhou.webappfilms.dao.DBDao;
import com.bsuir.kirillpastukhou.webappfilms.dao.DBDaoFactory;
import com.bsuir.kirillpastukhou.webappfilms.exception.CommandException;
import com.bsuir.kirillpastukhou.webappfilms.exception.DatabaseDAOException;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Command that handles displaying films to the user.
 * This command is executed when the user is viewing the films page.
 * It retrieves the films from the database and sets the necessary attributes in the request.
 */
public class ToUserCommand implements Command {
	private static Logger logger = LogManager.getLogger(ToUserCommand.class.getName());
    /**
     * The number of films to display on each page.
     */
    final static Integer filmsOnPage = 5;

    /**
     * Executes the ToUserCommand logic.
     * Retrieves the films from the database based on the requested page number and sets the necessary attributes in the request.
     *
     * @param request The HttpServletRequest object representing the current request.
     * @return The String representing the path to the user page.
     * @throws CommandException If an error occurs while executing the command.
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        ResourceBundle bundle = LangBundle.getBundle(request.getSession());

        DBDaoFactory daoFactory = DBDaoFactory.getInstance();
        try {
            DBDao dao = daoFactory.getDao(DBDaoFactory.DaoType.MySQL);

            // Retrieve the total number of films
            Integer filmsNumber = dao.getFilmsNumber();
            request.setAttribute("filmsNumber", filmsNumber);

            // Set the number of films to display on each page
            request.setAttribute("filmsOnPage", filmsOnPage);

            // Retrieve the current page number from the request parameter
            Integer currentPage = Integer.parseInt(request.getParameter("page"));
            request.setAttribute("page", currentPage);

            // Retrieve the films for the current page from the database
            ArrayList < Film > filmList = dao.getFilms(currentPage);
            request.setAttribute("filmList", filmList);
        } catch (DatabaseDAOException e) {
        	logger.error(e.getMessage());
            // Throw a CommandException with an error message from the language bundle
            throw new CommandException(bundle.getString("to_user_error"));
        }

        return Page.USER;
    }

}