package com.example.choice;

import com.example.model.Choice;

public class AlwaysPicksRockStrategy implements ChoiceStrategy {

    @Override
    public Choice getChoice() {
        return Choice.ROCK;
    }
}
