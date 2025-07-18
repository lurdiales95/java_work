package com.inventory.manager.model;

public class Product {
    private String productID;
    private String productName;
    private int quantity;
    private double price;

    public Product(String productID, String productName, int quantity, double price) {
        this.productID = productID;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }

    public String getProductID() {
        return productID;
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }


    public void setQuantity(int quantity) {
        if (quantity >= 0) {
            this.quantity = quantity;
        }
    }

    public void setPrice ( double price) {
        if (price > 0) {
            this.price = price;
        }
    }

    @Override
    public String toString() {
        return String.format("| %-4s | %-30s | %-8d | $%-6.2f |",
                productID, productName, quantity, price);
    }
}




