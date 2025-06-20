package com.example.factory;

import com.example.choice.*;
import com.example.model.*;
import java.util.Scanner;

public class PlayerFactory {
    private Scanner scanner;

    public PlayerFactory(Scanner scanner) {
        this.scanner = scanner;
    }

    public Player create(PlayerType playerType) {
        ChoiceStrategy strategy;

        if (playerType == PlayerType.COMPUTER) {
            strategy = new RandomChoiceStrategy();
        } else {
            strategy = new TerminalChoiceStrategy(scanner);
        }

        return new Player(strategy);
    }

}
