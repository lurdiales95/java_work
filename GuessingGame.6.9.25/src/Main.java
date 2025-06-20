public class Main {
    public static void main(String[] args) {


    do {
            int max = TerminalUtils.getMaxNumber();
            GuessService game = new GuessService(max);

            game.startGame();
            boolean keepPlaying = true;

            while(keepPlaying) {
                TerminalUtils.print(String.format("Pick a number between 1 and %d", max));
                int guess = TerminalUtils.getGuess();

                GuessResult result = game.processGuess(guess);

                switch(result) {
                    case LOWER:
                        TerminalUtils.print("Lower!");
                        break;

                    case HIGHER:
                        TerminalUtils.print("Higher!");
                        break;

                    case DUPLICATE:
                        TerminalUtils.print("You already guessed that number!");
                        break;

                    default:
                        TerminalUtils.print(String.format("You got it! It took you %d tries.", game.getTotalGuesses()));
                        keepPlaying = false;
                        break;
                }
            }

            if (!TerminalUtils.getPlayAgain()) {
                break;
            }
        } while(true);
    }
}