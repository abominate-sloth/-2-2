package com.bsuir.kirillpastukhou.webappfilms.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ResourceBundle;
import com.bsuir.kirillpastukhou.webappfilms.beans.LangBundle;
import com.bsuir.kirillpastukhou.webappfilms.command.Command;
import com.bsuir.kirillpastukhou.webappfilms.command.CommandHelper;
import com.bsuir.kirillpastukhou.webappfilms.exception.CommandException;

import org.apache.logging.log4j.*;

/**
 * The Controller class is a servlet that handles HTTP requests and delegates the processing to the appropriate command.
 */
public class Controller extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String COMMAND_NAME = "command";
    private static Logger logger = LogManager.getLogger(Controller.class.getName());
    /**
     * Default constructor.
     */
    public Controller() {
        super();
    }

    /**
     * Handles GET requests.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if there is a servlet exception
     * @throws IOException      if there is an I/O exception
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    /**
     * Handles POST requests.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if there is a servlet exception
     * @throws IOException      if there is an I/O exception
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    /**
     * Processes the incoming request.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if there is a servlet exception
     * @throws IOException      if there is an I/O exception
     */
    private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ResourceBundle bundle = LangBundle.getBundle(request.getSession());
        
        String commandName = request.getParameter(COMMAND_NAME);
        String page;
        if (commandName != null) {
            try {
                Command command = CommandHelper.getInstance().getCommand(commandName);
                page = command.execute(request);
            } catch (CommandException e) {
                request.setAttribute("errorMessage", e.getMessage());
                page = Page.ERROR;
            }
        } else if (request.getParameter("changeLocale") != null) {
            try {
                Command command = CommandHelper.getInstance().getCommand("to_main");
                page = command.execute(request);
            } catch (CommandException e) {
                request.setAttribute("errorMessage", e.getMessage());
                page = Page.ERROR;
            }
        } else {
            request.setAttribute("errorMessage", bundle.getString("no_command"));
            page = Page.ERROR;
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher(page);
        if (dispatcher != null) {
            dispatcher.forward(request, response);
        } else {
            errorMessageDireclyFromresponse(response);
        }
    }

    /**
     * Displays an error message directly from the HttpServletResponse object.
     *
     * @param response the HttpServletResponse object
     * @throws IOException if there is an I/O exception
     */
    private void errorMessageDireclyFromresponse(HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.getWriter().println("E R R O R");
    }
}