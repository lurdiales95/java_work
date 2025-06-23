package com.shoppinginc;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    static List<OrderItem> orderItems = new ArrayList<>();

    // Storage for order items
    public Cart() {


    }
    public static void main(String[] args) {
        Cart cart = new Cart();
    }
    public static List<OrderItem> getOrderItems() {
        return orderItems;
    }
    public static void removeItem(int id) {

        orderItems.remove(id);
    }
    public static void addItem(OrderItem orderItem) {
        orderItems.add(orderItem);
    }
    @Override
    public String toString() {
        return "Cart{}";
    }
}
