package com.shoppinginc;

public class Store {

    void displayCart() {
        System.out.println(Cart.getOrderItems());
    }

    void displayMenu() {
    }
    OrderItem removeItem(OrderItem orderItem) {
        return orderItem;
    }

    OrderItem addItem(OrderItem orderItem) {
        return orderItem;
    }
    void checkOut() {

    }
    void exit() {

    }

    public static void main(String[] args) {
        Store store = new Store();

        Cart.getOrderItems().add(new OrderItem(1, 5));

        Cart.getOrderItems().add(new OrderItem(2, 2));

        Cart.getOrderItems().add(new OrderItem(3, 8));

        Cart.getOrderItems().add(new OrderItem(4, 3));

        Cart.getOrderItems().add(new OrderItem(5, 9));

        store.displayCart();
        Cart.removeItem(2);
        store.displayCart();
        Cart.addItem(new OrderItem(7, 0));
        store.displayCart();
    }
}
