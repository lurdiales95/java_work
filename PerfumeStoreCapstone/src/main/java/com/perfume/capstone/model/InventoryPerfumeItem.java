package com.perfume.capstone.model;

import java.math.BigDecimal;

public class InventoryPerfumeItem {
    private final Perfume perfume;
    private int quantity;
    private BigDecimal price;


    public InventoryPerfumeItem(Perfume perfume, int quantity, BigDecimal price) {
        this.perfume = perfume;
        this.quantity = quantity;
        this.price = price;
    }

    public Perfume getPerfume() { return perfume; }

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
        return String.format("%s - Stock: %d - Price: $%.2f", perfume.toString(), quantity, price);
    }
}
