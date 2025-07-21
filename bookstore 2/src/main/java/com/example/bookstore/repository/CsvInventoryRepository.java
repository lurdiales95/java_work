package com.example.bookstore.repository;

import com.example.bookstore.model.Book;
import com.example.bookstore.model.InventoryItem;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class CsvInventoryRepository implements InventoryRepository {

    private final Map<String, InventoryItem> inventory = new HashMap<>();
    @Value("${inventory.csv.filepath:data/inventory.csv}")
    private String filename;


    // we must invoke load from file using the post construct because the @Value won't be bound until after the object is created.
    @PostConstruct
    public void init() {
        loadFromFile();
    }

    @Override
    public List<InventoryItem> getAll() {
        return new ArrayList<>(inventory.values());
    }

    @Override
    public List<InventoryItem> getInStock() {
        return inventory.values().stream()
                .filter(item -> item.getQuantity() > 0)
                .collect(Collectors.toList());
    }

    @Override
    public void add(InventoryItem item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        inventory.put(item.getBook().isbn(), item);
        saveToFile();
    }

    @Override
    public void update(InventoryItem item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        String isbn = item.getBook().isbn();
        if (!inventory.containsKey(isbn)) {
            throw new IllegalArgumentException("Item with ISBN " + isbn + " not found");
        }
        inventory.put(isbn, item);
        saveToFile();
    }

    @Override
    public void delete(String isbn) {
        if (isbn == null || isbn.trim().isEmpty()) {
            throw new IllegalArgumentException("ISBN cannot be null or empty");
        }
        inventory.remove(isbn);
        saveToFile();
    }

    @Override
    public InventoryItem getByIsbn(String isbn) {
        if (isbn == null || isbn.trim().isEmpty()) {
            throw new IllegalArgumentException("ISBN cannot be null or empty");
        }
        return inventory.get(isbn);
    }

    private void loadFromFile() {
        File file = new File(filename);
        if (!file.exists()) {
            return; // No file exists yet, start with empty inventory
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] parts = line.split(",");
                if (parts.length == 6) {
                    String isbn = parts[0].trim();
                    String title = parts[1].trim();
                    String author = parts[2].trim();
                    String genre = parts[3].trim();
                    int quantity = Integer.parseInt(parts[4].trim());
                    BigDecimal price = new BigDecimal(parts[5].trim());

                    Book book = new Book(isbn, title, author, genre);
                    InventoryItem item = new InventoryItem(book, quantity, price);
                    inventory.put(isbn, item);
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
            for (InventoryItem item : inventory.values()) {
                Book book = item.getBook();
                writer.printf("%s,%s,%s,%s,%d,%.2f%n",
                        book.isbn(),
                        book.title(),
                        book.author(),
                        book.genre(),
                        item.getQuantity(),
                        item.getPrice());
            }
        } catch (IOException e) {
            throw new RuntimeException("Error writing to file: " + filename, e);
        }
    }
}
