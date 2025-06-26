package com.shoppinginc.ui;

import com.shoppinginc.model.OrderItem;
import com.shoppinginc.service.ICartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

// test to see if UI works for accurately
public class TerminalUtilsTest {
    private ICartService mockCartService;
    private ByteArrayOutputStream outContent;
    private TerminalUtils terminal;

    @BeforeEach
    void setUp() {
        //fake cart created
        mockCartService = mock(ICartService.class);
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        // creates a new TerminalUtils to do the test
        terminal = new TerminalUtils(mockCartService);
    }

    @Test
    void testDisplayCart_Empty() {

        // checks to see if fake cart is empty, if true
        when(mockCartService.isEmpty()).thenReturn(true);
        //  used to start the request to displayCart
        terminal.displayCart();

        // notifies if the cart is empty and passes the test if true
        assertTrue(outContent.toString().contains("Your cart is currently empty."));
    }

    @Test
    // tests to see if the AddItem feature works
    void testAddItem() {
        // info for item to add
        String simulatedInput = "A1\nLaptop\n2\n1000.00\n";
        // creates simulated item
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        // adds item to fake cart
        terminal.addItem();

        // checks to make sure all the item details were properly added
        verify(mockCartService).addItem(argThat(item ->
                item.getId().equals("A1") &&
                        item.getName().equals("Laptop") &&
                        item.getQuantity() == 2 &&
                        item.getPrice() == 1000.00
        ));

        // returns true of the item was added in cart by giving notification "Item added."
        String output = outContent.toString();
        assertTrue(output.contains("Item added."));
    }

    @Test
    // checks to see if items are displayed to user appropriately
    void testDisplayCart_WithItems() {
        // mock item
        OrderItem item = new OrderItem("A1", 2, "Laptop", 1000.00);
        when(mockCartService.isEmpty()).thenReturn(false);
        when(mockCartService.getAllItems()).thenReturn(List.of(item));
        when(mockCartService.getCartTotal()).thenReturn(2000.00);

        // checks item to be display
        terminal.displayCart();

        // provides true if contents are as presented
        String output = outContent.toString();
        assertTrue(output.contains("Laptop"));
        assertTrue(output.contains("Total: $2000.00"));
    }

    @Test
    // tests if items were removed correctly
    void testRemoveItem_PromptsCorrectly_WhenCartEmptyOrItemMissing() {
        // simulates item info product X1 with qty of 1
        String simulatedInput = "X1\n1\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        // removes item identified above with id: X1 and qty 1
        when(mockCartService.removeItem("X1", 1)).thenReturn(false);

        // checks if the .isEmpty() works correctly
        when(mockCartService.isEmpty()).thenReturn(false);

        // gives list of items if not empty
        when(mockCartService.getAllItems()).thenReturn(List.of());

        terminal.removeItem();

        String output = outContent.toString();
        // checks to make sure the user is appropriately asked what item they wish to remove
        assertTrue(output.contains("Enter the item ID to remove:"));
        // makes sure that the outputs are done accordingly based on removed, empty, or item not found
        assertTrue(output.contains("Item removed.") || output.contains("Your cart is currently empty.") || output.contains("Item not found."));
    }

    @Test
    // makes sure items get checked out correctly from cart
    void testCheckoutWithItems() {
        // if empty returns false response, which is "Your cart is empty"
        when(mockCartService.isEmpty()).thenReturn(false);
        // asks the mock to get the total cost of items if there are items
        when(mockCartService.getCartTotal()).thenReturn(9.99);

        terminal.checkout();

        // tests with one item to provide total, provide a conclusion notification, then clears cart
        verify(mockCartService, times(1)).clearCart();
        String output = outContent.toString();
        assertTrue(output.contains("Final total: $9.99"));
        assertTrue(output.contains("Thank you for your purchase!"));
    }

    @Test
    // checks to see if cart is empty
    void testCheckoutEmptyCart() {
        // if empty in this situation, the test will come out as true
        when(mockCartService.isEmpty()).thenReturn(true);

        terminal.checkout();

        String output = outContent.toString();
        // checks to make sure the notification is cone correctly to announce an empty cart that can't checkout items
        assertTrue(output.contains("Cart is empty. Nothing to checkout."));
    }
}