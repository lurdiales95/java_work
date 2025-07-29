package com.example.jdbc.demo.repository;

import com.example.jdbc.demo.model.Movie;
import com.example.jdbc.demo.model.MovieDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@Primary
public class MySqlMovieRepository implements MovieRepository {
    private final JdbcTemplate jdbcTemplate;

    public MySqlMovieRepository(@Autowired JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Movie> getById(int movieID) {
        String sql = "SELECT * FROM movie WHERE MovieID = ?";

        try {
            Movie movie = jdbcTemplate.queryForObject(sql, movieRowMapper(), movieID);
            return Optional.of(movie);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        } catch(Exception e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }

    public Optional<Movie> getByIdSproc(int movieID) {
        try {
            return jdbcTemplate.execute("{CALL GetMovieById(?)}",
                    (CallableStatementCallback<Optional<Movie>>) cs -> {
                        cs.setInt(1, movieID);
                        ResultSet rs = cs.executeQuery();

                        if (rs.next()) {
                            return Optional.of(movieRowMapper().mapRow(rs, 1));
                        }

                        return Optional.empty();
                    });
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        } catch(Exception e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public List<Movie> getAll() {
        String sql = "SELECT * FROM movie ORDER BY Title";
        try {
            return jdbcTemplate.query(sql, movieRowMapper());
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }

        return List.of();
    }

    @Override
    public Movie add(Movie movie) {
        String sql = "INSERT INTO Movie (Title, ReleaseDate, GenreID, RatingID) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

                ps.setString(1, movie.getTitle());
                ps.setDate(2, Date.valueOf(movie.getReleaseDate()));
                ps.setInt(3, movie.getGenreID());
                ps.setInt(4, movie.getRatingID());

                return ps;
            }, keyHolder);

            movie.setMovieID(keyHolder.getKey().intValue());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return movie;
    }

    @Override
    public Movie addSproc(Movie movie) {
        return jdbcTemplate.execute("{CALL MovieInsert(?, ?, ?, ?, ?)}",
                (CallableStatementCallback<Movie>) cs -> {
                    cs.setString(1, movie.getTitle());
                    cs.setDate(2, Date.valueOf(movie.getReleaseDate()));
                    cs.setInt(3, movie.getGenreID());
                    cs.setInt(4, movie.getRatingID());
                    cs.registerOutParameter(5, Types.INTEGER);

                    cs.execute();

                    Integer newMovieId = cs.getInt(5);
                    movie.setMovieID(newMovieId);

                    return movie;
                });
    }

    @Override
    public Movie update(Movie movie) {
        String sql = "UPDATE movie SET Title = ?, ReleaseDate = ?, GenreID = ?, RatingID = ? WHERE MovieID = ?";

        try {
            int rowsAffected = jdbcTemplate.update(sql,
                    movie.getTitle(),
                    movie.getReleaseDate(),
                    movie.getGenreID(),
                    movie.getRatingID(),
                    movie.getMovieID());

            if (rowsAffected == 0) {
                throw new RuntimeException("Movie with ID " + movie.getMovieID() + " is not found!");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return movie;
    }

    @Override
    public boolean delete(int movieID) {
        String sql = "DELETE FROM movie WHERE MovieID = ?";
        try {
            int rowsAffected = jdbcTemplate.update(sql, movieID);
            return rowsAffected > 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    @Override
    public List<MovieDetails> getByGenre(int genreID) {
        String sql = """
                SELECT MovieID, Title, GenreName, RatingCode 
                FROM movie m 
                    INNER JOIN genre g ON m.GenreID = g.GenreID 
                    INNER JOIN rating r ON m.RatingID = r.RatingID 
                WHERE g.GenreID = ?
                """;

        try {
            return jdbcTemplate.query(sql, movieDetailRowMapper(), genreID);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return List.of();
    }


    // Mapper function tells JDBC how to convert a SQL query's columns into a Java Model
    private RowMapper<Movie> movieRowMapper() {
        return (rs, rowNum) -> {
          Movie movie = new Movie();

          movie.setMovieID(rs.getInt("MovieID"));
          movie.setTitle(rs.getString("Title"));
          movie.setReleaseDate(rs.getObject("ReleaseDate", LocalDate.class));
          movie.setGenreID(rs.getInt("GenreID"));
          movie.setRatingID(rs.getInt("RatingID"));

          return movie;
        };
    }

    private RowMapper<MovieDetails> movieDetailRowMapper() {
        return (rs, rowNum) -> {
            MovieDetails movie = new MovieDetails();

            movie.setMovieID(rs.getInt("MovieID"));
            movie.setTitle(rs.getString("Title"));
            movie.setGenreName(rs.getString("GenreName"));
            movie.setRatingCode(rs.getString("RatingCode"));

            return movie;
        };
    }
}
