package com.bsuir.kirillpastukhou.webappfilms.command;

/**
 * Enum representing the available command names in the application.
 * Each command name corresponds to a specific functionality or action.
 */
public enum CommandName {
    REGISTRATION, // Command for user registration
    TO_REGISTRATION, // Command to navigate to the registration page
    SIGN_IN, // Command for user sign in
    TO_SIGN_IN, // Command to navigate to the sign in page
    LOGOUT, // Command for user logout
    UNKNOWN_COMMAND, // Command for handling unknown commands
    TO_MAIN, // Command to navigate to the main page
    TO_ADMIN, // Command to navigate to the admin page
    TO_CART, // Command to navigate to the cart page
    TO_USER, // Command to navigate to the user page
    TO_FILM, // Command to navigate to the film page
    LIKE, // Command for liking a comment or a film
    DISLIKE, // Command for disliking a comment or a film
    DELETE_COMMENT, // Command for deleting a comment
    SHOW_EDIT_COMMENT, // Command to show the edit comment form
    EDIT_COMMENT, // Command for editing a comment
    ADD_COMMENT, // Command for adding a comment
    ADD_TO_CART, // Command for adding a film to the cart
    DELETE_FROM_CART, // Command for deleting a film from the cart
    BUY_FILMS, // Command for buying films in the cart
    DELETE_FILM, // Command for deleting a film
    SHOW_EDIT_FILM, // Command to show the edit film form
    EDIT_FILM, // Command for editing a film
    ADD_FILM, // Command for adding a film
    SHOW_EDIT_SALE, // Command to show the edit sale form
    EDIT_SALE // Command for editing a sale
}