package com.example.Capstone_Java_Console_App_Inventory_Manager.view;

import com.example.Capstone_Java_Console_App_Inventory_Manager.model.Candle;
import com.example.Capstone_Java_Console_App_Inventory_Manager.model.InventoryCandleItem;
import com.example.Capstone_Java_Console_App_Inventory_Manager.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class Inventory {

    private final InventoryService inventoryService;
    private final InventoryIO inventoryIO;

    @Autowired
    public Inventory(InventoryService inventoryService, InventoryIO inventoryIO) {
        this.inventoryService = inventoryService;
        this.inventoryIO = inventoryIO;
    }

    public void run() {
        inventoryIO.displayWelcome();
        boolean running = true;

        while (running) {
            inventoryIO.showMenu();
            String choice = inventoryIO.getMenuChoice();

            // menu selections based on numerical String
            switch (choice) {
                case "1" -> handleAddOrUpdateItem();
                case "2" -> handleRemoveItem();
                case "3" -> handleViewItem();
                case "4" -> handleViewAllItems();
                case "5" -> running = false;
                default -> inventoryIO.displayError("Invalid choice. Please try again.");
            }

            // command to signal that the user wants app to stop running the loop using choice 5
            if (!choice.equals("5")) {
                inventoryIO.promptEnterKey();
            }
        }

        // response to closing the loop
        inventoryIO.displayGoodbye();
    }

    private void handleAddOrUpdateItem() {
        inventoryIO.displaySectionHeader("Add/Update Candle Inventory Item");

        String productID = inventoryIO.getStringInput("Enter ProductID: ");
        if (productID == null) return;

        String productName = inventoryIO.getStringInput("Enter Candle Name: ");
        if (productName == null) return;

        String scentType = inventoryIO.getStringInput("Enter Scent Type: ");
        if (scentType == null) return;

        String seasonAvailability = inventoryIO.getStringInput("Enter Season Availability: ");
        if (seasonAvailability == null) return;

        Integer quantity = inventoryIO.getIntegerInput("Enter quantity: ");
        if (quantity == null) return;

        BigDecimal price = inventoryIO.getBigDecimalInput("Enter price: $");
        if (price == null) return;

        try {
            // Create a new Candle record with all 4 parameters
            Candle candle = new Candle(productID, productName, scentType, seasonAvailability);

            // Create a new InventoryCandleItem
            InventoryCandleItem item = new InventoryCandleItem(candle, quantity, price);

            // Check if item already exists
            InventoryCandleItem existingItem = inventoryService.getItem(productID);

            inventoryService.updateOrAddItem(item);

            if (existingItem != null) {
                inventoryIO.displaySuccess("Item updated successfully!");
            } else {
                inventoryIO.displaySuccess("Item added successfully!");
            }
        } catch (Exception e) {
            inventoryIO.displayError("Failed to add/update candle item: " + e.getMessage());
        }
    }

    private void handleRemoveItem() {
        inventoryIO.displaySectionHeader("Remove Inventory Item");

        List<InventoryCandleItem> allItems = inventoryService.getAllItems();
        if (allItems.isEmpty()) {
            inventoryIO.displayInfo("No items in inventory.");
            return;
        }

        // Show current inventory
        inventoryIO.displayInventoryItems(allItems);

        String productID = inventoryIO.getStringInput("Enter ProductID to remove: ");
        if (productID == null) return;

        InventoryCandleItem existingItem = inventoryService.getItem(productID);
        if (existingItem == null) {
            inventoryIO.displayError("Item with ProductID '" + productID + "' not found.");
            return;
        }

        // Show the item that will be removed
        inventoryIO.displaySectionHeader("Item to Remove");
        inventoryIO.displaySingleItem(existingItem);

        boolean confirm = inventoryIO.getConfirmation("Are you sure you want to remove this item? (y/n): ");
        if (!confirm) {
            inventoryIO.displayInfo("Remove operation cancelled.");
            return;
        }

        try {
            inventoryService.removeItem(productID);
            inventoryIO.displaySuccess("Item removed successfully!");
        } catch (Exception e) {
            inventoryIO.displayError("Failed to remove item: " + e.getMessage());
        }
    }

    private void handleViewItem() {
        inventoryIO.displaySectionHeader("View Inventory Item");

        String productID = inventoryIO.getStringInput("Enter ProductID: ");
        if (productID == null) return;

        InventoryCandleItem item = inventoryService.getItem(productID);
        if (item == null) {
            inventoryIO.displayError("Item with ProductID '" + productID + "' not found.");
            return;
        }

        inventoryIO.displaySingleItem(item);
    }

    private void handleViewAllItems() {
        inventoryIO.displaySectionHeader("All Inventory Items");

        List<InventoryCandleItem> allItems = inventoryService.getAllItems();
        if (allItems.isEmpty()) {
            inventoryIO.displayInfo("No items in inventory.");
            return;
        }

        inventoryIO.displayInventoryItems(allItems);
        inventoryIO.displayInfo("Total items in inventory: " + allItems.size());
    }
}