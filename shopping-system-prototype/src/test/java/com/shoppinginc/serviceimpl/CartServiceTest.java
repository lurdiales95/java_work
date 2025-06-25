package com.shoppinginc.serviceimpl;

import com.shoppinginc.model.OrderItem;
import com.shoppinginc.service.CartService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class CartServiceTest {
    private CartService cartService;

    @BeforeEach
    void setUp() {
        cartService = new CartService();
    }

    @Test
    void testAddNewItem() {
        OrderItem item = new OrderItem("A1", 2, "Laptop", 1000.00);
        cartService.addItem(item);

        Map<String, OrderItem> orderItem = cartService.getOrderItems();
        Assertions.assertEquals(1, orderItem.size());
        Assertions.assertTrue(orderItem.containsKey("A1"));
        Assertions.assertEquals(2, orderItem.get("A1").getQuantity());
    }

    @Test
    void testAddToExistingItem() {
        cartService.addItem(new OrderItem("A1", 2, "Laptop", 1000.00));
        cartService.addItem(new OrderItem("A1", 3, "Laptop", 1000.00));

        Map<String, OrderItem> orderItems = cartService.getOrderItems();
        Assertions.assertEquals(1, orderItems.size());
        Assertions.assertEquals(5, orderItems.get("A1").getQuantity());
    }

    @Test
    void testRemoveItemPartially() {
        cartService.addItem(new OrderItem("B2", 5, "USB Drives", 70.00));
        cartService.removeItem("B2", 2);

        Assertions.assertEquals(3, cartService.getOrderItems().get("B2").getQuantity());

    }

    @Test
    void testRemoveItemCompletely() {
        cartService.addItem(new OrderItem("C3", 3, "Keyboards", 50.00));
        cartService.removeItem("C3", 3);

        Assertions.assertFalse(cartService.getOrderItems().containsKey("C3"));

    }

    @Test
    void testCartTotalCalculation() {
        cartService.addItem(new OrderItem("X1", 2, "Xylophone", 10.0)); // 20.0
        cartService.addItem(new OrderItem("Y2", 1, "Yogurt", 5.5));     // 5.5

        Assertions.assertEquals(25.5, cartService.getCartTotal(), 0.01);
    }

    @Test
    void testTotalItemCount() {
        cartService.addItem(new OrderItem("A", 3, "Apple", 1.0));
        cartService.addItem(new OrderItem("B", 4, "Banana", 1.0));

        Assertions.assertEquals(7, cartService.getTotalItemCount());
    }

    @Test
    void testClearCart() {
        cartService.addItem(new OrderItem("Z9", 1, "Zebra", 9.99));
        cartService.clearCart();

        Assertions.assertTrue(cartService.isEmpty());
        Assertions.assertEquals(0, cartService.getOrderItems().size());
    }

    @Test
    void testRemoveNonexistentItemReturnsFalse() {
        boolean result = cartService.removeItem("NOTFOUND", 1);
        Assertions.assertFalse(result);
    }
}

