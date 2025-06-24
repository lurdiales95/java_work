package com.shoppinginc.service;

import com.shoppinginc.model.OrderItem;
import java.util.Map;

public interface ICartService {
    void addItem(OrderItem item);
    boolean removeItem(String id, int quantityToRemove);
    Map<String, OrderItem> getOrderItems();
    void clearCart();
    int getTotalItemCount();  // Fixed: Changed from String to int
    boolean isEmpty();
    double getCartTotal();
}