package com.example.Capstone_Java_Console_App_Inventory_Manager.service;

import com.example.Capstone_Java_Console_App_Inventory_Manager.model.CartItem;
import com.example.Capstone_Java_Console_App_Inventory_Manager.model.InventoryCandleItem;
import com.example.Capstone_Java_Console_App_Inventory_Manager.model.Result;
import com.example.Capstone_Java_Console_App_Inventory_Manager.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
public class CartService {

    private final InventoryRepository inventoryRepository;
    private final Map<String, CartItem> cart = new HashMap<>();

    @Autowired
    public CartService(InventoryRepository inventoryRepository) { this.inventoryRepository = inventoryRepository; }

    public Result<Void> addToCart(String productID, int quantity) {
        if (productID == null || productID.trim().isEmpty()) {
            return new Result<>(false, "ProductID cannot be null or empty", null);
        }
        if (quantity <= 0) {
            return new Result<>(false, "Quantity must be greater than 0", null);

        }

        InventoryCandleItem item = inventoryRepository.getByProductID(productID);

        if (item == null) {
            return new Result<>(false, "Product not found with ProductID " + productID, null);
        }

        int currentCartQuantity = cart.containsKey(productID) ? cart.get(productID).getQuantity() : 0;
        int newTotalQuantity = currentCartQuantity + quantity;

        if (newTotalQuantity > item.getQuantity()) {
            return new Result<>(false,
                    String.format("Not enough stock. Available: %d, Requested: %d", item.getQuantity(), newTotalQuantity), null);
        }

        if (cart.containsKey(productID)) {
            // Update existing cart item
            CartItem existingCartItem = cart.get(productID);
            existingCartItem.setQuantity(newTotalQuantity);
        } else {
            // Create new cart item
            BigDecimal price = item.getPrice().setScale(2, RoundingMode.HALF_UP);
            CartItem newCartItem = new CartItem(item.getCandle(), newTotalQuantity, price);
            cart.put(productID, newCartItem);

        }

        return new Result<>(true,
                String.format("Added %d candles of '%s' to cart", quantity, item.getCandle().productName()), null);
    }

    public Result<Void> removeFromCart(String productID, int quantity) {
        if (productID == null || productID.trim().isEmpty()) {
            return new Result<>(false, "ProductID cannot be null or empty", null);
        }
        if (quantity <= 0) {
            return new Result<>(false, "Quantity must be greater than 0", null);
        }

        if (!cart.containsKey(productID)) {
            return new Result<>(false, "Item not in cart", null);
        }

        CartItem cartItem = cart.get(productID);
        int currentQuantity = cartItem.getQuantity();
        String candle = cartItem.getCandle().productName();

        if (quantity >= currentQuantity) {
            cart.remove(productID);
            return new Result<>(true,
                    String.format("Removed all of '%s' candles from cart", candle), null);
        } else {
            cartItem.setQuantity(currentQuantity - quantity);
            return new Result<>(true,
                    String.format("Removed %d candles of '%s' from cart", quantity, candle), null);
        }
    }
    public Result<BigDecimal> getTotalPrice() {
        BigDecimal total = BigDecimal.ZERO;

        for (CartItem cartItem : cart.values()) {
            total = total.add(cartItem.getExtendedPrice());
        }

        return new Result<>(true, "Total calculated successfully", total.setScale(2, RoundingMode.HALF_UP));
    }

    public Result<String> checkout() {
        if (cart.isEmpty()) {
            return new Result<>(false, "Cart is empty", null);
        }

        Result<BigDecimal> totalResult = getTotalPrice();
        if (!totalResult.isSuccess()) {
            return new Result<>(false, totalResult.getMessage(), null);
        }
        BigDecimal total = totalResult.getData();

        // Update inventory quantities
        for (Map.Entry<String, CartItem> entry : cart.entrySet()) {
            String productID = entry.getKey();
            CartItem cartItem = entry.getValue();
            int purchasedQuantity = cartItem.getQuantity();

            InventoryCandleItem item = inventoryRepository.getByProductID(productID);
            item.setQuantity(item.getQuantity() - purchasedQuantity);
            inventoryRepository.update(item);
        }

        cart.clear();

        return new Result<>(true,
                String.format("Checkout successful! Total: $%s", total),
                String.format("$%s", total));
    }

    public List<CartItem> getCartContents() {
        return new ArrayList<>(cart.values());
    }

    public boolean isEmpty() {
        return cart.isEmpty();
    }
}
