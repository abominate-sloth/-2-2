package com.bsuir.kirillpastukhou.webappfilms.beans;

/**
 * Class representing a user.
 * Contains information about the user's ID, login, admin status, and user discount.
 */
public class User {
    private Integer id; // User ID
    private String login; // User login
    private Boolean is_admin; // User admin status
    private Double sale; // User discount

    /**
     * Constructor for the User class with specified parameters, initializing a user without a discount.
     *
     * @param id       User ID
     * @param login    User login
     * @param is_admin User admin status	
     */
    public User(Integer id, String login, Boolean is_admin) {
        this.id = id;
        this.login = login;
        this.is_admin = is_admin;
        this.sale = 0.0;
    }

    /**
     * Constructor for the User class with specified parameters.
     *
     * @param id       User ID
     * @param login    User login
     * @param is_admin User admin status
     * @param sale     User discount
     */
    public User(Integer id, String login, Boolean is_admin, Double sale) {
        this.id = id;
        this.login = login;
        this.is_admin = is_admin;
        this.sale = sale;
    }

    /**
     * Method to get the user ID.
     *
     * @return User ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * Method to get the user login.
     *
     * @return User login
     */
    public String getLogin() {
        return login;
    }

    /**
     * Method to check the user's admin status.
     *
     * @return true if the user is an admin, else false
     */
    public Boolean isAdmin() {
        return is_admin;
    }

    /**
     * Method to get the user discount.
     *
     * @return User discount
     */
    public Double getSale() {
        return sale;
    }
}