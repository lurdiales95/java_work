package com.example.movieapi.controller;


import com.example.movieapi.model.Movie;
import com.example.movieapi.model.MovieSummary;
import com.example.movieapi.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping // function will respond to get request
    public ResponseEntity<List<Movie>> getAllMovies() {
        List<Movie> movies = movieRepository.findAll();
        return ResponseEntity.ok(movies);
    }
    // /api/products/{categoryId}/{subcategoryId}/{productId}
    // @PathVariable Integer categoryID, @Pathvariable Integer SubcategoryId,
    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable Integer id) {
        Optional<Movie> movie = movieRepository.findById(id);
        return movie.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/summaries")
    public ResponseEntity<List<MovieSummary>> getAllMovieSummaries() {
        List<MovieSummary> summaries = movieRepository.findAllMovieSummaries();
        return ResponseEntity.ok(summaries);
    }

    @GetMapping("/{id}/summary")
    public ResponseEntity<MovieSummary> getMovieSummaryById(@PathVariable Integer id) {
        Optional<MovieSummary> summary = movieRepository.findMovieSummaryById(id);
        return summary.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {
        Movie created = movieRepository.save(movie);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);

    }
    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable Integer id, @RequestBody Movie movie) {
        Optional<Movie> existingMovie = movieRepository.findById(id);

        if (existingMovie.isPresent()) {
            movie.setMovieId(id);
            Movie updateMovie = movieRepository.update(movie);
            return ResponseEntity.ok(updateMovie);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Integer id) {
        boolean deleted = movieRepository.deleteById(id);
        return deleted ? ResponseEntity.noContent().build()
            : ResponseEntity.notFound().build();


    }
}
