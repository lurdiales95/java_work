
package org.example.data.mappers;

import org.example.model.Item;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class ItemMapper implements RowMapper<Item> {

    @Override
    public Item mapRow(ResultSet rs, int rowNum) throws SQLException {
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
    }
}