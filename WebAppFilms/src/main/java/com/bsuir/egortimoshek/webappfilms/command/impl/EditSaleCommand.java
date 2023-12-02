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
 * The EditSaleCommand class implements the Command interface 
 * and represents a command to edit the sale of a user in the system.
 */
public class EditSaleCommand implements Command {
	private static Logger logger = LogManager.getLogger(EditSaleCommand.class.getName());

    /**
     * Executes the command to edit the sale of a user based on the given HttpServletRequest.
     * It updates the user's sale in the database and then redirects the user to the admin page.
     *
     * @param request the HttpServletRequest object that contains the request parameters and attributes
     * @return a string representing the destination page
     * @throws CommandException if an error occurs while executing the command
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        // Obtaining the language bundle for localization
        ResourceBundle bundle = LangBundle.getBundle(request.getSession());
  
        // Getting the DBDaoFactory instance
        DBDaoFactory daoFactory = DBDaoFactory.getInstance();
        try {
            // Getting the DBDao instance for MySQL implementation
            DBDao dao = daoFactory.getDao(DBDaoFactory.DaoType.MySQL);
   
            // Obtaining the user ID and sale amount from the request parameters
            Integer userId = Integer.parseInt(request.getParameter("userId"));
            Double sale = Double.parseDouble(request.getParameter("sale"));
   
            // Updating the user's sale in the database by calling the DAO method
            dao.updateUserSale(userId, sale);
        } catch (DatabaseDAOException e) {
        	logger.error(e.getMessage());
            // Throw a command exception with a localized error message if an error occurs
            throw new CommandException(bundle.getString("edit_sale_error"));
        }
  
        // Redirecting to the admin page after editing the user's sale
        Command command = CommandHelper.getInstance().getCommand("to_admin");
        return command.execute(request);
    }
}