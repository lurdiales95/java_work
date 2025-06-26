package com.shoppinginc.serviceimpl;

import com.shoppinginc.model.OrderItem;
import com.shoppinginc.service.CartService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
// tests to make sure the systems in place that add, remove, eliminate items in cart work (calculations)
public class CartServiceTest {
    private CartService cartService;

    // sets up brand-new shopping cart with all previous info erased
    @BeforeEach
    void setUp() {
        cartService = new CartService();
    }

    // BeforeEach test to add items to cart
    @Test
    void testAddNewItem() {
        OrderItem item = new OrderItem("A1", 2, "Laptop", 1000.00);
        cartService.addItem(item);

        Map<String, OrderItem> orderItem = cartService.getOrderItems();
        // the below checks to see if the test confirms one item in cart
        Assertions.assertEquals(1, orderItem.size());
        // signal item we're looking at
        Assertions.assertTrue(orderItem.containsKey("A1"));
        // checks to see if the test shows 2 items in the cart
        Assertions.assertEquals(2, orderItem.get("A1").getQuantity());
    }

    // checks to see if we can add more items to the existing cart

    @Test
    void testAddToExistingItem() {
        // identifies that there are two items in cart (Laptops)
        cartService.addItem(new OrderItem("A1", 2, "Laptop", 1000.00));
        // gets info from previous item and now has a new quantity to add
        cartService.addItem(new OrderItem("A1", 3, "Laptop", 1000.00));


        Map<String, OrderItem> orderItems = cartService.getOrderItems();
        // tests to make sure it's only one item type ("Laptop")
        Assertions.assertEquals(1, orderItems.size());
        // tests to make sure there are 5 total items in the cart (5 "Laptop" items total)
        Assertions.assertEquals(5, orderItems.get("A1").getQuantity());
    }

    @Test
    // tests to make sure items are removed successfully without removing the whole item type in cart
    void testRemoveItemPartially() {
        // adds item ("USB Drives")
        cartService.addItem(new OrderItem("B2", 5, "USB Drives", 70.00));
        // identifies that to add 2 more of "USB Drives" to the cart
        cartService.removeItem("B2", 2);

        // test to confirm there are now only 3 "USB Drives" in cart
        Assertions.assertEquals(3, cartService.getOrderItems().get("B2").getQuantity());

    }

    @Test
    // tests to make sure items are removed completely if requested
    void testRemoveItemCompletely() {
        // adds three "Keyboards"
        cartService.addItem(new OrderItem("C3", 3, "Keyboards", 50.00));
        // identifies item based on id and signals the qty that needs to be removed
        cartService.removeItem("C3", 3);
        // notifies false to make sure there are no items in cart - the test fails if there are still items in cart
        Assertions.assertFalse(cartService.getOrderItems().containsKey("C3"));

    }

    @Test
    // calculates the total amount of money in the cart
    void testCartTotalCalculation() {
        cartService.addItem(new OrderItem("X1", 2, "Xylophone", 10.0)); // 20.0
        cartService.addItem(new OrderItem("Y2", 1, "Yogurt", 5.5));     // 5.5

        // checks if calculator works / equals the expected amount
        Assertions.assertEquals(25.5, cartService.getCartTotal(), 0.01);
    }

    @Test
    // counts all items quantities in cart, regardless of product type
    void testTotalItemCount() {
        cartService.addItem(new OrderItem("A", 3, "Apple", 1.0));
        cartService.addItem(new OrderItem("B", 4, "Banana", 1.0));

        // checks to make sure the total is 7 items
        Assertions.assertEquals(7, cartService.getTotalItemCount());
    }

    @Test
    // tests if you can clear cart successfully
    void testClearCart() {
        // adds items
        cartService.addItem(new OrderItem("Z9", 1, "Zebra", 9.99));
        // calls for clearing cart
        cartService.clearCart();

        //evaluates that the .isEmpty() works
        Assertions.assertTrue(cartService.isEmpty());
        // confirms there are no items in cart
        Assertions.assertEquals(0, cartService.getOrderItems().size());
    }

    @Test
    // tests to make sure there is no way to remove an item that doesn't exist
    void testRemoveNonexistentItemReturnsFalse() {
        // identifies id and qty that do not exist submitted by user
        boolean result = cartService.removeItem("NOTFOUND", 1);

        // returns false to show that the item doesn't exist in the cart
        Assertions.assertFalse(result);
    }
}

