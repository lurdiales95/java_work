package com.inventory.manager.service;

import com.inventory.manager.model.Product;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class InventoryService {
    private ArrayList<Product> inventory = new ArrayList<>();

    public void addProduct(String productID, String productName, int quantity, double price) {
        Product newProduct = new Product(productID, productName, quantity, price);
        inventory.add(newProduct);
    }

    public void displayInventory() {
        System.out.println("===== Inventory List =====");

        // top border
        System.out.println("+------+--------------------------------+----------+---------+");

        // header row
        System.out.println("| ID   | Product Name                   | Quantity | Price   |");

        // header seperator
        System.out.println("+------+--------------------------------+----------+---------+");

        for (Product product : inventory) {
            System.out.println(product);
        }

        System.out.println("+------+--------------------------------+----------+---------+");
        System.out.println("Press Enter to return to the Main Menu...");
    }

    public Product searchProduct(String searchTerm) {
        for (Product product : inventory) {
            if (searchTerm.equals(product.getProductID()) || searchTerm.equals(product.getProductName())) {
                return product;
            }
        }
        return null;
    }

    // NEW SMART SEARCH METHOD - Combines exact and keyword search
    public ArrayList<Product> smartSearch(String searchTerm) {
        ArrayList<Product> results = new ArrayList<>();

        // First: Try exact match (ID or full name - case insensitive for names)
        for (Product product : inventory) {
            if (searchTerm.equals(product.getProductID()) ||
                    searchTerm.equalsIgnoreCase(product.getProductName())) {
                results.add(product);
                return results; // Return immediately if exact match found
            }
        }

        // Second: Try keyword search if no exact match found
        for (Product product : inventory) {
            if (product.getProductName().toLowerCase().contains(searchTerm.toLowerCase())) {
                results.add(product);
            }
        }

        return results; // Returns empty list if nothing found

    }

    public boolean removeProduct(String productID) {
        Product productToRemove = searchProduct(productID);
        if (productToRemove != null) {
            inventory.remove(productToRemove);
            return true;
        } else {
            return false;
        }
    }

    public boolean saveInventory() {
        try {
            PrintWriter writer = new PrintWriter("inventory.txt");
            for (Product product : inventory) {
                writer.println(product.getProductID() + "," + product.getProductName() + "," + product.getQuantity() + "," + product.getPrice());
            }
            writer.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean loadInventory() {
        try {
            Scanner scanner = new Scanner(new File("inventory.txt"));

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");

                String productID = parts[0];
                String productName = parts[1];
                int quantity = Integer.parseInt(parts[2]);
                double price = Double.parseDouble(parts[3]);

                Product product = new Product(productID, productName, quantity, price);
                inventory.add(product); // method should say inventory = repository.getAll() - return all list of products
            }
            scanner.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean updateProduct(String productID, int quantity, double price) {
        Product productToUpdate = searchProduct(productID);  // Now this will work!
        if (productToUpdate != null) {
            productToUpdate.setQuantity(quantity);
            productToUpdate.setPrice(price);
            return true;
        } else {
            return false;
        }
    }
}




