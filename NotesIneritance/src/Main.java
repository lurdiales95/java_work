import java.util.ArrayList;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        // polymorphism
        // poly = many
        // morphic = type

        ArrayList<Animal> zoo = new ArrayList<>();


        zoo.add(new Pig("Pig", 1));
        zoo.add(new Dog("Corgi", "Dog", 2));
        zoo.add(new Dog("Aussie", "Dog", 4));

        Pig a1 = new Pig("Pig", 1);
        Dog a2 = new Dog("Corgi", "Dog", 2);
        Dog a3 = new Dog("Aussie", "Dog", 4);

        /* a1.makeSound();
        a2.makeSound();
        a3.makeSound(); */

        for (int i = 0; i < zoo.size(); i++) {
            zoo.get(i).makeSound();
        }
    }
}

    //Every object has at least one type. Pig is a class, but it's also an animal

