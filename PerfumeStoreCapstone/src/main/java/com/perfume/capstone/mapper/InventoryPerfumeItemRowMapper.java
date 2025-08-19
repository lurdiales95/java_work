package com.perfume.capstone.mapper;

import com.perfume.capstone.model.*;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * INVENTORY PERFUME ITEM ROW MAPPER
 * Maps database rows from 'perfume_inventory' + 'perfumes' JOIN to InventoryPerfumeItem
 * This mapper handles the complex join between perfumes and inventory tables
 */
public class InventoryPerfumeItemRowMapper implements RowMapper<InventoryPerfumeItem> {

    @Override
    public InventoryPerfumeItem mapRow(ResultSet rs, int rowNum) throws SQLException {
        // First, create the Perfume object from the joined data
        Perfume perfume = new Perfume(
                rs.getInt("product_id"),
                rs.getString("product_name"),
                rs.getString("scent_type"),
                rs.getString("season_availability")
        );

        // Then create the InventoryPerfumeItem with the perfume + inventory data
        return new InventoryPerfumeItem(
                perfume,                           // Perfume object
                rs.getInt("quantity"),             // int quantity
                rs.getBigDecimal("price")          // BigDecimal price
        );
    }
}