import java.util.Scanner;

public class Decisions {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Start scanner

        // Constants
        double taxRate = 0.07;
        double standardShipping = 2.00;
        double twoDayShipping = 5.00;
        double overnightShipping = 10.00;

        // User input
        System.out.println("Are you tax-exempt? (yes/no)");
        String taxExempt = scanner.nextLine();

        System.out.println("What is the order total?");
        double orderTotal = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        System.out.println("Choose a shipping speed: (standard/2-day/overnight)");
        String shippingMethod = scanner.nextLine();

        System.out.println("Do you have a promo code?");
        String promoCode = scanner.nextLine();

        // Discount logic
        double discount = 0;
        if (orderTotal > 500) {
            discount = orderTotal * 0.10;
        } else if (orderTotal > 100) {
            discount = orderTotal * 0.05;
        }

        double subtotal = orderTotal - discount;

        // Tax calculation
        double tax = 0;
        if (!taxExempt.equalsIgnoreCase("yes")) {
            tax = subtotal * taxRate;
        }

        // Shipping logic
        double shipping = 0;
        if (shippingMethod.equalsIgnoreCase("standard")) {
            if (promoCode.equalsIgnoreCase("FREE")) {
                shipping = 0;
            } else {
                shipping = standardShipping;
            }
        } else if (shippingMethod.equalsIgnoreCase("2-day")) {
            shipping = twoDayShipping;
        } else if (shippingMethod.equalsIgnoreCase("overnight")) {
            shipping = overnightShipping;
        } else {
            System.out.println("Invalid shipping method selected. Defaulting to standard shipping.");
            shipping = standardShipping;
        }

        // Final total
        double total = subtotal + tax + shipping;

        // Output summary
        System.out.println("\n--- Order Summary ---");
        System.out.printf("Original Order Total: $%.2f\n", orderTotal);
        System.out.printf("Discount:              $%.2f\n", discount);
        System.out.printf("Subtotal:              $%.2f\n", subtotal);
        System.out.printf("Tax:                   $%.2f\n", tax);
        System.out.printf("Shipping:              $%.2f\n", shipping);
        System.out.printf("Grand Total:           $%.2f\n", total);

        scanner.close();
    }
}
