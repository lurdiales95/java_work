package com.perfume.capstone.mapper;

import com.perfume.capstone.model.CartItem;
import com.perfume.capstone.model.Perfume;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CartItemWithTotalsRowMapper implements RowMapper<CartItem> {
    @Override
    public CartItem mapRow(ResultSet rs, int rowNum) throws SQLException {
        Perfume perfume = new Perfume(
                rs.getInt("product_id"),
                rs.getString("product_name"),
                rs.getString("scent_type"),
                rs.getString("season_availability")
        );
        // Create CartItem and manually set the total if needed
        CartItem cartItem = new CartItem(
                perfume,
                rs.getInt("quantity"),
                rs.getBigDecimal("unit_price")
        );

        // If your CartItem has a setter for total price, you could use:
        // BigDecimal totalFromDB = rs.getBigDecimal("total_price");
        // cartItem.setTotalPrice(totalFromDB);

        return cartItem;
    }
}