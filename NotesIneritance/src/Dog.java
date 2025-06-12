public class Dog extends Animal {
    private String breed;

    public Dog (String breed, String name, int age) {
        super(name, age);
        this.breed = breed;
    }

    public String getBreed() {
        return this.breed;

    }

    @Override
    public void makeSound() {
        System.out.printf("The %s says woof!%n", this.breed);



    }





}