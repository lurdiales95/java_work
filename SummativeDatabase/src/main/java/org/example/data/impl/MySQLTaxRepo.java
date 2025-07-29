package org.example.data.impl;

import org.example.data.repository.TaxRepo;
import org.example.data.exceptions.InternalErrorException;
import org.example.data.exceptions.RecordNotFoundException;
import org.example.data.mappers.TaxMapper;
import org.example.model.Tax;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public class MySQLTaxRepo implements TaxRepo {

    private final JdbcTemplate jdbcTemplate;

    public MySQLTaxRepo(@Autowired JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Tax getCurrentTax(LocalDate dateOf) throws InternalErrorException, RecordNotFoundException {
        String sql = """
            SELECT TaxID, TaxPercentage, StartDate, EndDate 
            FROM Tax 
            WHERE ? >= StartDate 
            AND (EndDate IS NULL OR ? <= EndDate)
            LIMIT 1
            """;

        try {
            return jdbcTemplate.queryForObject(sql, new TaxMapper(), dateOf, dateOf);
        } catch (EmptyResultDataAccessException e) {
            throw new RecordNotFoundException();
        } catch (DataAccessException e) {
            throw new InternalErrorException(e);
        }
    }
}