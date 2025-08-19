package com.perfume.capstone.repository;

import com.perfume.capstone.mapper.InventoryStatsRowMapper;
import com.perfume.capstone.mapper.MapperFactory;
import com.perfume.capstone.model.InventoryPerfumeItem;
import com.perfume.capstone.model.Perfume;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Primary  // This will override your InMemoryPerfumeRepository
public class JdbcPerfumeRepository implements PerfumeRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcPerfumeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<InventoryPerfumeItem> getAll() {
        String sql = """
            SELECT p.product_id, p.product_name, p.scent_type, p.season_availability,
                   pi.quantity, pi.price
            FROM perfumes p
            JOIN perfume_inventory pi ON p.product_id = pi.product_id
            ORDER BY p.product_name
            """;

        return jdbcTemplate.query(sql, MapperFactory.inventoryMapper());
    }

    @Override
    public List<InventoryPerfumeItem> getInStock() {
        String sql = """
            SELECT p.product_id, p.product_name, p.scent_type, p.season_availability,
                   pi.quantity, pi.price
            FROM perfumes p
            JOIN perfume_inventory pi ON p.product_id = pi.product_id
            WHERE pi.quantity > 0
            ORDER BY p.product_name
            """;

        return jdbcTemplate.query(sql, MapperFactory.inventoryMapper());
    }

    @Override
    public void add(InventoryPerfumeItem item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }

        Perfume perfume = item.getPerfume();

        // Insert into perfumes table first
        String perfumeSql = """
            INSERT INTO perfumes (product_name, scent_type, season_availability)
            VALUES (?, ?, ?)
            """;

        jdbcTemplate.update(perfumeSql,
                perfume.productName(),
                perfume.scentType(),
                perfume.seasonAvailability());

        // Get the generated product_id
        String getIdSql = "SELECT LAST_INSERT_ID()";
        Integer productId = jdbcTemplate.queryForObject(getIdSql, Integer.class);

        // Insert into inventory table
        String inventorySql = """
            INSERT INTO perfume_inventory (product_id, quantity, price)
            VALUES (?, ?, ?)
            """;

        jdbcTemplate.update(inventorySql,
                productId,
                item.getQuantity(),
                item.getPrice());
    }

    @Override
    public void update(InventoryPerfumeItem item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }

        int productId = item.getPerfume().productID();

        // Update perfume details
        String perfumeSql = """
            UPDATE perfumes 
            SET product_name = ?, scent_type = ?, season_availability = ?
            WHERE product_id = ?
            """;

        int perfumeRows = jdbcTemplate.update(perfumeSql,
                item.getPerfume().productName(),
                item.getPerfume().scentType(),
                item.getPerfume().seasonAvailability(),
                productId);

        if (perfumeRows == 0) {
            throw new IllegalArgumentException("Product with ID " + productId + " not found");
        }

        // Update inventory details
        String inventorySql = """
            UPDATE perfume_inventory 
            SET quantity = ?, price = ?
            WHERE product_id = ?
            """;

        jdbcTemplate.update(inventorySql,
                item.getQuantity(),
                item.getPrice(),
                productId);
    }

    @Override
    public void delete(int productID) {
        if (productID <= 0) {
            throw new IllegalArgumentException("ProductID must be positive");
        }

        // Delete from perfumes table (CASCADE will handle inventory)
        String sql = "DELETE FROM perfumes WHERE product_id = ?";

        int rowsAffected = jdbcTemplate.update(sql, productID);

        if (rowsAffected == 0) {
            throw new IllegalArgumentException("Product with ID " + productID + " not found");
        }
    }

    @Override
    public InventoryPerfumeItem getByProductID(int productID) {
        if (productID <= 0) {
            throw new IllegalArgumentException("ProductID must be positive");
        }

        String sql = """
            SELECT p.product_id, p.product_name, p.scent_type, p.season_availability,
                   pi.quantity, pi.price
            FROM perfumes p
            JOIN perfume_inventory pi ON p.product_id = pi.product_id
            WHERE p.product_id = ?
            """;

        try {
            return jdbcTemplate.queryForObject(sql, MapperFactory.inventoryMapper(), productID);
        } catch (DataAccessException e) {
            return null; // Product not found
        }
    }

    // Additional method for analytics (uses your InventoryStatsRowMapper)
    public InventoryStatsRowMapper.InventoryStats getInventoryStats() {
        String sql = """
            SELECT 
                COUNT(*) as total_products,
                SUM(CASE WHEN pi.quantity > 0 THEN 1 ELSE 0 END) as in_stock_products,
                SUM(CASE WHEN pi.quantity = 0 THEN 1 ELSE 0 END) as out_of_stock_products,
                SUM(pi.quantity * pi.price) as total_inventory_value,
                (SELECT p2.scent_type 
                 FROM perfumes p2 
                 JOIN perfume_inventory pi2 ON p2.product_id = pi2.product_id
                 GROUP BY p2.scent_type 
                 ORDER BY SUM(pi2.quantity) DESC 
                 LIMIT 1) as most_popular_scent_type
            FROM perfumes p
            JOIN perfume_inventory pi ON p.product_id = pi.product_id
            """;

        return jdbcTemplate.queryForObject(sql, MapperFactory.inventoryStatsMapper());
    }
}