package com.example.Capstone_Java_Console_App_Inventory_Manager.view;

import com.example.Capstone_Java_Console_App_Inventory_Manager.model.InventoryCandleItem;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

@Component
public class InventoryIO {

    private final Scanner scanner;

    public InventoryIO() {this.scanner = new Scanner(System.in); }

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

    public void displaySectionHeader(String productName) {
        System.out.println();
        System.out.println("=== " + productName.toUpperCase() + " ===");
    }

    public void displaySuccess(String message) {System.out.println("✓ " + message); }

    public  void displayError(String message) {System.out.println("✗ ERROR: " + message); }

    public void displayInfo(String message) {System.out.println("ℹ " + message); }

    public void displayInventoryItems(List<InventoryCandleItem> items)  {
        System.out.println("═══════════════════════════════════════════════════════════════════════════");
        System.out.println("                              INVENTORY ITEMS");
        System.out.println("═══════════════════════════════════════════════════════════════════════════");

        if (items.isEmpty()) {
            System.out.println("                           No items in inventory");
            System.out.println("═══════════════════════════════════════════════════════════════════════════");
            return;
        }

        // Header
        System.out.printf("%-18s %-20s %-15s %-12s %3s %12s%n", "PRODUCT ID", "CANDLE", "QTY", "PRICE");
        System.out.println("───────────────────────────────────────────────────────────────────────────");

        for (InventoryCandleItem item : items) {
            String productID = item.getCandle().productID();
            String productName = item.getCandle().productName();
            int quantity = item.getQuantity();
            BigDecimal price = item.getPrice();

            // Truncate title if too long to fit in the display
            if (productName.length() > 20) {
                productName = productName.substring(0, 17) + "...";
            }


            System.out.printf("%-18s %-20s %-15s %-12s %3d       $%6.2f%n",
                    productID, productName, quantity, price);
        }

        System.out.println("═══════════════════════════════════════════════════════════════════════════");
    }

    public void displaySingleItem(InventoryCandleItem item) {
        System.out.println("═══════════════════════════════════════════════════════════════════════════");
        System.out.println("                              ITEM DETAILS");
        System.out.println("═══════════════════════════════════════════════════════════════════════════");
        System.out.printf("ISBN:     %s%n", item.getCandle().productID());
        System.out.printf("Title:    %s%n", item.getCandle().productName());
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
