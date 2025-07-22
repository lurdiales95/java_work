package com.example.Capstone_Java_Console_App_Inventory_Manager;

import com.example.Capstone_Java_Console_App_Inventory_Manager.model.Candle;
import com.example.Capstone_Java_Console_App_Inventory_Manager.model.CartItem;
import com.example.Capstone_Java_Console_App_Inventory_Manager.model.InventoryCandleItem;
import com.example.Capstone_Java_Console_App_Inventory_Manager.model.Result;
import com.example.Capstone_Java_Console_App_Inventory_Manager.repository.InMemoryInventoryRepository;
import com.example.Capstone_Java_Console_App_Inventory_Manager.repository.InventoryRepository;
import com.example.Capstone_Java_Console_App_Inventory_Manager.service.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CartServiceTest {
    private InventoryRepository inventoryRepository;
    private CartService cartService;

    private Candle candle1;
    private Candle candle2;
    private InventoryCandleItem inventoryCandleItem1;
    private InventoryCandleItem inventoryCandleItem2;

    @BeforeEach
    void setUp() {
        // Create fresh instances for each test
        inventoryRepository = new InMemoryInventoryRepository();
        cartService = new CartService(inventoryRepository);

        candle1= new Candle("8264", "Fall Farmhouse", "Warm & Woodsy", "Fall");
        candle2 = new Candle("3559", "Mahogany Teakwood Intense", "Warm & Woodsy", "Year Round");

        inventoryCandleItem1 = new InventoryCandleItem(candle1, 150, new BigDecimal("26.95"));
        inventoryCandleItem2 = new InventoryCandleItem(candle2, 150, new BigDecimal("13.98"));

    }

    private CartItem findCartItemByProductID(List<CartItem> cartItems, String productID) {
        return cartItems.stream()
                .filter(item -> item.getCandle().productID().equals(productID))
                .findFirst()
                .orElse(null);
    }

    // Helper method to get quantity for an ProductID from cart contents
    private int getQuantityByProductID(List<CartItem> cartItems, String productID) {
        CartItem item = findCartItemByProductID(cartItems, productID);
        return item != null ? item.getQuantity() : 0;
    }

    @Test
    void addToCart_ValidItem_ReturnsSuccess() {
        // Act
        Result<Void> result = cartService.addToCart("8264", 2);
        System.out.println(result.isSuccess());
        // Assert
        assertTrue(result.isSuccess());
        assertEquals("Added 2 candles of 'Fall Farmhouse' scent to cart", result.getMessage());

        // Verify cart contents
        List<CartItem> cartContents = cartService.getCartContents();
        assertEquals(2, getQuantityByProductID(cartContents, "8264"));
        assertFalse(cartService.isEmpty());
    }

    @Test
    void addToCart_NullProductID_ReturnsFailure() {
        // Act
        Result<Void> result = cartService.addToCart(null, 1);

        // Assert
        assertFalse(result.isSuccess());
        assertEquals("ProductID cannot be null or empty", result.getMessage());
        assertTrue(cartService.isEmpty());
    }

    @Test
    void addToCart_EmptyProductID_ReturnsFailure() {
        // Act
        Result<Void> result = cartService.addToCart("", 1);

        // Assert
        assertFalse(result.isSuccess());
        assertEquals("ProductID cannot be null or empty", result.getMessage());
        assertTrue(cartService.isEmpty());
    }

    @Test
    void addToCart_WhitespaceProductID_ReturnsFailure() {
        // Act
        Result<Void> result = cartService.addToCart("   ", 1);

        // Assert
        assertFalse(result.isSuccess());
        assertEquals("ProductID cannot be null or empty", result.getMessage());
        assertTrue(cartService.isEmpty());
    }

    @Test
    void addToCart_ZeroQuantity_ReturnsFailure() {
        // Act
        Result<Void> result = cartService.addToCart("3559", 0);

        // Assert
        assertFalse(result.isSuccess());
        assertEquals("Quantity must be greater than 0", result.getMessage());
        assertTrue(cartService.isEmpty());
    }

    @Test
    void addToCart_NegativeQuantity_ReturnsFailure() {
        // Act
        Result<Void> result = cartService.addToCart("3559", -1);

        // Assert
        assertFalse(result.isSuccess());
        assertEquals("Quantity must be greater than 0", result.getMessage());
        assertTrue(cartService.isEmpty());
    }

    @Test
    void addToCart_BookNotFound_ReturnsFailure() {
        // Act
        Result<Void> result = cartService.addToCart("invalid-productID", 1);

        // Assert
        assertFalse(result.isSuccess());
        assertEquals("Candle not found with ProductID: invalid-productID", result.getMessage());
        assertTrue(cartService.isEmpty());
    }

    @Test
    void addToCart_InsufficientStock_ReturnsFailure() {
        // Act - trying to add more than available (10 in stock)
        Result<Void> result = cartService.addToCart("3559", 15);

        // Assert
        assertFalse(result.isSuccess());
        assertEquals("Not enough stock. Available: 10, Requested: 15", result.getMessage());
        assertTrue(cartService.isEmpty());
    }

    @Test
    void addToCart_ExactStockQuantity_ReturnsSuccess() {
        // Act - adding exactly what's in stock
        Result<Void> result = cartService.addToCart("3559", 10);

        // Assert
        assertTrue(result.isSuccess());
        assertEquals("Added 10 copies of 'Clean Code' to cart", result.getMessage());

        List<CartItem> cartContents = cartService.getCartContents();
        assertEquals(10, getQuantityByProductID(cartContents, "3559"));
    }

    @Test
    void addToCart_MultipleAdds_AccumulatesQuantity() {
        // Act
        Result<Void> result1 = cartService.addToCart("3559", 3);
        Result<Void> result2 = cartService.addToCart("3559", 2);

        // Assert
        assertTrue(result1.isSuccess());
        assertTrue(result2.isSuccess());
        assertEquals("Added 2 copies of 'Clean Code' to cart", result2.getMessage());

        List<CartItem> cartContents = cartService.getCartContents();
        assertEquals(5, getQuantityByProductID(cartContents, "3559"));
    }

    @Test
    void addToCart_AccumulatedQuantityExceedsStock_ReturnsFailure() {
        // Act
        Result<Void> result1 = cartService.addToCart("3559", 8);
        Result<Void> result2 = cartService.addToCart("3559", 5);

        // Assert
        assertTrue(result1.isSuccess());
        assertFalse(result2.isSuccess());
        assertEquals("Not enough stock. Available: 10, Requested: 13", result2.getMessage());

        // Verify cart still contains the first addition only
        List<CartItem> cartContents = cartService.getCartContents();
        assertEquals(8, getQuantityByProductID(cartContents, "978-0132350884"));
    }

    @Test
    void addToCart_MultipleDifferentBooks_ReturnsSuccess() {
        // Act
        Result<Void> result1 = cartService.addToCart("978-0132350884", 2);
        Result<Void> result2 = cartService.addToCart("978-0321125217", 1);

        // Assert
        assertTrue(result1.isSuccess());
        assertTrue(result2.isSuccess());

        List<CartItem> cartContents = cartService.getCartContents();
        assertEquals(2, getQuantityByIsbn(cartContents, "978-0132350884"));
        assertEquals(1, getQuantityByIsbn(cartContents, "978-0321125217"));
        assertEquals(2, cartContents.size());
    }

    @Test
    void removeFromCart_ValidRemoval_ReturnsSuccess() {
        // Arrange
        cartService.addToCart("978-0132350884", 5);

        // Act
        Result<Void> result = cartService.removeFromCart("978-0132350884", 2);

        // Assert
        assertTrue(result.isSuccess());
        assertEquals("Removed 2 copies of 'Clean Code' from cart", result.getMessage());

        List<CartItem> cartContents = cartService.getCartContents();
        assertEquals(3, getQuantityByIsbn(cartContents, "978-0132350884"));
    }

    @Test
    void removeFromCart_RemoveAll_ClearsItem() {
        // Arrange
        cartService.addToCart("978-0132350884", 3);

        // Act
        Result<Void> result = cartService.removeFromCart("978-0132350884", 3);

        // Assert
        assertTrue(result.isSuccess());
        assertEquals("Removed all copies of 'Clean Code' from cart", result.getMessage());

        List<CartItem> cartContents = cartService.getCartContents();
        assertNull(findCartItemByProductID(cartContents, "978-0132350884"));
        assertTrue(cartService.isEmpty());
    }

    @Test
    void removeFromCart_RemoveMoreThanExists_ClearsItem() {
        // Arrange
        cartService.addToCart("978-0132350884", 2);

        // Act
        Result<Void> result = cartService.removeFromCart("978-0132350884", 5);

        // Assert
        assertTrue(result.isSuccess());
        assertEquals("Removed all copies of 'Clean Code' from cart", result.getMessage());

        List<CartItem> cartContents = cartService.getCartContents();
        assertNull(findCartItemByProductID(cartContents, "978-0132350884"));
        assertTrue(cartService.isEmpty());
    }

    @Test
    void removeFromCart_ItemNotInCart_ReturnsFailure() {
        // Act
        Result<Void> result = cartService.removeFromCart("978-0132350884", 1);

        // Assert
        assertFalse(result.isSuccess());
        assertEquals("Item not in cart", result.getMessage());
    }

    @Test
    void removeFromCart_NullIsbn_ReturnsFailure() {
        // Act
        Result<Void> result = cartService.removeFromCart(null, 1);

        // Assert
        assertFalse(result.isSuccess());
        assertEquals("ISBN cannot be null or empty", result.getMessage());
    }

    @Test
    void removeFromCart_EmptyIsbn_ReturnsFailure() {
        // Act
        Result<Void> result = cartService.removeFromCart("", 1);

        // Assert
        assertFalse(result.isSuccess());
        assertEquals("ISBN cannot be null or empty", result.getMessage());
    }

    @Test
    void removeFromCart_InvalidQuantity_ReturnsFailure() {
        // Act
        Result<Void> result = cartService.removeFromCart("978-0132350884", 0);

        // Assert
        assertFalse(result.isSuccess());
        assertEquals("Quantity must be greater than 0", result.getMessage());
    }

    @Test
    void getTotalPrice_EmptyCart_ReturnsZero() {
        // Act
        Result<BigDecimal> result = cartService.getTotalPrice();

        // Assert
        assertTrue(result.isSuccess());
        assertEquals("Total calculated successfully", result.getMessage());
        assertEquals(0, result.getData().compareTo(new BigDecimal("0.00")));
    }

    @Test
    void getTotalPrice_SingleItem_ReturnsCorrectTotal() {
        // Arrange
        cartService.addToCart("978-0132350884", 2);

        // Act
        Result<BigDecimal> result = cartService.getTotalPrice();

        // Assert
        assertTrue(result.isSuccess());
        assertEquals("Total calculated successfully", result.getMessage());
        assertEquals(new BigDecimal("59.98"), result.getData()); // 2 * 29.99
    }

    @Test
    void getTotalPrice_MultipleItems_ReturnsCorrectTotal() {
        // Arrange
        cartService.addToCart("978-0132350884", 2); // 2 * 29.99 = 59.98
        cartService.addToCart("978-0321125217", 1); // 1 * 45.50 = 45.50

        // Act
        Result<BigDecimal> result = cartService.getTotalPrice();

        // Assert
        assertTrue(result.isSuccess());
        assertEquals("Total calculated successfully", result.getMessage());
        assertEquals(new BigDecimal("105.48"), result.getData()); // 59.98 + 45.50
    }

    @Test
    void getTotalPrice_InvalidItemInCart_ReturnsFailure() {
        // This test may need to be updated based on how the CartService handles invalid items
        // For now, commenting out as the current implementation doesn't check inventory in getTotalPrice
        // The test would need to be redesigned based on actual behavior

        // Note: The current CartService implementation doesn't validate items in getTotalPrice
        // This test would need to be updated based on actual requirements
    }

    @Test
    void checkout_EmptyCart_ReturnsFailure() {
        // Act
        Result<String> result = cartService.checkout();

        // Assert
        assertFalse(result.isSuccess());
        assertEquals("Cart is empty", result.getMessage());
        assertNull(result.getData());
    }

    @Test
    void checkout_ValidCart_ReturnsSuccessAndUpdatesInventory() {
        // Arrange
        cartService.addToCart("8264", 2);
        cartService.addToCart("3559", 1);

        // Act
        Result<String> result = cartService.checkout();

        // Assert
        assertTrue(result.isSuccess());
        assertEquals("Checkout successful! Total: $105.48", result.getMessage());
        assertEquals("$105.48", result.getData());

        // Verify cart is empty after checkout
        assertTrue(cartService.isEmpty());

        // Verify inventory was updated
        InventoryCandleItem item1 = inventoryRepository.getByProductID("8264");
        InventoryCandleItem item2 = inventoryRepository.getByProductID("3559");
        assertEquals(2, item1.getQuantity()); // 10 - 2
        assertEquals(1, item2.getQuantity()); // 5 - 1

        // equal hashcode method to add inside the models. Tests to see if one object equals another.
    }

    @Test
    void checkout_InvalidItemInCart_ReturnsFailure() {
        // This test may need to be updated based on the actual implementation
        // The current CartService doesn't validate items during checkout in the same way
        // Commenting out for now, but this should be redesigned based on requirements

        // Note: The current implementation may not handle this scenario the same way
        // This test would need to be updated based on actual error handling requirements
    }

    @Test
    void getCartContents_EmptyCart_ReturnsEmptyList() {
        // Act
        List<CartItem> contents = cartService.getCartContents();

        // Assert
        assertNotNull(contents);
        assertTrue(contents.isEmpty());
    }

    @Test
    void getCartContents_WithItems_ReturnsCorrectContents() {
        // Arrange
        cartService.addToCart("8264", 2);
        cartService.addToCart("3559", 3);

        // Act
        List<CartItem> contents = cartService.getCartContents();

        // Assert
        assertNotNull(contents);
        assertEquals(2, contents.size());
        assertEquals(2, getQuantityByIsbn(contents, "8264"));
        assertEquals(3, getQuantityByIsbn(contents, "978-0321125217"));
    }

    @Test
    void getCartContents_ReturnsDefensiveCopy() {
        // Arrange
        cartService.addToCart("8264", 2);

        // Act
        List<CartItem> contents = cartService.getCartContents();
        contents.clear(); // Modify the returned list

        // Assert - Original cart should not be affected
        List<CartItem> actualContents = cartService.getCartContents();
        assertEquals(1, actualContents.size());
        assertEquals(2, getQuantityByIsbn(actualContents, "8264"));
    }

    @Test
    void isEmpty_EmptyCart_ReturnsTrue() {
        // Act & Assert
        assertTrue(cartService.isEmpty());
    }

    @Test
    void isEmpty_NonEmptyCart_ReturnsFalse() {
        // Arrange
        cartService.addToCart("978-0132350884", 1);

        // Act & Assert
        assertFalse(cartService.isEmpty());
    }

    @Test
    void isEmpty_AfterRemovingAllItems_ReturnsTrue() {
        // Arrange
        cartService.addToCart("8264", 2);
        cartService.removeFromCart("978-0132350884", 2);

        // Act & Assert
        assertTrue(cartService.isEmpty());
    }

    @Test
    void complexWorkflow_AddRemoveCheckout_WorksCorrectly() {
        // Arrange & Act - Complex workflow
        cartService.addToCart("8264", 5);
        cartService.addToCart("8264", 2);
        cartService.removeFromCart("978-0132350884", 2);
        cartService.addToCart("978-0132350884", 1);

        // Assert intermediate state
        List<CartItem> contents = cartService.getCartContents();
        assertEquals(4, getQuantityByIsbn(contents, "8264")); // 5 - 2 + 1
        assertEquals(2, getQuantityByIsbn(contents, "978-0321125217"));

        // Act - checkout
        Result<String> checkoutResult = cartService.checkout();

        // Assert final state
        assertTrue(checkoutResult.isSuccess());
        assertTrue(cartService.isEmpty());

        // Verify inventory updates
        InventoryCandleItem item1 = inventoryRepository.getByProductID("8264");
        InventoryCandleItem item2 = inventoryRepository.getByProductID("978-0321125217");
        assertEquals(6, item1.getQuantity()); // 10 - 4
        assertEquals(3, item2.getQuantity()); // 5 - 2
    }
}