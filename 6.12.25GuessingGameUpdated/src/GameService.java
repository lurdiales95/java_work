import java.util.Random;

// service / receiver ALWAYS refers ONLY to the interface
public class GameService {
    private int secretNumber;
    private final int maxRange;
    private final NumberGenerator generator;

    public GameService(int maxRange, NumberGenerator generator) {
        this.maxRange = maxRange;
        this.generator = generator;
    }

    public void startGame() {
        secretNumber = generator.generateNumber(maxRange);

    }

    public GuessResult guess(int number) {
        if (number == secretNumber) {
            return GuessResult.WIN;
        } else if (number < secretNumber) {
            return GuessResult.HIGHER;
        } else {
            return GuessResult.LOWER;
        }
    }
}


//When you modify the