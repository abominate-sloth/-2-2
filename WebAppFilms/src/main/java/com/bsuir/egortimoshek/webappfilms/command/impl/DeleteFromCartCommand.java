package com.bsuir.kirillpastukhou.webappfilms.command.impl;

import com.bsuir.kirillpastukhou.webappfilms.command.Command;
import com.bsuir.kirillpastukhou.webappfilms.command.CommandHelper;
import com.bsuir.kirillpastukhou.webappfilms.controller.Controller;
import com.bsuir.kirillpastukhou.webappfilms.dao.DBDao;
import com.bsuir.kirillpastukhou.webappfilms.dao.DBDaoFactory;
import com.bsuir.kirillpastukhou.webappfilms.exception.CommandException;
import com.bsuir.kirillpastukhou.webappfilms.exception.DatabaseDAOException;

import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.bsuir.kirillpastukhou.webappfilms.beans.LangBundle;
import com.bsuir.kirillpastukhou.webappfilms.beans.User;

import jakarta.servlet.http.HttpServletRequest;

/**
 * The DeleteFromCartCommand class implements the Command interface
 * and represents a command to delete a film from the user's cart.
 */
public class DeleteFromCartCommand implements Command {
	private static Logger logger = LogManager.getLogger(DeleteFromCartCommand.class.getName());

    /**
     * Executes the command to delete a film from the user's cart based on the given HttpServletRequest.
     *
     * @param request the HttpServletRequest object that contains the request parameters and attributes
     * @return a string representing the destination page
     * @throws CommandException if an error occurs while executing the command
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        // Obtaining the language bundle
        ResourceBundle bundle = LangBundle.getBundle(request.getSession());

        // Getting the DBDaoFactory instance
        DBDaoFactory daoFactory = DBDaoFactory.getInstance();
        try {
            DBDao dao = daoFactory.getDao(DBDaoFactory.DaoType.MySQL);

            // Obtaining the film ID and user ID from the request parameters
            Integer filmId = Integer.parseInt(request.getParameter("film"));
            Integer userId = ((User) request.getSession().getAttribute("user")).getId();

            // Deleting the film from the user's cart by calling the DAO method
            dao.deleteFromCart(filmId, userId);
        } catch (DatabaseDAOException e) {
        	logger.error(e.getMessage());
            // Throw a command exception with a localized error message
            throw new CommandException(bundle.getString("delete_from_cart_error"));
        }

        // Redirecting to the cart page after deleting the film from the cart
        Command command = CommandHelper.getInstance().getCommand("to_cart");
        return command.execute(request);
    }

}