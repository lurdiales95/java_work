package com.perfume.capstone.view;

import com.perfume.capstone.model.InventoryPerfumeItem;
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

    // NEW METHOD: Show menu without getting input
    public void showMenu() {
        System.out.println();
        System.out.println("=== INVENTORY MANAGEMENT MENU ===");
        System.out.println("1. Add/Update inventory item");
        System.out.println("2. Remove inventory item");
        System.out.println("3. View inventory item");
        System.out.println("4. View all inventory items");
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

    // REMOVE OR REPLACE the old displayMenuAndGetChoice() method since we split it

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

    public void displayInventoryItems(List<InventoryPerfumeItem> items) {
        System.out.println("═══════════════════════════════════════════════════════════════════════════");
        System.out.println("                              INVENTORY ITEMS");
        System.out.println("═══════════════════════════════════════════════════════════════════════════");

        if (items.isEmpty()) {
            System.out.println("                           No items in inventory");
            System.out.println("═══════════════════════════════════════════════════════════════════════════");
            return;
        }

        // Header
        System.out.printf("%-12s %-20s %-15s %-12s %3s %8s%n", "PRODUCT ID", "PERFUME", "SCENT TYPE", "SEASON", "QTY", "PRICE");
        System.out.println("───────────────────────────────────────────────────────────────────────────");

        for (InventoryPerfumeItem item : items) {
            String productID = item.getPerfume().productID();
            String productName = item.getPerfume().productName();
            String scentType = item.getPerfume().scentType();
            String seasonAvailability = item.getPerfume().seasonAvailability();
            int quantity = item.getQuantity();
            BigDecimal price = item.getPrice();

            // Truncate fields if too long to fit in the display
            if (productName.length() > 20) {
                productName = productName.substring(0, 17) + "...";
            }
            if (scentType.length() > 15) {
                scentType = scentType.substring(0, 12) + "...";
            }
            if (seasonAvailability.length() > 12) {
                seasonAvailability = seasonAvailability.substring(0, 9) + "...";
            }

            System.out.printf("%-12s %-20s %-15s %-12s %3d    $%6.2f%n",
                    productID, productName, scentType, seasonAvailability, quantity, price);
        }

        System.out.println("═══════════════════════════════════════════════════════════════════════════");
    }

    public void displaySingleItem(InventoryPerfumeItem item) {
        System.out.println("═══════════════════════════════════════════════════════════════════════════");
        System.out.println("                              ITEM DETAILS");
        System.out.println("═══════════════════════════════════════════════════════════════════════════");
        System.out.printf("Product ID:    %s%n", item.getPerfume().productID());
        System.out.printf("Perfume Name:   %s%n", item.getPerfume().productName());
        System.out.printf("Scent Type:    %s%n", item.getPerfume().scentType());
        System.out.printf("Season:        %s%n", item.getPerfume().seasonAvailability());
        System.out.printf("Quantity:      %d%n", item.getQuantity());
        System.out.printf("Price:         $%.2f%n", item.getPrice());
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