package com.perfume.capstone.mapper;

import com.perfume.capstone.model.CartItem;
import com.perfume.capstone.model.Perfume;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CartItemRowMapper implements RowMapper<CartItem> {

    @Override
    public CartItem mapRow(ResultSet rs, int rowNum) throws SQLException {
        // Create the Perfume object from joined data
        Perfume perfume = new Perfume(
                rs.getInt("product_id"),
                rs.getString("product_name"),
                rs.getString("scent_type"),
                rs.getString("season_availability")
        );

        // Create CartItem with the perfume + cart data
        // Note: Your CartItem constructor calculates totalPrice automatically
        return new CartItem(
                perfume,                           // Perfume object
                rs.getInt("quantity"),             // int quantity
                rs.getBigDecimal("unit_price")     // BigDecimal price (will auto-calculate total)
        );
    }
}