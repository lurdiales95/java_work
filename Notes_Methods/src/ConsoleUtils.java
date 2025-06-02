import java.util.Scanner;

public class ConsoleUtils {
    public static String getNonEmptyString(String prompt) {
        Scanner io = new Scanner(System.in);

        String input;

        do {
            System.out.print(prompt);
            input = io.nextLine();
        } while(input.length() == 0);

            return input;

        }
    }
