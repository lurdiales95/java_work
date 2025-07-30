package org.example.data.impl;

import org.example.data.mappers.ItemCategoryMapper;
import org.example.data.mappers.ItemMapper;
import org.example.data.repository.ItemRepo;
import org.example.data.exceptions.InternalErrorException;
import org.example.data.exceptions.RecordNotFoundException;
import org.example.model.Item;
import org.example.model.ItemCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@Primary
public class MySQLItemRepo implements ItemRepo {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private ItemCategoryMapper itemCategoryMapper;



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
            return jdbcTemplate.query(sql, itemMapper, today, today);
        } catch (DataAccessException e) {
            throw new InternalErrorException(e);
        }
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
            return jdbcTemplate.queryForObject(sql, itemMapper, id);
        } catch (EmptyResultDataAccessException e) {
            throw new RecordNotFoundException();
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
            return jdbcTemplate.query(sql, itemMapper, itemCategoryID, today, today);
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
            return jdbcTemplate.query(sql, itemCategoryMapper);
        } catch (DataAccessException e) {
            throw new InternalErrorException(e);
        }
    }
}

