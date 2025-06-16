public class Person {
    private final String firstName;
    private final String lastName;
    private final String primarySpecialty;

    public Person(String firstName, String lastName, String primarySpecialty) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.primarySpecialty = primarySpecialty;

    }

    public String getFirstName() {
        return firstName;

    }
    public String getLastName() {
        return lastName;

    }
    public String getPrimarySpecialty() {
        return primarySpecialty;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + ", " + primarySpecialty;

    }
}
