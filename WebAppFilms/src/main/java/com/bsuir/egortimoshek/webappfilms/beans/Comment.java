package com.bsuir.kirillpastukhou.webappfilms.beans;

/**
 * Class representing a comment.
 * Contains information about the comment's ID, username, text, number of likes, number of dislikes, and star rating.
 */
public class Comment {
    private Integer id; // Comment ID
    private String username; // Username of the commenter
    private String text; // Comment text
    private Integer likes; // Number of likes for the comment
    private Integer dislikes; // Number of dislikes for the comment
    private Integer stars; // Star rating for the comment

    /**
     * Constructor for the Comment class with specified parameters.
     *
     * @param id        Comment ID
     * @param username  Username of the commenter
     * @param text      Comment text
     * @param likes     Number of likes for the comment
     * @param dislikes  Number of dislikes for the comment
     * @param stars     Star rating for the comment
     */
    public Comment(Integer id, String username, String text, Integer likes, Integer dislikes, Integer stars) {
        this.id = id;
        if (username != null) {
            this.username = username;
        } else {
            this.username = "Удалённый пользователь";
        }
        this.setText(text);
        this.likes = likes;
        this.dislikes = dislikes;
        this.stars = stars;
    }

    /**
     * Method to get the comment ID.
     *
     * @return Comment ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * Method to get the username of the commenter.
     *
     * @return Username of the commenter
     */
    public String getUsername() {
        return username;
    }

    /**
     * Method to get the comment text.
     *
     * @return Comment text
     */
    public String getText() {
        return text;
    }

    /**
     * Method to set the comment text.
     *
     * @param text Comment text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Method to get the number of likes for the comment.
     *
     * @return Number of likes for the comment
     */
    public Integer getLikes() {
        return likes;
    }

    /**
     * Method to get the number of dislikes for the comment.
     *
     * @return Number of dislikes for the comment
     */
    public Integer getDislikes() {
        return dislikes;
    }

    /**
     * Method to get the star rating for the comment.
     *
     * @return Star rating for the comment
     */
    public Integer getStars() {
        return stars;
    }
}