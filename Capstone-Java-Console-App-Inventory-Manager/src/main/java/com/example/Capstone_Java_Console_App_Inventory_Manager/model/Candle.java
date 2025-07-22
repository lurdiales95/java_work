package com.example.Capstone_Java_Console_App_Inventory_Manager.model;

import java.math.BigDecimal;

public record Candle(String productID, String productName, String scentType, String seasonAvailability) {
    @Override
    public String toString() {
        return String.format("'%s' - %s [%s]", productName, scentType, seasonAvailability);
    }
}
