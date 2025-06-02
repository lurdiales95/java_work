public class Main {
    public static void main(String[] args) {
    System.out.println("\nThe Assignment Operator");

    System.out.println("int x = 0");
    int x = 0;
    System.out.println("This is the value of x: " + x);



    System.out.println("\nArithmetic Operators");
    System.out.println("Resetting x to 0...");
    System.out.println("x = 0");
    System.out.println("The value of x is now: " + x);

    System.out.println("\nx = x + 5");
    x = x + 5;
    System.out.println("The value of x is now: " + x);

    System.out.println("\nx = x - 2");
    x = x - 2;
    System.out.println("The value of x is now: " + x);

    System.out.println("\nx = x * 4");
    x = x * 4;
    System.out.println("The value of x is now: " + x);

    System.out.println("\nx = x / 3");
    x = x / 3;
    System.out.println("The value of x is now:  " + x);



    System.out.println("\nArithmetic With Assignment");
    System.out.println("Resetting x to 0...");
    System.out.println("x = 0");
    x = 0;
    System.out.println("The value of x is now: " + x);

    System.out.println("\nx =+ 2");
    x += 2;
    System.out.println("The value of x is now: " + x);

    System.out.println("\nx -= 3");
    x -= 3;
    System.out.println("The value of x is now: " + x);

    System.out.println("\nx *= 12");
    x *= 12;
    System.out.println("The value of x is now: " + x);

    System.out.println("\nx /= 3");
    x /= 3;
    System.out.println("The value of x is now: "+ x);





    System.out.println("\nIncrement and Decrement");
    System.out.println("Resetting x to 0...");
    System.out.println("x = 0");
    x = 0;
    System.out.println("The value of x is now: " + x);

    System.out.println("\nx++");
    x++;
    System.out.println("The value of x is now: " + x);

    System.out.println("x--");
    x--;
    System.out.println("The value of x is now: " + x);


    System.out.println("\nTruncation");
    System.out.println("int thisTruncates = 10 / 3");
    int thisTruncates = 10 / 3;
    System.out.println("thisTruncates is now:");
    System.out.println(thisTruncates);

    System.out.println("\nThe Modulus Operator");
    System.out.println("int wholeNumber = 10/3;");
    int wholeNumber = 10 / 3;
    System.out.println("int remeainder = 10 % 3;");
    int remainder = 10 % 3;
    System.out.println("wholeNumber is now:");
    System.out.println(wholeNumber);
    System.out.println("remainder is now:");
    System.out.println(remainder);

    //boolean outcome allows you to declare a variable to hold the result of comparisons
    System.out.println("\nComparison Operators");
        boolean outcome;
        System.out.println("outcome = 3 < 4");
        outcome = 3 < 4;
        System.out.println("outcome is now: " + outcome);

        System.out.println("outcome = 3 > 4");
        outcome = 3 > 4;
        System.out.println("outcome is now: " + outcome);

         System.out.println("outcome = 3 <= 4");
         outcome = 3 <= 4;
         System.out.println("outcome is now: " + outcome);

         System.out.println("outcome = 4 < 4");
         outcome = 4 < 4;
         System.out.println("outcome is now: " + outcome);

        System.out.println("outcome = 4 <= 4");
        outcome = 4 <= 4;
        System.out.println("outcome is now: " + outcome);

        System.out.println("outcome = 6 <= 4");
        outcome = 6 <= 4;
        System.out.println("outcome is now: " + outcome);

        System.out.println("outcome = 4 > 4");
        outcome = 4 > 4;
        System.out.println("outcome is now: "+ outcome);

        System.out.println("outcome = 4 >= 4");
        outcome = 4 >= 4;
        System.out.println("outcome is now: "+ outcome);

        System.out.println("outcome = 3 == 3");
        outcome = 3 == 3;
        System.out.println("outcome is now: "+ outcome);

        System.out.println("outcome = 3 == 4");
        outcome = 3 == 4;
        System.out.println("outcome is now: "+ outcome);

        System.out.println("\nLogical Operators");
        System.out.println("Exploring the && operator");
        System.out.println("Both sides are true");
        System.out.println("outcome = true && true");
        outcome = true && true;
        System.out.println("outcome is now: " + outcome);

        System.out.println("\nBoth sides are false");
        System.out.println("outcome = false && false");
        outcome = false && false;
        System.out.println("outcome is now: " + outcome);

        System.out.println("\nOne side is false");
        System.out.println("outcome = false && true");
        outcome = false && true;
        System.out.println("outcome is now: " + outcome);

        System.out.println("\nExploring the || operator");
        System.out.println("Both sides are true");
        outcome = true || true;
        System.out.println("outcome is now: " + outcome);

        System.out.println("\nBoth sides are false");
        System.out.println("outcome = false || false");
        outcome = false || false;
        System.out.println("outcome is now: " + outcome);

        System.out.println("\nOne side is false");
        System.out.println("outcome = false || false");
        outcome = 3 < 4 &&  5 <6;
        System.out.println("outcome is now: " + outcome);

        System.out.println("\nExploring the ! operator");
        System.out.println("outcome = !true");
        outcome = !true;
        System.out.println("outcome is now: " + outcome);

        System.out.println("outcome = !false");
        outcome = !false;
        System.out.println("outcome is now: " + outcome);














    }

}