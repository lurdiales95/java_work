package org.example.data.impl;

import org.example.data.ItemRepo;
import org.example.data.exceptions.InternalErrorException;
import org.example.data.exceptions.RecordNotFoundException;
import org.example.model.Item;
import org.example.model.ItemCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class MySQLItemRepo implements ItemRepo {

    private final JdbcTemplate jdbcTemplate;

    public MySQLItemRepo(@Autowired JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Item getItemById(int id) throws RecordNotFoundException, InternalErrorException {
        String sql = """
            SELECT ItemID, ItemCategoryID, ItemName, ItemDescription, 
                   StartDate, EndDate, UnitPrice 
            FROM Item 
            WHERE ItemID = ?
            """;

        try {
            return jdbcTemplate.queryForObject(sql, itemRowMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            throw new RecordNotFoundException();
        } catch (DataAccessException e) {
            throw new InternalErrorException(e);
        }
    }

    @Override
    public List<Item> getAllAvailableItems(LocalDate today) throws InternalErrorException {
        String sql = """
            SELECT ItemID, ItemCategoryID, ItemName, ItemDescription, 
                   StartDate, EndDate, UnitPrice 
            FROM Item 
            WHERE ? >= StartDate 
            AND (EndDate IS NULL OR ? <= EndDate)
            ORDER BY ItemCategoryID, ItemName
            """;

        try {
            return jdbcTemplate.query(sql, itemRowMapper(), today, today);
        } catch (DataAccessException e) {
            throw new InternalErrorException(e);
        }
    }

    @Override
    public List<Item> getItemsByCategory(LocalDate today, int itemCategoryID) throws InternalErrorException {
        String sql = """
            SELECT ItemID, ItemCategoryID, ItemName, ItemDescription, 
                   StartDate, EndDate, UnitPrice 
            FROM Item 
            WHERE ItemCategoryID = ? 
            AND ? >= StartDate 
            AND (EndDate IS NULL OR ? <= EndDate)
            ORDER BY ItemName
            """;

        try {
            return jdbcTemplate.query(sql, itemRowMapper(), itemCategoryID, today, today);
        } catch (DataAccessException e) {
            throw new InternalErrorException(e);
        }
    }

    @Override
    public List<ItemCategory> getAllItemCategories() throws InternalErrorException {
        String sql = """
            SELECT ItemCategoryID, ItemCategoryName 
            FROM ItemCategory 
            ORDER BY ItemCategoryName
            """;

        try {
            return jdbcTemplate.query(sql, itemCategoryRowMapper());
        } catch (DataAccessException e) {
            throw new InternalErrorException(e);
        }
    }

    // Mapper function tells JDBC how to convert a SQL query's columns into a Java Model
    private RowMapper<Item> itemRowMapper() {
        return (rs, rowNum) -> {
            Item item = new Item();
            item.setItemID(rs.getInt("ItemID"));
            item.setItemCategoryID(rs.getInt("ItemCategoryID"));
            item.setItemName(rs.getString("ItemName"));
            item.setItemDescription(rs.getString("ItemDescription"));
            item.setStartDate(rs.getObject("StartDate", LocalDate.class));

            // Handle null EndDate
            if (rs.getObject("EndDate") != null) {
                item.setEndDate(rs.getObject("EndDate", LocalDate.class));
            }

            item.setUnitPrice(rs.getBigDecimal("UnitPrice"));
            return item;
        };
    }

    // Mapper function for ItemCategory
    private RowMapper<ItemCategory> itemCategoryRowMapper() {
        return (rs, rowNum) -> {
            ItemCategory category = new ItemCategory();
            category.setItemCategoryID(rs.getInt("ItemCategoryID"));
            category.setItemCategoryName(rs.getString("ItemCategoryName"));
            return category;
        };
    }
}