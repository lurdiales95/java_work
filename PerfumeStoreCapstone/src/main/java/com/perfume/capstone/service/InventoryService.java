package com.perfume.capstone.service;

import com.perfume.capstone.model.InventoryPerfumeItem;
import com.perfume.capstone.repository.InventoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    // Updating item
    public void updateOrAddItem(InventoryPerfumeItem item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }

        String productID = item.getPerfume().productID();
        InventoryPerfumeItem existingItem = inventoryRepository.getByProductID(productID);

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
    public InventoryPerfumeItem getItem(String productID) {
        return inventoryRepository.getByProductID(productID);
    }

    // Retrieves all items from InventoryRepository
    public List<InventoryPerfumeItem> getAllItems() {
        return inventoryRepository.getAll();
    }
}