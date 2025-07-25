package com.example.Capstone_Java_Console_App_Inventory_Manager.view;

import com.example.Capstone_Java_Console_App_Inventory_Manager.model.CartItem;
import com.example.Capstone_Java_Console_App_Inventory_Manager.model.Result;
import com.example.Capstone_Java_Console_App_Inventory_Manager.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

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
            kioskIO.showMenu();
            String choice = kioskIO.getMenuChoice();

            // menu selections based on numerical String
            switch (choice) {
                case "1" -> handleAddToCart();
                case "2" -> handleRemoveFromCart();
                case "3" -> handleDisplayCart();
                case "4" -> handleCheckout();
                case "5" -> running = false;
                default -> kioskIO.displayError("Invalid choice. Please try again.");
            }

            // command to signal that the user wants app to stop running the loop using choice 5
            if (!choice.equals("5")) {
                kioskIO.promptEnterKey();
            }
        }

        // response to closing the loop
        kioskIO.displayGoodbye();
    }

    private void handleAddToCart() {
        kioskIO.displaySectionHeader("Add Candle to Cart");

        String productID = kioskIO.getStringInput("Enter ProductID: ");
        if (productID == null) return;

        Integer quantity = kioskIO.getIntegerInput("Enter quantity: ");
        if (quantity == null) return;

        Result<Void> result = cartService.addToCart(productID, quantity);

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

        kioskIO.displaySectionHeader("Remove Candle from Cart");
        handleDisplayCart();

        String productID = kioskIO.getStringInput("Enter ProductID to remove: ");
        if (productID == null) return;

        Integer quantity = kioskIO.getIntegerInput("Enter quantity to remove: ");
        if (quantity == null) return;

        Result<Void> result = cartService.removeFromCart(productID, quantity);

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