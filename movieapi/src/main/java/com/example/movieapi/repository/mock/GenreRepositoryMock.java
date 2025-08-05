package com.example.movieapi.repository.mock;
import com.example.movieapi.model.Genre;
import com.example.movieapi.repository.GenreRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;


@Repository
public class GenreRepositoryMock implements GenreRepository {
    private final Map<Integer, Genre> genres = new HashMap<>();
    private final AtomicInteger idCounter = new AtomicInteger(1);

    public GenreRepositoryMock() {
        initializeSampleData();
    }

    private void initializeSampleData() {
        save(new Genre(null, "Action"));
        save(new Genre(null, "Comedy"));
        save(new Genre(null, "Drama"));
        save(new Genre(null, "Horror"));
        save(new Genre(null, "Sci-Fi"));
        save(new Genre(null, "Romance"));
        save(new Genre(null, "Thriller"));
        save(new Genre(null, "Animation"));
        save(new Genre(null, "Documentary"));
        save(new Genre(null, "Fantasy"));
    }

    @Override
    public List<Genre> findAll() {
        return new ArrayList<>(genres.values());
    }

    @Override
    public Optional<Genre> findById(Integer id) {
        return Optional.ofNullable(genres.get(id));
    }

    @Override
    public Genre save(Genre genre) {
        if (genre.getGenreId() == null) {
            genre.setGenreId(idCounter.getAndIncrement());
        }
        genres.put(genre.getGenreId(), genre);
        return genre;
    }

    @Override
    public Genre update(Genre genre) {
        if (genre.getGenreId() == null || !genres.containsKey(genre.getGenreId())) {
            throw new IllegalArgumentException("Genre not found for update");
        }
        genres.put(genre.getGenreId(), genre);
        return genre;
    }

    @Override
    public boolean deleteById(Integer id) {
        return genres.remove(id) != null;
    }
}