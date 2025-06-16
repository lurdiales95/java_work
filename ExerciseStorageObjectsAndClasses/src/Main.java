import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        LockerManager manager = new LockerManager();
        Scanner scanner = new Scanner(System.in);
        String choice;

        System.out.println("Welcome to the Storage Locker Manager!");

        do {
            System.out.println("\n1. Add Locker");
            System.out.println("2. Remove Locker");
            System.out.println("3. Store Item");
            System.out.println("4. Retrieve Item");
            System.out.println("5. Display All Lockers");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            choice = scanner.nextLine();

            switch (choice) {
                case "1": {
                    System.out.print("Enter locker ID: ");
                    String addId = scanner.nextLine().trim();
                    if (addId.isEmpty()) {
                        System.out.println("Locker ID cannot be empty.");
                    } else {
                        System.out.println(manager.addLocker(addId));
                    }
                    break;
                }

                case "2": {
                    System.out.print("Enter locker ID: ");
                    String removeId = scanner.nextLine().trim();
                    System.out.println(manager.removeLocker(removeId));
                    break;
                }

                case "3": {
                    System.out.print("Enter locker ID: ");
                    String storeId = scanner.nextLine().trim();
                    if (storeId.isEmpty()) {
                        System.out.println("Locker ID cannot be empty.");
                        break;
                    }
                    System.out.print("Enter item to store: ");
                    String item = scanner.nextLine().trim();
                    if (item.isEmpty()) {
                        System.out.println("Item cannot be empty.");
                        break;
                    }
                    System.out.println(manager.storeItemInLocker(storeId, item));
                    break;
                }

                case "4": {
                    System.out.print("Enter locker ID: ");
                    String retrieveId = scanner.nextLine().trim();
                    System.out.println(manager.retrieveItem(retrieveId));
                    break;
                }

                case "5": {
                    for (Locker locker : manager.getAllLockers()) {
                        System.out.println(locker);
                    }
                    break;
                }

                case "6": {
                    System.out.println("Exiting program.");
                    break;
                }

                default:
                    System.out.println("Invalid option. Please enter 1-6.");
            }

        } while (!choice.equals("6"));

        scanner.close();
    }
}




