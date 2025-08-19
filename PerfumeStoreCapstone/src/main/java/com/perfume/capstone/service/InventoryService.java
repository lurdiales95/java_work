package com.perfume.capstone.service;

import com.perfume.capstone.model.InventoryPerfumeItem;
import com.perfume.capstone.model.Result;
import com.perfume.capstone.repository.PerfumeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {
    private final PerfumeRepository perfumeRepository;

    public InventoryService(PerfumeRepository perfumeRepository) {
        this.perfumeRepository = perfumeRepository;
    }

    public Result<InventoryPerfumeItem> updateOrAddItem(InventoryPerfumeItem item) {
        if (item == null) {
            return new Result<>(false, "Item cannot be null", null);
        }

        try {
            int productID = item.getPerfume().productID();
            InventoryPerfumeItem existingItem = perfumeRepository.getByProductID(productID);

            if (existingItem != null) {
                // Update existing item
                existingItem.setQuantity(item.getQuantity());
                existingItem.setPrice(item.getPrice());
                perfumeRepository.update(existingItem);
                return new Result<>(true, "Item updated successfully", existingItem);
            } else {
                // Add new item
                perfumeRepository.add(item);
                return new Result<>(true, "Item added successfully", item);
            }
        } catch (Exception e) {
            return new Result<>(false, "Error updating item: " + e.getMessage(), null);
        }
    }

    public Result<Void> removeItem(int productID) {
        try {
            perfumeRepository.delete(productID);
            return new Result<>(true, "Item removed successfully", null);
        } catch (Exception e) {
            return new Result<>(false, "Error removing item: " + e.getMessage(), null);
        }
    }

    public Result<InventoryPerfumeItem> getItem(int productID) {
        try {
            InventoryPerfumeItem item = perfumeRepository.getByProductID(productID);
            if (item != null) {
                return new Result<>(true, "Item found", item);
            } else {
                return new Result<>(false, "Item not found", null);
            }
        } catch (Exception e) {
            return new Result<>(false, "Error retrieving item: " + e.getMessage(), null);
        }
    }

    public Result<List<InventoryPerfumeItem>> getAllItems() {
        try {
            List<InventoryPerfumeItem> items = perfumeRepository.getAll();
            return new Result<>(true, "Items retrieved successfully", items);
        } catch (Exception e) {
            return new Result<>(false, "Error retrieving items: " + e.getMessage(), null);
        }
    }

    public Result<List<InventoryPerfumeItem>> getInStockItems() {
        try {
            List<InventoryPerfumeItem> items = perfumeRepository.getInStock();
            return new Result<>(true, "In-stock items retrieved successfully", items);
        } catch (Exception e) {
            return new Result<>(false, "Error retrieving in-stock items: " + e.getMessage(), null);
        }
    }
}