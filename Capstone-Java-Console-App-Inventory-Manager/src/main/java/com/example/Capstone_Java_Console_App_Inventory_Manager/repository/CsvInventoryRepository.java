package com.example.Capstone_Java_Console_App_Inventory_Manager.repository;

import com.example.Capstone_Java_Console_App_Inventory_Manager.model.Candle;
import com.example.Capstone_Java_Console_App_Inventory_Manager.model.InventoryCandleItem;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;


public class CsvInventoryRepository implements InventoryRepository {
    private void loadFromFIle() {}

    private final Map<String, InventoryCandleItem> inventory = new HashMap<>();
    @Value ("$candle-inventory.csv.filepath=data/candle-inventory.csv}")
    private String filename;

    // We must invoke load from file to the post construct because the @Value  won't be bound until after the object is created
    @PostConstruct
    public void init() { loadFromFile(); }

    @Override
    public List<InventoryCandleItem> getAll() { return new ArrayList<>(inventory.values());

    @Override
    public List<InventoryCandleItem> getInStock() {
            return inventory.values().stream()
                    .filter(item -> item.getQuantity() > 0)
                    .collect(Collectors.toList());
        }

    }
    @Override
        public void add(InventoryCandleItem item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        inventory.put(item.getCandle().productID(), item);
        saveToFile();


    }

    @Override
    public void update(InventoryCandleItem item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null.");
        }
        String productID = item.getCandle().productID();
        if (inventory.containsKey(productID)) {
            throw new IllegalArgumentException("Item with ProductID " + productID + " not found.");
        }
        inventory.put(productID, item);
        saveToFile();
    }

    @Override
    public void delete(String productID) {
        if (productID == null || productID.trim().isEmpty()) {
            throw new IllegalArgumentException("ProductID cannot be null or empty.");

        }
        inventory.remove(productID);
        saveToFile();

    }

    @Override
    public InventoryCandleItem getByProductID(String productID) {
        if (productID == null || productID.trim().isEmpty()) {
            throw new IllegalArgumentException("ProductID cannot be null or empty.");
    }
        return inventory.get(productID);
    }

    private void loadFromFile() {
        File file = new File(filename);
        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;

                }

                String[] parts = line.split(",");
                if (parts.length == 6) {
                    String productID = parts[0].trim();
                    String productName = parts[1].trim();
                    int quantity = Integer.parseInt(parts[2].trim());
                    BigDecimal price = new BigDecimal(parts[3].trim());

                    Candle candle = new Candle(productID, productName);
                    InventoryCandleItem item = new InventoryCandleItem(candle, quantity, price);
                    inventory.put(productID, item);


                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading from file: " + filename, e);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Error parsing number from file: " + filename, e);
        }
    }

    private void SaveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (InventoryCandleItem item: inventory.values()) {
                Candle candle = item.getCandle();
                writer.printf("%s,%s,%d,%.2f%n"),
                candle.productID(),
                candle.productName(),
                item.getQuantity(),
                item.getPrice());
            }

        } catch (IOException e) {
            throw new RuntimeException("Error writing to file: " + filename, e);
        }
    }

}
