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
 * The ShowEditFilmCommand class represents a command to show the edit film form.
 * It implements the Command interface.
 */
public class ShowEditFilmCommand implements Command {
	private static Logger logger = LogManager.getLogger(ShowEditFilmCommand.class.getName());

    /**
     * Executes the command to show the edit film form based on the given HttpServletRequest.
     * It retrieves the ResourceBundle for localization.
     * It gets the DBDaoFactory instance and DBDao instance for MySQL implementation.
     * It retrieves the film ID from the request parameters and sets an attribute for the film to be edited.
     * If any errors occur, it throws a CommandException with the appropriate error message.
     * It then retrieves the "to_admin" command from the CommandHelper and executes it to redirect to the admin page.
     *
     * @param request the HttpServletRequest object that contains the request parameters and attributes
     * @return a string representing the destination page
     * @throws CommandException if an error occurs while executing the command
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        // Retrieves the ResourceBundle for localization
        ResourceBundle bundle = LangBundle.getBundle(request.getSession());

        // Gets the DBDaoFactory instance
        DBDaoFactory daoFactory = DBDaoFactory.getInstance();
        try {
            // Gets the DBDao instance for MySQL implementation
            DBDao dao = daoFactory.getDao(DBDaoFactory.DaoType.MySQL);

            // Retrieves the film ID from the request parameters and sets an attribute for the film to be edited
            Integer filmId = Integer.parseInt(request.getParameter("film"));
            request.setAttribute("editFilm", dao.getFilm(filmId));
        } catch (DatabaseDAOException e) {
        	logger.error(e.getMessage());
            // Throws a CommandException with the error message if an error occurs
            throw new CommandException(bundle.getString("show_edit_film_error"));
        }

        // Retrieves the "to_admin" command from the CommandHelper and executes it to redirect to the admin page
        Command command = CommandHelper.getInstance().getCommand("to_admin");
        return command.execute(request);
    }
}