//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        System.out.println("Counting from 1 to 100:");

        for (int i = 1; i <= 100; i++) {
            System.out.println(i);
        }

        System.out.println("\nCounting only even numbers from 1 to 100;");

        for (int i = 2; i <= 100; i += 2) {
            System.out.println(i);
        }
    }
}