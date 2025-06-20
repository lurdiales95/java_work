package com.example;

import com.example.factory.PlayerFactory;
import com.example.model.GameResult;
import com.example.model.Player;
import com.example.model.PlayerType;
import com.example.service.GameService;

import java.util.Scanner;

public class App {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        GameService game = createGame();
        boolean playAgain = true;

        while(playAgain) {
            GameResult result = game.playRound();
            printResult(result);
            playAgain = promptPlayAgain();
        }

    }

    private static void printResult(GameResult result) {
        switch(result) {
            case DRAW:
                System.out.println("It was a draw!");
                break;
            case PLAYER1WINS:
                System.out.println("Player 1 wins!");
                break;
            default:
                System.out.println("Player 2 wins!");
                break;
        }

    }

    private static GameService createGame() {
        PlayerFactory factory = new PlayerFactory(scanner);

        Player p1 = factory.create(PlayerType.HUMAN);
        Player p2 = factory.create(PlayerType.COMPUTER);

        return new GameService(p1, p2);
    }

    private static boolean promptPlayAgain() {
        System.out.print("Would you like to play again? (y/n): ");
        String input = scanner.nextLine();

        return input.equalsIgnoreCase("y");
    }
}
