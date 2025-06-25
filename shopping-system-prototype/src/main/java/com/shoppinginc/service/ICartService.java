package com.shoppinginc.service;

import com.shoppinginc.model.OrderItem;
import java.util.List;

public interface ICartService {
    boolean isEmpty();
    void addItem(OrderItem item);
    boolean removeItem(String id, int qty);
    List<OrderItem> getAllItems();
    double getCartTotal();
    void clearCart();
    int getTotalItemCount();
}
