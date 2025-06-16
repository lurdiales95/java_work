import java.util.Scanner;

public class TerminalUtils {
    private static Scanner io = new Scanner(System.in);

    public static void print(String message) {
        System.out.println(message);
    }

    public static int getGuess() {
        while (true) {
            try {
                System.out.print("Enter number: ");
                int val = Integer.parseInt(io.nextLine());

                if (val < 1) {
                    System.out.println("The number must be at least 1!");

                } else {
                    return val;
                }
            } catch (Exception e) {
                System.out.println("That's not a number!");

            }
        }
    }

    public static int getMaxNumber() {
        while (true) {
            try {
                System.out.print("Enter the max to guess: ");
                int val = Integer.parseInt(io.nextLine());

                if (val <= 0) {
                    System.out.println("Number must be positive!");

                } else {
                    return val;

                }
            } catch (Exception e) {
                System.out.println("That's not a number!");

            }
        }
    }
    public static boolean getPlayAgain() {
        System.out.print("Do you want to play again? (y/n): ");
        String response = io.nextLine();
        return response.equalsIgnoreCase("y");

    }
}
