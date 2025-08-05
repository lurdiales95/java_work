package com.example.movieapi.model;

import java.time.LocalDate;

public class MovieSummary {
    private Integer movieId;
    private String title;
    private LocalDate releaseDate;
    private String genreName;
    private String ratingCode;
    private String ratingDescription;

    // Default constructor
    public MovieSummary() {
    }

    // Constructor with parameters
    public MovieSummary(Integer movieId, String title, LocalDate releaseDate,
                        String genreName, String ratingCode, String ratingDescription) {
        this.movieId = movieId;
        this.title = title;
        this.releaseDate = releaseDate;
        this.genreName = genreName;
        this.ratingCode = ratingCode;
        this.ratingDescription = ratingDescription;
    }

    // Getters and Setters
    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    public String getRatingCode() {
        return ratingCode;
    }

    public void setRatingCode(String ratingCode) {
        this.ratingCode = ratingCode;
    }

    public String getRatingDescription() {
        return ratingDescription;
    }

    public void setRatingDescription(String ratingDescription) {
        this.ratingDescription = ratingDescription;
    }

    @Override
    public String toString() {
        return "MovieSummary{" +
                "movieId=" + movieId +
                ", title='" + title + '\'' +
                ", releaseDate=" + releaseDate +
                ", genreName='" + genreName + '\'' +
                ", ratingCode='" + ratingCode + '\'' +
                ", ratingDescription='" + ratingDescription + '\'' +
                '}';
    }
}