package com.example.bookstore.view;

import com.example.bookstore.model.InventoryItem;
import com.example.bookstore.model.Book;
import com.example.bookstore.service.InventoryService;
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
            int choice = inventoryIO.displayMenuAndGetChoice();

            switch (choice) {
                case 1:
                    handleAddOrUpdateItem();
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

    private void handleAddOrUpdateItem() {
        inventoryIO.displaySectionHeader("Add/Update Inventory Item");

        String isbn = inventoryIO.getStringInput("Enter ISBN: ");
        if (isbn == null) return; // User cancelled

        String title = inventoryIO.getStringInput("Enter book title: ");
        if (title == null) return; // User cancelled

        String author = inventoryIO.getStringInput("Enter author: ");
        if (author == null) return; // User cancelled

        String genre = inventoryIO.getStringInput("Enter genre: ");
        if (genre == null) return; // User cancelled

        Integer quantity = inventoryIO.getIntegerInput("Enter quantity: ");
        if (quantity == null) return; // User cancelled

        BigDecimal price = inventoryIO.getBigDecimalInput("Enter price: $");
        if (price == null) return; // User cancelled

        try {
            // Create a new Book record
            Book book = new Book(isbn, title, author, genre);

            // Create a new InventoryItem
            InventoryItem item = new InventoryItem(book, quantity, price);

            // Check if item already exists, this will tell us if this is an add or update
            InventoryItem existingItem = inventoryService.getItem(isbn);

            inventoryService.updateOrAddItem(item);

            if (existingItem != null) {
                inventoryIO.displaySuccess("Item updated successfully!");
            } else {
                inventoryIO.displaySuccess("Item added successfully!");
            }
        } catch (Exception e) {
            inventoryIO.displayError("Failed to add/update item: " + e.getMessage());
        }
    }

    private void handleRemoveItem() {
        inventoryIO.displaySectionHeader("Remove Inventory Item");

        List<InventoryItem> allItems = inventoryService.getAllItems();
        if (allItems.isEmpty()) {
            inventoryIO.displayInfo("No items in inventory.");
            return;
        }

        // Show current inventory
        inventoryIO.displayInventoryItems(allItems);

        String isbn = inventoryIO.getStringInput("Enter ISBN to remove: ");
        if (isbn == null) return; // User cancelled

        InventoryItem existingItem = inventoryService.getItem(isbn);
        if (existingItem == null) {
            inventoryIO.displayError("Item with ISBN '" + isbn + "' not found.");
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
            inventoryService.removeItem(isbn);
            inventoryIO.displaySuccess("Item removed successfully!");
        } catch (Exception e) {
            inventoryIO.displayError("Failed to remove item: " + e.getMessage());
        }
    }

    private void handleViewItem() {
        inventoryIO.displaySectionHeader("View Inventory Item");

        String isbn = inventoryIO.getStringInput("Enter ISBN: ");
        if (isbn == null) return; // User cancelled

        InventoryItem item = inventoryService.getItem(isbn);
        if (item == null) {
            inventoryIO.displayError("Item with ISBN '" + isbn + "' not found.");
            return;
        }

        inventoryIO.displaySingleItem(item);
    }

    private void handleViewAllItems() {
        inventoryIO.displaySectionHeader("All Inventory Items");

        List<InventoryItem> allItems = inventoryService.getAllItems();
        if (allItems.isEmpty()) {
            inventoryIO.displayInfo("No items in inventory.");
            return;
        }

        inventoryIO.displayInventoryItems(allItems);
        inventoryIO.displayInfo("Total items in inventory: " + allItems.size());
    }
}