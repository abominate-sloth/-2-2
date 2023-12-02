package com.bsuir.kirillpastukhou.webappfilms.dao.impl;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.bsuir.kirillpastukhou.webappfilms.beans.Comment;
import com.bsuir.kirillpastukhou.webappfilms.beans.Film;
import com.bsuir.kirillpastukhou.webappfilms.beans.User;
import com.bsuir.kirillpastukhou.webappfilms.connectionpool.ConnectionPool;
import com.bsuir.kirillpastukhou.webappfilms.dao.DBDao;
import com.bsuir.kirillpastukhou.webappfilms.exception.DatabaseDAOException;

/**
 * The MySqlDBDao class is an implementation of the DBDao interface that provides
 * access to a MySQL database for retrieving and modifying various objects related
 * to films, users, and comments.
 * It establishes a connection to the database using a connection pool, and it
 * implements the methods defined in the DBDao interface to perform database operations.
 * This class is responsible for executing SQL queries and handling any resulting exceptions
 * by throwing a DBDaoException.
 *
 * The methods implemented in this class allow retrieving information such as login and registration of users,
 * fetching comments related to a film, updating user data, fetching films, adding, updating, and deleting films,
 * and managing comment statuses.
 *
 * @see DBDao
 * @see com.bsuir.kirillpastukhou.webappfilms.beans.Comment
 * @see com.bsuir.kirillpastukhou.webappfilms.beans.Film
 * @see com.bsuir.kirillpastukhou.webappfilms.beans.User
 * @see com.bsuir.kirillpastukhou.webappfilms.exception.DatabaseDAOException
 */
public final class MySqlDBDao implements DBDao {
	private final static MySqlDBDao instance = new MySqlDBDao();
	private final static ConnectionPool connectionPool = ConnectionPool.getInstance();
	
	/**
	 * Returns the singleton instance of the MySqlDBDao class.
	 *
	 * @return the DBDao singleton instance.
	 */
	public static DBDao getInstance() {
	    return instance;
	}

	/**
	 * Attempts to sign in a user with the given login and password hash.
	 *
	 * @param login         the login of the user.
	 * @param password_hash the hashed password of the user.
	 * @return the User object representing the signed-in user, if the sign-in is successful.
	 * @throws DatabaseDAOException if an SQL exception occurs during the database operation or if the sign-in credentials are invalid.
	 */
	@Override
	public User signIn(String login, String password_hash) throws DatabaseDAOException {
		String query = "SELECT id, login, is_admin, discount FROM user WHERE login = ? AND password_hash = ?";
		
		Connection connection = null;
    	PreparedStatement statement = null;
        ResultSet result = null;
		try {
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(query);
            statement.setString(1, login);
            statement.setString(2, password_hash);
            result = statement.executeQuery();
            if (!result.next()) {
            	throw new DatabaseDAOException();
            } else {
            	return new User(result.getInt("id"), result.getString("login"), result.getBoolean("is_admin"), result.getDouble("discount"));
            }
        } catch (SQLException e) {
            throw new DatabaseDAOException(e.getMessage());
        } finally {
        	try {
        		connectionPool.releaseConnection(connection);
        		if (statement != null) statement.close();
        		if (result != null) result.close();
        	} catch (SQLException e) {
        		throw new DatabaseDAOException(e.getMessage());
        	}
        }
	}

	/**
	 * Registers a new user with the given login and hashed password.
	 *
	 * @param login         the login of the user.
	 * @param password_hash the hashed password of the user.
	 * @return the User object representing the registered user, if the registration is successful.
	 * @throws DatabaseDAOException if an SQL exception occurs during the database operation or if the registration fails.
	 */
	@Override
	public User registration(String login, String password_hash) throws DatabaseDAOException {
	    String query_insert = "INSERT INTO user (login, password_hash, is_admin, discount) VALUES (?, ?, 0, 0.0)";
	    String query_select = "SELECT id FROM user WHERE login = ?";

	    Connection connection = null;
	    PreparedStatement statement_insert = null;
	    PreparedStatement statement_select = null;
	    ResultSet result = null;
	    try {
	        connection = connectionPool.getConnection();
	        statement_insert = connection.prepareStatement(query_insert);
	        statement_insert.setString(1, login);
	        statement_insert.setString(2, password_hash);
	        try {
	            statement_insert.executeUpdate();
	        } catch (SQLException e) {
	            throw new DatabaseDAOException(e.getMessage());
	        }

	        statement_select = connection.prepareStatement(query_select);
	        statement_select.setString(1, login);
	        result = statement_select.executeQuery();
	        result.next();
	        return new User(result.getInt("id"), login, false);
	    } catch (SQLException e) {
	        throw new DatabaseDAOException(e.getMessage());
	    } finally {
	        try {
	            connectionPool.releaseConnection(connection);
	            if (statement_insert != null) statement_insert.close();
	            if (statement_select != null) statement_select.close();
	            if (result != null) result.close();
	        } catch (SQLException e) {
	            throw new DatabaseDAOException(e.getMessage());
	        }
	    }
	}

	/**
	 * Retrieves a list of films for a specified page.
	 *
	 * @param currentPage the page number of the films to retrieve.
	 * @return an ArrayList of Film objects representing the films on the specified page.
	 * @throws DatabaseDAOException if an SQL exception occurs during the database operation.
	 */
	@Override
	public ArrayList<Film> getFilms(Integer currentPage) throws DatabaseDAOException {
	    final Integer filmsOnPage = 5;
	    String query = "SELECT * FROM film ORDER BY id LIMIT ? OFFSET ?";
	    Integer offset = (currentPage - 1) * filmsOnPage;

	    Connection connection = null;
	    PreparedStatement statement = null;
	    ResultSet result = null;
	    try {
	        connection = connectionPool.getConnection();
	        statement = connection.prepareStatement(query);
	        statement.setInt(1, filmsOnPage);
	        statement.setInt(2, offset);
	        result = statement.executeQuery();

	        ArrayList<Film> filmsList = new ArrayList<Film>();
	        while (result.next()) {
	            filmsList.add(new Film(
	                    result.getInt("id"),
	                    result.getString("title"),
	                    result.getString("description"),
	                    result.getString("genre"),
	                    result.getString("length"),
	                    result.getDouble("cost")));
	        }

	        return filmsList;
	    } catch (SQLException e) {
	        throw new DatabaseDAOException(e.getMessage());
	    } finally {
	        try {
	            connectionPool.releaseConnection(connection);
	            if (statement != null) statement.close();
	            if (result != null) result.close();
	        } catch (SQLException e) {
	            throw new DatabaseDAOException(e.getMessage());
	        }
	    }
	}
	
	/**
	 * Retrieves a list of films from the cart for a specified user.
	 *
	 * @param userId the ID of the user.
	 * @return an ArrayList of Film objects representing the films in the user's cart.
	 * @throws DatabaseDAOException if an SQL exception occurs during the database operation.
	 */
	@Override
	public ArrayList<Film> getFilmsFromCart(Integer userId) throws DatabaseDAOException {
	    String query = "SELECT * FROM cartitem WHERE id_user = ? AND active = 0 ORDER BY id_film";

	    Connection connection = null;
	    PreparedStatement statement = null;
	    ResultSet result = null, film_result = null;
	    try {
	        connection = connectionPool.getConnection();
	        statement = connection.prepareStatement(query);
	        statement.setInt(1, userId);
	        result = statement.executeQuery();

	        ArrayList<Film> filmsList = new ArrayList<Film>();
	        while (result.next()) {
	            filmsList.add(getFilm(result.getInt("id_film")));
	        }

	        return filmsList;
	    } catch (SQLException e) {
	        throw new DatabaseDAOException(e.getMessage());
	    } finally {
	        try {
	            connectionPool.releaseConnection(connection);
	            if (statement != null) statement.close();
	            if (result != null) result.close();
	            if (film_result != null) film_result.close();
	        } catch (SQLException e) {
	            throw new DatabaseDAOException(e.getMessage());
	        }
	    }
	}
	
	/**
	 * Retrieves a film with the specified ID.
	 *
	 * @param filmId the ID of the film to retrieve.
	 * @return a Film object representing the film with the specified ID.
	 * @throws DatabaseDAOException if an SQL exception occurs during the database operation.
	 */
	@Override
	public Film getFilm(Integer filmId) throws DatabaseDAOException {
	    String query = "SELECT * FROM film WHERE id = ?";

	    Connection connection = null;
	    PreparedStatement statement = null;
	    ResultSet result = null;
	    try {
	        connection = connectionPool.getConnection();
	        statement = connection.prepareStatement(query);
	        statement.setInt(1, filmId);
	        result = statement.executeQuery();
	        result.next();
	        Film film = new Film(
	                result.getInt("id"),
	                result.getString("title"),
	                result.getString("description"),
	                result.getString("genre"),
	                result.getString("length"),
	                result.getDouble("cost"));
	        return film;
	    } catch (SQLException e) {
	        throw new DatabaseDAOException(e.getMessage());
	    } finally {
	        try {
	            connectionPool.releaseConnection(connection);
	            if (statement != null) statement.close();
	            if (result != null) result.close();
	        } catch (SQLException e) {
	            throw new DatabaseDAOException(e.getMessage());
	        }
	    }
	}
	
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
	@Override
	public void addFilm(String name, String description, String genre, String length, Double cost)
	        throws DatabaseDAOException {
	    String query = "INSERT INTO film (title, description, genre, length, cost) VALUES (?, ?, ?, ?, ?)";

	    Connection connection = null;
	    PreparedStatement statement = null;
	    try {
	        connection = connectionPool.getConnection();
	        statement = connection.prepareStatement(query);
	        statement.setString(1, name);
	        statement.setString(2, description);
	        statement.setString(3, genre);
	        statement.setString(4, length);
	        statement.setDouble(5, cost);
	        statement.executeUpdate();
	    } catch (SQLException e) {
	        throw new DatabaseDAOException(e.getMessage());
	    } finally {
	        try {
	            connectionPool.releaseConnection(connection);
	            if (statement != null) statement.close();
	        } catch (SQLException e) {
	            throw new DatabaseDAOException(e.getMessage());
	        }
	    }
	}
	
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
	@Override
	public void updateFilm(Integer filmId, String name, String description, String genre, String length, Double cost)
			throws DatabaseDAOException {
		String query = "UPDATE film SET title = ?, description = ?, genre = ?, length = ?, cost = ? WHERE id = ?";
		
		Connection connection = null;
    	PreparedStatement statement = null;
        try {
        	connection = connectionPool.getConnection();
        	statement = connection.prepareStatement(query);
        	statement.setInt(6, filmId);
        	statement.setString(1, name);
        	statement.setString(2, description);
        	statement.setString(3, genre);
        	statement.setString(4, length);
        	statement.setDouble(5, cost);
        	statement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseDAOException(e.getMessage());
        } finally {
        	try {
        		connectionPool.releaseConnection(connection);
        		if (statement != null) statement.close();
        	} catch (SQLException e) {
        		throw new DatabaseDAOException(e.getMessage());
        	}
        }
	}
	
	/**
	 * Deletes a film from the database with the specified ID.
	 *
	 * @param filmId the ID of the film to delete.
	 * @throws DatabaseDAOException if there is an error executing the SQL query or releasing database resources.
	 */
	@Override
	public void deleteFilm(Integer filmId) throws DatabaseDAOException {
		String query = "DELETE FROM film WHERE id = ?";
		
		Connection connection = null;
    	PreparedStatement statement = null;
    	try {
    		connection = connectionPool.getConnection();
        	statement = connection.prepareStatement(query);
        	statement.setInt(1, filmId);
        	statement.executeUpdate();
    	} catch (SQLException e) {
            throw new DatabaseDAOException(e.getMessage());
        } finally {
        	try {
        		connectionPool.releaseConnection(connection);
        		if (statement != null) statement.close();
        	} catch (SQLException e) {
        		throw new DatabaseDAOException(e.getMessage());
        	}
        }
	}

	/**
	 * Retrieves the number of films in the database.
	 *
	 * @return the number of films in the database.
	 * @throws DatabaseDAOException if an SQL exception occurs during the database operation.
	 */
	@Override
	public Integer getFilmsNumber() throws DatabaseDAOException {
		String query = "SELECT COUNT(*) FROM film";
		
		Connection connection = null;
		Statement statement = null;
		ResultSet result = null;
		try {
            connection = connectionPool.getConnection();
            statement = connection.createStatement();
            result = statement.executeQuery(query);
            result.next();
            return result.getInt(1);
        } catch (SQLException e) {
            throw new DatabaseDAOException(e.getMessage());
        } finally {
        	try {
        		connectionPool.releaseConnection(connection);
        		if (statement != null) statement.close();
        		if (result != null) result.close();
        	} catch (SQLException e) {
        		throw new DatabaseDAOException(e.getMessage());
        	}
        }
	}

	/**
	 * Retrieves the comments for a film with the specified ID from the database.
	 *
	 * @param filmId the ID of the film to retrieve comments for.
	 * @return an ArrayList of Comment objects representing the comments for the film.
	 * @throws DatabaseDAOException if there is an error executing the SQL query or releasing database resources.
	 */
	@Override
	public ArrayList<Comment> getComments(Integer filmId) throws DatabaseDAOException {
		String query = "SELECT * FROM comment WHERE id_film = ?";
		
		Connection connection = null;
    	PreparedStatement statement = null;
        ResultSet result = null;
        try {
        	connection = connectionPool.getConnection();
        	statement = connection.prepareStatement(query);
        	statement.setInt(1, filmId);
        	result = statement.executeQuery();
        	
        	ArrayList<Comment> reviewsList = new ArrayList<Comment>();
        	while (result.next()) {
        		reviewsList.add(new Comment(
        				result.getInt("id"), 
        				result.getString("username"), 
        				result.getString("text"), 
        				result.getInt("likes"),
        				result.getInt("dislikes"),
        				result.getInt("stars")));
        	}
        	
        	return reviewsList;
        } catch (SQLException e) {
            throw new DatabaseDAOException(e.getMessage());
        } finally {
        	try {
        		connectionPool.releaseConnection(connection);
        		if (statement != null) statement.close();
        		if (result != null) result.close();
        	} catch (SQLException e) {
        		throw new DatabaseDAOException(e.getMessage());
        	}
        }
	}

	/**
	 * Updates the number of likes for a comment with the specified review ID in the database.
	 *
	 * @param userId    the ID of the user who liked the comment.
	 * @param reviewId  the ID of the comment to update the likes for.
	 * @param likes     the new number of likes for the comment.
	 * @throws DatabaseDAOException if there is an error executing the SQL query or releasing database resources.
	 */
	@Override
	public void updateCommentLikes(Integer userId, Integer reviewId, Integer likes) throws DatabaseDAOException {
		String query = "UPDATE comment SET likes = ? WHERE id = ?";
		Integer status = getCommentStatus(userId, reviewId);
		if (status == 1) {
			likes--;
			deleteCommentStatus(userId, reviewId);
		} else {
			likes++;
			setCommentStatus(userId, reviewId, 1);
		}
		
		Connection connection = null;
    	PreparedStatement statement = null;
    	try {
    		connection = connectionPool.getConnection();
        	statement = connection.prepareStatement(query);
        	statement.setInt(1, likes);
        	statement.setInt(2, reviewId);
        	statement.executeUpdate();
    	} catch (SQLException e) {
            throw new DatabaseDAOException(e.getMessage());
        } finally {
        	try {
        		connectionPool.releaseConnection(connection);
        		if (statement != null) statement.close();
        	} catch (SQLException e) {
        		throw new DatabaseDAOException(e.getMessage());
        	}
        }
	}
	
	/**
	 * Updates the number of dislikes for a comment with the specified review ID in the database.
	 *
	 * @param userId    the ID of the user who disliked the comment.
	 * @param reviewId  the ID of the comment to update the dislikes for.
	 * @param dislikes  the new number of dislikes for the comment.
	 * @throws DatabaseDAOException if there is an error executing the SQL query or releasing database resources.
	 */
	@Override
	public void updateCommentDislikes(Integer userId, Integer reviewId, Integer dislikes) throws DatabaseDAOException {
		String query = "UPDATE comment SET dislikes = ? WHERE id = ?";
		Integer status = getCommentStatus(userId, reviewId);
		if (status == 0) {
			dislikes--;
			deleteCommentStatus(userId, reviewId);
		} else {
			dislikes++;
			setCommentStatus(userId, reviewId, 0);
		}
		
		Connection connection = null;
    	PreparedStatement statement = null;
    	try {
    		connection = connectionPool.getConnection();
        	statement = connection.prepareStatement(query);
        	statement.setInt(1, dislikes);
        	statement.setInt(2, reviewId);
        	statement.executeUpdate();
    	} catch (SQLException e) {
            throw new DatabaseDAOException(e.getMessage());
        } finally {
        	try {
        		connectionPool.releaseConnection(connection);
        		if (statement != null) statement.close();
        	} catch (SQLException e) {
        		throw new DatabaseDAOException(e.getMessage());
        	}
        }
	}

	/**
	 * Deletes a comment with the specified review ID from the database.
	 *
	 * @param reviewId the ID of the comment to delete.
	 * @throws DatabaseDAOException if there is an error executing the SQL query or releasing database resources.
	 */
	@Override
	public void deleteComment(Integer reviewId) throws DatabaseDAOException {
		String query = "DELETE FROM comment WHERE id = ?";
		
		Connection connection = null;
    	PreparedStatement statement = null;
    	try {
    		connection = connectionPool.getConnection();
        	statement = connection.prepareStatement(query);
        	statement.setInt(1, reviewId);
        	statement.executeUpdate();
    	} catch (SQLException e) {
            throw new DatabaseDAOException(e.getMessage());
        } finally {
        	try {
        		connectionPool.releaseConnection(connection);
        		if (statement != null) statement.close();
        	} catch (SQLException e) {
        		throw new DatabaseDAOException(e.getMessage());
        	}
        }
	}

	/**
	 * Updates the details of a comment with the specified review ID in the database.
	 *
	 * @param reviewId the ID of the comment to update.
	 * @param stars    the new stars rating for the comment.
	 * @param comment  the new comment text.
	 * @throws DatabaseDAOException if there is an error executing the SQL query or releasing database resources.
	 */
	@Override
	public void updateComment(Integer reviewId, Integer stars, String comment) throws DatabaseDAOException {
		String query = "UPDATE comment SET stars = ?, text = ? WHERE id = ?";
		
		Connection connection = null;
    	PreparedStatement statement = null;
    	try {
    		connection = connectionPool.getConnection();
        	statement = connection.prepareStatement(query);
        	statement.setInt(1, stars);
        	statement.setString(2, comment);
        	statement.setInt(3, reviewId);
        	statement.executeUpdate();
    	} catch (SQLException e) {
            throw new DatabaseDAOException(e.getMessage());
        } finally {
        	try {
        		connectionPool.releaseConnection(connection);
        		if (statement != null) statement.close();
        	} catch (SQLException e) {
        		throw new DatabaseDAOException(e.getMessage());
        	}
        }
	}
	
	/**
	 * Adds a new comment to the database for the film with the specified ID.
	 *
	 * @param filmId   the ID of the film to add the comment for.
	 * @param username the username of the user who added the comment.
	 * @param stars    the stars rating for the comment.
	 * @param comment  the comment text.
	 * @throws DatabaseDAOException if there is an error executing the SQL query or releasing database resources.
	 */
	@Override
	public void addComment(Integer filmId, String username, Integer stars, String comment) throws DatabaseDAOException {
		String query = "INSERT INTO comment (id_film, username, text, likes, dislikes, stars) VALUES (?, ?, ?, 0, 0, ?)";
		
		Connection connection = null;
    	PreparedStatement statement = null;
    	try {
    		connection = connectionPool.getConnection();
        	statement = connection.prepareStatement(query);
        	statement.setInt(1, filmId);
        	statement.setString(2, username);
        	statement.setString(3, comment);
        	statement.setInt(4, stars);
        	statement.executeUpdate();
    	} catch (SQLException e) {
            throw new DatabaseDAOException(e.getMessage());
        } finally {
        	try {
        		connectionPool.releaseConnection(connection);
        		if (statement != null) statement.close();
        	} catch (SQLException e) {
        		throw new DatabaseDAOException(e.getMessage());
        	}
        }
	}

	/**
	 * Adds a film with the specified ID to the shopping cart for the user with the specified ID.
	 *
	 * @param filmId the ID of the film to add to the shopping cart.
	 * @param userId the ID of the user who owns the shopping cart.
	 * @throws DatabaseDAOException if there is an error executing the SQL query or releasing database resources.
	 */
	@Override
	public void addCartItem(Integer filmId, Integer userId) throws DatabaseDAOException {
		String query = "INSERT INTO cartitem (id_film, id_user, active) VALUES (?, ?, 0)";
		
		Connection connection = null;
    	PreparedStatement statement = null;
    	try {
    		connection = connectionPool.getConnection();
        	statement = connection.prepareStatement(query);
        	statement.setInt(1, filmId);
        	statement.setInt(2, userId);
        	statement.executeUpdate();
    	} catch (SQLException e) {
            throw new DatabaseDAOException(e.getMessage());
        } finally {
        	try {
        		connectionPool.releaseConnection(connection);
        		if (statement != null) statement.close();
        	} catch (SQLException e) {
        		throw new DatabaseDAOException(e.getMessage());
        	}
        }
	}

	/**
	 * Removes a film with the specified ID from the shopping cart for the user with the specified ID.
	 *
	 * @param filmId the ID of the film to remove from the shopping cart.
	 * @param userId the ID of the user who owns the shopping cart.
	 * @throws DatabaseDAOException if there is an error executing the SQL query or releasing database resources.
	 */
	@Override
	public void deleteFromCart(Integer filmId, Integer userId) throws DatabaseDAOException {
		String query = "DELETE FROM cartitem WHERE id_film = ? AND id_user = ?";
		
		Connection connection = null;
    	PreparedStatement statement = null;
    	try {
    		connection = connectionPool.getConnection();
        	statement = connection.prepareStatement(query);
        	statement.setInt(1, filmId);
        	statement.setInt(2, userId);
        	statement.executeUpdate();
    	} catch (SQLException e) {
            throw new DatabaseDAOException(e.getMessage());
        } finally {
        	try {
        		connectionPool.releaseConnection(connection);
        		if (statement != null) statement.close();
        	} catch (SQLException e) {
        		throw new DatabaseDAOException(e.getMessage());
        	}
        }
	}

	/**
	 * Processes the purchase of films in the shopping cart for the user with the specified ID.
	 *
	 * @param userId the ID of the user who owns the shopping cart.
	 * @throws DatabaseDAOException if there is an error executing the SQL query or releasing database resources.
	 */
	@Override
	public void buyFilms(Integer userId) throws DatabaseDAOException {

		String query = "UPDATE cartitem SET active = 1 WHERE id_user = ? AND active = 0";
		
		Connection connection = null;
    	PreparedStatement statement = null;
        try {
        	connection = connectionPool.getConnection();
        	statement = connection.prepareStatement(query);
        	statement.setInt(1, userId);
        	statement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseDAOException(e.getMessage());
        } finally {
        	try {
        		connectionPool.releaseConnection(connection);
        		if (statement != null) statement.close();
        	} catch (SQLException e) {
        		throw new DatabaseDAOException(e.getMessage());
        	}
        }
	}

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
	@Override
	public Integer getFilmStatus(Integer filmId, Integer userId) throws DatabaseDAOException {
		String query = "SELECT active FROM cartitem WHERE id_user = ? AND id_film = ?";
		
		Connection connection = null;
    	PreparedStatement statement = null;
        ResultSet result = null;
        try {
        	connection = connectionPool.getConnection();
        	statement = connection.prepareStatement(query);
        	statement.setInt(1, userId);
        	statement.setInt(2, filmId);
        	result = statement.executeQuery();
        	
        	if (result.next()) {
        		return result.getInt(1);
        	} else {
        		return -1;
        	}
        } catch (SQLException e) {
                throw new DatabaseDAOException(e.getMessage());
        } finally {
            try {
            	connectionPool.releaseConnection(connection);
            	if (statement != null) statement.close();
           		if (result != null) result.close();
           	} catch (SQLException e) {
           		throw new DatabaseDAOException(e.getMessage());
           	}
        }
	}

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
	@Override
	public HashMap<Integer, Integer> getCommentStatuses(Integer filmId, Integer userId) throws DatabaseDAOException {
		ArrayList<Comment> comments = getComments(filmId);
		HashMap<Integer, Integer> commentStatus = new HashMap<Integer, Integer>();
		for (Comment comment : comments) {
			Integer commentId = comment.getId(); 
			commentStatus.put(commentId, getCommentStatus(userId, commentId));
		}
		return commentStatus;
	}

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
	@Override
	public Integer getCommentStatus(Integer userId, Integer commentId) throws DatabaseDAOException {
		String query = "SELECT status FROM commentstatus WHERE id_user = ? AND id_comment = ?";
		
		Connection connection = null;
    	PreparedStatement statement = null;
        ResultSet result = null;
        try {
        	connection = connectionPool.getConnection();
        	statement = connection.prepareStatement(query);
        	statement.setInt(1, userId);
        	statement.setInt(2, commentId);
        	result = statement.executeQuery();
        	
        	if (result.next()) {
        		return result.getInt(1);
        	} else {
        		return -1;
        	}
        } catch (SQLException e) {
                throw new DatabaseDAOException(e.getMessage());
        } finally {
            try {
            	connectionPool.releaseConnection(connection);
            	if (statement != null) statement.close();
           		if (result != null) result.close();
           	} catch (SQLException e) {
           		throw new DatabaseDAOException(e.getMessage());
           	}
        }
	}

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
	@Override
	public void setCommentStatus(Integer userId, Integer commentId, Integer status) throws DatabaseDAOException {
		String query = "INSERT INTO commentstatus (id_user, id_comment, status) VALUES (?, ?, ?)";
		
		Connection connection = null;
    	PreparedStatement statement = null;
        ResultSet result = null;
        try {
        	connection = connectionPool.getConnection();
        	statement = connection.prepareStatement(query);
        	statement.setInt(1, userId);
        	statement.setInt(2, commentId);
        	statement.setInt(3, status);
        	statement.executeUpdate();
        } catch (SQLException e) {
                throw new DatabaseDAOException(e.getMessage());
        } finally {
            try {
            	connectionPool.releaseConnection(connection);
            	if (statement != null) statement.close();
           		if (result != null) result.close();
           	} catch (SQLException e) {
           		throw new DatabaseDAOException(e.getMessage());
           	}
        }
	}

	/**
	 * Deletes the status of a comment with the specified ID for the user with the specified ID.
	 *
	 * @param userId    the ID of the user to delete the comment status for.
	 * @param commentId the ID of the comment to delete the status for.
	 * @throws DatabaseDAOException if there is an error executing the SQL query or releasing database resources.
	 */
	@Override
	public void deleteCommentStatus(Integer userId, Integer commentId) throws DatabaseDAOException {
		String query = "DELETE FROM commentstatus WHERE id_user = ? AND id_comment = ?";
		
		Connection connection = null;
    	PreparedStatement statement = null;
        ResultSet result = null;
        try {
        	connection = connectionPool.getConnection();
        	statement = connection.prepareStatement(query);
        	statement.setInt(1, userId);
        	statement.setInt(2, commentId);
        	statement.executeUpdate();
        } catch (SQLException e) {
                throw new DatabaseDAOException(e.getMessage());
        } finally {
            try {
            	connectionPool.releaseConnection(connection);
            	if (statement != null) statement.close();
           		if (result != null) result.close();
           	} catch (SQLException e) {
           		throw new DatabaseDAOException(e.getMessage());
           	}
        }
	}

	/**
	 * Retrieves a list of User objects for the specified page of users.
	 *
	 * @param currentPageUser the index of the current page of users to retrieve.
	 * @return an ArrayList of User objects representing the users on the current page.
	 * @throws DatabaseDAOException if there is an error executing the SQL query or releasing database resources.
	 */
	@Override
	public ArrayList<User> getUsers(Integer currentPageUser) throws	 DatabaseDAOException {
		final Integer usersOnPage = 5;
		String query = "SELECT * FROM user ORDER BY id LIMIT ? OFFSET ?";
		Integer offset = (currentPageUser - 1) * usersOnPage;
		
		Connection connection = null;
    	PreparedStatement statement = null;
        ResultSet result = null;
        try {
        	connection = connectionPool.getConnection();
        	statement = connection.prepareStatement(query);
        	statement.setInt(1, usersOnPage);
        	statement.setInt(2, offset);
        	result = statement.executeQuery();
        	
        	ArrayList<User> userList = new ArrayList<User>();
        	while (result.next()) {
        		userList.add(new User(
        				result.getInt("id"), 
        				result.getString("login"), 
        				result.getBoolean("is_admin"), 
        				result.getDouble("discount")));
        	}
        	
        	return userList;
        } catch (SQLException e) {
            throw new DatabaseDAOException(e.getMessage());
        } finally {
        	try {
        		connectionPool.releaseConnection(connection);
        		if (statement != null) statement.close();
        		if (result != null) result.close();
        	} catch (SQLException e) {
        		throw new DatabaseDAOException(e.getMessage());
        	}
        }
	}

	/**
	 * Retrieves the total number of users in the system.
	 *
	 * @return an Integer representing the total number of users.
	 * @throws DatabaseDAOException if there is an error executing the SQL query or releasing database resources.
	 */
	@Override
	public Integer getUsersNumber() throws DatabaseDAOException {
		String query = "SELECT COUNT(*) FROM user";
		
		Connection connection = null;
		Statement statement = null;
		ResultSet result = null;
		try {
            connection = connectionPool.getConnection();
            statement = connection.createStatement();
            result = statement.executeQuery(query);
            result.next();
            return result.getInt(1);
        } catch (SQLException e) {
            throw new DatabaseDAOException(e.getMessage());
        } finally {
        	try {
        		connectionPool.releaseConnection(connection);
        		if (statement != null) statement.close();
        		if (result != null) result.close();
        	} catch (SQLException e) {
        		throw new DatabaseDAOException(e.getMessage());
        	}
        }
	}

	/**
	 * Retrieves the User object with the specified ID.
	 *
	 * @param userId the ID of the user to retrieve.
	 * @return the User object corresponding to the specified ID, or null if the user is not found.
	 * @throws DatabaseDAOException if there is an error executing the SQL query or releasing database resources.
	 */
	@Override
	public User getUser(Integer userId) throws DatabaseDAOException {
		String query = "SELECT * FROM user WHERE id = ?";
		
		Connection connection = null;
    	PreparedStatement statement = null;
        ResultSet result = null;
        try {
        	connection = connectionPool.getConnection();
        	statement = connection.prepareStatement(query);
        	statement.setInt(1, userId);
        	result = statement.executeQuery();
        	result.next();
        	User user = new User(
        			result.getInt("id"), 
    				result.getString("login"), 
    				result.getBoolean("is_admin"), 
    				result.getDouble("discount"));
        	return user;
        } catch (SQLException e) {
            throw new DatabaseDAOException(e.getMessage());
        } finally {
        	try {
        		connectionPool.releaseConnection(connection);
        		if (statement != null) statement.close();
        		if (result != null) result.close();
        	} catch (SQLException e) {
        		throw new DatabaseDAOException(e.getMessage());
        	}
        }
	}

	/**
	 * Updates the sale value for the user with the specified ID.
	 *
	 * @param userId  the ID of the user to update the sale value for.
	 * @param sale    the new sale value to set for the user.
	 * @throws DatabaseDAOException if there is an error executing the SQL query or releasing database resources.
	 */
	@Override
	public void updateUserSale(Integer userId, Double sale) throws DatabaseDAOException {
		String query = "UPDATE user SET discount = ? WHERE id = ?";
		
		Connection connection = null;
    	PreparedStatement statement = null;
        try {
        	connection = connectionPool.getConnection();
        	statement = connection.prepareStatement(query);
        	statement.setDouble(1, sale);
        	statement.setInt(2, userId);
        	statement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseDAOException(e.getMessage());
        } finally {
        	try {
        		connectionPool.releaseConnection(connection);
        		if (statement != null) statement.close();
        	} catch (SQLException e) {
        		throw new DatabaseDAOException(e.getMessage());
        	}
        }
	}

	@Override
	public void deleteUser(String username) throws DatabaseDAOException {
		String query = "DELETE FROM user WHERE login = ?";
		
		Connection connection = null;
    	PreparedStatement statement = null;
        try {
        	connection = connectionPool.getConnection();
        	statement = connection.prepareStatement(query);
        	statement.setString(1, username);
        	statement.executeUpdate();
        	
        } catch (SQLException e) {
            throw new DatabaseDAOException(e.getMessage());
        } finally {
        	try {
        		connectionPool.releaseConnection(connection);
        		if (statement != null) statement.close();	
        	} catch (SQLException e) {
        		throw new DatabaseDAOException(e.getMessage());
        	}
        }
		
	}
}
