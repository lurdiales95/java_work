package com.example.Capstone_Java_Console_App_Inventory_Manager.view;

import com.example.Capstone_Java_Console_App_Inventory_Manager.model.Candle;
import com.example.Capstone_Java_Console_App_Inventory_Manager.model.InventoryCandleItem;
import com.example.Capstone_Java_Console_App_Inventory_Manager.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;

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
            int choice = inventoryIO.displayMenuAndGetChoice();

            switch (choice) {
                case 1:
                    handledAddOrUpdateItem();
                    break;

                case 2:
                    handleRemoveItem();
                    break;
                case 3:
                    handleViewItem();
                    break;
                case 4:
                    handleViewAllItems();
                    break;
                case 5:
                    running = false;
                    inventoryIO.displayGoodbye();
                    break;
                default:
                    inventoryIO.displayError("Invalid choice. Please try again.");
            }
        }
    }

    private void handledAddOrUpdateItem() {
        String productID = inventoryIO.getStringInput("Enter ProductID: ");
        if (productID == null) return;

        String productName = inventoryIO.getStringInput("Enter Candle Name: ");

        Integer quantity = inventoryIO.getIntegerInput("Enter quantity: ");
        if (quantity == null) return;

        BigDecimal price = inventoryIO.getDecimalInput("Enter price: $");

        }

        try {
        Candle candle = new Candle(productID, productName);

        InventoryCandleItem item = new InventoryCandleItem(productName, quantity, price);

        InventoryCandleItem existingItem = inventoryService.getItem(productID);

        if (existingItem != null) {
            inventoryIO.displaySuccess("Item updated successfully!");
        } else {
            inventoryIO.displaySuccess("Item added successfully!");
        }
        catch (Exception e) {
            inventoryIO.displayError("Failed to add/update candle item " + e.getMessage());
        }
    }



    private void handleRemoveItem() {
            inventoryIO.displaySectionHeader("Remove inventory item");

        List<InventoryCandleItem> allItems = inventoryService.getAllItems();
        if (allItems.isEmpty()) {
            inventoryIO.displayInfo("No items in inventory.");
            return;
        }

        inventoryIO.displayInventoryItems(allItems);

        String productID = inventoryIO.getStringInput("Enter ProductID to remove: ");
        if (productID == null) { return;

        }

        InventoryCandleItem existingItem = inventoryService.getItem(productID);
        if (existingItem == null) {
            inventoryIO.displayError("Item with ProductID '" + productID + "' not found.");
            return;

    }

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
        if (productID == null) return; // User cancelled

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



