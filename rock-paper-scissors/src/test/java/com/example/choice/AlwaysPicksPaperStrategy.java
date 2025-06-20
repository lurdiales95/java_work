package com.example.choice;

import com.example.model.Choice;

public class AlwaysPicksPaperStrategy implements ChoiceStrategy {

    @Override
    public Choice getChoice() {
        return Choice.PAPER;
    }
}
