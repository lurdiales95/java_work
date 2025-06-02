import java.util.Scanner;

public class Arrays {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        //1. Shipping addresses
        String [] addresses = {"123 Main St", "456 Main St", "789 Main St"};

        System.out.println("Shipping addresses");
        for (int i = 0; i < addresses.length; i++) {
            System.out.println((i + 1) + ". " + addresses[i]);

        }
        System.out.print("Shipping address? ");
        int addressChoice = scanner.nextInt();
        scanner.nextLine();
        String selectedAddress = addresses[addressChoice -1];

        //2. Order quant
        System.out.print("Order quantity? ");
        int quantity = scanner.nextInt();
        scanner.nextLine();

        //3. Product sizes
        String[] sizes = {"small", "medium", "large"};
        System.out.println("Sizes:");
        for (int i = 0; i < sizes.length; i++) {
            System.out.println((i + 1) + ". " + sizes[i]);
        }
        System.out.print("Size? ");
        int sizeChoice = scanner.nextInt();
        scanner.nextLine();
        String selectedSize = sizes[sizeChoice - 1];

        //4. Promo code info
        System.out.print("Promo code for free shipping? ");
        String promoCode = scanner.nextLine();

        //5. Summary
        System.out.println("\nYour Order:");
        System.out.println("Shipping to: " + selectedAddress);
        System.out.println("Quantity: " + quantity);
        System.out.println("Size: " +selectedSize);
        System.out.println("Promo code: " + promoCode);
        System.out.println("Thank you for shopping with us!");

        scanner.close();








    }
}
