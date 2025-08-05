package com.example.movieapi.repository;

import com.example.movieapi.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {
    List<Genre> findAll();
    Optional<Genre> findById(Integer id);
    Genre save(Genre genre);
    Genre update(Genre genre);
    boolean deleteById(Integer id);
}