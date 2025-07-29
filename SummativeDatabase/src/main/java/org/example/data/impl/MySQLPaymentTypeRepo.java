package org.example.data.impl;

import org.example.data.repository.PaymentTypeRepo;
import org.example.data.exceptions.InternalErrorException;
import org.example.data.mappers.PaymentTypeMapper;
import org.example.model.PaymentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MySQLPaymentTypeRepo implements PaymentTypeRepo {

    private final JdbcTemplate jdbcTemplate;

    public MySQLPaymentTypeRepo(@Autowired JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<PaymentType> getAll() throws InternalErrorException {
        String sql = "SELECT PaymentTypeID, PaymentTypeName FROM PaymentType";

        try {
            return jdbcTemplate.query(sql, new PaymentTypeMapper());
        } catch (DataAccessException e) {
            throw new InternalErrorException(e);
        }
    }
}

// Make this work with SQL and GDC Template
// create an implementation for each interface, implement methods, make work
// Put mapper classes in Mappers folder
// Look at what interfaces want. then models. Look at queries to hydrate models.
// Look at PaymentType model
// 5 implementations with unit tests add test file for each repository