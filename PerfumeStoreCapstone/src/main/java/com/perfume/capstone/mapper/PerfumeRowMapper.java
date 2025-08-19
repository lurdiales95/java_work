package com.perfume.capstone.mapper;

import com.perfume.capstone.model.Perfume;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PerfumeRowMapper implements RowMapper<Perfume> {

    @Override
    public Perfume mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Perfume(
                rs.getInt("product_id"),           // int productId (auto-increment from DB)
                rs.getString("product_name"),      // String productName
                rs.getString("scent_type"),        // String scentType
                rs.getString("season_availability") // String seasonAvailability
        );
    }
}