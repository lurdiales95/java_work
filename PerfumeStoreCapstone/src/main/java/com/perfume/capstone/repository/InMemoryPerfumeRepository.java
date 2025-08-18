package com.perfume.capstone.repository;

import com.perfume.capstone.model.InventoryPerfumeItem;
import com.perfume.capstone.model.Perfume;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InMemoryPerfumeRepository implements PerfumeRepository {

    private final Map<String, InventoryPerfumeItem> inventory = new HashMap<>();

    public InMemoryPerfumeRepository() {
        initializeSampleData();
    }

    private void initializeSampleData() {
        // Botanical & Blooms - Floral scented.
        addSamplePerfume("5905", "Japanese Cherry Blossom", "Botanical & Blooms", "Year Round", 150, "26.95");
        addSamplePerfume("4535", "Fresh Cut Lilacs", "Botanical & Blooms", "Spring", 150, "10.99");
        addSamplePerfume("8247", "Ghoul Friend", "Botanical & Blooms", "Fall", 150, "26.95");
        addSamplePerfume("3268", "Moonlight Path", "Botanical & Blooms", "Winter", 150, "26.95");
        addSamplePerfume("3841", "Orange Lily Bloom", "Botanical & Blooms", "Summer", 150, "10.99");

        // Fresh & Clean - More likely to have less floral, fruity, or sweet scents.
        addSamplePerfume("6757", "Sweater Weather", "Fresh & Clean", "Winter", 150, "26.95");
        addSamplePerfume("3568", "Eucalyptus Mint", "Fresh & Clean", "Year Round", 150, "26.95");
        addSamplePerfume("7408", "Sweet Tea & Lemonade", "Fresh & Clean", "Summer", 150, "26.95");
        addSamplePerfume("6411", "Mountainside Mist", "Fresh & Clean", "Winter", 150, "26.96");
        addSamplePerfume("8332", "Vanilla Breeze", "Fresh & Clean", "Spring", 150, "26.95");

        // Fruity & Bright - Scents focused on citrus scents.
        addSamplePerfume("7737", "Honeycrisp Apple", "Fruity & Bright", "Year Round", 150, "16.95");
        addSamplePerfume("6760", "Raspberry Mimosa", "Fruity & Bright", "Summer", 150, "26.95");
        addSamplePerfume("3564", "Midnight Blue Citrus", "Fruity & Bright", "Year Round", 150, "26.95");
        addSamplePerfume("6939", "Candy Apple Cauldron", "Fruity & Bright", "Fall", 150, "39.95");
        addSamplePerfume("8256", "Vampire Blood", "Fruity & Bright", "Fall", 150, "39.95");

        // Treats & Sweets - Scents focus on candy, dessert, and sweet drinks.
        addSamplePerfume("6413", "Campfire Cocoa", "Treats & Sweets", "Winter", 150, "26.95");
        addSamplePerfume("7735", "Pumpkin Pecan Waffles", "Treats & Sweets", "Fall", 150, "26.95");
        addSamplePerfume("6421", "Pumpkin Cinnamon Bun", "Treats & Sweets", "Fall", 150, "26.95");
        addSamplePerfume("7778", "Praline Delight", "Treats & Sweets", "Fall", 150, "24.95");
        addSamplePerfume("8249", "I Scream Float", "Treats & Sweets", "Fall", 150, "26.95");

        // Warm & Woodsy - Scents are bit stronger than Fresh & Clean. Cologne-like scents.
        addSamplePerfume("6419", "Leather & Brandy", "Warm & Woodsy", "Year Round", 150, "26.95");
        addSamplePerfume("7404", "Palo Santo", "Warm & Woodsy", "Spring", 150, "26.96");
        addSamplePerfume("8426", "Sunrise Woods", "Warm & Woodsy", "Summer", 150, "24.95");
        addSamplePerfume("3455", "Into the Night", "Warm & Woodsy", "Winter", 150, "26.95");
        addSamplePerfume("8001", "Vanilla Ease", "Warm & Woodsy", "Spring", 150, "26.95");
    }

    private void addSamplePerfume(String productID, String productName, String scentType, String seasonAvailability, int quantity, String price) {
        Perfume perfume = new Perfume(productID, productName, scentType, seasonAvailability);
        InventoryPerfumeItem item = new InventoryPerfumeItem(perfume, quantity, new BigDecimal(price));
        inventory.put(productID, item);
    }

    @Override
    public List<InventoryPerfumeItem> getAll() {
        return new ArrayList<>(inventory.values());
    }

    @Override
    public List<InventoryPerfumeItem> getInStock() {
        return inventory.values().stream()
                .filter(item -> item.getQuantity() > 0)
                .collect(Collectors.toList());
    }


    @Override
    public void add(InventoryPerfumeItem item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null.");

        }
        inventory.put(item.getPerfume().productID(), item);

    }

    @Override
    public void update(InventoryPerfumeItem item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        String productID = item.getPerfume().productID();
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
    public InventoryPerfumeItem getByProductID(String productID) {
        if (productID == null || productID.trim().isEmpty()) {
            throw new IllegalArgumentException("ProductID cannot be null or empty.");
        }
        return inventory.get(productID);
    }
}



