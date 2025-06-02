import java.util.Scanner;

public class SimpleATM {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter a word: ");
        double balance = 500.00;
        int choice;

        do {
            System.out.println("\n--- Simple ATM Menu ---");
            System.out.println("1. Withdraw");
            System.out.println("2. Deposit");
            System.out.println("3. Check Balance");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1: // Withdraw
                    System.out.print("Enter amount to withdraw: ");
                    double withdrawAmount = scanner.nextDouble();
                    if (withdrawAmount > balance) {
                        System.out.println("Insufficient funds!");
                    } else {
                        balance -= withdrawAmount;
                        System.out.printf("You withdrew $%.2f\n", withdrawAmount);
                    }
                    break;
                case 2: // Deposit
                    System.out.print("Enter amount to deposit: ");
                    double depositAmount = scanner.nextDouble();
                    balance += depositAmount;
                    System.out.printf("You deposited $%.2f\n", depositAmount);
                    break;
                case 3: // Check Balance
                    System.out.printf("Your current balance is $%.2f\n", balance);
                    break;
                case 4: // Exit
                    System.out.println("Thank you for using Simple ATM. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please choose 1-4.");
            }

        } while (choice != 4);

        scanner.close();
    }
}