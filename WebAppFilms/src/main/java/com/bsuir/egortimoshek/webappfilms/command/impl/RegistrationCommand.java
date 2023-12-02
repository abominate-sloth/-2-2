package com.bsuir.kirillpastukhou.webappfilms.command.impl;

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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;

/**
 * The RegistrationCommand class represents a command to register a new user in the system.
 * It implements the Command interface.
 */
public class RegistrationCommand implements Command {
    /**
     * The number of films to display on each page.
     */
    final static Integer filmsOnPage = 5;
    private static Logger logger = LogManager.getLogger(RegistrationCommand.class.getName());
    
    /**
     * Executes the command to register a new user based on the given HttpServletRequest.
     * It performs user registration by calling the DAO methods and sets the appropriate attributes in the request.
     * It also handles localization and hashing of the user's password.
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
            
            // Hashing the password using SHA-256 algorithm
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(request.getParameter("password").getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            String sha256Hash = hexString.toString();
            
            String page = null;
            try {
                // Registering the user in the system by calling the DAO method
                User user = dao.registration(request.getParameter("login"), sha256Hash);
                request.getSession().setAttribute("user", user);
                request.getSession().setMaxInactiveInterval(3600);
                page = Page.USER;
            } catch (DatabaseDAOException e) {
            	logger.error(e.getMessage());
                // Handling the exception for failed registration attempt
                request.setAttribute("failedRegistration", bundle.getString("registration_error"));
                page = Page.REGISTRATION;
            }
            
            if (page == Page.USER) {
                // Setting the attributes required for displaying films on the user page
                Integer filmsNumber = dao.getFilmsNumber();
                request.setAttribute("filmsNumber", filmsNumber);
                request.setAttribute("filmsOnPage", filmsOnPage);
                request.setAttribute("page", 1);
                
                ArrayList<Film> filmList = dao.getFilms(1);
                request.setAttribute("filmList", filmList);
            }
            
            return page;
        } catch (DatabaseDAOException | NoSuchAlgorithmException e) {
        	logger.error(e.getMessage());
            // Throwing a command exception with a localized error message
            throw new CommandException(bundle.getString("fatal_registration_error"));
        }
    }
}