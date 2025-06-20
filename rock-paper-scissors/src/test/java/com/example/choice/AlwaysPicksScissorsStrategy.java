package com.example.choice;

import com.example.model.Choice;

public class AlwaysPicksScissorsStrategy implements ChoiceStrategy {

    @Override
    public Choice getChoice() {
        return Choice.SCISSORS;
    }
}
