public class Reservation {
    private String name;
    private int size;

    public Reservation(String name, int size) {
        this.name = name;
        this.size = size;
    }

    public void printInfo() {
        System.out.printf("%s party of %d.%n", this.name, this.size);
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public String getName() {
        return this.name;

    }
}
