package com.shoppinginc.ui;

import com.shoppinginc.model.OrderItem;
import com.shoppinginc.service.ICartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;


public class TerminalUtilsTest {
    private ICartService mockCartService;
    private ByteArrayOutputStream outContent;
    private TerminalUtils terminal;

    @BeforeEach
    void setUp() {
        mockCartService = mock(ICartService.class);
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        terminal = new TerminalUtils(mockCartService);
    }

    @Test
    void testDisplayCart_Empty() {
        when(mockCartService.isEmpty()).thenReturn(true);

        terminal.displayCart();

        assertTrue(outContent.toString().contains("Your cart is currently empty."));
    }

    @Test
    void testAddItem() {
        String simulatedInput = "A1\nLaptop\n2\n1000.00\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        terminal.addItem();

        verify(mockCartService).addItem(argThat(item ->
                item.getId().equals("A1") &&
                        item.getName().equals("Laptop") &&
                        item.getQuantity() == 2 &&
                        item.getPrice() == 1000.00
        ));

        String output = outContent.toString();
        assertTrue(output.contains("Item added."));
    }



    @Test
    void testDisplayCart_WithItems() {
        OrderItem item = new OrderItem("A1", 2, "Laptop", 1000.00);
        when(mockCartService.isEmpty()).thenReturn(false);
        when(mockCartService.getAllItems()).thenReturn(List.of(item));
        when(mockCartService.getCartTotal()).thenReturn(2000.00);

        terminal.displayCart();

        String output = outContent.toString();
        assertTrue(output.contains("Laptop"));
        assertTrue(output.contains("Total: $2000.00"));
    }

    @Test
    void testRemoveItem_PromptsCorrectly_WhenCartEmptyOrItemMissing() {
        String simulatedInput = "X1\n1\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        when(mockCartService.isEmpty()).thenReturn(false);
        when(mockCartService.getAllItems()).thenReturn(List.of());
        when(mockCartService.removeItem("X1", 1)).thenReturn(false);

        terminal.removeItem();

        String output = outContent.toString();
        assertTrue(output.contains("Enter the item ID to remove:"));
        assertTrue(output.contains("Item removed.") || output.contains("Your cart is currently empty.") || output.contains("Item not found."));
    }

    @Test
    void testCheckoutWithItems() {
        when(mockCartService.isEmpty()).thenReturn(false);
        when(mockCartService.getCartTotal()).thenReturn(9.99);

        terminal.checkout();

        verify(mockCartService, times(1)).clearCart();
        String output = outContent.toString();
        assertTrue(output.contains("Final total: $9.99"));
        assertTrue(output.contains("Thank you for your purchase!"));
    }

    @Test
    void testCheckoutEmptyCart() {
        when(mockCartService.isEmpty()).thenReturn(true);

        terminal.checkout();

        String output = outContent.toString();
        assertTrue(output.contains("Cart is empty. Nothing to checkout."));
    }
}