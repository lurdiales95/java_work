package com.example.movieapi.controller;


import com.example.movieapi.model.Rating;
import com.example.movieapi.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    @Autowired
    private RatingRepository ratingRepository;

    @GetMapping
    public ResponseEntity<List<Rating>> getAllRatings() {
        List<Rating> ratings = ratingRepository.findAll();
        return ResponseEntity.ok(ratings);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rating> getRatingById(@PathVariable Integer id) {
        Optional<Rating> rating = ratingRepository.findById(id);
        return rating.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Rating> createRating(@RequestBody Rating rating) {
        Rating savedRating = ratingRepository.save(rating);
        return ResponseEntity.ok(savedRating);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rating> updateRating(@PathVariable Integer id, @RequestBody Rating rating) {
        Optional<Rating> existingRating = ratingRepository.findById(id);
        if (existingRating.isPresent()) {
            rating.setRatingId(id);
            Rating updatedRating = ratingRepository.update(rating);
            return ResponseEntity.ok(updatedRating);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRating(@PathVariable Integer id) {
        boolean deleted = ratingRepository.deleteById(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}



