package com.shoppinginc.ui;

import com.shoppinginc.model.OrderItem;
import com.shoppinginc.service.ICartService;

import java.util.Scanner;

public class TerminalUtils {
    // Reads user's response
    private final Scanner scanner = new Scanner(System.in);
    // Stores item info on cart list
    private final ICartService cartService;

    public TerminalUtils(ICartService cartService) {
        this.cartService = cartService;
    }
    // Main loop
    public void run() {
        boolean running = true;

        while (running) {
            showMenu();
            String choice = scanner.nextLine().trim();

            // Menu selections based on numerical String
            switch (choice) {
                case "1" -> displayCart();
                case "2" -> removeItem();
                case "3" -> addItem();
                case "4" -> checkout();
                case "5" -> running = false;
                default -> System.out.println("Invalid option. Try again.");
            }

            // Command to signal that the user wants app to stop running
            if (!choice.equals("5")) {
                promptEnterKey();
            }
        }

        // Response
        System.out.println("Thank you for using Shopping Inc. Goodbye!");
    }

    // Print version constructing what the app will look like to users
    private void showMenu() {
        System.out.println("\n--- CASH REGISTER MAIN MENU ---");
        System.out.println("1. Display Cart");
        System.out.println("2. Remove an Item");
        System.out.println("3. Add an Item");
        System.out.println("4. Checkout");
        System.out.println("5. Exit");
        System.out.print("Please select an option (1-5): ");
    }

    // Shows user what items are in the cart
    protected void displayCart() {
        System.out.println("\n--- CART ITEMS ---");

        if (cartService.isEmpty()) {
            System.out.println("Your cart is currently empty.");
        } else {
            for (OrderItem item : cartService.getAllItems()) {
                System.out.println(item);
            }

            System.out.printf("Total: $%.2f%n", cartService.getCartTotal());
        }
    }

    // Intakes the details of the items that will be added to the cart and adds them to the cart.
    protected void addItem() {
        System.out.print("Enter item ID: ");
        String id = scanner.nextLine().trim();

        System.out.print("Enter item name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Enter quantity: ");
        int quantity = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Enter price per item: ");
        double price = Double.parseDouble(scanner.nextLine().trim());

        // Adds product info to cartService
        OrderItem item = new OrderItem(id, quantity, name, price);
        cartService.addItem(item);
        // Shows that the item has been added successfully.
        System.out.println("Item added.");
    }

    // Allows user to remove item from cart. Asks what item to remove based on ID and quantity.
    // name of item not necessary since the IDs should be unique to each item.
    protected void removeItem() {
        displayCart();

        if (cartService.isEmpty()) return;

        System.out.print("Enter the item ID to remove: ");
        String id = scanner.nextLine().trim();

        System.out.print("Enter quantity to remove: ");
        int qtyToRemove = Integer.parseInt(scanner.nextLine().trim());

        cartService.removeItem(id, qtyToRemove);
        System.out.println("Item removed.");
    }


    protected void checkout() {
        if (cartService.isEmpty()) {
            System.out.println("Cart is empty. Nothing to checkout.");
        } else {
            System.out.printf("Final total: $%.2f%n", cartService.getCartTotal());
            cartService.clearCart();
            System.out.println("Thank you for your purchase! Come again soon!");
        }
    }

    private void promptEnterKey() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }
}