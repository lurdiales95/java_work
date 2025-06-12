import java.util.ArrayList;
import java.util.Random;

public class GuessService {
    // available anywhere in the class
    private int secretNumber;
    private Random rng;
    private int maxNumber;
    private int totalGuesses;
    private ArrayList<Integer> previousGuesses;

    // constructors are for setup
    public GuessService(int maxNumber) {
        this.maxNumber = maxNumber;
        rng = new Random();
    }

    private void generateSecretNumber() {
        secretNumber = rng.nextInt(maxNumber) + 1;
    }

    public void startGame() {
        generateSecretNumber();
        totalGuesses = 0;
        previousGuesses = new ArrayList<>();
    }

    public GuessResult processGuess(int guess) {
        if (alreadyGuessed(guess)) {
            return GuessResult.DUPLICATE;
        }

        previousGuesses.add(guess);
        totalGuesses++;
        if (guess == secretNumber) {
            return GuessResult.CORRECT;
        } else if (guess < secretNumber) {
            return GuessResult.HIGHER;
        } else {
            return GuessResult.LOWER;
        }
    }

    public int getTotalGuesses() {
        return totalGuesses;
    }

    private boolean alreadyGuessed(int guess) {
        for(int i = 0; i < previousGuesses.size(); i++) {
            if (previousGuesses.get(i) == guess) {
                return true;
            }
        }

        return false;
    }
}