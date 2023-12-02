package com.bsuir.kirillpastukhou.webappfilms.dao;

import java.util.ArrayList;
import java.util.HashMap;

import com.bsuir.kirillpastukhou.webappfilms.beans.Comment;
import com.bsuir.kirillpastukhou.webappfilms.beans.Film;
import com.bsuir.kirillpastukhou.webappfilms.beans.User;
import com.bsuir.kirillpastukhou.webappfilms.exception.DatabaseDAOException;

/**
 * The DBDao interface defines methods for interacting with the database.
 */
public interface DBDao {

	/**
	 * Attempts to sign in a user with the given login and password hash.
	 *
	 * @param login         the login of the user.
	 * @param password_hash the hashed password of the user.
	 * @return the User object representing the signed-in user, if the sign-in is successful.
	 * @throws DatabaseDAOException if an SQL exception occurs during the database operation or if the sign-in credentials are invalid.
	 */
    User signIn(String login, String password_hash) throws DatabaseDAOException;

    /**
	 * Registers a new user with the given login and hashed password.
	 *
	 * @param login         the login of the user.
	 * @param password_hash the hashed password of the user.
	 * @return the User object representing the registered user, if the registration is successful.
	 * @throws DatabaseDAOException if an SQL exception occurs during the database operation or if the registration fails.
	 */
    User registration(String login, String password_hash) throws DatabaseDAOException;

    /**
	 * Retrieves the comments for a film with the specified ID from the database.
	 *
	 * @param filmId the ID of the film to retrieve comments for.
	 * @return an ArrayList of Comment objects representing the comments for the film.
	 * @throws DatabaseDAOException if there is an error executing the SQL query or releasing database resources.
	 */
    ArrayList<Comment> getComments(Integer filmId) throws DatabaseDAOException;

    /**
	 * Retrieves the User object with the specified ID.
	 *
	 * @param userId the ID of the user to retrieve.
	 * @return the User object corresponding to the specified ID, or null if the user is not found.
	 * @throws DatabaseDAOException if there is an error executing the SQL query or releasing database resources.
	 */
    User getUser(Integer userId) throws DatabaseDAOException;

    void deleteUser(String username) throws DatabaseDAOException;
    /**
	 * Retrieves a list of User objects for the specified page of users.
	 *
	 * @param currentPageUser the index of the current page of users to retrieve.
	 * @return an ArrayList of User objects representing the users on the current page.
	 * @throws DatabaseDAOException if there is an error executing the SQL query or releasing database resources.
	 */
    ArrayList<User> getUsers(Integer currentPageUser) throws DatabaseDAOException;

    /**
	 * Updates the sale value for the user with the specified ID.
	 *
	 * @param userId  the ID of the user to update the sale value for.
	 * @param sale    the new sale value to set for the user.
	 * @throws DatabaseDAOException if there is an error executing the SQL query or releasing database resources.
	 */
    void updateUserSale(Integer userId, Double sale) throws DatabaseDAOException;

    /**
	 * Retrieves a list of films for a specified page.
	 *
	 * @param currentPage the page number of the films to retrieve.
	 * @return an ArrayList of Film objects representing the films on the specified page.
	 * @throws DatabaseDAOException if an SQL exception occurs during the database operation.
	 */
    ArrayList<Film> getFilms(Integer currentPage) throws DatabaseDAOException;

    /**
	 * Retrieves a list of films from the cart for a specified user.
	 *
	 * @param userId the ID of the user.
	 * @return an ArrayList of Film objects representing the films in the user's cart.
	 * @throws DatabaseDAOException if an SQL exception occurs during the database operation.
	 */
    ArrayList<Film> getFilmsFromCart(Integer userId) throws DatabaseDAOException;

    /**
	 * Retrieves a film with the specified ID.
	 *
	 * @param filmId the ID of the film to retrieve.
	 * @return a Film object representing the film with the specified ID.
	 * @throws DatabaseDAOException if an SQL exception occurs during the database operation.
	 */
    Film getFilm(Integer filmId) throws DatabaseDAOException;

    /**
	 * Adds a new film to the database with the specified details.
	 *
	 * @param name        the title of the film.
	 * @param description the description of the film.
	 * @param genre       the genre of the film.
	 * @param length      the length of the film.
	 * @param cost        the cost of the film.
	 * @throws DatabaseDAOException if an SQL exception occurs during the database operation.
	 */
    void addFilm(String name, String description, String genre, String length, Double cost) throws DatabaseDAOException;

    /**
	 * Updates the details of a film in the database with the specified ID.
	 *
	 * @param filmId      the ID of the film to update.
	 * @param name        the new title of the film.
	 * @param description the new description of the film.
	 * @param genre       the new genre of the film.
	 * @param length      the new length of the film.
	 * @param cost        the new cost of the film.
	 * @throws DatabaseDAOException if there is an error executing the SQL query or releasing database resources.
	 */
    void updateFilm(Integer filmId, String name, String description, String genre, String length, Double cost) throws DatabaseDAOException;

    /**
	 * Deletes a film from the database with the specified ID.
	 *
	 * @param filmId the ID of the film to delete.
	 * @throws DatabaseDAOException if there is an error executing the SQL query or releasing database resources.
	 */
    void deleteFilm(Integer filmId) throws DatabaseDAOException;

    /**
	 * Retrieves the number of films in the database.
	 *
	 * @return the number of films in the database.
	 * @throws DatabaseDAOException if an SQL exception occurs during the database operation.
	 */
    Integer getFilmsNumber() throws DatabaseDAOException;

    /**
	 * Retrieves the total number of users in the system.
	 *
	 * @return an Integer representing the total number of users.
	 * @throws DatabaseDAOException if there is an error executing the SQL query or releasing database resources.
	 */
    Integer getUsersNumber() throws DatabaseDAOException;

    /**
	 * Updates the number of likes for a comment with the specified review ID in the database.
	 *
	 * @param userId    the ID of the user who liked the comment.
	 * @param reviewId  the ID of the comment to update the likes for.
	 * @param likes     the new number of likes for the comment.
	 * @throws DatabaseDAOException if there is an error executing the SQL query or releasing database resources.
	 */
    void updateCommentLikes(Integer userId, Integer reviewId, Integer likes) throws DatabaseDAOException;

    /**
	 * Updates the number of dislikes for a comment with the specified review ID in the database.
	 *
	 * @param userId    the ID of the user who disliked the comment.
	 * @param reviewId  the ID of the comment to update the dislikes for.
	 * @param dislikes  the new number of dislikes for the comment.
	 * @throws DatabaseDAOException if there is an error executing the SQL query or releasing database resources.
	 */
    void updateCommentDislikes(Integer userId, Integer reviewId, Integer dislikes) throws DatabaseDAOException;

    /**
	 * Deletes a comment with the specified review ID from the database.
	 *
	 * @param reviewId the ID of the comment to delete.
	 * @throws DatabaseDAOException if there is an error executing the SQL query or releasing database resources.
	 */
    void deleteComment(Integer reviewId) throws DatabaseDAOException;
    
    /**
	 * Updates the details of a comment with the specified review ID in the database.
	 *
	 * @param reviewId the ID of the comment to update.
	 * @param stars    the new stars rating for the comment.
	 * @param comment  the new comment text.
	 * @throws DatabaseDAOException if there is an error executing the SQL query or releasing database resources.
	 */
    void updateComment(Integer reviewId, Integer stars, String comment) throws DatabaseDAOException;

    /**
	 * Adds a new comment to the database for the film with the specified ID.
	 *
	 * @param filmId   the ID of the film to add the comment for.
	 * @param username the username of the user who added the comment.
	 * @param stars    the stars rating for the comment.
	 * @param comment  the comment text.
	 * @throws DatabaseDAOException if there is an error executing the SQL query or releasing database resources.
	 */
    void addComment(Integer filmId, String username, Integer stars, String comment) throws DatabaseDAOException;

    /**
	 * Adds a film with the specified ID to the shopping cart for the user with the specified ID.
	 *
	 * @param filmId the ID of the film to add to the shopping cart.
	 * @param userId the ID of the user who owns the shopping cart.
	 * @throws DatabaseDAOException if there is an error executing the SQL query or releasing database resources.
	 */
    void addCartItem(Integer filmId, Integer userId) throws DatabaseDAOException;

    /**
	 * Removes a film with the specified ID from the shopping cart for the user with the specified ID.
	 *
	 * @param filmId the ID of the film to remove from the shopping cart.
	 * @param userId the ID of the user who owns the shopping cart.
	 * @throws DatabaseDAOException if there is an error executing the SQL query or releasing database resources.
	 */
    void deleteFromCart(Integer filmId, Integer userId) throws DatabaseDAOException;

    /**
	 * Processes the purchase of films in the shopping cart for the user with the specified ID.
	 *
	 * @param userId the ID of the user who owns the shopping cart.
	 * @throws DatabaseDAOException if there is an error executing the SQL query or releasing database resources.
	 */
    void buyFilms(Integer userId) throws DatabaseDAOException;

    /**
	 * Retrieves the status of a film with the specified ID for the user with the specified ID.
	 *
	 * @param filmId the ID of the film to retrieve the status for.
	 * @param userId the ID of the user to retrieve the film status for.
	 * @return an Integer representing the status of the film:
	 *         - 0: Not purchased
	 *         - 1: Purchased
	 *         - -1: Film is not in a cart
	 * @throws DatabaseDAOException if there is an error executing the SQL query or releasing database resources.
	 */
    Integer getFilmStatus(Integer filmId, Integer userId) throws DatabaseDAOException;

    /**
	 * Retrieves the statuses of comments for a film with the specified ID by the user with the specified ID.
	 *
	 * @param filmId the ID of the film to retrieve the comment statuses for.
	 * @param userId the ID of the user to retrieve the comment statuses for.
	 * @return a HashMap<Integer, Integer> containing the mapping of comment IDs to their statuses:
	 *         - Key (Integer): Comment ID
	 *         - Value (Integer): Comment status:
	 *                           - 0: Disliked
	 *                           - 1: Liked
	 *                           - -1: No attitude
	 * @throws DatabaseDAOException if there is an error executing the SQL query or releasing database resources.
	 */
    HashMap<Integer, Integer> getCommentStatuses(Integer filmId, Integer userId) throws DatabaseDAOException;

    /**
	 * Retrieves the status of a comment with the specified ID for the user with the specified ID.
	 *
	 * @param userId the ID of the user to retrieve the comment status for.
	 * @param commentId the ID of the comment to retrieve the status for.
	 * @return an Integer representing the status of the comment:
	 *         - 0: Disliked
	 *         - 1: Liked
	 *         - -1: No attitude
	 * @throws DatabaseDAOException if there is an error executing the SQL query or releasing database resources.
	 */
    Integer getCommentStatus(Integer userId, Integer commentId) throws DatabaseDAOException;

    /**
	 * Sets the status of a comment with the specified ID by the user with the specified ID.
	 *
	 * @param userId    the ID of the user setting the comment status.
	 * @param commentId the ID of the comment to set the status for.
	 * @param status    the desired status to set for the comment:
	 *                  - 0: Disliked
	 *                  - 1: Liked
	 * @throws DatabaseDAOException if there is an error executing the SQL query or releasing database resources.
	 */
    void setCommentStatus(Integer userId, Integer commentId, Integer status) throws DatabaseDAOException;

    /**
	 * Deletes the status of a comment with the specified ID for the user with the specified ID.
	 *
	 * @param userId    the ID of the user to delete the comment status for.
	 * @param commentId the ID of the comment to delete the status for.
	 * @throws DatabaseDAOException if there is an error executing the SQL query or releasing database resources.
	 */
    void deleteCommentStatus(Integer userId, Integer commentId) throws DatabaseDAOException;
}