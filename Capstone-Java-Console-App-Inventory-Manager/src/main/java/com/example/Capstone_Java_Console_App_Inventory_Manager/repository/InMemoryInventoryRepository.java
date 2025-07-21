package com.example.Capstone_Java_Console_App_Inventory_Manager.repository;

import com.example.Capstone_Java_Console_App_Inventory_Manager.model.Candle;
import com.example.Capstone_Java_Console_App_Inventory_Manager.model.InventoryCandleItem;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class InMemoryInventoryRepository implements InventoryRepository {

    private final Map<String, InventoryCandleItem> inventory = new HashMap<>();

    public InMemoryInventoryRepository() {
        initializeSampleData();
    }

    private void initializeSampleData() {
        addSampleCandle("5905", "Japanese Cherry Blossom Candle", 150, "26.95");
        addSampleCandle("4535", "Fresh Cut Lilacs Candle", 150,"10.99");
        addSampleCandle("6757", "Sweater Weather Candle", 150, "26.95");
        addSampleCandle("3568", "Eucalyptus Mint Candle",150, "26.95");
        addSampleCandle("7737", "Honeycrisp Apple", 150, "16.95");
        addSampleCandle("6760", "Raspberry Mimosa", 150, "26.95");
        addSampleCandle("3564", "Midnight Blue Citrus",150, "26.95");
        addSampleCandle("6413", "Campfire Cocoa", 150, "26.95");
        addSampleCandle("7735", "Pumpkin Pecan Waffles", 150, "26.95");
        addSampleCandle("6421", "Pumpkin Cinnamon Bun", 150, "26.95");
        addSampleCandle("7778", "Praline Delight", 150, "24.95");
        addSampleCandle("8256", "Vampire Blood", 150, "39.95");
        addSampleCandle("6939", "Candy Apple Cauldron", 150, "39.95");
        addSampleCandle("8249", "I Scream Float", 150, "26.95");
        addSampleCandle("8247", "Ghoul Friend", 150, "26.95");

    }

    private void addSampleCandle(String productID, String productName, int quantity, String price) {
        Candle candle = new Candle(productID, productName);
        InventoryCandleItem item = new InventoryCandleItem(candle, quantity, new BigDecimal(price));
        inventory.put(productID, item);
    }

    @Override
    public List<InventoryCandleItem> getAll() { return new ArrayList<>(inventory.values()); }

    @Override
    public List<InventoryCandleItem> getInStock() {
        return inventory.values().stream()
                .filter(item -> item.getQuantity() > 0)
                .collect(Collectors.toList());
    }

\

    @Override
    public void add(InventoryCandleItem item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null.");

        }
        inventory.put(item.getCandle().productID(), item);

    }

    @Override
    public void update(InventoryCandleItem item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        String productID = item.getCandle().productID();
        if (!inventory.containsKey(productID)) {
            throw new IllegalArgumentException("Item with ProductID " + productID + " not found");
        }
        inventory.remove(productID);
    }

    @Override
    public void delete(String productID) {
      if (productID == null || productID.trim().isEmpty()) {
          throw new IllegalArgumentException("ProductID cannot be null or empty.");
      }
      inventory.remove(productID);
    }

    @Override
    public InventoryCandleItem getByProductID(String productID) {
        if (productID == null || productID.trim().isEmpty()) {
            throw new IllegalArgumentException("ProductID cannot be null or empty.");

    }
        return inventory.get(productID);
    }
}



