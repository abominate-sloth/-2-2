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
 * The BuyFilmsCommand class implements the Command interface
 * and represents a command to buy films from the user's cart.
 */
public class BuyFilmsCommand implements Command {
	private static Logger logger = LogManager.getLogger(BuyFilmsCommand.class.getName());
	
    /**
     * Executes the command to buy films from the user's cart based on the given HttpServletRequest.
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
            Integer userId = ((User) request.getSession().getAttribute("user")).getId();

            // Buying films from the user's cart by calling the DAO method
            dao.buyFilms(userId);

            // Retrieving the list of films from the user's cart
            ArrayList<Film> filmList = dao.getFilmsFromCart(userId);
            request.setAttribute("filmList", filmList);
        } catch (DatabaseDAOException e) {
        	logger.error(e.getMessage());
            // Throw a command exception with a localized error message
            throw new CommandException(bundle.getString("buy_films_error"));
        }
        
        // Returning the cart page as the destination page
        return Page.CART;
    }
}