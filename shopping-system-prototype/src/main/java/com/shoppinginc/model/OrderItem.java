package com.shoppinginc.model;


// Data on the items added to the system
public class OrderItem {
    // instance variables that set up what data required per each item in the cart
    private final String id; // id is considered a string because the number/info is specifically set to that item.
    private int quantity; // int since quantity is a whole and can be reduced or increased
    private final String name; // name is obviously unique to the item
    private double price; // not int because of decimals

    // constructor
    public OrderItem(String id, int quantity, String name, double price) {
        this.id = id;
        this.quantity = quantity;
        this.name = name;
        this.price = price;
    }

    // getter for item id, won't change so setter is unnecessary
    public String getId() {
        return id;

    }

    // getter for name of item, setter unnecessary
    public String getName() {
        return name;
    }

    // getter for quantity of item
    public int getQuantity() {
        return quantity;
    }

    // setter to update quantity later
    public void setQuantity(int quantity) {
        this.quantity = quantity;

    }

    // getter for price
    public double getPrice() {
        return price;
    }

    // setter to update price
    public void setPrice(double price) {
        this.price = price;
    }

    // calls on info to print the requested item info
    @Override
    public String toString() {
        return String.format("%s x%d - $%.2f each", name, quantity, price);
    }
}

