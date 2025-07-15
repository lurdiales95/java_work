package com.inventory.manager;

import com.inventory.manager.model.Product;
import com.inventory.manager.service.InventoryService;

import java.util.Scanner;
import java.util.SortedMap;

public class MainMenu {
    private InventoryService inventoryService = new InventoryService();

    private MainMenu() {
        public void showMainMenu() {
            System.out.println("===== Inventory Manager =====");
            System.out.println("1. Add Product");
            System.out.println("2. View Products");
            System.out.println("3. Search Product");
            System.out.println("4. Update Product");
            System.out.println("5. Delete Product");
            System.out.println("6. Save Inventory to File");
            System.out.println("7. Load Inventory from File");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");
        }




        inventoryService.loadInventory();
    }

    public static void main( String[] args ) {
        MainMenu menu = new MainMenu();
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            menu.showMainMenu();
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("===== Add Product =====");
                    System.out.print("Enter Product ID: ");
                    String productID = scanner.next();
                    System.out.print("Enter Product Name: ");
                    String productName = scanner.next();
                    System.out.print("Enter Quantity: ");
                    int quantity = scanner.nextInt();
                    System.out.print("Enter Price: ");
                    double price = scanner.nextDouble();

                    menu.inventoryService.addProduct(productID, productName, quantity, price);
                    System.out.println("Product added successfully!");


                    break;

                case 2:
                    menu.inventoryService.displayInventory();
                    break;

                case 3:
                    System.out.println("===== Search Product =====");
                    System.out.print("Enter Product ID or Name: ");
                    String searchTerm = scanner.next();

                    Product foundProduct = menu.inventoryService.searchProduct(searchTerm);

                    if (foundProduct != null) {
                        System.out.println("Product Found:");
                        System.out.println(foundProduct);  // Uses our toString() method!
                    } else {
                        System.out.println("Product not found!");
                    }
                    break;

                case 4:
                    System.out.println("===== Update Product =====");
                    System.out.print("Enter Product ID: ");
                    String updateID = scanner.next();
                    System.out.print("Enter New Quantity: ");
                    int newQuantity = scanner.nextInt();
                    System.out.print("Enter new price: ");
                    double newPrice = scanner.nextDouble();

                    if (menu.inventoryService.updateProduct(updateID, newQuantity, newPrice)) {
                        System.out.println("Product updated succesfully!");
                    } else {
                        System.out.println("Product not found!");
                    }
                    break;

                case 5:
                    System.out.println("===== Delete Product =====");
                    System.out.print("Enter Product ID: ");
                    String deleteID = scanner.next();

                    if (menu.inventoryService.removeProduct(deleteID)) {
                        System.out.println("Product deleted successfully!");
                    } else {
                        System.out.println("Product not found!");
                    }
                    break;

                case 6:
                    if (menu.inventoryService.saveInventory()) {
                        System.out.println("Inventory successfully saved to inventory.txt");
                    } else {
                        System.out.println("Error saving inventory!");
                    }
                    break;

                case 7:
                    if (menu.inventoryService.loadInventory()) {
                        System.out.println("Inventory successfully loaded from inventory.txt");

                    } else  {
                        System.out.println("Error loading inventory (file may not exist)!");

                    }
                    break;
                case 8:
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
                    break;
            }

        } while (choice != 8);




    }

    private void showMainMenu() {
    }
}
