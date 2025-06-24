package com.shoppinginc.service;

import com.shoppinginc.model.OrderItem;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CartService implements ICartService {
    private final Map<String, OrderItem> orderItems = new HashMap<>();

    @Override
    public void addItem(OrderItem item) {
        if (item == null || item.getId() == null || item.getQuantity() <= 0) {
            throw new IllegalArgumentException("Invalid item");
        }

        if (orderItems.containsKey(item.getId())) {
            OrderItem existingItem = orderItems.get(item.getId());
            existingItem.setQuantity(existingItem.getQuantity() + item.getQuantity());
        } else {
            orderItems.put(item.getId(), item);
        }
    }

    @Override
    public boolean removeItem(String itemId, int quantityToRemove) {
        if (itemId == null || quantityToRemove <= 0) {
            return false;
        }

        if (orderItems.containsKey(itemId)) {
            OrderItem existingItem = orderItems.get(itemId);
            int newQuantity = existingItem.getQuantity() - quantityToRemove;
            if (newQuantity > 0) {
                existingItem.setQuantity(newQuantity);
            } else {
                orderItems.remove(itemId);
            }
            return true;
        }
        return false;
    }

    @Override
    public Map<String, OrderItem> getOrderItems() {
        return new HashMap<>(orderItems);  // Fixed: Return copy to protect encapsulation
    }

    public Collection<OrderItem> getAllItems() {
        return orderItems.values();
    }

    @Override
    public double getCartTotal() {
        double total = 0.0;
        for (OrderItem item : orderItems.values()) {
            total += item.getPrice() * item.getQuantity();
        }
        return total;
    }

    @Override
    public void clearCart() {
        orderItems.clear();
    }

    @Override
    public boolean isEmpty() {
        return orderItems.isEmpty();
    }

    @Override
    public int getTotalItemCount() {  // Fixed: Changed return type to int
        int count = 0;
        for (OrderItem item : orderItems.values()) {
            count += item.getQuantity();
        }
        return count;
    }
}