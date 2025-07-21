package com.example.bookstore.view;

import com.example.bookstore.model.CartItem;
import com.example.bookstore.service.CartService;
import com.example.bookstore.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Component
public class Kiosk {

    private final CartService cartService;
    private final KioskIO kioskIO;

    @Autowired
    public Kiosk(CartService cartService, KioskIO kioskIO) {
        this.cartService = cartService;
        this.kioskIO = kioskIO;
    }

    public void run() {
        kioskIO.displayWelcome();

        boolean running = true;
        while (running) {
            int choice = kioskIO.displayMenuAndGetChoice();

            switch (choice) {
                case 1:
                    handleAddToCart();
                    break;
                case 2:
                    handleRemoveFromCart();
                    break;
                case 3:
                    handleDisplayCart();
                    break;
                case 4:
                    handleCheckout();
                    break;
                case 5:
                    running = false;
                    kioskIO.displayGoodbye();
                    break;
                default:
                    kioskIO.displayError("Invalid choice. Please try again.");
            }
        }
    }

    private void handleAddToCart() {
        kioskIO.displaySectionHeader("Add Book to Cart");

        String isbn = kioskIO.getStringInput("Enter ISBN: ");
        if (isbn == null) return; // User cancelled

        Integer quantity = kioskIO.getIntegerInput("Enter quantity: ");
        if (quantity == null) return; // User cancelled

        Result<Void> result = cartService.addToCart(isbn, quantity);

        if (result.isSuccess()) {
            kioskIO.displaySuccess(result.getMessage());
        } else {
            kioskIO.displayError(result.getMessage());
        }
    }

    private void handleRemoveFromCart() {
        if (cartService.isEmpty()) {
            kioskIO.displayInfo("Cart is empty. Nothing to remove.");
            return;
        }

        kioskIO.displaySectionHeader("Remove Book from Cart");
        handleDisplayCart(); // Show current cart contents

        String isbn = kioskIO.getStringInput("Enter ISBN to remove: ");
        if (isbn == null) return; // User cancelled

        Integer quantity = kioskIO.getIntegerInput("Enter quantity to remove: ");
        if (quantity == null) return; // User cancelled

        Result<Void> result = cartService.removeFromCart(isbn, quantity);

        if (result.isSuccess()) {
            kioskIO.displaySuccess(result.getMessage());
        } else {
            kioskIO.displayError(result.getMessage());
        }
    }

    private void handleDisplayCart() {
        kioskIO.displaySectionHeader("Cart Contents");

        if (cartService.isEmpty()) {
            kioskIO.displayInfo("Cart is empty.");
            return;
        }

        List<CartItem> cartContents = cartService.getCartContents();
        kioskIO.displayCartContents(cartContents);

        Result<BigDecimal> totalResult = cartService.getTotalPrice();
        if (totalResult.isSuccess()) {
            kioskIO.displayInfo(String.format("Total: $%.2f", totalResult.getData()));
        } else {
            kioskIO.displayError("Error calculating total: " + totalResult.getMessage());
        }
    }

    private void handleCheckout() {
        if (cartService.isEmpty()) {
            kioskIO.displayInfo("Cart is empty. Nothing to checkout.");
            return;
        }

        kioskIO.displaySectionHeader("Checkout");
        handleDisplayCart(); // Show cart before checkout

        boolean confirm = kioskIO.getConfirmation("Proceed with checkout? (y/n): ");
        if (!confirm) {
            kioskIO.displayInfo("Checkout cancelled.");
            return;
        }

        Result<String> result = cartService.checkout();

        if (result.isSuccess()) {
            kioskIO.displaySuccess(result.getMessage());
        } else {
            kioskIO.displayError("Checkout failed: " + result.getMessage());
        }
    }
}