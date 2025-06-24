package com.shoppinginc.ui;

import com.shoppinginc.model.OrderItem;
import com.shoppinginc.service.ICartService;
import java.util.Scanner;

public class TerminalUtils {
    private final Scanner scanner = new Scanner(System.in);
    private final ICartService cartService;

    public TerminalUtils(ICartService cartService) {
        this.cartService = cartService;
    }

    public void run() {
        boolean running = true;

        while (running) {
            displayMenu();
            String choice = getMenuChoice();

            switch (choice) {
                case "1":
                    displayCart();
                    break;
                case "2":
                        promptRemoveItem();
                break;

                case "3":
                    promptAddItem();
                    break;
                case "4":
                    checkout();
                    break;
                case "5":
                    conclusion();
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
                    break;
            }
        }
    }

    public void displayMenu() {
        System.out.println("\n--- CASH REGISTER MAIN MENU ---");
        System.out.println("1. Display Cart");
        System.out.println("2. Remove an Item");
        System.out.println("3. Add an Item");
        System.out.println("4. Checkout");
        System.out.println("5. Exit");
        System.out.print("Please select an option (1-5): ");
    }

    public void displayCart() {
        System.out.println("\n--- CART ITEMS ---");

        if (cartService.isEmpty()) {
            System.out.println("Your cart is currently empty.");
        } else {
            for (OrderItem item : cartService.getOrderItems().values()) {
                System.out.println(item);
            }
            System.out.printf("Total: $%.2f%n", cartService.getCartTotal());
            System.out.println("Total items: " + cartService.getTotalItemCount());
        }
    }

    public void promptAddItem() {
        try {
            System.out.print("Enter item ID: ");
            String id = scanner.nextLine().trim();

            if (id.isEmpty()) {
                System.out.println("Item ID cannot be empty.");
                return;
            }

            System.out.print("Enter item name: ");
            String name = scanner.nextLine().trim();

            if (name.isEmpty()) {
                System.out.println("Item name cannot be empty.");
                return;
            }

            System.out.print("Enter quantity: ");
            int quantity;
            try {
                quantity = Integer.parseInt(scanner.nextLine().trim());
                if (quantity <= 0) {
                    System.out.println("Quantity must be greater than 0.");
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid quantity format. Please enter a valid number.");
                return;
            }

            System.out.print("Enter price per item: ");
            double price;
            try {
                price = Double.parseDouble(scanner.nextLine().trim());
                if (price <= 0) {
                    System.out.println("Price must be greater than 0.");
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid price format. Please enter a valid number.");
                return;
            }

            OrderItem item = new OrderItem(id, quantity, name, price);
            cartService.addItem(item);
            System.out.println("Item added successfully.");

        } catch (Exception e) {
            System.out.println("An error occurred while adding the item. Please try again.");
        }
    }

    public void promptRemoveItem() {
        if (cartService.isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }

        try {
            System.out.print("Enter the item ID to remove: ");
            String id = scanner.nextLine().trim();

            if (id.isEmpty()) {
                System.out.println("Item ID cannot be empty.");
                return;
            }

            System.out.print("Enter quantity to remove: ");
            int qtyToRemove;
            try {
                qtyToRemove = Integer.parseInt(scanner.nextLine().trim());
                if (qtyToRemove <= 0) {
                    System.out.println("Quantity to remove must be greater than 0.");
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid quantity format. Please enter a valid number.");
                return;
            }

            boolean removed = cartService.removeItem(id, qtyToRemove);
            if (removed) {
                System.out.println("Item removed successfully.");
            } else {
                System.out.println("Item not found or invalid quantity.");
            }

        } catch (Exception e) {
            System.out.println("An error occurred while removing the item. Please try again.");
        }
    }

    public void checkout() {
        if (cartService.isEmpty()) {
            System.out.println("Cart is empty. Nothing to checkout.");
        } else {
            System.out.printf("Final total: $%.2f%n", cartService.getCartTotal());
            cartService.clearCart();
            System.out.println("Your purchase is complete. Thank you!");
        }
    }

    public void conclusion() {
        System.out.println("Thank you for using Shopping Inc. Goodbye!");
    }

    public String getMenuChoice() {
        return scanner.nextLine().trim();
    }

    private void promptEnterKey() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }
}