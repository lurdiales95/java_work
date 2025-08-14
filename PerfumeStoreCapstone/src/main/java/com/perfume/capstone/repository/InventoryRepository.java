package com.perfume.capstone.repository;

import com.perfume.capstone.model.InventoryPerfumeItem;

import java.util.List;

public interface InventoryRepository {

    List<InventoryPerfumeItem> getAll();

    List<InventoryPerfumeItem> getInStock();

    void add(InventoryPerfumeItem item);

    void update(InventoryPerfumeItem item);

    void delete(String productID);

    InventoryPerfumeItem getByProductID(String productID);

}
