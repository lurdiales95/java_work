
package org.example.data.mappers;

import org.example.model.ItemCategory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ItemCategoryMapper implements RowMapper<ItemCategory> {

    @Override
    public ItemCategory mapRow(ResultSet rs, int rowNum) throws SQLException {
        ItemCategory category = new ItemCategory();
        category.setItemCategoryID(rs.getInt("ItemCategoryID"));
        category.setItemCategoryName(rs.getString("ItemCategoryName"));
        return category;
    }
}