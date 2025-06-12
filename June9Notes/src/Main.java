class Main {
    public static void main(String[] args) {
        TerminalUtils utils = new TerminalUtils();

        do {
            int max = utils.getMaxNumber();
            GuessService game = new GuessService(max);
            game.startGame();
            boolean KeepPlaying = true;

            while(KeepPlaying) {
               TerminalUtils.print(String.format("Pick a number between 1 and %d", max));
                int guess = TerminalUtils.getGuess();

                GuessResult = game.processGuess(guess);

                switch (result) {
                    case LOWER:
                        utils.print("Lower!");
                        break;
                    case HIGHER:
                        utils.print("Higher!");
                    case DUPLICATE:
                        utils.print("You already guessed that number!");
                        break;
                    default:
                        utils.print(String.format("You got it! It took you %d tries."), game.getTotalGuesses()));
                        break;
                }
            }
            if (!utils.getPlayAgain()) {
                break;
            }
        } while (true);
    }
}