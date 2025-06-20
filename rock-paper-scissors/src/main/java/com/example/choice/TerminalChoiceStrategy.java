package com.example.choice;

import com.example.model.Choice;

import java.util.Scanner;

public class TerminalChoiceStrategy implements ChoiceStrategy {
    private final Scanner scanner;

    public TerminalChoiceStrategy(Scanner scanner) {
        this.scanner = scanner;
    }
    @Override
    public Choice getChoice() {
        do {
            System.out.print("Enter a choice (R)ock, (P)aper, or (S)cissors: ");
            String input = scanner.nextLine().toLowerCase();

            if (input.equals("r")) {
                return Choice.ROCK;
            } else if (input.equals("p")) {
                return Choice.PAPER;
            } else if (input.equals("s")) {
                return Choice.SCISSORS;
            } else {
                System.out.println("That is not a valid choice!");
            }
        } while(true);


    }
}
