import java.util.Scanner;

public class NumericArrays {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        //test scores
        int[] scores = {85, 90, 64, 33, 80};

        int sum = 0;
        int max = scores[0];
        int min = scores[1];

        System.out.println("Test Scores:");
        for (int i = 0; i < scores.length; i++) {
            System.out.println("_ " + scores[i]);
            sum += scores[i];

            if (scores[i] > max) {
                max = scores[i];

            }

            if (scores[i] < min) {
                min = scores[i];
            }
        }

     double average = (double) sum / scores.length;

        System.out.println("\nTotal score: " + sum);
        System.out.println("Highest score:" +max);
        System.out.println("Lowest score:" + min);
        System.out.printf("Average score: %.2f\n", average);


        scanner.close();




        {
        }
    }
}
