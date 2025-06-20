package commands.racing;

import commands.Command;

public class BreakCommand implements Command {
    @Override
    public void execute() {
        System.out.println("The tires screech as the vehicle slows down.");
    }

    @Override
    public String getDescription() {
        return "Hold this down to reduce speed.";
    }
}