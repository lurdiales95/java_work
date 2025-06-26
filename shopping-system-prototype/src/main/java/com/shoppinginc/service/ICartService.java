package com.shoppinginc.service;

import com.shoppinginc.model.OrderItem;
import java.util.List;

// cartService blueprint
public interface ICartService {

    // sets up boolean true or false system to show of cart is empty
    boolean isEmpty();

    // adds item to cart with all info that goes under item = id, name, etc.
    void addItem(OrderItem item);

    // sets up boolean to remove items with parameters id and qty, gives true or false upon completion
    boolean removeItem(String id, int qty);

    // gets list of items to display for user when they want to know what items are in the cart
    List<OrderItem> getAllItems();

    // gets information regard the total cost of the items combined and gives it as a double (decimals)
    double getCartTotal();

    // clears cart when user is done with their purchase or has cancelled in the middle of the shopping process
    void clearCart();

    // gets total number of items in cart - used for the "15 items or less" checkout line as an example
    int getTotalItemCount();
}
