package com.example.Capstone_Java_Console_App_Inventory_Manager.model;

import java.math.BigDecimal;

public record Candle(String productID, String productName, int quantity, BigDecimal price) {
    @Override
    public String toString() {
        return "Candle{" +
                "productName='" + productName + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
