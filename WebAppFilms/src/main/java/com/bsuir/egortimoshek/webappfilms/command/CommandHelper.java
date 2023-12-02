package com.bsuir.kirillpastukhou.webappfilms.command;

import java.util.HashMap;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.bsuir.kirillpastukhou.webappfilms.command.impl.*;
import com.bsuir.kirillpastukhou.webappfilms.controller.Controller;

/**
 * Helper class for managing and retrieving command instances.
 * This class is responsible for mapping the command names to their corresponding command implementations.
 */
public final class CommandHelper {
    private static final CommandHelper instance = new CommandHelper();
    private Map < CommandName, Command > commands = new HashMap < > ();
    private static Logger logger = LogManager.getLogger(CommandHelper.class.getName());

    private CommandHelper() {
        // Initialize the map with the command name to command implementation mappings
        commands.put(CommandName.UNKNOWN_COMMAND, new UnknownCommand());
        commands.put(CommandName.SIGN_IN, new SignInCommand());
        commands.put(CommandName.REGISTRATION, new RegistrationCommand());
        commands.put(CommandName.TO_SIGN_IN, new ToSignInCommand());
        commands.put(CommandName.TO_REGISTRATION, new ToRegistrationCommand());
        commands.put(CommandName.LOGOUT, new LogoutCommand());
        commands.put(CommandName.TO_MAIN, new ToMainCommand());
        commands.put(CommandName.TO_ADMIN, new ToAdminCommand());
        commands.put(CommandName.TO_CART, new ToCartCommand());
        commands.put(CommandName.TO_USER, new ToUserCommand());
        commands.put(CommandName.TO_FILM, new ToFilmCommand());
        commands.put(CommandName.LIKE, new LikeCommand());
        commands.put(CommandName.DISLIKE, new DislikeCommand());
        commands.put(CommandName.DELETE_COMMENT, new DeleteCommentCommand());
        commands.put(CommandName.SHOW_EDIT_COMMENT, new ShowEditCommentCommand());
        commands.put(CommandName.EDIT_COMMENT, new EditCommentCommand());
        commands.put(CommandName.ADD_COMMENT, new AddCommentCommand());
        commands.put(CommandName.ADD_TO_CART, new AddToCartCommand());
        commands.put(CommandName.DELETE_FROM_CART, new DeleteFromCartCommand());
        commands.put(CommandName.BUY_FILMS, new BuyFilmsCommand());
        commands.put(CommandName.DELETE_FILM, new DeleteFilmCommand());
        commands.put(CommandName.SHOW_EDIT_FILM, new ShowEditFilmCommand());
        commands.put(CommandName.EDIT_FILM, new EditFilmCommand());
        commands.put(CommandName.ADD_FILM, new AddFilmCommand());
        commands.put(CommandName.EDIT_SALE, new EditSaleCommand());
        commands.put(CommandName.SHOW_EDIT_SALE, new ShowEditSaleCommand());
    }

    /**
     * Retrieves the singleton instance of the CommandHelper.
     *
     * @return The CommandHelper instance.
     */
    public static CommandHelper getInstance() {
        return instance;
    }

    /**
     * Retrieves the command implementation based on the provided command name.
     *
     * @param commandName The name of the command.
     * @return The Command instance corresponding to the command name.
     */
    public Command getCommand(String commandName) {
        CommandName name = null;
        Command command = null;
        try {
            name = CommandName.valueOf(commandName.toUpperCase());
            command = commands.get(name);
        } catch (IllegalArgumentException e) {
        	logger.error(e.getMessage());
            command = commands.get(CommandName.UNKNOWN_COMMAND);
        }
        return command;
    }
}