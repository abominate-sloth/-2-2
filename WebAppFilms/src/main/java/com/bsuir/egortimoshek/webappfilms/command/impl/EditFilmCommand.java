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
 * The EditFilmCommand class represents a command to edit a film in the system.
 * It implements the Command interface.
 */
public class EditFilmCommand implements Command {
	private static Logger logger = LogManager.getLogger(EditFilmCommand.class.getName());

    /**
     * Executes the command to edit a film based on the given HttpServletRequest.
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

            // Obtaining the film ID, name, description, genre, length, and cost from the request parameters
            Integer filmId = Integer.parseInt(request.getParameter("film"));
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            String genre = request.getParameter("genre");
            String length = request.getParameter("length");
            Double cost = Double.parseDouble(request.getParameter("cost"));

            // Updating the film in the system by calling the DAO method
            dao.updateFilm(filmId, name, description, genre, length, cost);
        } catch (DatabaseDAOException e) {
        	logger.error(e.getMessage());
            // Throw a command exception with a localized error message
            throw new CommandException(bundle.getString("edit_film_error"));
        }

        // Redirecting to the admin page after successfully editing the film
        Command command = CommandHelper.getInstance().getCommand("to_admin");
        return command.execute(request);
    }
}