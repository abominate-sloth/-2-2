package com.bsuir.kirillpastukhou.webappfilms.command.impl;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
 * The SignInCommand class implements the Command interface and represents a command to sign in a user.
 */
public class SignInCommand implements Command {
    
    /** The number of films to display on each page */
    private static final int FILMS_ON_PAGE = 5;
    private static Logger logger = LogManager.getLogger(SignInCommand.class.getName());
    
    /**
     * Executes the command to sign in a user based on the given HttpServletRequest.
     *
     * @param request the HttpServletRequest object that contains the request parameters and attributes
     * @return a string representing the destination page
     * @throws CommandException if an error occurs while executing the command
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        // Get the resource bundle for language localization
    	ResourceBundle bundle = LangBundle.getBundle(request.getSession());

        // Get the DAO factory instance
        DBDaoFactory daoFactory = DBDaoFactory.getInstance();
        
        try {
            // Get the DAO for accessing the database
            DBDao dao = daoFactory.getDao(DBDaoFactory.DaoType.MySQL);
            
            // Hash the entered password using SHA-256 algorithm
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(request.getParameter("password").getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();

            // Convert the hashed password to hexadecimal format
            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            String sha256Hash = hexString.toString();

            String page = null;
            try {
                // Sign in the user using the entered login and hashed password
                User user = dao.signIn(request.getParameter("login"), sha256Hash);
                
                // Set the signed-in user in the session
                request.getSession().setAttribute("user", user);
                request.getSession().setMaxInactiveInterval(3600);
                page = Page.USER;
            } catch (DatabaseDAOException e) {
            	logger.error(e.getMessage());
                // Set an attribute to indicate a failed sign-in attempt and show the sign-in page again
                request.setAttribute("failedSignIn", bundle.getString("signin_error"));
                page = Page.SIGN_IN;
            }

            if (page.equals(Page.USER)) {
                // Get the total number of films in the database
                Integer filmsNumber = dao.getFilmsNumber();
                request.setAttribute("filmsNumber", filmsNumber);
                request.setAttribute("filmsOnPage", FILMS_ON_PAGE);
                request.setAttribute("page", 1);

                // Get a list of films to display on the initial page
                ArrayList<Film> filmList = dao.getFilms(1);
                request.setAttribute("filmList", filmList);
            }

            return page;
        } catch (DatabaseDAOException | NoSuchAlgorithmException e) {
        	logger.error(e.getMessage());
            // Throw a command exception with a localized error message
            throw new CommandException(bundle.getString("fatal_signin_error"));
        }
    }
}