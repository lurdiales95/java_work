package com.shoppinginc.service;

import com.shoppinginc.model.OrderItem;
import java.util.*;


// CartService powers to add, remove, checkout, etc.
public class CartService implements ICartService {
    // Map = way to store items using a String and OrderItem variable
    private final Map<String, OrderItem> cart = new HashMap<>();

    @Override
    public boolean isEmpty() {
        return cart.isEmpty();
    }

    // Adding items to cart or updating quantity of existing items in cart.
    @Override
    public void addItem(OrderItem item) {
        cart.merge(item.getId(), item, (existing, incoming) -> {
            existing.setQuantity(existing.getQuantity() + incoming.getQuantity());
            return existing;
        });
    }

    // Removes item by String id and int qty.
    @Override
    public boolean removeItem(String id, int qty) {
        OrderItem existing = cart.get(id);

        // Determines if cart is empty
        if (existing == null) return false;

        // Removes items based on id and quantity
        if (qty >= existing.getQuantity()) {
            cart.remove(id);
        } else {
            // Can also reduce quantity without taking the whole item selection out of cart.
            existing.setQuantity(existing.getQuantity() - qty);
        }
        return true;
    }

    // Prints all items in a cart.
    @Override
    public List<OrderItem> getAllItems() {
        return new ArrayList<>(cart.values());
    }

    // Loops items. Gets price * quantity and adds to give total bill.
    @Override
    public double getCartTotal() {
        return cart.values().stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
    }

    // Removes cart items after user has checked out.
    @Override
    public void clearCart() {
        cart.clear();
    }

    // Allows for testing the cart. Under CartServiceTest class?
    public Map<String, OrderItem> getOrderItems() {
        return cart;
    }

    // Gives total number of items in the cart overall. (RL Exp: Under 15 items line).
    @Override
    public int getTotalItemCount() {
        return cart.values().stream().mapToInt(i -> i.getQuantity()).sum();
    }
}
