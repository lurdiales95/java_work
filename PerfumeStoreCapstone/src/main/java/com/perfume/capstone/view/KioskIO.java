package com.perfume.capstone.view;

import com.perfume.capstone.model.CartItem;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

@Component
public class KioskIO {

    private final Scanner scanner;

    public KioskIO() {
        this.scanner = new Scanner(System.in);
    }

    public void displayWelcome() {
        System.out.println("=======================================");
        System.out.println("    Welcome to the Perfume Store Kiosk!");
        System.out.println("=======================================");
        System.out.println();
    }

    public void displayGoodbye() {
        System.out.println();
        System.out.println("Thank you for using the Perfume Store Kiosk!");
        System.out.println("Have a great day!");
        System.out.println();
    }

    // NEW METHOD: Show menu without getting input
    public void showMenu() {
        System.out.println();
        System.out.println("=== MAIN MENU ===");
        System.out.println("1. Add perfume to cart");
        System.out.println("2. Remove perfume from cart");
        System.out.println("3. Display perfume");
        System.out.println("4. Checkout");
        System.out.println("5. Quit");
        System.out.println();
        System.out.print("Please select an option (1-5): ");
    }

    // NEW METHOD: Get menu choice as String
    public String getMenuChoice() {
        return scanner.nextLine().trim();
    }

    // NEW METHOD: Pause for user to press Enter
    public void promptEnterKey() {
        System.out.println();
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }

    // REMOVE OR REPLACE the old displayMenuAndGetChoice() method since we split it into showMenu() and getMenuChoice()

    public void displaySectionHeader(String title) {
        System.out.println();
        System.out.println("=== " + title.toUpperCase() + " ===");
    }

    public void displaySuccess(String message) {
        System.out.println("✓ " + message);
    }

    public void displayError(String message) {
        System.out.println("✗ ERROR: " + message);
    }

    public void displayInfo(String message) {
        System.out.println("ℹ " + message);
    }

    public void displayCartContents(List<CartItem> cartContents) {
        System.out.println("═══════════════════════════════════════════════════════════════════════════");
        System.out.println("                              SHOPPING CART");
        System.out.println("═══════════════════════════════════════════════════════════════════════════");

        if (cartContents.isEmpty()) {
            System.out.println("                           Your cart is empty");
            System.out.println("═══════════════════════════════════════════════════════════════════════════");
            return;
        }

        // Header
        System.out.printf("%-18s %-35s %3s %12s%n", "PRODUCT ID", "PRODUCT NAME", "QTY", "TOTAL");
        System.out.println("───────────────────────────────────────────────────────────────────────────");

        for (CartItem item : cartContents) {
            String productID = item.getPerfume().productID();
            String productName = item.getPerfume().productName();
            int quantity = item.getQuantity();
            BigDecimal totalPrice = item.getTotalPrice();

            // Truncate product name if too long to fit in the display
            if (productName.length() > 35) {
                productName = productName.substring(0, 32) + "...";
            }

            System.out.printf("%-18s %-35s %3d       $%6.2f%n",
                    productID, productName, quantity, totalPrice);
        }
    }

    public String getStringInput(String prompt) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();

        if (input.isEmpty()) {
            displayError("Input cannot be empty. Please try again.");
            return getStringInput(prompt);
        }

        return input;
    }

    public Integer getIntegerInput(String prompt) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();

        if (input.isEmpty()) {
            displayError("Input cannot be empty. Please try again.");
            return getIntegerInput(prompt);
        }

        try {
            int value = Integer.parseInt(input);
            if (value <= 0) {
                displayError("Please enter a positive number.");
                return getIntegerInput(prompt);
            }
            return value;
        } catch (NumberFormatException e) {
            displayError("Please enter a valid number.");
            return getIntegerInput(prompt);
        }
    }

    public boolean getConfirmation(String prompt) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim().toLowerCase();

        while (!input.equals("y") && !input.equals("n") &&
                !input.equals("yes") && !input.equals("no")) {
            displayError("Please enter 'y' for yes or 'n' for no.");
            System.out.print(prompt);
            input = scanner.nextLine().trim().toLowerCase();
        }

        return input.equals("y") || input.equals("yes");
    }
}
