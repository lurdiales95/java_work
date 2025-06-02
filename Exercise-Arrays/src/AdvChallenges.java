import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class AdvChallenges {
    public static void main(String[] args) {
        Random rand = new Random();

        int[] randomNumbers = new int[10];
        int countThrees = 0;

        System.out.println("Random numbers (1-5):");
        for (int i = 0; i < randomNumbers.length; i++) {
            randomNumbers[i] = rand.nextInt(5) + 1;
            System.out.print(randomNumbers[i] + "");
            if (randomNumbers[i] == 3) {
                countThrees++;

            }
        }

        System.out.println("\nNumber of times 3 appears: " + countThrees);

        // Part 11: Shift Elements in Array
        int[] original = {1, 2, 3, 4, 5};
        int first = original[0];

        for (int i = 0; i < original.length - 1; i++) {
            original[i] = original[i + 1];
        }

       original[original.length - 1] = first;

        System.out.println("\nArray after shifting left: ");
        System.out.println(Arrays.toString(original));

        String [] student = {"Minerva", "Ginny", "Ron", "Harry", "Luna"};
        boolean duplicatesFound = false;

        for (int i = 0; i < student.length; i++) {
            for (int j = i + 1; j < student.length; j++) {
                if (student[i].equals(student[j])) {
                    duplicatesFound = true;
                    break;
                }
            }
            if (duplicatesFound) break;


}
        System.out.println("\nStudent Names: " +Arrays.toString(student));
        System.out.println(duplicatesFound ? "Duplicates found!" : "No duplicates.");



}
}
