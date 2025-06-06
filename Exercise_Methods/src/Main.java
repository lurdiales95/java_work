//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static int sum(int a, int b) {
        return a + b;
    }

    public static double convertToFahrenheit(double celsius) {
        return (celsius * 9 / 5) + 32;
    }

    public static boolean isEven(int number) {
        if (number % 2 == 0) {
            return true;
        } else {
            return false;

        }
    }

    public static void printMultipleTimes(String text, int times) {
        for (int i = 0; i < times; i++) {
            System.out.println(text);
        }
    }

    // Find the maximum of three numbers
    public static int findMax(int a, int b, int c) {
        return Math.max(a, Math.max(b, c));
    }

    public static int factorial(int n) {
        if (n <= 1) {
            return 1;
        } else {
            return n * factorial(n - 1);
        }
    }

    public static void greet(String name) {
        System.out.println("Hello, " + name + "!");
    }

    public static void greet(String name, int age) {
        System.out.println("Hello, " + name + "! You are " + age + " years old.");

    }
    public static int countVowels(String text) {
        int count = 0;
        text = text.toLowerCase();

        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u') {
                count++;
            }
        }
        return count;
    }
    public static double calculator(int num1, int num2, char operator) {
        switch (operator) {
            case '+':
                return num1 + num2;
            case '-':
                return num1 - num2;
            case '*':
                return num1 * num2;
            case '/':
                if (num2 == 0) {
                    System.out.println("Error: Division by zero.");
                    return Double.NaN;
                } else {
                    return (double) num1 / num2;
                }
            default:
                System.out.println("Error: invalid operator.");
                return Double.NaN;
        }
    }

    public static void main(String[] args) {
        String printWelcomeMessage = "Welcome to the Java Methods Exercise!";
        System.out.println(printWelcomeMessage);

        int sum1 = sum(8, 11);
        int sum2 = sum(20, 50);
        int sum3 = sum(1, 2);
        int sum4 = sum(6, 100);

        System.out.println("\nThe sum of 8 and 11 is: " + sum1);
        System.out.println("The sum of 20 and 50 is: " + sum2);
        System.out.println("The sum of 1 and 2 is: " + sum3);
        System.out.println("The sum of 6 and 100 is: " + sum4);

        System.out.println("\nConverting Celsius to Fahrenheit:");

        double f1 = convertToFahrenheit(0);
        double f2 = convertToFahrenheit(100);
        double f3 = convertToFahrenheit(37);

        System.out.println("0°C = " + f1 + "°F");
        System.out.println("100°C = " + f2 + "°F");
        System.out.println("37°C = " + f3 + "°F");

        System.out.println("\nEven or Odd?");
        System.out.println("Is 6 an even number? " + isEven(6));
        System.out.println("Is 15 an even number? " + isEven(15));
        System.out.println("Is 28 an even number? " + isEven(28));
        System.out.println("Is 45 an even number? " + isEven(45));

        System.out.println("\nCalling printMultipleTimes...");

        printMultipleTimes("I love learning!", 8);

        int max1 = findMax(30, 60, 90);
        int max2 = findMax(98, 70, 50);
        System.out.println("\nWhich one is the maximum number of the three?");
        System.out.println("The maximum of 30, 60, 90 is: " + max1);
        System.out.println("The maximum of 90, 70, 50 is: " + max2);

        System.out.println("\nFactorial using recursion:");

        long fact3 = factorial(3);
        long fact8 = factorial(8);
        long fact15 = factorial(15);

        System.out.print("3! = " + fact3);
        System.out.print("\n8! = " + fact8);
        System.out.print("\n15! = " + fact15);

        System.out.println();
        System.out.println("\nMethod Overloading");
        greet("Rebecca");
        greet("Chance", 15);

        System.out.println("\nCounting Vowels in Strings");
        int vow1 = countVowels("Java is cool:");
        int vow2 = countVowels("It's Friday!");

        System.out.println("The number of vowels in 'Java is cool' are : " + vow1);
        System.out.println("The number of vowels in 'It's Friday!' are: " + vow2);

        System.out.println("\nMini Calculator:");

        System.out.println("8 + 4 = " + calculator(8, 4, '+'));
        System.out.println("15 - 6 = " + calculator(15, 6, '-'));
        System.out.println("7 * 5 = " + calculator(7, 5, '*'));
        System.out.println("20 / 4 = " + calculator(20, 4, '/'));
        System.out.println("10 / 0 = " + calculator(10, 0, '/')); // test division by zero
        System.out.println("5 ? 2 = " + calculator(5, 2, '?')); // test invalid operator
    }
}

