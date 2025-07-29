package com.example.jdbc.demo.repository;

import com.example.jdbc.demo.model.Movie;
import com.example.jdbc.demo.model.MovieDetails;

import java.util.List;
import java.util.Optional;

public interface MovieRepository {
    List<Movie> getAll();
    Movie add(Movie movie);
    Movie addSproc(Movie movie);
    Optional<Movie> getById(int movieID);
    Optional<Movie> getByIdSproc(int movieID);
    Movie update(Movie movie);
    boolean delete(int movieID);
    List<MovieDetails> getByGenre(int genreID);
}
