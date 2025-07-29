package org.example.data.impl;

import org.example.data.ServerRepo;
import org.example.data.exceptions.InternalErrorException;
import org.example.data.exceptions.RecordNotFoundException;
import org.example.data.mappers.ServerMapper;
import org.example.model.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class MySQLServerRepo implements ServerRepo {

    private final JdbcTemplate jdbcTemplate;

    public MySQLServerRepo(@Autowired JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Server getServerById(int id) throws InternalErrorException, RecordNotFoundException {
        String sql = """
            SELECT ServerID, FirstName, LastName, HireDate, TermDate 
            FROM Server 
            WHERE ServerID = ?
            """;

        try {
            return jdbcTemplate.queryForObject(sql, new ServerMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            throw new RecordNotFoundException();
        } catch (DataAccessException e) {
            throw new InternalErrorException(e);
        }
    }

    @Override
    public List<Server> getAllAvailableServers(LocalDate date) throws InternalErrorException {
        String sql = """
            SELECT ServerID, FirstName, LastName, HireDate, TermDate 
            FROM Server 
            WHERE ? >= HireDate 
            AND (TermDate IS NULL OR ? <= TermDate)
            ORDER BY FirstName, LastName
            """;

        try {
            return jdbcTemplate.query(sql, new ServerMapper(), date, date);
        } catch (DataAccessException e) {
            throw new InternalErrorException(e);
        }
    }
}