package org.example.data.impl;

import org.example.data.repository.OrderRepo;
import org.example.model.Order;
import org.junit.jupiter.api.*;
import org.mockito.internal.matchers.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MySQLOrderRepoTest {

    @Autowired
    private OrderRepo orderRepo;
    private Order testOrder;

    @BeforeAll
    void setup() {
        testOrder = new Order();
        testOrder.setOrderDate(LocalDateTime.now());
        testOrder.setSubTotal(BigDecimal.valueOf(50));
        testOrder.setTax(BigDecimal.valueOf(5));
        testOrder.setTip(BigDecimal.valueOf(10));
        testOrder.setTotal(BigDecimal.valueOf(65));
    }


    @Test
    public void testAddAndGetOrder() throws Exception {
        Order saved = orderRepo.addOrder(testOrder);
        assertThat(saved.getOrderID()).isGreaterThan(0);

        Order fetched = orderRepo.getOrderById(saved.getOrderID());
        assertThat(fetched).isNotNull();
        assertThat(fetched.getTotal()).isEqualByComparingTo(testOrder.getTotal());
    }

    @Test
    public void testGetAllOrders() throws Exception {
        List<Order> allOrders = orderRepo.getAllOrders();
        assertThat(allOrders).isNotNull();
    }

    @Test
    public void testDeleteOrder() throws Exception {
        Order saved = orderRepo.addOrder(testOrder);
        Order deleted = orderRepo.deleteOrder(saved.getOrderID());

        Assertions.assertThrows(Exception.class, () -> orderRepo.getOrderById(saved.getOrderID()));

    }

}