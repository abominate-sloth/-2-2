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
 * The AddFilmCommand class implements the Command interface
 * and represents a command to add a film.
 */
public class AddFilmCommand implements Command {
	private static Logger logger = LogManager.getLogger(AddFilmCommand.class.getName());
	
    /**
     * Executes the command to add a film based on the given HttpServletRequest.
     *
     * @param request the HttpServletRequest object that contains the request parameters and attributes
     * @return a string representing the destination page
     * @throws CommandException if an error occurs while executing the command
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        // Obtaining the language bundle
        ResourceBundle bundle = LangBundle.getBundle(request.getSession());

        // Getting the DBDao instance from the factory
        DBDaoFactory daoFactory = DBDaoFactory.getInstance();
        try {
            DBDao dao = daoFactory.getDao(DBDaoFactory.DaoType.MySQL);

            // Obtaining the film details from the request parameters
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            String genre = request.getParameter("genre");
            String length = request.getParameter("length");
            Double cost = Double.parseDouble(request.getParameter("cost"));

            // Adding the film to the database by calling the DAO method
            dao.addFilm(name, description, genre, length, cost);
        } catch (DatabaseDAOException e) {
        	logger.error(e.getMessage());
            // Throw a command exception with a localized error message
            throw new CommandException(bundle.getString("add_film_error"));
        }

        // Redirecting to the admin panel after adding the film
        Command command = CommandHelper.getInstance().getCommand("to_admin");
        return command.execute(request);
    }

}