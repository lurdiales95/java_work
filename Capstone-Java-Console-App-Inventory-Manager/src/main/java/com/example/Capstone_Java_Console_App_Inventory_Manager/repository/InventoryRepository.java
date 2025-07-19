package com.example.Capstone_Java_Console_App_Inventory_Manager.repository;

import com.example.Capstone_Java_Console_App_Inventory_Manager.model.InventoryCandleItem;

import java.util.List;

public interface InventoryRepository {

    List<InventoryCandleItem> getAll();

    List<InventoryCandleItem> getInStock();

    void add(InventoryCandleItem item);

    void update(InventoryCandleItem item);

    void delete(String productID);

    InventoryCandleItem getByProductID(String productID);

}
