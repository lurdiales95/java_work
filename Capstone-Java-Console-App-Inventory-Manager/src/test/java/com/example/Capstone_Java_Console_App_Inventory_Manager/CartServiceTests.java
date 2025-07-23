
package com.example.Capstone_Java_Console_App_Inventory_Manager;

import com.example.Capstone_Java_Console_App_Inventory_Manager.model.*;
import com.example.Capstone_Java_Console_App_Inventory_Manager.repository.*;
import com.example.Capstone_Java_Console_App_Inventory_Manager.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CartServiceTests {
    private InventoryRepository inventoryRepository;
    private CartService cartService;

    // Test data
    private Candle candle1;
    private Candle candle2;
    private InventoryCandleItem inventoryItem1;
    private InventoryCandleItem inventoryItem2;

    @BeforeEach
    void setUp() {
        // Create fresh instances for each test
        inventoryRepository = new InMemoryInventoryRepository();
        cartService = new CartService(inventoryRepository);

        candle1 = new Candle("5905", "Japanese Cherry Blossom", "Botanical & Blooms", "Year Round");
        candle2 = new Candle("6757", "Sweater Weather", "Fresh & Clean", "Winter");

        // FIXED: Create both inventory items properly
        inventoryItem1 = new InventoryCandleItem(candle1, 10, new BigDecimal("26.95"));
        inventoryItem2 = new InventoryCandleItem(candle2, 5, new BigDecimal("24.99"));

        // Add items to inventory
        inventoryRepository.add(inventoryItem1);
        inventoryRepository.add(inventoryItem2);
    }

    // Helper method to find CartItem by ProductID
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
        Result<Void> result = cartService.addToCart("5905", 2);

        System.out.println(result.isSuccess());
        // Assert
        assertTrue(result.isSuccess());
        assertEquals("Added 2 candles of 'Japanese Cherry Blossom' to cart", result.getMessage());

        // Verify cart contents
        List<CartItem> cartContents = cartService.getCartContents();
        assertEquals(2, getQuantityByProductID(cartContents, "5905"));
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
        Result<Void> result = cartService.addToCart("5905", 0);

        // Assert
        assertFalse(result.isSuccess());
        assertEquals("Quantity must be greater than 0", result.getMessage());
        assertTrue(cartService.isEmpty());
    }

    @Test
    void addToCart_NegativeQuantity_ReturnsFailure() {
        // Act
        Result<Void> result = cartService.addToCart("5905", -1);

        // Assert
        assertFalse(result.isSuccess());
        assertEquals("Quantity must be greater than 0", result.getMessage());
        assertTrue(cartService.isEmpty());
    }

    @Test
    void addToCart_BookNotFound_ReturnsFailure() {
        // Act
        Result<Void> result = cartService.addToCart("invalid-id", 1);

        // Assert
        assertFalse(result.isSuccess());
        assertEquals("Product not found with ProductID invalid-id", result.getMessage());
        assertTrue(cartService.isEmpty());
    }

    @Test
    void addToCart_InsufficientStock_ReturnsFailure() {
        // Act - trying to add more than available (10 in stock)
        Result<Void> result = cartService.addToCart("5905", 15);

        // Assert
        assertFalse(result.isSuccess());
        assertEquals("Not enough stock. Available: 10, Requested: 15", result.getMessage());
        assertTrue(cartService.isEmpty());
    }

    @Test
    void addToCart_ExactStockQuantity_ReturnsSuccess() {
        // Act - adding exactly what's in stock
        Result<Void> result = cartService.addToCart("5905", 10);

        // Assert
        assertTrue(result.isSuccess());
        assertEquals("Added 10 candles of 'Japanese Cherry Blossom' to cart", result.getMessage());

        List<CartItem> cartContents = cartService.getCartContents();
        assertEquals(10, getQuantityByProductID(cartContents, "5905"));
    }

    @Test
    void addToCart_MultipleAdds_AccumulatesQuantity() {
        // Act
        Result<Void> result1 = cartService.addToCart("5905", 3);
        Result<Void> result2 = cartService.addToCart("5905", 2);

        // Assert
        assertTrue(result1.isSuccess());
        assertTrue(result2.isSuccess());


        assertEquals("Added 2 candles of 'Japanese Cherry Blossom' to cart", result2.getMessage());

        List<CartItem> cartContents = cartService.getCartContents();
        assertEquals(5, getQuantityByProductID(cartContents, "5905"));
    }

    @Test
    void addToCart_AccumulatedQuantityExceedsStock_ReturnsFailure() {
        // Act
        Result<Void> result1 = cartService.addToCart("5905", 8);
        Result<Void> result2 = cartService.addToCart("5905", 5);

        // Assert
        assertTrue(result1.isSuccess());
        assertFalse(result2.isSuccess());
        assertEquals("Not enough stock. Available: 10, Requested: 13", result2.getMessage());

        // Verify cart still contains the first addition only
        List<CartItem> cartContents = cartService.getCartContents();
        assertEquals(8, getQuantityByProductID(cartContents, "5905"));
    }

    @Test
    void addToCart_MultipleDifferentBooks_ReturnsSuccess() {
        // Act
        Result<Void> result1 = cartService.addToCart("5905", 2);
        Result<Void> result2 = cartService.addToCart("6757", 1);

        // Assert
        assertTrue(result1.isSuccess());
        assertTrue(result2.isSuccess());

        List<CartItem> cartContents = cartService.getCartContents();
        assertEquals(2, getQuantityByProductID(cartContents, "5905"));
        assertEquals(1, getQuantityByProductID(cartContents, "6757"));
        assertEquals(2, cartContents.size());
    }

    @Test
    void removeFromCart_ValidRemoval_ReturnsSuccess() {
        // Arrange
        cartService.addToCart("5905", 5);

        // Act
        Result<Void> result = cartService.removeFromCart("5905", 2);

        // Assert
        assertTrue(result.isSuccess());
        assertEquals("Removed 2 candles of 'Japanese Cherry Blossom' from cart", result.getMessage());

        List<CartItem> cartContents = cartService.getCartContents();
        assertEquals(3, getQuantityByProductID(cartContents, "5905"));
    }

    @Test
    void removeFromCart_RemoveAll_ClearsItem() {
        // Arrange
        cartService.addToCart("5905", 3);

        // Act
        Result<Void> result = cartService.removeFromCart("5905", 3);

        // Assert
        assertTrue(result.isSuccess());
        assertEquals("Removed all of 'Japanese Cherry Blossom' candles from cart", result.getMessage());

        List<CartItem> cartContents = cartService.getCartContents();
        assertNull(findCartItemByProductID(cartContents, "5905"));
        assertTrue(cartService.isEmpty());
    }

    @Test
    void removeFromCart_RemoveMoreThanExists_ClearsItem() {
        // Arrange
        cartService.addToCart("5905", 2);

        // Act
        Result<Void> result = cartService.removeFromCart("5905", 5);

        // Assert
        assertTrue(result.isSuccess());
        assertEquals("Removed all of 'Japanese Cherry Blossom' candles from cart", result.getMessage());

        List<CartItem> cartContents = cartService.getCartContents();
        assertNull(findCartItemByProductID(cartContents, "5905"));
        assertTrue(cartService.isEmpty());
    }

    @Test
    void removeFromCart_ItemNotInCart_ReturnsFailure() {
        // Act
        Result<Void> result = cartService.removeFromCart("5905", 1);


        // Assert
        assertFalse(result.isSuccess());
        assertEquals("Item not in cart", result.getMessage());
    }

    @Test
    void removeFromCart_NullProductID_ReturnsFailure() {
        // Act
        Result<Void> result = cartService.removeFromCart(null, 1);

        // Assert
        assertFalse(result.isSuccess());
        assertEquals("ProductID cannot be null or empty", result.getMessage());
    }

    @Test
    void removeFromCart_EmptyProductID_ReturnsFailure() {
        // Act
        Result<Void> result = cartService.removeFromCart("", 1);

        // Assert
        assertFalse(result.isSuccess());
        assertEquals("ProductID cannot be null or empty", result.getMessage());
    }

    @Test
    void removeFromCart_InvalidQuantity_ReturnsFailure() {
        // Act
        Result<Void> result = cartService.removeFromCart("5905", 0);

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
        cartService.addToCart("5905", 2);

        // Act
        Result<BigDecimal> result = cartService.getTotalPrice();

        // Assert
        assertTrue(result.isSuccess());
        assertEquals("Total calculated successfully", result.getMessage());
        assertEquals(new BigDecimal("53.90"), result.getData());
    }

    @Test
    void getTotalPrice_MultipleItems_ReturnsCorrectTotal() {
        // Arrange
        cartService.addToCart("5905", 2); // 2 * 26.95 = 53.90
        cartService.addToCart("6757", 1); // 1 * 24.99 = 24.99
        // Act
        Result<BigDecimal> result = cartService.getTotalPrice();

        // Assert
        assertTrue(result.isSuccess());
        assertEquals("Total calculated successfully", result.getMessage());
        assertEquals(new BigDecimal("78.89"), result.getData()); // 53.90 + 24.99
    }

    @Test
    void getTotalPrice_InvalidItemInCart_ReturnsFailure() {
//        cartService.addToCart("5905", 2); // 2 * 26.95 = 53.90
//        cartService.addToCart("6757", 1); // 1 * 24.99 = 24.99
//
//        Result<BigDecimal> result = cartService.getTotalPrice();
//        assertTrue(result.isFailure());
//        assertEquals("Total calculated successfully"), result.getMessage();
//        assertEquals(BigDecimal(77.77), result.getData());
//
//    }

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
        cartService.addToCart("5905", 2);
        cartService.addToCart("6757", 1);

        // Act
        Result<String> result = cartService.checkout();

        // Assert
        assertTrue(result.isSuccess());
        assertEquals("Checkout successful! Total: $78.89", result.getMessage());
        assertEquals("$78.89", result.getData());


        // Verify cart is empty after checkout
        assertTrue(cartService.isEmpty());

        // Verify inventory was updated
        InventoryCandleItem item1 = inventoryRepository.getByProductID("5905");
        InventoryCandleItem item2 = inventoryRepository.getByProductID("6757");
        assertEquals(8, item1.getQuantity()); // 10 - 2 = 8
        assertEquals(4, item2.getQuantity()); // 5 - 1 = 4

        // equal hashcode method to add inside the models. Tests to see if one object equals another.
    }

//    @Test
//    void checkout_InvalidItemInCart_ReturnsFailure() {
//        // This test may need to be updated based on the actual implementation
//        // This CartService doesn't validate items during checkout in the same way
//        // Should be redesigned based on requirements
//        // Note:This implementation may not handle this scenario the same way
//        // This test would need to be updated based on actual error handling requirements
//    }

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
        cartService.addToCart("5905", 2);
        cartService.addToCart("6757", 3);

        // Act
        List<CartItem> contents = cartService.getCartContents();

        // Assert
        assertNotNull(contents);
        assertEquals(2, contents.size());
        assertEquals(2, getQuantityByProductID(contents, "5905"));
        assertEquals(3, getQuantityByProductID(contents, "6757"));
    }

    @Test
    void getCartContents_ReturnsDefensiveCopy() {
        // Arrange
        cartService.addToCart("5905", 2);

        // Act
        List<CartItem> contents = cartService.getCartContents();
        contents.clear(); // Modify the returned list

        // Assert - Original cart should not be affected
        List<CartItem> actualContents = cartService.getCartContents();
        assertEquals(1, actualContents.size());
        assertEquals(2, getQuantityByProductID(actualContents, "5905"));
    }

    @Test
    void isEmpty_EmptyCart_ReturnsTrue() {
        // Act & Assert
        assertTrue(cartService.isEmpty());
    }

    @Test
    void isEmpty_NonEmptyCart_ReturnsFalse() {
        // Arrange
        cartService.addToCart("5905", 1);

        // Act & Assert
        assertFalse(cartService.isEmpty());
    }

    @Test
    void isEmpty_AfterRemovingAllItems_ReturnsTrue() {
        // Arrange
        cartService.addToCart("5905", 2);
        cartService.removeFromCart("5905", 2);

        // Act & Assert
        assertTrue(cartService.isEmpty());
    }

    @Test
    void complexWorkflow_AddRemoveCheckout_WorksCorrectly() {
        // Arrange & Act - Complex workflow
        cartService.addToCart("5905", 5);
        cartService.addToCart("6757", 2);
        cartService.removeFromCart("5905", 2);
        cartService.addToCart("5905", 1);

        // Assert intermediate state
        List<CartItem> contents = cartService.getCartContents();
        assertEquals(4, getQuantityByProductID(contents, "5905")); // 5 - 2 + 1
        assertEquals(2, getQuantityByProductID(contents, "6757"));

        // Act - checkout
        Result<String> checkoutResult = cartService.checkout();

        // Assert final state
        assertTrue(checkoutResult.isSuccess());
        assertTrue(cartService.isEmpty());

        // Verify inventory updates
        InventoryCandleItem item1 = inventoryRepository.getByProductID("5905");
        InventoryCandleItem item2 = inventoryRepository.getByProductID("6757");
        assertEquals(6, item1.getQuantity()); // 10 - 4
        assertEquals(3, item2.getQuantity()); // 5 - 2
    }

    @Test
    void addToCart_DifferentScentTypes_WorksCorrectly() {
        Candle floralCandle = new Candle("1001", "Rose Petals", "Botanical & Blooms", "Spring");
        Candle fruitCandle = new Candle("1002", "Orange Citrus", "Fruity & Bright", "Summer");

        InventoryCandleItem floralItem = new InventoryCandleItem(floralCandle, 15, new BigDecimal("22.50"));
        InventoryCandleItem fruitItem = new InventoryCandleItem(fruitCandle, 8, new BigDecimal("28.75"));

        inventoryRepository.add(floralItem);
        inventoryRepository.add(fruitItem);

        Result<Void> result1 = cartService.addToCart("1001", 3);
        Result<Void> result2 = cartService.addToCart("1002", 2);

        assertTrue(result1.isSuccess());
        assertTrue(result2.isSuccess());

        List<CartItem> contents = cartService.getCartContents();
        assertEquals(2, contents.size());
        assertEquals(3, getQuantityByProductID(contents, "1001"));
        assertEquals(2, getQuantityByProductID(contents, "1002"));

    }
    @Test
    void addToCart_SeasonalCandles_WorksCorrectly() {

        Candle winterCandle = new Candle("2001", "Winter Wonderland", "Fresh & Clean", "Winter");
        Candle yearRoundCandle = new Candle("2002", "Vanilla Dream", "Treats & Sweets", "Year Round");

        InventoryCandleItem winterItem = new InventoryCandleItem(winterCandle, 20, new BigDecimal("31.99"));
        InventoryCandleItem yearRoundItem = new InventoryCandleItem(yearRoundCandle, 25, new BigDecimal("19.99"));

        inventoryRepository.add(winterItem);
        inventoryRepository.add(yearRoundItem);

        Result<Void> result1 =cartService.addToCart("2001", 2);
        Result<Void> result2 = cartService.addToCart("2002", 4);

        assertTrue(result1.isSuccess());
        assertTrue(result2.isSuccess());

        List<CartItem> contents = cartService.getCartContents();
        assertEquals(2, getQuantityByProductID(contents, "2001"));
        assertEquals(4, getQuantityByProductID(contents, "2002"));
    }
}