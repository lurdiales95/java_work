package org.example.data.mappers;

import org.example.model.Item;
import org.example.model.OrderItem;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

@Component
public class OrderItemWithItemMapper implements RowMapper<OrderItem> {

    @Override
    public OrderItem mapRow(ResultSet rs, int rowNum) throws SQLException {

        OrderItem orderItem = new OrderItem();
        orderItem.setOrderItemID(rs.getInt("OrderItemID"));
        orderItem.setOrderID(rs.getInt("OrderID"));
        orderItem.setItemID(rs.getInt("ItemID"));
        orderItem.setQuantity(rs.getInt("Quantity"));
        orderItem.setPrice(rs.getBigDecimal("Price"));

        Item item = new Item();
        item.setItemID(rs.getInt("ItemID"));
        item.setItemName(rs.getString("ItemName"));
        item.setItemDescription(rs.getString("ItemDescription"));
        item.setUnitPrice(rs.getBigDecimal("UnitPrice"));
        item.setItemCategoryID(rs.getInt("ItemCategoryID"));

        item.setStartDate(rs.getObject("StartDate", LocalDate.class));
        if (rs.getObject("EndDate") != null) {
            item.setEndDate(rs.getObject("EndDate", LocalDate.class));

        }

        orderItem.setItem(item);

        return orderItem;


    }
}
