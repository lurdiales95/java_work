package commands.boxing;

import commands.Command;

public class HookCommand implements Command {
    @Override
    public void execute() {
        System.out.println("You throw a left hook.");
    }

    @Override
    public String getDescription() {
        return "Press to throw a hook across your body.";
    }
}