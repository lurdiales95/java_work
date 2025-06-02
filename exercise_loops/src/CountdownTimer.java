import java.util.Scanner;

public class CountdownTimer {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter a starting number for the countdown: ");
        int number = scanner.nextInt();

        while (number > 0) {
            System.out.println(number);
            number--;
        }

        System.out.println("Blast off!");
        scanner.close();
    }
}
