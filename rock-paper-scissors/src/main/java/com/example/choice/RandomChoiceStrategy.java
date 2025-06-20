package com.example.choice;
import com.example.model.Choice;

import java.util.Random;

public class RandomChoiceStrategy implements ChoiceStrategy {
    private Random rng;

    public RandomChoiceStrategy() {
        rng = new Random();
    }

    @Override
    public Choice getChoice() {
        switch(rng.nextInt(3)) {
            case 0:
                return Choice.ROCK;
            case 1:
                return Choice.PAPER;
            default:
                return Choice.SCISSORS;
        }
    }
}
