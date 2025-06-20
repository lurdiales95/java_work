package commands.racing;
import commands.Command;

public class AccelerateCommand implements Command {
    @Override
    public void execute() {
        System.out.println("Vroom vroom");
    }

    @Override
    public String getDescription() {
        return "Hold the button down to accelerate.";
    }
}