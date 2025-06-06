import java.util.Scanner;

public class Methods {

    private static void displayChoices(String[] choices) {
        for (int i = 0; i < choices.length; i++) {
            System.out.println((i + 1) + ": " + choices[i]);
       }
    }

   public static int promptUserForInt(String prompt) {
       Scanner scanner = new Scanner(System.in);
           System.out.println(prompt);
           return Integer.parseInt(scanner.nextLine());

   }
   private static String promptUserForString(String prompt) {
       Scanner scanner = new Scanner(System.in);
       System.out.println(prompt);
       return scanner.nextLine();
   }

   public static void main(String[] args) {
       String[] colors = {"Red", "Green", "Blue"};
           displayChoices(colors);

           int colorChoice = promptUserForInt("Choose a color by number:");
           String name = promptUserForString("What is your name?");

           System.out.println("Hi " + name +  " you choose: " + colors[colorChoice -1]);
   }
}