public class Dog {
    //If you want to keep them around declare it at the class level. Parameters are transient, destroyed when the function is done executing
    //data member, attribute, and field mean the same thing for developers = class level variable
    private String name;
    private  int age;
    private String breed;

    public Dog(String name, int age, String breed){

        //If you want to keep them around declare it at the class level. Parameters are transient, destroyed when the function is done executing
        this.name = name;
        this.age = age;
        this.breed = breed;

    }
//void means your not returning any data, you'll forget soon
    public void Bark() {
        System.out.println("Woof woof!");
    }
    {

    // getter function
        public String getName() {
            return this.name;
    }
        //setter function
    }   public void setName(String) {
        this.name = newName;

    public void setAge(int newAge) {
        if (newAge <0) {


        } else {
            this.age = newAge;
        }
public void setBread(String newBreed) {
            // check to see if the breed is valid
                this.bread = newBreed
            }
    }

    public void printInfo()-
        //

}