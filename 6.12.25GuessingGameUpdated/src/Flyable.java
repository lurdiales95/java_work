public interface Flyable {
    void fly();

    default int getMaxAltitude() {
        return 500;
    }

}

public interface Swimmable {
    void swim();

    default int getMaxDepth() {
        return 100;
    }
}


//interfaces are public. They cannot be private.
//you can inherit from interfaces, but it's not very common