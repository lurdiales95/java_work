package com.example.movieapi.controller;


import com.example.movieapi.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {
    @Autowired
    private RatingRepository ratingRepository;
}
