import java.util.Scanner;

public class FirstVowel {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a word: ");
        String word = scanner.nextLine().toLowerCase();

        boolean found = false;

        for (int i = 0; i < word.length(); i++) {
            char letter = word.charAt(i);

            if (letter == 'a' || letter == 'e' || letter == 'i' ||
                    letter == 'o' || letter == 'u') {
                System.out.println("First vowel found at position: " + i);
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("No vowels found in the word.");
        }

        scanner.close();
    }
}
