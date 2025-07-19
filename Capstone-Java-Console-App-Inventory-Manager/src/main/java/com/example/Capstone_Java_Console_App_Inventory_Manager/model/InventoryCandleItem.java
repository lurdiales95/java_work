package com.example.Capstone_Java_Console_App_Inventory_Manager.model;


import java.math.BigDecimal;

public class InventoryCandleItem {
    private Candle candle;
    private int quantity;
    private BigDecimal price;


    public InventoryCandleItem(Candle candle, int quantity, BigDecimal price) {
        this.candle = candle;
        this.quantity = quantity;
        this.price = price;
    }

    public Candle getCandle() { return candle; }

    public int getQuantity() { return quantity; }

    public void setQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("The quantity of the candles cannot be negative.");
        }
        this.quantity = quantity;
    }


    public BigDecimal getPrice() { return price; }

    public void setPrice(BigDecimal price) {
        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price cannot be negative.");
        }
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("%s - Stock: %d - Price: $%.2f", candle.toString(), quantity, price);
    }
}
