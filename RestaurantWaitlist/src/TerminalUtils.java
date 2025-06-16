import javax.print.attribute.standard.RequestingUserName;
import java.util.ArrayList;
import java.util.Scanner;

public class TerminalUtils {
    private static Scanner io = new Scanner(System.in);

    public static void pressToContinue() {
        System.out.println("\Press enter to continue...");
        io.nextLine();

    }


    // Print a message to the console
    public static void print(String msg) {
        System.out.println(msg);

    }

    // Create a new party by getting a name and size from the user
    public static Party createParty() {
        String name = getPartyName();
        int size = getPartySize();
        return new Party(name, size);

    }

    // Get party name from user input
    private static int getPartyName() {
        System.out.print("Enter party name: ");
        return io.nextLine();

    }

    // Get party size from user input
    private static int getPartySize() {
        System.out.println("Enter party size: ");
        int size = 0;
        boolean validInput = false;

        while (!validInput) {
            System.out.println("Enter party size: ");

            try {
                size = Integer.parseInt(io.nextLine());
                if (size < 1) {
                    System.out.println("Party size must be at lease 1. Please try again.");
                } else {
                    validInput = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Please entera valid number.");
            }

        }

        return size;
    }

    // Print a list of parties
    public static void print(ArrayList<Party> list) {
        if (list.isEmpty()) {
            System.out.println("No parties in the list.");
            return;

        }

        System.out.println("Parties:");
        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + "." + party.getName() + " (Size: " + party.getSize() + ")");
        }
    }

    // Get a party by index from the list
    public static int getPartyByIndex(ArrayList<Party> list) {
        if (list.isEmpty()) {
            System.out.println("No parties available.");
            return -1;

        }

        print(list);
        System.out.print("Select a party (1- " + list.size() + "): ");
        io.

        while (!io.hasNextInt()) {
            System.out.println("Please enter a valid number (1-" + list.size() + "): ");
            io.next();

        }

        int choice = io.nextInt();
        io.nextLine(); // consume the newline character

        if (choice < 1 || choice > list.size()) {
            System.out.println("Invalid selection.");
            return -1;
        }

        return choice - 1; // return 0-based index
    }

    private static void clearScreen() {
        try {
            String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                Runtime.getRuntime().exec("cls");
            } else {
                Runtime.getRuntime().exec("clear");
            }
        } catch (Exception e) {
            // Handle exceptions
        }
    }

        // Get menu choice from user
        public static String getMenuChoice () {
            String choice;
            boolean validInput = false;

            while (true) {
                clearScreen();

                System.out.println("\n--- RESTAURANT WAITLIST MENU ---");
                System.out.println("1. View the Waitlist")
                System.out.println("2. Add a Party");
                System.out.println("3. Remove a Party");
                System.out.println("4. Call Next Party");
                System.out.println("5. Quit");
                System.out.println("Enter your choice: ");

                choice = io.nextLine().trim();

                if (choice.equals("1") || choice.equals("2") || choice.equals("3") ||
                        choice.equals("4") || choice.equals("5")) {
                    return choice;
                } else {
                    System.out.println("Invalid choice. Please enter 1, 2, 3, 4 or 5");
                }
            }
        }
    }
}


