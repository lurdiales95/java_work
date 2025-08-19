package com.perfume.capstone.repository;

import com.perfume.capstone.model.InventoryPerfumeItem;

import java.util.List;

public interface PerfumeRepository {

    /**
     * Get all perfumes with their inventory information
     */
    List<InventoryPerfumeItem> getAll();

    /**
     * Get only perfumes that are currently in stock (quantity > 0)
     */
    List<InventoryPerfumeItem> getInStock();

    /**
     * Add a new perfume and its inventory information
     */
    void add(InventoryPerfumeItem item);

    /**
     * Update an existing perfume and its inventory information
     */
    void update(InventoryPerfumeItem item);

    /**
     * Delete a perfume by its product ID
     */
    void delete(int productID);

    /**
     * Get a specific perfume with inventory by product ID
     * Returns null if not found
     */
    InventoryPerfumeItem getByProductID(int productID);
}