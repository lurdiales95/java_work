package com.example.movieapi.repository.mock;


import com.example.movieapi.model.Movie;
import com.example.movieapi.model.MovieSummary;
import com.example.movieapi.repository.MovieRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class MovieRepositoryMock implements MovieRepository {
    private final Map<Integer, Movie> movies = new HashMap<>();
    private final AtomicInteger idCounter = new AtomicInteger(1);

    // Mock data for genre names and rating info for MovieSummary
    private final Map<Integer, String> genreNames = Map.of(
            1, "Action", 2, "Comedy", 3, "Drama", 4, "Horror", 5, "Sci-Fi",
            6, "Romance", 7, "Thriller", 8, "Animation", 9, "Documentary", 10, "Fantasy"
    );

    private final Map<Integer, String> ratingCodes = Map.of(
            1, "G", 2, "PG", 3, "PG-13", 4, "R", 5, "NC-17"
    );

    private final Map<Integer, String> ratingDescriptions = Map.of(
            1, "General Audiences", 2, "Parental Guidance Suggested",
            3, "Parents Strongly Cautioned", 4, "Restricted", 5, "Adults Only"
    );

    public MovieRepositoryMock() {
        initializeSampleData();
    }

    private void initializeSampleData() {
        save(new Movie(null, "The Matrix", LocalDate.of(1999, 3, 31), 1, 4)); // Action, R
        save(new Movie(null, "Inception", LocalDate.of(2010, 7, 16), 5, 3)); // Sci-Fi, PG-13
        save(new Movie(null, "The Shawshank Redemption", LocalDate.of(1994, 9, 23), 3, 4)); // Drama, R
        save(new Movie(null, "Pulp Fiction", LocalDate.of(1994, 10, 14), 7, 4)); // Thriller, R
        save(new Movie(null, "The Dark Knight", LocalDate.of(2008, 7, 18), 1, 3)); // Action, PG-13
        save(new Movie(null, "Forrest Gump", LocalDate.of(1994, 7, 6), 3, 3)); // Drama, PG-13
        save(new Movie(null, "The Godfather", LocalDate.of(1972, 3, 24), 3, 4)); // Drama, R
        save(new Movie(null, "Spirited Away", LocalDate.of(2001, 7, 20), 8, 2)); // Animation, PG
        save(new Movie(null, "Parasite", LocalDate.of(2019, 5, 30), 7, 4)); // Thriller, R
        save(new Movie(null, "Interstellar", LocalDate.of(2014, 11, 7), 5, 3)); // Sci-Fi, PG-13
        save(new Movie(null, "Toy Story", LocalDate.of(1995, 11, 22), 8, 1)); // Animation, G
        save(new Movie(null, "The Avengers", LocalDate.of(2012, 5, 4), 1, 3)); // Action, PG-13
        save(new Movie(null, "Titanic", LocalDate.of(1997, 12, 19), 6, 3)); // Romance, PG-13
        save(new Movie(null, "The Exorcist", LocalDate.of(1973, 12, 26), 4, 4)); // Horror, R
        save(new Movie(null, "Superbad", LocalDate.of(2007, 8, 17), 2, 4)); // Comedy, R
    }

    @Override
    public List<Movie> findAll() {
        return new ArrayList<>(movies.values());
    }

    @Override
    public Optional<Movie> findById(Integer id) {
        return Optional.ofNullable(movies.get(id));
    }

    @Override
    public Movie save(Movie movie) {
        if (movie.getMovieId() == null) {
            movie.setMovieId(idCounter.getAndIncrement());
        }
        movies.put(movie.getMovieId(), movie);
        return movie;
    }

    @Override
    public Movie update(Movie movie) {
        if (movie.getMovieId() == null || !movies.containsKey(movie.getMovieId())) {
            throw new IllegalArgumentException("Movie not found for update");
        }
        movies.put(movie.getMovieId(), movie);
        return movie;
    }

    @Override
    public boolean deleteById(Integer id) {
        return movies.remove(id) != null;
    }

    @Override
    public List<MovieSummary> findAllMovieSummaries() {
        return movies.values().stream()
                .map(this::convertToMovieSummary)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<MovieSummary> findMovieSummaryById(Integer id) {
        return findById(id).map(this::convertToMovieSummary);
    }

    private MovieSummary convertToMovieSummary(Movie movie) {
        String genreName = genreNames.getOrDefault(movie.getGenreId(), "Unknown");
        String ratingCode = ratingCodes.getOrDefault(movie.getRatingId(), "NR");
        String ratingDescription = ratingDescriptions.getOrDefault(movie.getRatingId(), "Not Rated");

        return new MovieSummary(
                movie.getMovieId(),
                movie.getTitle(),
                movie.getReleaseDate(),
                genreName,
                ratingCode,
                ratingDescription
        );
    }
}