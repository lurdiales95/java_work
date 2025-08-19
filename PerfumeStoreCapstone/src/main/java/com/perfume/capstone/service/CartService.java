package com.perfume.capstone.service;

import com.perfume.capstone.model.CartItem;
import com.perfume.capstone.model.InventoryPerfumeItem;
import com.perfume.capstone.model.Result;
import com.perfume.capstone.repository.PerfumeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
public class CartService {

    private final PerfumeRepository perfumeRepository;
    private final Map<String, CartItem> cart = new HashMap<>(); // ProductID -> CartItem

    @Autowired
    public CartService(PerfumeRepository perfumeRepository) {
        this.perfumeRepository = perfumeRepository;
    }

    public Result<Void> addToCart(int productID, int quantity) {
        if (productID <= 0) {
            return new Result<>(false, "ProductID must be positive", null);
        }
        if (quantity <= 0) {
            return new Result<>(false, "Quantity must be greater than 0", null);
        }

        InventoryPerfumeItem item = perfumeRepository.getByProductID(productID);
        if (item == null) {
            return new Result<>(false, "Product not found with ProductID " + productID, null);
        }

        String productKey = String.valueOf(productID); // Convert to String for cart map key
        int currentCartQuantity = cart.containsKey(productKey) ? cart.get(productKey).getQuantity() : 0;
        int newTotalQuantity = currentCartQuantity + quantity;

        if (newTotalQuantity > item.getQuantity()) {
            return new Result<>(false,
                    String.format("Not enough stock. Available: %d, Requested: %d",
                            item.getQuantity(), newTotalQuantity), null);
        }

        if (cart.containsKey(productKey)) {
            // Update existing cart item
            CartItem existingCartItem = cart.get(productKey);
            existingCartItem.setQuantity(newTotalQuantity);
        } else {
            // Create new cart item
            BigDecimal price = item.getPrice().setScale(2, RoundingMode.HALF_UP);
            CartItem newCartItem = new CartItem(item.getPerfume(), newTotalQuantity, price);
            cart.put(productKey, newCartItem);
        }

        return new Result<>(true,
                String.format("Added %d of '%s' to cart", quantity, item.getPerfume().productName()), null);
    }

    public Result<Void> removeFromCart(int productID, int quantity) {
        if (productID <= 0) {
            return new Result<>(false, "ProductID must be positive", null);
        }
        if (quantity <= 0) {
            return new Result<>(false, "Quantity must be greater than 0", null);
        }

        String productKey = String.valueOf(productID); // Convert to String for cart map key
        if (!cart.containsKey(productKey)) {
            return new Result<>(false, "Item not in cart", null);
        }

        CartItem cartItem = cart.get(productKey);
        int currentQuantity = cartItem.getQuantity();
        String productName = cartItem.getPerfume().productName();

        if (quantity >= currentQuantity) {
            cart.remove(productKey);
            return new Result<>(true,
                    String.format("Removed all '%s' from cart", productName), null);
        } else {
            cartItem.setQuantity(currentQuantity - quantity);
            return new Result<>(true,
                    String.format("Removed %d of '%s' from cart", quantity, productName), null);
        }
    }

    public Result<BigDecimal> getTotalPrice() {
        BigDecimal total = BigDecimal.ZERO;

        for (CartItem cartItem : cart.values()) {
            total = total.add(cartItem.getTotalPrice());
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
            String productKey = entry.getKey();
            CartItem cartItem = entry.getValue();
            int purchasedQuantity = cartItem.getQuantity();

            // Convert String key back to int for repository call
            int productIdInt = Integer.parseInt(productKey);
            InventoryPerfumeItem item = perfumeRepository.getByProductID(productIdInt);
            item.setQuantity(item.getQuantity() - purchasedQuantity);
            perfumeRepository.update(item);
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