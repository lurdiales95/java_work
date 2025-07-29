package org.example.data.impl;

import org.example.data.exceptions.RecordNotFoundException;
import org.example.data.repository.OrderRepo;
import org.example.model.Order;
import org.example.model.OrderItem;
import org.example.model.Payment;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test") // This ensures test profile is active
public class MySQLOrderRepoTest {

    @Autowired
    private OrderRepo orderRepo;

    private List<Integer> createdOrderIds = new ArrayList<>();


    @AfterEach
    void cleanup() {
        // Clean up any orders created during tests
        for (Integer orderId : createdOrderIds) {
            try {
                orderRepo.deleteOrder(orderId);
            } catch (Exception e) {
                // Ignore cleanup errors
            }
        }
        createdOrderIds.clear();
    }

    private Order createTestOrder() {
        Order order = new Order();
        order.setServerID(1); // CRITICAL: Must set a valid server ID
        order.setOrderDate(LocalDateTime.now());
        order.setSubTotal(BigDecimal.valueOf(50.00));
        order.setTax(BigDecimal.valueOf(5.00));
        order.setTip(BigDecimal.valueOf(10.00));
        order.setTotal(BigDecimal.valueOf(65.00));

        // Initialize empty collections to prevent null pointer exceptions
        order.setItems(new ArrayList<>());
        order.setPayments(new ArrayList<>());

        return order;
    }


    @Test
    public void testAddAndGetOrder() throws Exception {
        // Arrange
        Order testOrder = createTestOrder();

        // Act
        Order saved = orderRepo.addOrder(testOrder);
        createdOrderIds.add(saved.getOrderID()); // Track for cleanup

        Order fetched = orderRepo.getOrderById(saved.getOrderID());

        // Assert
        assertThat(saved.getOrderID()).isGreaterThan(0);
        assertThat(fetched).isNotNull();
        assertThat(fetched.getOrderID()).isEqualTo(saved.getOrderID());
        assertThat(fetched.getServerID()).isEqualTo(testOrder.getServerID());
        assertThat(fetched.getTotal()).isEqualByComparingTo(testOrder.getTotal());
        assertThat(fetched.getSubTotal()).isEqualByComparingTo(testOrder.getSubTotal());
        assertThat(fetched.getTax()).isEqualByComparingTo(testOrder.getTax());
        assertThat(fetched.getTip()).isEqualByComparingTo(testOrder.getTip());
    }

    @Test
    public void testGetAllOrders() throws Exception {
        // Arrange
        Order testOrder = createTestOrder();
        Order saved = orderRepo.addOrder(testOrder);
        createdOrderIds.add(saved.getOrderID());

        // Act
        List<Order> allOrders = orderRepo.getAllOrders();

        // Assert
        assertThat(allOrders).isNotNull();
        assertThat(allOrders).isNotEmpty();
        assertThat(allOrders.stream())
                .anyMatch(order -> order.getOrderID() == saved.getOrderID());
    }

    @Test
    public void testUpdateOrder() throws Exception {
        // Arrange
        Order testOrder = createTestOrder();
        Order saved = orderRepo.addOrder(testOrder);
        createdOrderIds.add(saved.getOrderID());

        // Modify the order
        saved.setTip(BigDecimal.valueOf(15.00));
        saved.setTotal(BigDecimal.valueOf(70.00));

        // Act
        orderRepo.updateOrder(saved);
        Order updated = orderRepo.getOrderById(saved.getOrderID());

        // Assert
        assertThat(updated.getTip()).isEqualByComparingTo(BigDecimal.valueOf(15.00));
        assertThat(updated.getTotal()).isEqualByComparingTo(BigDecimal.valueOf(70.00));
    }

    @Test
    public void testDeleteOrder() throws Exception {
        // Arrange
        Order testOrder = createTestOrder();
        Order saved = orderRepo.addOrder(testOrder);
        int orderId = saved.getOrderID();

        // Act
        Order deleted = orderRepo.deleteOrder(orderId);

        // Assert
        assertThat(deleted).isNotNull();
        assertThat(deleted.getOrderID()).isEqualTo(orderId);

        // Verify the order is actually deleted
        Assertions.assertThrows(RecordNotFoundException.class,
                () -> orderRepo.getOrderById(orderId));
    }

    @Test
    public void testGetOrderById_NotFound() {
        // Test that getting a non-existent order throws RecordNotFoundException
        Assertions.assertThrows(RecordNotFoundException.class,
                () -> orderRepo.getOrderById(99999));
    }
}