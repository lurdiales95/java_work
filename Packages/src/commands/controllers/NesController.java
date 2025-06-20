package controllers;

import commands.Command;
import commands.NullCommand;

public class NesController {
    private Command aButton;
    private Command bButton;

    // no constructor injection
    public NesController() {
        aButton = new NullCommand();
        bButton = new NullCommand();
    }

    // setter injection
    public void setaButton(Command command) {
        this.aButton = command;
    }

    public void setbButton(Command command) {
        this.bButton = command;
    }

    public void pressA() {
        aButton.execute();
    }

    public void pressB() {
        bButton.execute();
    }

    public void helpA() {
        System.out.println(aButton.getDescription());
    }

    public void helpB() {
        System.out.println(bButton.getDescription());
    }
}