package com.example.Capstone_Java_Console_App_Inventory_Manager.model;

import java.math.BigDecimal;

public record Candle(String productID, String productName) {
    @Override
    public String toString() {
        return String.format(productName);
    }
}
