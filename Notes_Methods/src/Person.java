public class Person {

    private String name;
    private int age;



    public Person(String name, int age) {
        this.name = name;
        this.age = age;


    }

    public void printInfo() {
        System.out.printf("%s is %d years old.", this.name, this.age);



    }
}
