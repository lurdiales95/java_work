import java.util.Scanner;

public class TerminalUtils {
    private Scanner io;

    // Constructors do NOT require parameter
    public TerminalUtils() {
        io = new Scanner(System.in);

    }

    public void print(String message) {
        System.out.println(message);

    }
    public int getGuess() {
        while(true) {
            try {
                System.out.print("Enter number: ");
                int val = Integer.parseInt(io.nextLine());

                if (val < 0) {
                    System.out.println("The number must be positive!");
                } else {
                    return val;
                }
            } catch (Exception e) {
                System.out.println("That's not a number!");
            }
        }
    }
}
