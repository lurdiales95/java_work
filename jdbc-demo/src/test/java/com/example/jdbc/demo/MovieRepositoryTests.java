package com.example.jdbc.demo;

import com.example.jdbc.demo.repository.MovieRepository;
import com.example.jdbc.demo.repository.MySqlMovieRepository;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import static org.junit.jupiter.api.Assertions.*;

public class MovieRepositoryTests {

    @Test
    void canLoadMovies() {
        // Create datasource manually
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/moviedb");
        dataSource.setUsername("root");
        dataSource.setPassword("$Testing123");
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");

        // Create JdbcTemplate manually
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        // Create your repository manually (assuming it takes JdbcTemplate)
        MovieRepository movieRepository = new MySqlMovieRepository(jdbcTemplate);

        // Test it
        var movies = movieRepository.getAll();
        System.out.println("Found " + movies.size() + " movies");
        assertFalse(movies.isEmpty());
    }
}
