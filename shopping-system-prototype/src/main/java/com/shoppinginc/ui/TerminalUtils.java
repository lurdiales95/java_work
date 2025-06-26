// activates user interface
package com.shoppinginc.ui;

import com.shoppinginc.model.OrderItem;
import com.shoppinginc.service.ICartService;

import java.util.Scanner;

public class TerminalUtils {
    // reads user's response
    private final Scanner scanner = new Scanner(System.in);
    // stores item info on cart list
    private final ICartService cartService;

    // constructor to take info from CartService
    public TerminalUtils(ICartService cartService) {

        this.cartService = cartService;
    }
    // main loop
    public void run() {
        boolean running = true;

        while (running) {
            showMenu();
            String choice = scanner.nextLine().trim();

            // menu selections based on numerical String
            switch (choice) {
                case "1" -> displayCart();
                case "2" -> removeItem();
                case "3" -> addItem();
                case "4" -> checkout();
                case "5" -> running = false;
                default -> System.out.println("Invalid option. Try again.");
            }

            // command to signal that the user wants app to stop running the loop using choice 5
            if (!choice.equals("5")) {
                promptEnterKey();
            }
        }

        // response to closing the loop
        System.out.println("Thank you for using Shopping Inc. Goodbye!");
    }

    // sets up user interface when using the cartService
    private void showMenu() {
        System.out.println("\n--- CASH REGISTER MAIN MENU ---");
        System.out.println("1. Display Cart");
        System.out.println("2. Remove an Item");
        System.out.println("3. Add an Item");
        System.out.println("4. Checkout");
        System.out.println("5. Exit");
        System.out.print("Please select an option (1-5): ");
    }

    // Shows user what items are in the cartService
    protected void displayCart() {
        System.out.println("\n--- CART ITEMS ---");

        // notifies when cartService does not have any items in it
        if (cartService.isEmpty()) {
            System.out.println("Your cart is currently empty.");

        // if the cart is not empty, then this tells you the items in cartService
        } else {
            for (OrderItem item : cartService.getAllItems()) {
                System.out.println(item);
            }

            // prints the total cost owed
            System.out.printf("Total: $%.2f%n", cartService.getCartTotal());
        }
    }

    // allows the details of the items that will be added to the cart and adds them to the cartService.
    protected void addItem() {
        System.out.print("Enter item ID: ");
        String id = scanner.nextLine().trim();

        // intakes name of items
        System.out.print("Enter item name: ");
        String name = scanner.nextLine().trim();

        // intakes quantity of each item type
        System.out.print("Enter quantity: ");
        int quantity = Integer.parseInt(scanner.nextLine().trim());

        // intakes cost of each item
        System.out.print("Enter price per item: ");
        double price = Double.parseDouble(scanner.nextLine().trim());

        // Adds item info to cartService
        OrderItem item = new OrderItem(id, quantity, name, price);
        cartService.addItem(item);
        // Shows that the item has been added to the cartService successfully.
        System.out.println("Item added.");
    }

    // allows user to remove item from cart. Asks what item to remove based on id and quantity.
    // name of item not necessary since the IDs should be unique to each item.
    protected void removeItem() {
        displayCart();

        if (cartService.isEmpty()) return;

        System.out.print("Enter the item ID to remove: ");
        String id = scanner.nextLine().trim();

        System.out.print("Enter quantity to remove: ");
        int qtyToRemove = Integer.parseInt(scanner.nextLine().trim());

        cartService.removeItem(id, qtyToRemove);
        System.out.println("Item removed.");
    }

    // activates checkout feature
    protected void checkout() {
        if (cartService.isEmpty()) {
            // notifies if there's nothing in the cartService data
            System.out.println("Cart is empty. Nothing to checkout.");
        } else {
            // if there are items in the cartService, this will give you the total cost of items added up
            System.out.printf("Final total: $%.2f%n", cartService.getCartTotal());

            // clears cart to have the next user add their item info or simply to just start over
            cartService.clearCart();

            // notification that the loop has been closed after the user has checked checked out
            System.out.println("Thank you for your purchase! Come again soon!");
        }
    }

    // usees the promptEnterKey to have "Enter" key be used to bring you back to the main menu after you've completed a task
    private void promptEnterKey() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }
}