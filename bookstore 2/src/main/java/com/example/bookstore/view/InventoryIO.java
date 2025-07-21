package com.example.bookstore.view;

import com.example.bookstore.model.InventoryItem;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

@Component
public class InventoryIO {

    private final Scanner scanner;

    public InventoryIO() {
        this.scanner = new Scanner(System.in);
    }

    public void displayWelcome() {
        System.out.println("=======================================");
        System.out.println("    Welcome to Inventory Management!");
        System.out.println("=======================================");
        System.out.println();
    }

    public void displayGoodbye() {
        System.out.println();
        System.out.println("Thank you for using Inventory Management!");
        System.out.println("Have a great day!");
        System.out.println();
    }

    public int displayMenuAndGetChoice() {
        System.out.println();
        System.out.println("=== INVENTORY MANAGEMENT MENU ===");
        System.out.println("1. Add/Update inventory item");
        System.out.println("2. Remove inventory item");
        System.out.println("3. View inventory item");
        System.out.println("4. View all inventory items");
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

    public void displayInventoryItems(List<InventoryItem> items) {
        System.out.println("═══════════════════════════════════════════════════════════════════════════");
        System.out.println("                              INVENTORY ITEMS");
        System.out.println("═══════════════════════════════════════════════════════════════════════════");

        if (items.isEmpty()) {
            System.out.println("                           No items in inventory");
            System.out.println("═══════════════════════════════════════════════════════════════════════════");
            return;
        }

        // Header
        System.out.printf("%-18s %-20s %-15s %-12s %3s %12s%n", "ISBN", "TITLE", "AUTHOR", "GENRE", "QTY", "PRICE");
        System.out.println("───────────────────────────────────────────────────────────────────────────");

        for (InventoryItem item : items) {
            String isbn = item.getBook().isbn();
            String title = item.getBook().title();
            String author = item.getBook().author();
            String genre = item.getBook().genre();
            int quantity = item.getQuantity();
            BigDecimal price = item.getPrice();

            // Truncate title if too long to fit in the display
            if (title.length() > 20) {
                title = title.substring(0, 17) + "...";
            }

            // Truncate author if too long to fit in the display
            if (author.length() > 15) {
                author = author.substring(0, 12) + "...";
            }

            // Truncate genre if too long to fit in the display
            if (genre.length() > 12) {
                genre = genre.substring(0, 9) + "...";
            }

            System.out.printf("%-18s %-20s %-15s %-12s %3d       $%6.2f%n",
                    isbn, title, author, genre, quantity, price);
        }

        System.out.println("═══════════════════════════════════════════════════════════════════════════");
    }

    public void displaySingleItem(InventoryItem item) {
        System.out.println("═══════════════════════════════════════════════════════════════════════════");
        System.out.println("                              ITEM DETAILS");
        System.out.println("═══════════════════════════════════════════════════════════════════════════");
        System.out.printf("ISBN:     %s%n", item.getBook().isbn());
        System.out.printf("Title:    %s%n", item.getBook().title());
        System.out.printf("Author:   %s%n", item.getBook().author());
        System.out.printf("Genre:    %s%n", item.getBook().genre());
        System.out.printf("Quantity: %d%n", item.getQuantity());
        System.out.printf("Price:    $%.2f%n", item.getPrice());
        System.out.println("═══════════════════════════════════════════════════════════════════════════");
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

    public BigDecimal getBigDecimalInput(String prompt) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();

        if (input.isEmpty()) {
            displayError("Input cannot be empty. Please try again.");
            return getBigDecimalInput(prompt);
        }

        try {
            BigDecimal value = new BigDecimal(input);
            if (value.compareTo(BigDecimal.ZERO) <= 0) {
                displayError("Please enter a positive price.");
                return getBigDecimalInput(prompt);
            }
            return value;
        } catch (NumberFormatException e) {
            displayError("Please enter a valid price (e.g., 19.99).");
            return getBigDecimalInput(prompt);
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