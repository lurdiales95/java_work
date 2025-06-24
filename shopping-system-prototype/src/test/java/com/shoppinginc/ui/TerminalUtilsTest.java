package com.shoppinginc.ui;

import com.shoppinginc.model.OrderItem;
import com.shoppinginc.service.ICartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.*;
import java.util.Map;

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
    void testDisplayCart_WithItems() {
        when(mockCartService.isEmpty()).thenReturn(false);
        when(mockCartService.getOrderItems()).thenReturn(Map.of(
                "A1", new OrderItem("A1", 2, "Laptop",  1000.00)
        ));

        when(mockCartService.getCartTotal()).thenReturn(2.0);

        terminal.displayCart();

        String output = outContent.toString();
        assertTrue(output.contains("Apple"));
        assertTrue(output.contains("Total: $1000.00"));

    }

    @Test
    void testPromptREmoveItem_NotFound() {
        String userInput = "X1\n1\n";
        System.setIn(new ByteArrayInputStream(userInput.getBytes()));

        when(mockCartService.isEmpty()).thenReturn(false);
        when(mockCartService.removeItem("X1",1)).thenReturn(false);


        TerminalUtils inputTerminal = new TerminalUtils(mockCartService);
        inputTerminal.promptRemoveItem();

        String output = outContent.toString();
        assertTrue(output.contains("Item not found."));
    }

    @Test
    void testCheckoutWithItems() {
        when(mockCartService.isEmpty()).thenReturn(false);
        when(mockCartService.getCartTotal()).thenReturn(9.99);

        terminal.checkout();

        verify(mockCartService, times(1).clearCart();
        String output = outContent.toString();
        assertTrue(output.contains("Final total: $9.99"));
        assertTrue(output.contains("Your purchase is complete."));
    }


    @Test
    void TestCheckoutEmptyCart() {
        when(mockCartService.isEmpty()).thenReturn(true);

        terminal.checkout();

        String output = outContent.toString();
        assertTrue(output.contains("Cart is empty. Nothing to checkout."));

    }

}
