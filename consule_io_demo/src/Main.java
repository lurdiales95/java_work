import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
    // Type Name Initialize/Assign
//        Scanner io = new Scanner (System.in);

 //       System.out.print("Enter your name: ");
   //     String name = io.nextLine();
 //       System.out.print("Enter your favorite movie: ");
 //       String movie = io.nextLine();

 //       System.out.print("Enter your age: ");
        // (int())
 //       int age = Integer.parseInt(io.nextLine());


 //       System.out.println("Hello, " + name);
// System.out.println("Your favorite movie is: " + movie);
//  System.out.print("Your age is: " +age);

        //System.out.printf("%, as you are d% years old. Your favorite movie is %.", name, age, movie);
        //%d integers (whole numbers)
        //%f floating point (double, float)
        //%s string %c character %b booleans
        //%x hexadecimal
        //%e scientific
        //%n
        //System.out.print("Enter the price: ");
        //double price = Double.parseDouble(io.nextLine());

        //System.out.printf("Roses are red.%n Violets are Blue.%n");
    System.out.printf("|%10s|%n", "hello");
    System.out.printf("|%-10s|%n", "hello");

    int upc = 1283;
        System.out.printf("Barcode: %010d%n", upc);

    }
}
