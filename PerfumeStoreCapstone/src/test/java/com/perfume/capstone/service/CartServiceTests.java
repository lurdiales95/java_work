package com.perfume.capstone.service;

import com.perfume.capstone.model.*;
import com.perfume.capstone.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CartServiceTests {

    private PerfumeRepository perfumeRepository;
    private CartService cartService;

    // Test data
    private Perfume perfume1;
    private Perfume perfume2;
    private InventoryPerfumeItem inventoryItem1;
    private InventoryPerfumeItem inventoryItem2;

    @BeforeEach
    void setUp() {
        // Create fresh instances for each test
        perfumeRepository = new InMemoryPerfumeRepository();
        cartService = new CartService(perfumeRepository);

        // Create test perfumes using your exact 4-field structure
        perfume1 = new Perfume("5905", "Japanese Cherry Blossom", "Botanical & Blooms", "Year Round");
        perfume2 = new Perfume("6757", "Sweater Weather", "Fresh & Clean", "Winter");

        // Create inventory items using your exact class name
        inventoryItem1 = new InventoryPerfumeItem(perfume1, 10, new BigDecimal("26.95"));
        inventoryItem2 = new InventoryPerfumeItem(perfume2, 5, new BigDecimal("24.99"));

        // Add items to inventory using your exact method names
        perfumeRepository.add(inventoryItem1);
        perfumeRepository.add(inventoryItem2);
    }

    // Helper method to find CartItem by ProductID using your exact field names
    private CartItem findCartItemByProductID(List<CartItem> cartItems, String productID) {
        return cartItems.stream()
                .filter(item -> item.getPerfume().productID().equals(productID))
                .findFirst()
                .orElse(null);
    }

    // Helper method to get quantity for a ProductID from cart contents
    private int getQuantityByProductID(List<CartItem> cartItems, String productID) {
        CartItem item = findCartItemByProductID(cartItems, productID);
        return item != null ? item.getQuantity() : 0;
    }

    @Test
    void addToCart_ValidItem_ReturnsSuccess() {
        // Act
        Result<Void> result = cartService.addToCart("5905", 2);

        System.out.println(result.isSuccess());
        // Assert - using your exact success message format
        assertTrue(result.isSuccess());
        assertEquals("Added 2 perfumes of 'Japanese Cherry Blossom' to cart", result.getMessage());

        // Verify cart contents
        List<CartItem> cartContents = cartService.getCartContents();
        assertEquals(2, getQuantityByProductID(cartContents, "5905"));
        assertFalse(cartService.isEmpty());
    }

    @Test
    void addToCart_NullProductID_ReturnsFailure() {
        // Act
        Result<Void> result = cartService.addToCart(null, 1);

        // Assert - using your exact error message
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
    void addToCart_ProductNotFound_ReturnsFailure() {
        // Act
        Result<Void> result = cartService.addToCart("invalid-id", 1);

        // Assert - using your exact error message format
        assertFalse(result.isSuccess());
        assertEquals("Product not found with ProductID invalid-id", result.getMessage());
        assertTrue(cartService.isEmpty());
    }

    @Test
    void addToCart_InsufficientStock_ReturnsFailure() {
        // Act - trying to add more than available (10 in stock)
        Result<Void> result = cartService.addToCart("5905", 15);

        // Assert - using your exact error message format
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
        assertEquals("Added 10 perfumes of 'Japanese Cherry Blossom' to cart", result.getMessage());

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

        assertEquals("Added 2 perfumes of 'Japanese Cherry Blossom' to cart", result2.getMessage());

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
    void addToCart_MultipleDifferentPerfumes_ReturnsSuccess() {
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

        // Assert - using your exact success message format
        assertTrue(result.isSuccess());
        assertEquals("Removed 2 perfumes of 'Japanese Cherry Blossom' from cart", result.getMessage());

        List<CartItem> cartContents = cartService.getCartContents();
        assertEquals(3, getQuantityByProductID(cartContents, "5905"));
    }

    @Test
    void removeFromCart_RemoveAll_ClearsItem() {
        // Arrange
        cartService.addToCart("5905", 3);

        // Act
        Result<Void> result = cartService.removeFromCart("5905", 3);

        // Assert - using your exact success message format
        assertTrue(result.isSuccess());
        assertEquals("Removed all of 'Japanese Cherry Blossom' perfumes from cart", result.getMessage());

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
        assertEquals("Removed all of 'Japanese Cherry Blossom' perfumes from cart", result.getMessage());

        List<CartItem> cartContents = cartService.getCartContents();
        assertNull(findCartItemByProductID(cartContents, "5905"));
        assertTrue(cartService.isEmpty());
    }

    @Test
    void removeFromCart_ItemNotInCart_ReturnsFailure() {
        // Act
        Result<Void> result = cartService.removeFromCart("5905", 1);

        // Assert - using your exact error message
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

        // Assert - using your exact success message
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
    void checkout_EmptyCart_ReturnsFailure() {
        // Act
        Result<String> result = cartService.checkout();

        // Assert - using your exact error message
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

        // Assert - using your exact success message format
        assertTrue(result.isSuccess());
        assertEquals("Checkout successful! Total: $78.89", result.getMessage());
        assertEquals("$78.89", result.getData());

        // Verify cart is empty after checkout
        assertTrue(cartService.isEmpty());

        // Verify inventory was updated using your exact method names
        InventoryPerfumeItem item1 = perfumeRepository.getByProductID("5905");
        InventoryPerfumeItem item2 = perfumeRepository.getByProductID("6757");
        assertEquals(8, item1.getQuantity()); // 10 - 2 = 8
        assertEquals(4, item2.getQuantity()); // 5 - 1 = 4
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

        // Verify inventory updates using your exact method names
        InventoryPerfumeItem item1 = perfumeRepository.getByProductID("5905");
        InventoryPerfumeItem item2 = perfumeRepository.getByProductID("6757");
        assertEquals(6, item1.getQuantity()); // 10 - 4
        assertEquals(3, item2.getQuantity()); // 5 - 2
    }

    @Test
    void addToCart_DifferentScentTypes_WorksCorrectly() {
        // Create perfumes with different scent types using your exact 4-field structure
        Perfume floralPerfume = new Perfume("1001", "Rose Petals", "Botanical & Blooms", "Spring");
        Perfume fruitPerfume = new Perfume("1002", "Orange Citrus", "Fruity & Bright", "Summer");

        InventoryPerfumeItem floralItem = new InventoryPerfumeItem(floralPerfume, 15, new BigDecimal("22.50"));
        InventoryPerfumeItem fruitItem = new InventoryPerfumeItem(fruitPerfume, 8, new BigDecimal("28.75"));

        perfumeRepository.add(floralItem);
        perfumeRepository.add(fruitItem);

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
    void addToCart_SeasonalPerfumes_WorksCorrectly() {
        // Create seasonal perfumes using your exact structure
        Perfume winterPerfume = new Perfume("2001", "Winter Wonderland", "Fresh & Clean", "Winter");
        Perfume yearRoundPerfume = new Perfume("2002", "Vanilla Dream", "Treats & Sweets", "Year Round");

        InventoryPerfumeItem winterItem = new InventoryPerfumeItem(winterPerfume, 20, new BigDecimal("31.99"));
        InventoryPerfumeItem yearRoundItem = new InventoryPerfumeItem(yearRoundPerfume, 25, new BigDecimal("19.99"));

        perfumeRepository.add(winterItem);
        perfumeRepository.add(yearRoundItem);

        Result<Void> result1 = cartService.addToCart("2001", 2);
        Result<Void> result2 = cartService.addToCart("2002", 4);

        assertTrue(result1.isSuccess());
        assertTrue(result2.isSuccess());

        List<CartItem> contents = cartService.getCartContents();
        assertEquals(2, getQuantityByProductID(contents, "2001"));
        assertEquals(4, getQuantityByProductID(contents, "2002"));
    }
}