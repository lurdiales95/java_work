package com.inventory.manager.service;

import com.inventory.manager.model.Product;

import java.util.ArrayList;

public class InventoryService {
    private ArrayList<Product> inventory = new ArrayList<>();

    public void addProduct(String productID, String productName, int quantity, double price) {
        Product newProduct = new Product(productID, productName, quantity, price);
        inventory.add(newProduct);


    }

    public void displayInventory() {

        System.out.println("===== Inventory List =====");
        System.out.println("ID | Name | Quantity | Price");
        System.out.println("-----------------------------------------");

        for (Product product : inventory) {
            System.out.println(product);

        }

        System.out.println("-----------------------------------------\n" +
                "Press Enter to return to the main menu...");


    }

    public Product searchProduct(String searchTerm) {

        for (Product product : inventory) {
            if (searchTerm.equals(product.getProductID()) || searchTerm.equals(product.getProductName())) {

                return product;

            }
        }
        return null;

    }


}




/*          inventory.add("Labubu");
            inventory.add("Pokemon Cards");
            inventory.add("Pink Bike"); */