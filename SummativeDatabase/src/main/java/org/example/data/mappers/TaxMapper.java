package org.example.data.mappers;

import org.example.model.Tax;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class TaxMapper implements RowMapper<Tax> {

    @Override
    public Tax mapRow(ResultSet rs, int rowNum) throws SQLException {
        Tax tax = new Tax();
        tax.setTaxID(rs.getInt("TaxID"));
        tax.setTaxPercentage(rs.getBigDecimal("TaxPercentage"));
        tax.setStartDate(rs.getObject("StartDate", LocalDate.class));

        // Handle null EndDate
        if (rs.getObject("EndDate") != null) {
            tax.setEndDate(rs.getObject("EndDate", LocalDate.class));
        }

        return tax;
    }
}