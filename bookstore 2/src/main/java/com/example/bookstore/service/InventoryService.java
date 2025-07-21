package com.example.bookstore.service;

import com.example.bookstore.model.InventoryItem;
import com.example.bookstore.repository.InventoryRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    /**
     * Updates an existing inventory item or adds a new one if it doesn't exist.
     * If the item exists, updates both quantity and price.
     * If the item doesn't exist, creates a new inventory item.
     *
     * @param item The inventory item to update or add
     */
    public void updateOrAddItem(InventoryItem item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }

        String isbn = item.getBook().isbn();
        InventoryItem existingItem = inventoryRepository.getByIsbn(isbn);

        if (existingItem != null) {
            // Update existing item
            existingItem.setQuantity(item.getQuantity());
            existingItem.setPrice(item.getPrice());
            inventoryRepository.update(existingItem);
        } else {
            // Add new item
            inventoryRepository.add(item);
        }
    }

    /**
     * Removes an item from the inventory.
     *
     * @param isbn The ISBN of the book to remove
     */
    public void removeItem(String isbn) {
        inventoryRepository.delete(isbn);
    }

    /**
     * Retrieves an inventory item by ISBN.
     *
     * @param isbn The ISBN of the book to retrieve
     * @return The inventory item, or null if not found
     */
    public InventoryItem getItem(String isbn) {
        return inventoryRepository.getByIsbn(isbn);
    }

    /**
     * Retrieves all inventory items.
     *
     * @return List of all inventory items
     */
    public List<InventoryItem> getAllItems() {
        return inventoryRepository.getAll();
    }
}