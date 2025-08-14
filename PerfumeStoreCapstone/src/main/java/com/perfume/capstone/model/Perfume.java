package com.perfume.capstone.model;

public record Perfume(String productID, String productName, String scentType, String seasonAvailability) {
    @Override
    public String toString() {
        return String.format("'%s' - %s [%s]", productName, scentType, seasonAvailability);
    }
}
