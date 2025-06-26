package com.example;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Scanner;

import static java.math.RoundingMode.FLOOR;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BigDecimal val = BigDecimal.ZERO;
        BigDecimal val2 = BigDecimal.ZERO;

//        try {
//            // preferred way: pass string to constructor
//            System.out.print("Enter a value: ");
//            val = new BigDecimal(scanner.nextLine());
//
//            System.out.print("Enter a second value: ");
//            val2 = new BigDecimal(scanner.nextLine());
//        } catch (NumberFormatException e) {
//            System.out.println("That isn't a valid number.");
//        }
//
//        System.out.println(val.add(val2));
//        System.out.println(val.subtract(val2));
//        System.out.println(val.multiply(val2));
//        BigDecimal val3 = new BigDecimal(5);
//
//        System.out.println(val3.setScale(0, RoundingMode.DOWN));
//
//        System.out.println(val3.setScale(0, RoundingMode.UP));
//
//        System.out.println(val3.setScale(0, RoundingMode.CEILING));
//
//        System.out.println(val3.setScale(0, RoundingMode.HALF_UP));
//
//        System.out.println(val3.setScale(0, RoundingMode.HALF_EVEN));
//
//      System.out.println(val3.setScale(0, RoundingMode.HALF_DOWN));
//
//        // decimal a = 5;

        // from decimal import Decimal
        // x = Decimal("123.45")
        // x + y;
        System.out.println(val.setScale(4, RoundingMode.HALF_UP).stripTrailingZeros());
        System.out.println(val2.setScale(4, RoundingMode.HALF_UP));

    }

//    public static BigDecimal safeParse(String input) {
//        try {
//            return new BigDecimal(input);
//        } catch (NumberFormatException e) {
//            return BigDecimal.ZERO;
//        } catch (ArithmeticException e) {
//            System.out.println("Divide by zero error");
//        }
//    }
}