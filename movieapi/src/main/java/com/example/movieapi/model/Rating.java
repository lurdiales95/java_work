package com.example.movieapi.model;


public class Rating {
    private Integer ratingId;
    private String ratingCode;
    private String description;

    // Default constructor
    public Rating() {}

    // Constructor with parameters
    public Rating(Integer ratingId, String ratingCode, String description) {
        this.ratingId = ratingId;
        this.ratingCode = ratingCode;
        this.description = description;
    }

    // Getters and Setters
    public Integer getRatingId() { return ratingId; }
    public void setRatingId(Integer ratingId) { this.ratingId = ratingId; }

    public String getRatingCode() { return ratingCode; }
    public void setRatingCode(String ratingCode) { this.ratingCode = ratingCode; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    @Override
    public String toString() {
        return "Rating{" +
                "ratingId=" + ratingId +
                ", ratingCode='" + ratingCode + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}