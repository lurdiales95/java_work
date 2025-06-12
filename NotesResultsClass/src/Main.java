import java.util.Scanner;

public class Main {
    private static Scanner io = new Scanner(System.in);

    public static void main(String[] args) {
        Result<Integer> result = getNumberFromUser();
        Result<String> result2 = getNonEmptyString();

        System.out.println(result);
        System.out.println(result2);
    }

    public static Result<Integer> getNumberFromUser() {
        try {
            System.out.print("Enter a number: ");
            int data = Integer.parseInt(io.nextLine());
            return new Result<Integer>(true, "", data);
        } catch(NumberFormatException ex) {
            return new Result<Integer>(false, "That was not a number", 0);
        }
    }

    public static Result<String> getNonEmptyString() {
        System.out.print("Enter a word: ");
        String data = io.nextLine();

        if (data.isEmpty()) {
            return new Result<String>(false, "No characters were entered", data);
        } else {
            return new Result<String>(true, "", data);
        }
    }
}