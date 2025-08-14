package com.perfume.capstone.view;

import com.perfume.capstone.model.InventoryPerfumeItem;
import com.perfume.capstone.model.Perfume;
import com.perfume.capstone.service.InventoryService;
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
        inventoryIO.displaySectionHeader("Add/Update Perfume Inventory Item");

        String productID = inventoryIO.getStringInput("Enter ProductID: ");
        if (productID == null) return;

        String productName = inventoryIO.getStringInput("Enter Perfume Name: ");
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
            // Create a new Perfume record with all 4 parameters
            Perfume perfume = new Perfume(productID, productName, scentType, seasonAvailability);

            // Create a new InventoryPerfumeItem
            InventoryPerfumeItem item = new InventoryPerfumeItem(perfume, quantity, price);

            // Check if item already exists
            InventoryPerfumeItem existingItem = inventoryService.getItem(productID);

            inventoryService.updateOrAddItem(item);

            if (existingItem != null) {
                inventoryIO.displaySuccess("Item updated successfully!");
            } else {
                inventoryIO.displaySuccess("Item added successfully!");
            }
        } catch (Exception e) {
            inventoryIO.displayError("Failed to add/update perfume item: " + e.getMessage());
        }
    }

    private void handleRemoveItem() {
        inventoryIO.displaySectionHeader("Remove Inventory Item");

        List<InventoryPerfumeItem> allItems = inventoryService.getAllItems();
        if (allItems.isEmpty()) {
            inventoryIO.displayInfo("No items in inventory.");
            return;
        }

        // Show current inventory
        inventoryIO.displayInventoryItems(allItems);

        String productID = inventoryIO.getStringInput("Enter ProductID to remove: ");
        if (productID == null) return;

        InventoryPerfumeItem existingItem = inventoryService.getItem(productID);
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

        InventoryPerfumeItem item = inventoryService.getItem(productID);
        if (item == null) {
            inventoryIO.displayError("Item with ProductID '" + productID + "' not found.");
            return;
        }

        inventoryIO.displaySingleItem(item);
    }

    private void handleViewAllItems() {
        inventoryIO.displaySectionHeader("All Inventory Items");

        List<InventoryPerfumeItem> allItems = inventoryService.getAllItems();
        if (allItems.isEmpty()) {
            inventoryIO.displayInfo("No items in inventory.");
            return;
        }

        inventoryIO.displayInventoryItems(allItems);
        inventoryIO.displayInfo("Total items in inventory: " + allItems.size());
    }
}