package com.perfume.capstone.model;

public record Perfume(int productID, String productName, String scentType, String seasonAvailability) {

    // Constructor for creating new perfumes (before database insertion)
    public Perfume(String productName, String scentType, String seasonAvailability) {
        this(0, productName, scentType, seasonAvailability); // 0 for auto-increment
    }

    @Override
    public String toString() {
        return String.format("'%s' - %s [%s]", productName, scentType, seasonAvailability);
    }
}