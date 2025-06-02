import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the order form!");
        System.out.println("What is your name?");
        String name = scanner.nextLine();

        System.out.println("Hello, " + name + "! Let's get started with your order.");

        System.out.println("What product would you like to purchase?");
        String product = scanner.nextLine();

        System.out.println("How many would you like?");
        int quantity = scanner.nextInt();

        System.out.println("What is the unit price?");
        double price = scanner.nextDouble();

        double subtotal = quantity * price;
        double tax = subtotal * 0.07;
        double grandTotal = subtotal + tax;

        System.out.println("\nOrder Summary");
        System.out.println("-------------------------------");
        System.out.println("Item: " + product);
        System.out.println("Quantity: " + quantity);
        System.out.printf("Unit Price: $%.2f\n", price);
        System.out.println("-------------------------------");
        System.out.printf("Subtotal: $%.2f\n ", subtotal);
        System.out.printf("Tax (7%%): $%.2f\n", tax);
        System.out.printf("Grand Total: $%.2f\n", grandTotal);
        System.out.println("-------------------------------");

        System.out.println("Thank you for your order," + name + "!");
        }

        }
