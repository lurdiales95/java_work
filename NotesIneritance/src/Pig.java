// Inheritance - anything in the parent will be copied into the child.
public class Pig extends Animal {

    // super = parent
    public Pig(String name, int age) {
        super(name, age);
    }


    @Override
    public void makeSound() {
        super.makeSound();
        System.out.printf("The %s says Oink!%n", this.name);

    }

}

//override the methods you want to change