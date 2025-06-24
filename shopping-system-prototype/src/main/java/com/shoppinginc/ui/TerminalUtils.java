package com.shoppinginc.ui;

import com.shoppinginc.model.OrderItem;
import com.shoppinginc.service.CartService;

import java.util.Scanner;

public class TerminalUtils {

    private final Scanner scanner = new Scanner(System.in);
    private final CartService cartService = new CartService();

    public void run() {
        boolean running = true;

        while (running) {
            showMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> displayCart();
                case "2" -> removeItem();
                case "3" -> addItem();
                case "4" -> checkout();
                case "5" -> running = false;
                default -> System.out.println("Invalid option. Try again.");
            }

            if (!choice.equals("5")) {
                promptEnterKey();
            }
        }

        System.out.println("Thank you for using Shopping Inc. Goodbye!");
    }

    private void showMenu() {
        System.out.println("\n--- CASH REGISTER MAIN MENU ---");
        System.out.println("1. Display Cart");
        System.out.println("2. Remove an Item");
        System.out.println("3. Add an Item");
        System.out.println("4. Checkout");
        System.out.println("5. Exit");
        System.out.print("Please select an option (1-5): ");
    }

    private void displayCart() {
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

    private void addItem() {
        System.out.print("Enter item ID: ");
        String id = scanner.nextLine().trim();

        System.out.print("Enter item name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Enter quantity: ");
        int quantity = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Enter price per item: ");
        double price = Double.parseDouble(scanner.nextLine().trim());

        OrderItem item = new OrderItem(id, quantity, name, price);
        cartService.addItem(item);
        System.out.println("Item added.");
    }

    private void removeItem() {
        displayCart();

        if (cartService.isEmpty()) return;

        System.out.print("Enter the item ID to remove: ");
        String id = scanner.nextLine().trim();

        System.out.print("Enter quantity to remove: ");
        int qtyToRemove = Integer.parseInt(scanner.nextLine().trim());

        cartService.removeItem(id, qtyToRemove);
        System.out.println("Item removed.");
    }

    private void checkout() {
        if (cartService.isEmpty()) {
            System.out.println("Cart is empty. Nothing to checkout.");
        } else {
            System.out.printf("Final total: $%.2f%n", cartService.getCartTotal());
            cartService.clearCart();
            System.out.println("Thank you for your purchase!");
        }
    }

    private void promptEnterKey() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }
}