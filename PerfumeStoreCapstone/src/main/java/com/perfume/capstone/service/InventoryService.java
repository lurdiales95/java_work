package com.perfume.capstone.service;

import com.perfume.capstone.model.InventoryPerfumeItem;
import com.perfume.capstone.repository.PerfumeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {
    private final PerfumeRepository perfumeRepository;

    public InventoryService(PerfumeRepository perfumeRepository) {
        this.perfumeRepository = perfumeRepository;
    }

    // Updating item
    public void updateOrAddItem(InventoryPerfumeItem item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }

        String productID = item.getPerfume().productID();
        InventoryPerfumeItem existingItem = perfumeRepository.getByProductID(productID);

        if (existingItem != null) {
            // Update existing item
            existingItem.setQuantity(item.getQuantity());
            existingItem.setPrice(item.getPrice());
            perfumeRepository.update(existingItem);
        } else {
            // Add new item
            perfumeRepository.add(item);
        }
    }
    // Removes item
    public void removeItem(String productID) {
        perfumeRepository.delete(productID);
    }

    // Retrieves Item from PerfumeRepository
    public InventoryPerfumeItem getItem(String productID) {
        return perfumeRepository.getByProductID(productID);
    }

    // Retrieves all items from PerfumeRepository
    public List<InventoryPerfumeItem> getAllItems() {
        return perfumeRepository.getAll();
    }
}