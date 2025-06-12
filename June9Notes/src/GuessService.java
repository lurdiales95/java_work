import java.util.Random;

public class GuessService {
    //available anywhere in the class
    private int secretNumber;
    private Random rng;
    private int maxNumber;
    private  int totalGuesses;
    private ArrayList<int>;

    // constructors are for setup
    public GuessService(int maxNumber) {
        this.maxNumber = maxNumber;
        rng = new Random();

    }

    private void generateSecretNumber() {
        secretNumber = rng.nextInt(maxNumber) + 1;

    }

    public void StartGame() {
        generateSecretNumber();
        totalGuesses = 0;

    }

    public GuessResult processGuess(int guess) {
        if (alreadyGuessed(guess)) {
            return Guess.Result.DUPLICATE;
        }

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
        for (int 1 = 0; i <= previousGuesses.size(), i++ {
            if (previousGuesses.get(i) == guess) {


        })

    }
}
