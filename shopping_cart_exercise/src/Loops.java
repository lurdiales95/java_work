import java.util.Scanner;

public class Loops {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Example: order info
        double orderTotal = 250.00;
        double taxRate = 0.07;
        double tax = orderTotal * taxRate;
        double total = orderTotal + tax;

        // Confirmation loop
        String confirm = "";
        while (!confirm.equalsIgnoreCase("yes")) {
            System.out.print("Do you want to confirm your order? (yes/no): ");
            confirm = scanner.nextLine();
        }

        // Final summary
        System.out.println("\nOrder confirmed!");
        System.out.printf("Order Total: $%.2f\n", orderTotal);
        System.out.printf("Tax: $%.2f\n", tax);
        System.out.printf("Grand Total: $%.2f\n", total);

        scanner.close();
    }
}

