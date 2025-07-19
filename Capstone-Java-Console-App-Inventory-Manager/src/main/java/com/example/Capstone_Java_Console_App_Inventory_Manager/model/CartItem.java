package com.example.Capstone_Java_Console_App_Inventory_Manager.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CartItem {
    private final Candle candle;
    private int quantity;
    private final BigDecimal price;
    private BigDecimal extendedPrice;

    public CartItem(Candle candle, BigDecimal price, int quantity, BigDecimal extendedPrice) {
        this.candle = candle;
        this.price = price;
        this.quantity = quantity;
        this.extendedPrice = price.multiply(BigDecimal.valueOf(quantity)).setScale(2, RoundingMode.HALF_UP);
    }

    public Candle getCandle() {
        return candle;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("The quantity of candles cannot be negative.");
        }
        this.quantity = quantity;
        this.extendedPrice = this.price.multiply(BigDecimal.valueOf(quantity)).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getExtendedPrice() {
        return extendedPrice;
    }

    public void setExtendedPrice(BigDecimal extendedPrice) {
        this.extendedPrice = extendedPrice;
    }

    @Override
    public String toString() {
        return String.format("%s - Qty %d - Price: $%s - Extended: $%s", candle.toString(), quantity, price, extendedPrice);
    }
}
