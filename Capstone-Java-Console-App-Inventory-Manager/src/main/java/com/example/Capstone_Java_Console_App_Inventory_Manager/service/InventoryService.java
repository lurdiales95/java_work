package com.example.Capstone_Java_Console_App_Inventory_Manager.service;


import com.example.Capstone_Java_Console_App_Inventory_Manager.model.InventoryCandleItem;
import com.example.Capstone_Java_Console_App_Inventory_Manager.repository.InventoryRepository;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) { this.inventoryRepository = inventoryRepository; }

    public void updateOrAddItem(InventoryCandleItem item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");

        }

        String productID = item.getCandle().productID();
        InventoryCandleItem existingItem = inventoryRepository.getByProductID(productID);

        if (existingItem != null) {


        }
    }
}
