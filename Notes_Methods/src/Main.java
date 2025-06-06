import org.w3c.dom.css.CSSImportRule;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

//        String userName = ConsoleUtils.getNonEmptyString("Enter your user name: ");
//        String food = ConsoleUtils.getNonEmptyString("Enter your fav food: ");
//        System.out.printf("You entered %s.%nYour favorite food is %s.%n", userName, food);

        Circle c1 = new Circle(10);
        Circle c2 = new Circle(5);
        System.out.println(c1.getArea());
        System.out.println(c2.getArea());


    }

}

//Methods can return things, have a name, code block, etc.
//Simples method is the accessor, static/not, type, name(parameters)
// type - void means no return
//you can remove static if needed
//method name in this case main always starts with lower case
// (string[] args) is our parameters
// static means there is only ever create one. non-static can make multiple reservation objects

//if you're going to use multiple classes, you only need to use static if you need to store information, if you don't don't use it.