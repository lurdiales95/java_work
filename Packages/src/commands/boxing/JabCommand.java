package commands.boxing;

import commands.Command;

public class JabCommand implements Command {

    @Override
    public void execute() {
        System.out.println("You throw a quick jab.");
    }

    @Override
    public String getDescription() {
        return "Tap the button to throw a quick jab.";
    }
}