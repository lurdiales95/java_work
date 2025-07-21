package com.example.bookstore.repository;

import com.example.bookstore.model.InventoryItem;

import java.util.List;

public interface InventoryRepository {
    List<InventoryItem> getAll();

    List<InventoryItem> getInStock();

    void add(InventoryItem item);

    void update(InventoryItem item);

    void delete(String isbn);

    InventoryItem getByIsbn(String isbn);
}
