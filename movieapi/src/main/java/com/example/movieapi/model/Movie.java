package com.example.movieapi.model;

import java.time.LocalDate;

public class Movie {
    private Integer movieId;
    private String title;
    private LocalDate releaseDate;
    private Integer genreId;
    private Integer ratingId;

    // Default constructor
    public Movie() {}

    // Constructor with parameters
    public Movie(Integer movieId, String title, LocalDate releaseDate, Integer genreId, Integer ratingId) {
        this.movieId = movieId;
        this.title = title;
        this.releaseDate = releaseDate;
        this.genreId = genreId;
        this.ratingId = ratingId;
    }

    // Getters and Setters
    public Integer getMovieId() { return movieId; }
    public void setMovieId(Integer movieId) { this.movieId = movieId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public LocalDate getReleaseDate() { return releaseDate; }
    public void setReleaseDate(LocalDate releaseDate) { this.releaseDate = releaseDate; }

    public Integer getGenreId() { return genreId; }
    public void setGenreId(Integer genreId) { this.genreId = genreId; }

    public Integer getRatingId() { return ratingId; }
    public void setRatingId(Integer ratingId) { this.ratingId = ratingId; }

    @Override
    public String toString() {
        return "Movie{" +
                "movieId=" + movieId +
                ", title='" + title + '\'' +
                ", releaseDate=" + releaseDate +
                ", genreId=" + genreId +
                ", ratingId=" + ratingId +
                '}';
    }
}