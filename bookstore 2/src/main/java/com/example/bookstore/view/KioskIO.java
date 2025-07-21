package com.example.bookstore.view;

import com.example.bookstore.model.CartItem;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@Component
public class KioskIO {

    private final Scanner scanner;

    public KioskIO() {
        this.scanner = new Scanner(System.in);
    }

    public void displayWelcome() {
        System.out.println("=======================================");
        System.out.println("    Welcome to the Bookstore Kiosk!");
        System.out.println("=======================================");
        System.out.println();
    }

    public void displayGoodbye() {
        System.out.println();
        System.out.println("Thank you for using the Bookstore Kiosk!");
        System.out.println("Have a great day!");
        System.out.println();
    }

    public int displayMenuAndGetChoice() {
        System.out.println();
        System.out.println("=== MAIN MENU ===");
        System.out.println("1. Add book to cart");
        System.out.println("2. Remove book from cart");
        System.out.println("3. Display cart");
        System.out.println("4. Checkout");
        System.out.println("5. Quit");
        System.out.println();

        return getIntegerInputWithDefault("Please select an option (1-5): ", -1);
    }

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
        System.out.printf("%-18s %-35s %3s %12s%n", "ISBN", "TITLE", "QTY", "TOTAL");
        System.out.println("───────────────────────────────────────────────────────────────────────────");

        for (CartItem item : cartContents) {
            String isbn = item.getBook().isbn();
            String title = item.getBook().title();
            int quantity = item.getQuantity();
            BigDecimal extendedPrice = item.getExtendedPrice();

            // Truncate title if too long to fit in the display
            if (title.length() > 35) {
                title = title.substring(0, 32) + "...";
            }

            System.out.printf("%-18s %-35s %3d       $%6.2f%n",
                    isbn, title, quantity, extendedPrice);
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

    private int getIntegerInputWithDefault(String prompt, int defaultValue) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();

        if (input.isEmpty()) {
            return defaultValue;
        }

        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            displayError("Please enter a valid number.");
            return getIntegerInputWithDefault(prompt, defaultValue);
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