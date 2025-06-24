package com.shoppinginc.model;

public class OrderItem {
    private String id;
    private int quantity;
    private final String name;
    private double price;

    public OrderItem(String id, int quantity, String name, double price) {
        this.id = id;
        this.quantity = quantity;
        this.name = name;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {  // Fixed: Added parameter
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +  // Fixed: Added name field
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
