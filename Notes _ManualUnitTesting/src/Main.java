//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to the shopping cart app!");
        String[] addresses = { "123 Main St", "456 Main St", "789 Main St" };
        String[] sizes = { "small", "medium", "large" };
        String [] validTaxExemptResponses = {"y", "n"};
        String[] validShippingMethods = {"standard", "overnight", "twoday"};

        java.util.Scanner console = new java.util.Scanner(System.in);
        boolean confirm = false;
        String taxExempt = "";
        String shipping = "";
        String promoCode = "";
        while (!confirm) {
            String address = null;
            String size = null;
            // Prompt for tax exempt
            taxExempt = promptUserForValidatedString("Are you tax-exempt? (y/n)", validTaxExemptResponses);
            // Prompt for shipping address
            while (address == null) {
                try {
                    displayChoices(addresses);
                    int addressIndex = promptUserForInt("Shipping address?") -
                            1;
                    address = addresses[addressIndex];
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Please enter a valid menu option");
                }
            }
            // Prompt for shipping
            shipping = promptUserForValidatedString("Shipping? (standard/overnight/twoday)", validShippingMethods);
            // Prompt for order quantity
            int orderQuantity = promptUserForInt("Order quantity?");
            while (size == null) {
                // Prompt for Size
                try {
                    displayChoices(sizes);
                    int sizeIndex = promptUserForInt("Size?") - 1;
                    size = sizes[sizeIndex];
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Please enter a valid menu option");
                }
            }
            // Prompt for promo code
            promoCode = promptUserForString("Promo code for free shipping?");
            // Print details
            System.out.println("\nDetails:");
            System.out.println("Tax-exempt: " + taxExempt);
            System.out.println("Address: " + address);
            System.out.println("Shipping: " + shipping);
            System.out.println("Order Quantity: " + orderQuantity);
            System.out.println("Size: " + size);
            System.out.println("Promo code: " + promoCode);
            System.out.println("Confirm Order y/n");
            confirm = "y".equals(console.nextLine());
        }
        System.out.println("Bye");
    }
    // Method for display choices
    private static void displayChoices(String[] choices) {
        for (int i = 0; i < choices.length; i++) {
            System.out.println(i + 1 + ": " + choices[i]);
        }
    }
    // Method for prompt user for string
    private static String promptUserForString(String prompt) {
        java.util.Scanner console = new java.util.Scanner(System.in);
        System.out.println(prompt);
        return console.nextLine();
    }

    // NEW: Method for prompt user for validated string
    private static String promptUserForValidatedString(String prompt, String[] validResponses) {
        boolean valid = false;
        String result = "";
        while (!valid) {
            result = promptUserForString(prompt);
            for (int i = 0; i < validResponses.length; i++) {
                if (result.equals(validResponses[i])) {
                    valid = true;
                    break;
                }
            }
            if (!valid) {
                System.out.println("Please input a valid response.");
            }
        }
        return result;
    }
    // Method for prompt user for int
    private static int promptUserForInt(String prompt) {
        java.util.Scanner console = new java.util.Scanner(System.in);
        boolean isValid = false;
        int result = -1;
        while (!isValid) {
            System.out.println(prompt);
            try {
                result = Integer.parseInt(console.nextLine());
                isValid = true;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number");
            }
        }
        return result;
    }
}
