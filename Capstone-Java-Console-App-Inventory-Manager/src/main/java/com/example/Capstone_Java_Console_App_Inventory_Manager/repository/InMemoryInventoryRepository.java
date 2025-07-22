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
        // Botanical & Blooms - Floral scented candles.
        addSampleCandle("5905", "Japanese Cherry Blossom Candle", "Botanical & Blooms", "Year Round", 150, "26.95");
        addSampleCandle("4535", "Fresh Cut Lilacs Candle", "Botanical & Blooms", "Spring", 150, "10.99");
        addSampleCandle("8247", "Ghoul Friend", "Botanical & Blooms", "Fall", 150, "26.95");
        addSampleCandle("3268", "Moonlight Path", "Botanical & Blooms", "Winter", 150, "26.95");
        addSampleCandle("3841", "Orange Lily Bloom", "Botanical & Blooms", "Summer", 150, "10.99");

        // Fresh & Clean - More likely to have less floral, fruity, or sweet scents.
        addSampleCandle("6757", "Sweater Weather Candle", "Fresh & Clean", "Winter", 150, "26.95");
        addSampleCandle("3568", "Eucalyptus Mint Candle", "Fresh & Clean", "Year Round", 150, "26.95");
        addSampleCandle("7408", "Sweet Tea & Lemonade", "Fresh & Clean", "Summer", 150, "26.95");
        addSampleCandle("6411", "Mountainside Mist", "Fresh & Clean", "Winter", 150, "26.96");
        addSampleCandle("8332", "Vanilla Breeze", "Fresh & Clean", "Spring", 150, "26.95");

        // Fruity & Bright - Scents focused on citrus scents.
        addSampleCandle("7737", "Honeycrisp Apple", "Fruity & Bright", "Year Round", 150, "16.95");
        addSampleCandle("6760", "Raspberry Mimosa", "Fruity & Bright", "Summer", 150, "26.95");
        addSampleCandle("3564", "Midnight Blue Citrus", "Fruity & Bright", "Year Round", 150, "26.95");
        addSampleCandle("6939", "Candy Apple Cauldron", "Fruity & Bright", "Fall", 150, "39.95");
        addSampleCandle("8256", "Vampire Blood", "Fruity & Bright", "Fall", 150, "39.95");

        // Treats & Sweets - Scents focus on candy, dessert, and sweet drinks.
        addSampleCandle("6413", "Campfire Cocoa", "Treats & Sweets", "Winter", 150, "26.95");
        addSampleCandle("7735", "Pumpkin Pecan Waffles", "Treats & Sweets", "Fall", 150, "26.95");
        addSampleCandle("6421", "Pumpkin Cinnamon Bun", "Treats & Sweets", "Fall", 150, "26.95");
        addSampleCandle("7778", "Praline Delight", "Treats & Sweets", "Fall", 150, "24.95");
        addSampleCandle("8249", "I Scream Float", "Treats & Sweets", "Fall", 150, "26.95");

        // Warm & Woodsy - Scents are bit stronger than Fresh & Clean. Cologne-like scents.
        addSampleCandle("6419", "Leather & Brandy", "Warm & Woodsy", "Year Round", 150, "26.95");
        addSampleCandle("7404", "Palo Santo", "Warm & Woodsy", "Spring", 150, "26.96");
        addSampleCandle("8426", "Sunrise Woods", "Warm & Woodsy", "Summer", 150, "24.95");
        addSampleCandle("3455", "Into the Night", "Warm & Woodsy", "Winter", 150, "26.95");
        addSampleCandle("8001", "Vanilla Ease", "Warm & Woodsy", "Spring", 150, "26.95");
    }

    private void addSampleCandle(String productID, String productName, String scentType, String seasonAvailability, int quantity, String price) {
        Candle candle = new Candle(productID, productName, scentType, seasonAvailability);
        InventoryCandleItem item = new InventoryCandleItem(candle, quantity, new BigDecimal(price));
        inventory.put(productID, item);
    }

    @Override
    public List<InventoryCandleItem> getAll() {
        return new ArrayList<>(inventory.values());
    }

    @Override
    public List<InventoryCandleItem> getInStock() {
        return inventory.values().stream()
                .filter(item -> item.getQuantity() > 0)
                .collect(Collectors.toList());
    }


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
        inventory.put(productID, item);
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



