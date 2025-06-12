//IF YOU USE FINAL ON A CLASS LEVEL, YOU CAN EXTEND THE INHERITANCE ANYMORE.
public class Animal {
    protected String name;
    protected int age;

    public Animal(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void makeSound() {
        System.out.printf("The %s makes a sound.%n", name);
        //If you make "public final void" - you can't override or extend this statement.
    }

}



// protected vs. private. Only the animal class and classes that extend animal can see it.