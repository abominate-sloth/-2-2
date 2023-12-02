package com.bsuir.kirillpastukhou.webappfilms.listener;

import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.bsuir.kirillpastukhou.webappfilms.connectionpool.ConnectionPool;
import com.bsuir.kirillpastukhou.webappfilms.controller.Controller;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

/**
 * Application Lifecycle Listener implementation class ExitListener
 *
 */
public class ExitListener implements ServletContextListener {
	private static Logger logger = LogManager.getLogger(ExitListener.class.getName());
    
    public void contextInitialized(ServletContextEvent sce)  { 
    	try {
            ConnectionPool.getInstance().closeAllConnections();
        } catch (SQLException e) {
        	logger.error(e.getMessage());
            e.printStackTrace();
        }
    }
}
