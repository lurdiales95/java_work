package commands;

public class NullCommand implements Command {
    @Override
    public void execute() {
        return;
    }

    @Override
    public String getDescription() {
        return "";
    }
}