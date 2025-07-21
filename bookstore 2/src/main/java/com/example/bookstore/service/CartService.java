package com.example.bookstore.service;

import com.example.bookstore.model.CartItem;
import com.example.bookstore.model.InventoryItem;
import com.example.bookstore.model.Result;
import com.example.bookstore.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
public class CartService {

    private final InventoryRepository inventoryRepository;
    private final Map<String, CartItem> cart = new HashMap<>(); // ISBN -> CartItem

    @Autowired
    public CartService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public Result<Void> addToCart(String isbn, int quantity) {
        if (isbn == null || isbn.trim().isEmpty()) {
            return new Result<>(false, "ISBN cannot be null or empty", null);
        }
        if (quantity <= 0) {
            return new Result<>(false, "Quantity must be greater than 0", null);
        }

        InventoryItem item = inventoryRepository.getByIsbn(isbn);
        if (item == null) {
            return new Result<>(false, "Book not found with ISBN: " + isbn, null);
        }

        int currentCartQuantity = cart.containsKey(isbn) ? cart.get(isbn).getQuantity() : 0;
        int newTotalQuantity = currentCartQuantity + quantity;

        if (newTotalQuantity > item.getQuantity()) {
            return new Result<>(false,
                    String.format("Not enough stock. Available: %d, Requested: %d",
                            item.getQuantity(), newTotalQuantity), null);
        }

        if (cart.containsKey(isbn)) {
            // Update existing cart item
            CartItem existingCartItem = cart.get(isbn);
            existingCartItem.setQuantity(newTotalQuantity);
        } else {
            // Create new cart item
            BigDecimal price = item.getPrice().setScale(2, RoundingMode.HALF_UP);
            CartItem newCartItem = new CartItem(item.getBook(), newTotalQuantity, price);
            cart.put(isbn, newCartItem);
        }

        return new Result<>(true,
                String.format("Added %d copies of '%s' to cart", quantity, item.getBook().title()), null);
    }

    public Result<Void> removeFromCart(String isbn, int quantity) {
        if (isbn == null || isbn.trim().isEmpty()) {
            return new Result<>(false, "ISBN cannot be null or empty", null);
        }
        if (quantity <= 0) {
            return new Result<>(false, "Quantity must be greater than 0", null);
        }

        if (!cart.containsKey(isbn)) {
            return new Result<>(false, "Item not in cart", null);
        }

        CartItem cartItem = cart.get(isbn);
        int currentQuantity = cartItem.getQuantity();
        String title = cartItem.getBook().title();

        if (quantity >= currentQuantity) {
            cart.remove(isbn);
            return new Result<>(true,
                    String.format("Removed all copies of '%s' from cart", title), null);
        } else {
            cartItem.setQuantity(currentQuantity - quantity);
            return new Result<>(true,
                    String.format("Removed %d copies of '%s' from cart", quantity, title), null);
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
            String isbn = entry.getKey();
            CartItem cartItem = entry.getValue();
            int purchasedQuantity = cartItem.getQuantity();

            InventoryItem item = inventoryRepository.getByIsbn(isbn);
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