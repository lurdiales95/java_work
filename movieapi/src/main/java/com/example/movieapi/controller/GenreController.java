package com.example.movieapi.controller;

import com.example.movieapi.model.Genre;
import com.example.movieapi.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/genres")
public class GenreController {
    @Autowired
    private GenreRepository genreRepository;

    @GetMapping
    public ResponseEntity<List<Genre>> getAllGenres() {
        List<Genre> genres = genreRepository.findAll();
        return ResponseEntity.ok(genres);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Genre> getGenreById(@PathVariable Integer id) {
        Optional<Genre> genre = genreRepository.findById(id);
        return genre.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping
    public ResponseEntity<Genre> createGenre(@RequestBody Genre genre) {
        Genre savedGenre = genreRepository.save(genre);
        return ResponseEntity.ok(savedGenre);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Genre> updateGenre(@PathVariable Integer id, @RequestBody Genre genre) {
        Optional<Genre> existingGenre = genreRepository.findById(id);
        if (existingGenre.isPresent()) {
            genre.setGenreId(id); // Using setId instead of setGenreId
            Genre updatedGenre = genreRepository.update(genre);
            return ResponseEntity.ok(updatedGenre);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/id")
    public ResponseEntity<Void> deleteGenre(@PathVariable Integer id) {
        boolean deleted = genreRepository.deleteById(id);
        if (deleted) {
            return  ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
