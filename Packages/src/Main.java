import commands.boxing.HookCommand;
import commands.boxing.JabCommand;
import commands.racing.AccelerateCommand;
import commands.racing.BreakCommand;
import controllers.NesController;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        NesController controller = new NesController();

        // infinite loop on purpose because lazy
        while (true) {
            System.out.println("Let's play a game!");
            System.out.println("1. Racing");
            System.out.println("2. Boxing");
            System.out.print("\nEnter choice: ");

            String choice = scanner.nextLine();

            configureController(controller, choice);
            printConfiguration(controller);
        }

    }

    private static void configureController(NesController controller, String choice) {
        if (choice.equals("1")) {
            controller.setaButton(new AccelerateCommand());
            controller.setbButton(new BreakCommand());
        } else {
            controller.setaButton(new HookCommand());
            controller.setbButton(new JabCommand());
        }
    }

    private static void printConfiguration(NesController controller) {
        controller.helpA();
        controller.helpB();
    }
}