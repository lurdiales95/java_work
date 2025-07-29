package org.example.data.impl;

import org.example.data.repository.OrderRepo;
import org.example.data.exceptions.InternalErrorException;
import org.example.data.exceptions.RecordNotFoundException;
import org.example.data.mappers.*;
import org.example.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;


@Repository
public class MySQLOrderRepo implements OrderRepo {

    private final JdbcTemplate jdbcTemplate;

    public MySQLOrderRepo(@Autowired JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Order getOrderById(int id) throws RecordNotFoundException, InternalErrorException {
        String sql = """
            SELECT OrderID, ServerID, OrderDate, SubTotal, Tax, Tip, Total 
            FROM `Order` 
            WHERE OrderID = ?
            """;

        try {
            Order order = jdbcTemplate.queryForObject(sql, new OrderMapper(), id);

            // Load related data
            loadServerForOrder(order);
            loadItemsForOrder(order);
            loadPaymentsForOrder(order);

            return order;
        } catch (EmptyResultDataAccessException e) {
            throw new RecordNotFoundException();
        } catch (DataAccessException e) {
            throw new InternalErrorException(e);
        }
    }

    @Override
    public List<Order> getAllOrders() throws InternalErrorException, RecordNotFoundException {
        String sql = """
            SELECT OrderID, ServerID, OrderDate, SubTotal, Tax, Tip, Total 
            FROM `Order` 
            ORDER BY OrderDate DESC
            """;

        try {
            List<Order> orders = jdbcTemplate.query(sql, new OrderMapper());

            // Load related data for each order
            for (Order order : orders) {
                loadServerForOrder(order);
                loadItemsForOrder(order);
                loadPaymentsForOrder(order);
            }

            return orders;
        } catch (DataAccessException e) {
            throw new InternalErrorException(e);
        }
    }

    @Override
    public Order addOrder(Order order) throws InternalErrorException {
        String sql = """
            INSERT INTO `Order` (ServerID, OrderDate, SubTotal, Tax, Tip, Total) 
            VALUES (?, ?, ?, ?, ?, ?)
            """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                ps.setInt(1, order.getServerID());
                ps.setTimestamp(2, Timestamp.valueOf(order.getOrderDate()));
                ps.setBigDecimal(3, order.getSubTotal());
                ps.setBigDecimal(4, order.getTax());
                ps.setBigDecimal(5, order.getTip());
                ps.setBigDecimal(6, order.getTotal());
                return ps;
            }, keyHolder);

            order.setOrderID(Objects.requireNonNull(keyHolder.getKey()).intValue());

            // Save order items
            saveOrderItems(order);

            // Save payments
            savePayments(order);

            return order;
        } catch (DataAccessException e) {
            throw new InternalErrorException(e);
        }
    }

    @Override
    public void updateOrder(Order order) throws InternalErrorException {
        String sql = """
            UPDATE `Order` 
            SET ServerID = ?, OrderDate = ?, SubTotal = ?, Tax = ?, Tip = ?, Total = ? 
            WHERE OrderID = ?
            """;

        try {
            jdbcTemplate.update(sql,
                    order.getServerID(),
                    Timestamp.valueOf(order.getOrderDate()),
                    order.getSubTotal(),
                    order.getTax(),
                    order.getTip(),
                    order.getTotal(),
                    order.getOrderID());

            // Delete existing order items and payments, then re-add them
            deleteOrderItems(order.getOrderID());
            deletePayments(order.getOrderID());

            saveOrderItems(order);
            savePayments(order);

        } catch (DataAccessException e) {
            throw new InternalErrorException(e);
        }
    }

    @Override
    public Order deleteOrder(int id) throws InternalErrorException {
        try {
            // Get the order before deleting (for return value)
            Order order = getOrderById(id);

            // Delete related records first (foreign key constraints)
            deleteOrderItems(id);
            deletePayments(id);

            // Delete the order
            String sql = "DELETE FROM `Order` WHERE OrderID = ?";
            jdbcTemplate.update(sql, id);

            return order;
        } catch (RecordNotFoundException e) {
            throw new InternalErrorException(new Exception("Order not found for deletion"));
        } catch (DataAccessException e) {
            throw new InternalErrorException(e);
        }
    }

    // Helper methods
    private void loadServerForOrder(Order order) throws InternalErrorException {
        String sql = """
            SELECT ServerID, FirstName, LastName, HireDate, TermDate 
            FROM Server 
            WHERE ServerID = ?
            """;

        try {
            Server server = jdbcTemplate.queryForObject(sql, new ServerMapper(), order.getServerID());
            order.setServer(server);
        } catch (DataAccessException e) {
            throw new InternalErrorException(e);
        }
    }

    private void loadItemsForOrder(Order order) throws InternalErrorException {
        String sql = """
            SELECT oi.OrderItemID, oi.OrderID, oi.ItemID, oi.Quantity, oi.Price,
                   i.ItemName, i.ItemDescription, i.StartDate, i.EndDate, i.UnitPrice, i.ItemCategoryID
            FROM OrderItem oi
            INNER JOIN Item i ON oi.ItemID = i.ItemID
            WHERE oi.OrderID = ?
            ORDER BY oi.OrderItemID
            """;

        try {
            List<OrderItem> items = jdbcTemplate.query(sql, (rs, rowNum) -> {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrderItemID(rs.getInt("OrderItemID"));
                orderItem.setOrderID(rs.getInt("OrderID"));
                orderItem.setItemID(rs.getInt("ItemID"));
                orderItem.setQuantity(rs.getInt("Quantity"));
                orderItem.setPrice(rs.getBigDecimal("Price"));

                // Create and populate the Item
                Item item = new Item();
                item.setItemID(rs.getInt("ItemID"));
                item.setItemName(rs.getString("ItemName"));
                item.setItemDescription(rs.getString("ItemDescription"));
                item.setUnitPrice(rs.getBigDecimal("UnitPrice"));
                item.setItemCategoryID(rs.getInt("ItemCategoryID"));
                orderItem.setItem(item);

                return orderItem;
            }, order.getOrderID());

            order.setItems(items);
        } catch (DataAccessException e) {
            throw new InternalErrorException(e);
        }
    }

    private void loadPaymentsForOrder(Order order) throws InternalErrorException {
        String sql = """
            SELECT p.PaymentID, p.PaymentTypeID, p.OrderID, p.Amount,
                   pt.PaymentTypeName
            FROM Payment p
            INNER JOIN PaymentType pt ON p.PaymentTypeID = pt.PaymentTypeID
            WHERE p.OrderID = ?
            ORDER BY p.PaymentID
            """;

        try {
            List<Payment> payments = jdbcTemplate.query(sql, (rs, rowNum) -> {
                Payment payment = new Payment();
                payment.setPaymentID(rs.getInt("PaymentID"));
                payment.setPaymentTypeID(rs.getInt("PaymentTypeID"));
                payment.setOrderID(rs.getInt("OrderID"));
                payment.setAmount(rs.getBigDecimal("Amount"));

                // Create and populate the PaymentType
                PaymentType paymentType = new PaymentType();
                paymentType.setPaymentTypeID(rs.getInt("PaymentTypeID"));
                paymentType.setPaymentTypeName(rs.getString("PaymentTypeName"));
                payment.setPaymentType(paymentType);

                return payment;
            }, order.getOrderID());

            order.setPayments(payments);
        } catch (DataAccessException e) {
            throw new InternalErrorException(e);
        }
    }

    private void saveOrderItems(Order order) throws InternalErrorException {
        if (order.getItems() != null && !order.getItems().isEmpty()) {
            String sql = """
                INSERT INTO OrderItem (OrderID, ItemID, Quantity, Price) 
                VALUES (?, ?, ?, ?)
                """;

            try {
                for (OrderItem item : order.getItems()) {
                    jdbcTemplate.update(sql,
                            order.getOrderID(),
                            item.getItemID(),
                            item.getQuantity(),
                            item.getPrice());
                }
            } catch (DataAccessException e) {
                throw new InternalErrorException(e);
            }
        }
    }

    private void savePayments(Order order) throws InternalErrorException {
        if (order.getPayments() != null && !order.getPayments().isEmpty()) {
            String sql = """
                INSERT INTO Payment (OrderID, PaymentTypeID, Amount) 
                VALUES (?, ?, ?)
                """;

            try {
                for (Payment payment : order.getPayments()) {
                    jdbcTemplate.update(sql,
                            order.getOrderID(),
                            payment.getPaymentTypeID(),
                            payment.getAmount());
                }
            } catch (DataAccessException e) {
                throw new InternalErrorException(e);
            }
        }
    }

    private void deleteOrderItems(int orderId) throws InternalErrorException {
        String sql = "DELETE FROM OrderItem WHERE OrderID = ?";
        try {
            jdbcTemplate.update(sql, orderId);
        } catch (DataAccessException e) {
            throw new InternalErrorException(e);
        }
    }

    private void deletePayments(int orderId) throws InternalErrorException {
        String sql = "DELETE FROM Payment WHERE OrderID = ?";
        try {
            jdbcTemplate.update(sql, orderId);
        } catch (DataAccessException e) {
            throw new InternalErrorException(e);
        }
    }
}