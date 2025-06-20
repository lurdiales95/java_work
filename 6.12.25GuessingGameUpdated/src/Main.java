import java.util.Scanner;

public class Main {

    private static Scanner scanner;

    public static void main(String[] args) {

        String choice = displayMenu();
        Scanner scanner = new Scanner(System.in);
        // dependency injection : constructor injection
        GameService gameService = create(choice);

        gameService.startGame();
        GuessResult guessResult = GuessResult.HIGHER;


        do {
            System.out.println("Enter a guess: ");
            int guess = Integer.parseInt(scanner.nextLine());
            guessResult = gameService.guess(guess);

            System.out.println(guessResult);
        } while (guessResult != GuessResult.WIN);
    }

    private static GameService create(String choice) {
        // substitute the behavior by injecting it into the GameService
        return switch (choice) {
            case "1" -> new GameService(10, newRandomNumberGenerator());
            case "2" -> new GameService(10, new PlayerTwoPicksNumber(scanner));
            default -> new GameService(10, new AlwaysPickOne());
        };

    }

    private static NumberGenerator newRandomNumberGenerator() {
        return null;
    }

    private static String displayMenu() {
        System.out.println("Welcome to the Guessing Game!\nChoose your mode:");
        System.out.println("1. Random Number");
        System.out.println("2. Human picks number");
        System.out.println("3. Test mode(1)");
        System.out.println("\nEnter choice: ");

        String s = scanner.nextLine();
        String s1 = s;
        return s1;
    }

}

