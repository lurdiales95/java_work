package com.shoppinginc.service;

import com.shoppinginc.model.OrderItem;
import java.util.*;

public class CartService implements ICartService {
    private final Map<String, OrderItem> cart = new HashMap<>();

    @Override
    public boolean isEmpty() {
        return cart.isEmpty();
    }

    @Override
    public void addItem(OrderItem item) {
        cart.merge(item.getId(), item, (existing, incoming) -> {
            existing.setQuantity(existing.getQuantity() + incoming.getQuantity());
            return existing;
        });
    }

    @Override
    public boolean removeItem(String id, int qty) {
        OrderItem existing = cart.get(id);
        if (existing == null) return false;

        if (qty >= existing.getQuantity()) {
            cart.remove(id);
        } else {
            existing.setQuantity(existing.getQuantity() - qty);
        }
        return true;
    }

    @Override
    public List<OrderItem> getAllItems() {
        return new ArrayList<>(cart.values());
    }

    @Override
    public double getCartTotal() {
        return cart.values().stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
    }

    @Override
    public void clearCart() {
        cart.clear();
    }

    public Map<String, OrderItem> getOrderItems() {
        return cart;
    }

    @Override
    public int getTotalItemCount() {
        return cart.values().stream().mapToInt(OrderItem::getQuantity).sum();
    }
}
