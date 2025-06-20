package com.example.model;

import com.example.choice.ChoiceStrategy;

public class Player {
    ChoiceStrategy choiceStrategy;

    public Player(ChoiceStrategy choiceStrategy) {
        this.choiceStrategy = choiceStrategy;
    }

    public Choice getChoice() {
        return choiceStrategy.getChoice();
    }
}
