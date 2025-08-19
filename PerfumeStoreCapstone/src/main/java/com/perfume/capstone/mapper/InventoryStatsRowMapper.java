package com.perfume.capstone.mapper;

import org.springframework.jdbc.core.RowMapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;


public class InventoryStatsRowMapper implements RowMapper<InventoryStatsRowMapper.InventoryStats> {
    @Override
    public InventoryStats mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new InventoryStats(
                rs.getInt("total_products"),
                rs.getInt("in_stock_products"),
                rs.getInt("out_of_stock_products"),
                rs.getBigDecimal("total_inventory_value"),
                rs.getString("most_popular_scent_type")
        );
    }

    /**
     * Simple data class for inventory statistics
     * You can create this if you want dashboard data
     */
    public static class InventoryStats {
        private final int totalProducts;
        private final int inStockProducts;
        private final int outOfStockProducts;
        private final BigDecimal totalInventoryValue;
        private final String mostPopularScentType;

        public InventoryStats(int totalProducts, int inStockProducts, int outOfStockProducts,
                              BigDecimal totalInventoryValue, String mostPopularScentType) {
            this.totalProducts = totalProducts;
            this.inStockProducts = inStockProducts;
            this.outOfStockProducts = outOfStockProducts;
            this.totalInventoryValue = totalInventoryValue;
            this.mostPopularScentType = mostPopularScentType;
        }

        // Getters
        public int getTotalProducts() { return totalProducts; }
        public int getInStockProducts() { return inStockProducts; }
        public int getOutOfStockProducts() { return outOfStockProducts; }
        public BigDecimal getTotalInventoryValue() { return totalInventoryValue; }
        public String getMostPopularScentType() { return mostPopularScentType; }
    }
}
