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
