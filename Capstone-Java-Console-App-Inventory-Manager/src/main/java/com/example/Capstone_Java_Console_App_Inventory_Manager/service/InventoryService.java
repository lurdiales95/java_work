package com.example.Capstone_Java_Console_App_Inventory_Manager.service;

import com.example.Capstone_Java_Console_App_Inventory_Manager.model.InventoryCandleItem;
import com.example.Capstone_Java_Console_App_Inventory_Manager.repository.InventoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    // Updating item
    public void updateOrAddItem(InventoryCandleItem item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }

        String productID = item.getCandle().productID();
        InventoryCandleItem existingItem = inventoryRepository.getByProductID(productID);

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
    // Removes item
    public void removeItem(String productID) {
        inventoryRepository.delete(productID);
    }

    // Retrieves Item from InventoryRepository
    public InventoryCandleItem getItem(String productID) {
        return inventoryRepository.getByProductID(productID);
    }

    // Retrieves all items from InventoryRepository
    public List<InventoryCandleItem> getAllItems() {
        return inventoryRepository.getAll();
    }
}