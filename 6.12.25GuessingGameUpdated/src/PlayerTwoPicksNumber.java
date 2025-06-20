import java.util.Scanner;

public class PlayerTwoPicksNumber implements NumberGenerator {

    private Scanner scanner;

    public PlayerTwoPicksNumber(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public int generateNumber(int max) {
        do {
            System.out.print("Enter the secret number: ");
            int number = Integer.parseInt(scanner.nextLine());

            if (number > 0 && number < max) {
                return number;
            } else {
                System.out.println("That number is not in range!");
            }
        } while (true);
    }
}
