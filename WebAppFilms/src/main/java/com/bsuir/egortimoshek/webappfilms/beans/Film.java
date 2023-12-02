package com.bsuir.kirillpastukhou.webappfilms.beans;

/**
 * Class representing a film.
 * Contains information about the film's ID, title, description, genre, length, and cost.
 */
public class Film {
    private Integer id; // Film ID
    private String title; // Film title
    private String description; // Film description
    private String genre; // Film genre
    private String length; // Film length
    private Double cost; // Film cost

    /**
     * Constructor for the Film class with specified parameters.
     *
     * @param id          Film ID
     * @param title       Film title
     * @param description Film description
     * @param genre       Film genre
     * @param length      Film length
     * @param cost        Film cost
     */
    public Film(Integer id, String title, String description, String genre, String length, Double cost) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.genre = genre;
        this.length = length;
        this.cost = cost;
    }

    /**
     * Method to get the film ID.
     *
     * @return Film ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * Method to get the film title.
     *
     * @return Film title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Method to get the film description.
     *
     * @return Film description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Method to get the film genre.
     *
     * @return Film genre
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Method to get the film length.
     *
     * @return Film length
     */
    public String getLength() {
        return length;
    }

    /**
     * Method to get the film cost.
     *
     * @return Film cost
     */
    public Double getCost() {
        return cost;
    }
}