import java.util.Scanner;

public class IO {
    private static final Scanner input = new Scanner(System.in);

    public static int askForNumberOfLockersAvailable() {
        System.out.print("Enter the number of lockers available at this venue: ");
        while (!input.hasNextInt()) {
            System.out.print("Please enter a valid number: ");
            input.next();
        }
        int numberOfLockers = input.nextInt();
        input.nextLine(); // consume newline
        return numberOfLockers;
    }

    // Display menu and get user's choice
    public static int getMenuChoices() {
        System.out.println("\nWelcome to the locker rental system!");
        System.out.println("Please select an option:");
        System.out.println("1. Rent a locker");
        System.out.println("2. Access a locker");
        System.out.println("3. Release a locker");
        System.out.println("4. Exit");

        int choice = -1;
        while (choice < 1 || choice > 4) {
            System.out.print("Enter your choice (1-4): ");
            if (input.hasNextInt()) {
                choice = input.nextInt();
            } else {
                input.next(); // discard invalid input
                System.out.println("Invalid input. Please enter a number between 1 and 4.");
            }
        }
        return choice;
    }

    // Ask for locker number
    public static int getLockerNumber() {
        System.out.print("Enter locker number: ");
        while (!input.hasNextInt()) {
            System.out.print("Please enter a valid locker number: ");
            input.next();
        }
        return input.nextInt();
    }

    // Ask for pin code
    public static String getPinCode() {
        System.out.print("Enter PIN code: ");
        return input.next();
    }

    // Display result message
    public static void displayResult(Result result) {
        System.out.println(result.getMessage());

        if (result.getSuccess()) {
            if (result.getLockerNumber() != null) {
                System.out.println("Locker number: " + result.getLockerNumber());
            }
            if (result.getPinCode() != null) {
                System.out.println("Your PIN code is: " + result.getPinCode());
            }
        }
    }
}











    /* public static void displayResult(Result r) {
        String [] selctions = {"Rent a locker", "Access a Locker", "Release a Locker"};


        System.out.println(r.getMessage());

        //pinCode
        System.out.println("Your PIN code is: " + pinCode);


        System.out.println();"Welcome to the locker rental system! Would you  like to rent or open a locker?"
        // I need to provide the options 1. Rent a locker 2. Open a locker */

//This should be printing things.
//This  class needs to have the scanner in it.