import java.util.Scanner;

public class Methods {
    //Method display choices list
    private static void displayChoices(String[] choices) {
        for (int i = 0; i < choices.length; i++) {
            System.out.println((i + 1) + ": " + choices[i]);
        }
    }

    //Method to prompt user for an integer
    public static int promptUserForInt(String prompt) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(prompt);
        return Integer.parseInt(scanner.nextLine());

    }

    //Method to prompt user for a string
    private static String promptUserForString(String prompt) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(prompt);
        return scanner.nextLine();
    }

    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);

        //Welcome message
        System.out.println("Welcome to the shopping cart app!");

        //Prompt for tax-exempt
        String taxExempt = promptUserForString("\nAre you tax-exempt? (y/n)");

        //Address options
        String[] addresses = {"123 Main St", "456 Main St", "789 Main St"};
        displayChoices(addresses);
        int addressIndex = promptUserForInt("Shipping address?") - 1;

        //Prompt for shipping method
        String shipping = promptUserForString("\nShipping? (standard/overnight/2day)");

        //Prompt for order quantity
        int orderQuantity = promptUserForInt("\nOrder quantity?");

        //Size options
        String[] sizes = {"small", "medium", "large"};
        displayChoices(sizes);
        int sizeIndex = promptUserForInt("Size?") - 1;

        //Promo code
        String promoCode = promptUserForString("\nPromo code for free shipping?");

        // Output summary
        System.out.println("\nDetails:");
        System.out.println("Tax-exempt: " + taxExempt);
        System.out.println("Address:" + addresses[addressIndex]);
        System.out.println("Shipping: " + shipping);
        System.out.println("Size: " + sizes[sizeIndex]);
        System.out.println("Order quantity: " + orderQuantity);
        System.out.println("Promo code: " + promoCode);
        System.out.println("Bye");

        //Confirmation
        String confirm = promptUserForString("Confirm order y/n");
        if (confirm.equalsIgnoreCase("y")) {
            System.out.println("Bye");
        } else {
            System.out.println("Order cancelled.");

        }
    }
}