import java.util.Scanner;

public class TerminalUtils {
    public static  Scanner io = new Scanner(System.in);
}
public static void print(String message) {
    System.out.println(message);
}

public class TerminalUtils {
    private Scanner io;

    public TerminalUtils() {
        io = new Scanner(System.in);

    }
    public void print(String message) {
        System.out.println(message);
    }

    public int getGuess() {
        while(true) {
            trhy {
                System.out.println("Enter the max to guess: ");
                int val = Integer.parseInt((io.nextLine()));

                if (val <= 0) {
                    System.out.println();
                } else {

                return val;
                }

            } catch(Exception e) {
                System.out.println("That is not a number!");
            }
        }

    }
    public boolean getPlayAgain() {
        System.out.println("Do you want to play again? (y/n)");
        String response = io.next();

        return  response.equalsIgnorecase("y");

    }

}