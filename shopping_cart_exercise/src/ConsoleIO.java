import java.util.Scanner;

public class ConsoleIO {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        //intro
        System.out.println("Welcome to the shopping cart app!");

        //determine if tax exempt
        System.out.println("\nAre you tax-exempt? (y/n)");
        String taxExempt = scanner.nextLine();

        //ask if item will be shipped
        System.out.println("Shipping? y/n");
        String shipping = scanner.nextLine();

        //ask for order quantity
        System.out.println("Order quantity? ");
        int quantity = scanner.nextInt();
        scanner.nextLine();

        //ask for promo code
        System.out.println("Promo code for free shipping?");
        String promoCode = scanner.nextLine();


        System.out.println("\nDetails:");
        System.out.println("Tax-exempt: " + taxExempt);
        System.out.println("Shipping: " + shipping);
        System.out.println("Order quantity: " + quantity);
        System.out.println("Promo code: " + promoCode);
        System.out.println("Bye");


    }
}

