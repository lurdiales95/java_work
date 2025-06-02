import java.util.Scanner;
import java.util.Random;

public class Guess {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        int secretNumber = random.nextInt(50) + 1;
        int guess;

        System.out.println("Guess the secret number between 1 and 50.");

        do {
            System.out.println("Enter your guess: ");
            guess = scanner.nextInt();

            if (guess < secretNumber) {
                System.out.println("Too low! Try again.");
            } else if (guess > secretNumber) {
                System.out.println("Too high! Try again.");
            }
        } while (guess != secretNumber);

        System.out.println("Congratulations! You guessed the number!");

        scanner.close();

    }
}
