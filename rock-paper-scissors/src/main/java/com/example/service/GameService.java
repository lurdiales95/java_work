package com.example.service;

import com.example.model.*;

public class GameService {
    Player player1;
    Player player2;

    public GameService(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    public GameResult playRound() {
        Choice p1Choice = player1.getChoice();
        Choice p2Choice = player2.getChoice();

        System.out.printf("Player 1 picks %s, Player 2 picks %s%n", p1Choice, p2Choice);

        if (p1Choice == p2Choice) {
            return GameResult.DRAW;
        } else if ((p1Choice == Choice.ROCK && p2Choice == Choice.SCISSORS) ||
                (p1Choice == Choice.PAPER && p2Choice == Choice.ROCK) ||
                (p1Choice == Choice.SCISSORS && p2Choice == Choice.PAPER)) {
            return GameResult.PLAYER1WINS;
        } else {
            return GameResult.PLAYER2WINS;
        }
    }
}
