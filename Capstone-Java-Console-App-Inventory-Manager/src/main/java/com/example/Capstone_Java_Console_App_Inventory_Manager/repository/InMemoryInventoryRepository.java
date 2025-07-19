package com.example.Capstone_Java_Console_App_Inventory_Manager.repository;

import com.example.Capstone_Java_Console_App_Inventory_Manager.model.InventoryCandleItem;

import java.util.HashMap;
import java.util.Map;

public class InMemoryInventoryRepository implements InventoryRepository {

    private final Map<String, InventoryCandleItem> inventory = new HashMap<>();

    public InMemoryInventoryRepository() {
        initializeSampleData();
    }

    private void initializeSampleData() {
        addSampleCandle(5905, Japanese Cherry Blossom Candle, 150, 26.95);
    }


}
