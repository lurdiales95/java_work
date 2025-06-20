import java.util.Scanner;

public class UserInputHandler implements InputHandler{
   private Scanner scanner = new Scanner(System.in);

    @Override
    public int getPositiveInteger() {
        return 0;
    }

    @Override
    public int getMaxNumber() {
        return 0;
    }

    @Override
    public boolean getPlayAgain() {
        return false;
    }
}
