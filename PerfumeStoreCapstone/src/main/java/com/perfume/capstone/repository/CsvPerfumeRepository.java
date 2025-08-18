package com.perfume.capstone.repository;



import com.perfume.capstone.model.InventoryPerfumeItem;
import com.perfume.capstone.model.Perfume;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class CsvPerfumeRepository implements PerfumeRepository {

    private final Map<String, InventoryPerfumeItem> inventory = new HashMap<>();
    @Value("${perfume-inventory.csv.filepath:data/perfume-inventory.csv}")
    private String filename;

    // We must invoke load from file to the post construct because the @Value  won't be bound until after the object is created
    @PostConstruct
    public void init() { loadFromFile(); }

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
            throw new IllegalArgumentException("Item cannot be null");
        }
        inventory.put(item.getPerfume().productID(), item);
        saveToFile();
    }

    @Override
    public void update(InventoryPerfumeItem item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null.");
        }
        String productID = item.getPerfume().productID();
        if (!inventory.containsKey(productID)) {
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
    public InventoryPerfumeItem getByProductID(String productID) {
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
                    String scentType = parts[2].trim();
                    String seasonAvailability = parts[3].trim();
                    int quantity = Integer.parseInt(parts[4].trim());
                    BigDecimal price = new BigDecimal(parts[5].trim());

                    Perfume perfume = new Perfume(productID, productName, scentType, seasonAvailability);
                    InventoryPerfumeItem item = new InventoryPerfumeItem(perfume, quantity, price);
                    inventory.put(productID, item);
                }

            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading from file: " + filename, e);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Error parsing number from file: " + filename, e);
        }
    }

    private void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (InventoryPerfumeItem item : inventory.values()) {
                Perfume perfume = item.getPerfume();
                writer.printf("%s,%s,%s,%s,%d,%.2f%n",
                        perfume.productID(),
                        perfume.productName(),
                        perfume.scentType(),
                        perfume.seasonAvailability(),
                        item.getQuantity(),
                        item.getPrice());
            }

        } catch (IOException e) {
            throw new RuntimeException("Error writing to file: " + filename, e);
        }
    }
}
