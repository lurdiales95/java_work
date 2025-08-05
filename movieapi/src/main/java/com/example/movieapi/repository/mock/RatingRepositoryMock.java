package com.example.movieapi.repository.mock;



import com.example.movieapi.model.Rating;
import com.example.movieapi.repository.RatingRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class RatingRepositoryMock implements RatingRepository {
    private final Map<Integer, Rating> ratings = new HashMap<>();
    private final AtomicInteger idCounter = new AtomicInteger(1);

    public RatingRepositoryMock() {
        initializeSampleData();
    }

    private void initializeSampleData() {
        save(new Rating(null, "G", "General Audiences - All ages admitted"));
        save(new Rating(null, "PG", "Parental Guidance Suggested - Some material may not be suitable for children"));
        save(new Rating(null, "PG-13", "Parents Strongly Cautioned - Some material may be inappropriate for children under 13"));
        save(new Rating(null, "R", "Restricted - Under 17 requires accompanying parent or adult guardian"));
        save(new Rating(null, "NC-17", "Adults Only - No one 17 and under admitted"));
        save(new Rating(null, "NR", "Not Rated - Film has not been submitted for rating"));
        save(new Rating(null, "UR", "Unrated - Contains content not suitable for rating"));
    }

    @Override
    public List<Rating> findAll() {
        return new ArrayList<>(ratings.values());
    }

    @Override
    public Optional<Rating> findById(Integer id) {
        return Optional.ofNullable(ratings.get(id));
    }

    @Override
    public Rating save(Rating rating) {
        if (rating.getRatingId() == null) {
            rating.setRatingId(idCounter.getAndIncrement());
        }
        ratings.put(rating.getRatingId(), rating);
        return rating;
    }

    @Override
    public Rating update(Rating rating) {
        if (rating.getRatingId() == null || !ratings.containsKey(rating.getRatingId())) {
            throw new IllegalArgumentException("Rating not found for update");
        }
        ratings.put(rating.getRatingId(), rating);
        return rating;
    }

    @Override
    public boolean deleteById(Integer id) {
        return ratings.remove(id) != null;
    }

    // Utility method to find rating by code (useful for testing)
    public Optional<Rating> findByRatingCode(String ratingCode) {
        return ratings.values().stream()
                .filter(rating -> rating.getRatingCode().equals(ratingCode))
                .findFirst();
    }
}