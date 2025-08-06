package com.example.movieapi.controller;

import com.example.movieapi.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/genres")
public class GenreController {
    @Autowired
    private GenreRepository genreRepository;



}
