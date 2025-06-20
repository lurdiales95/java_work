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

public class Bird() implements Flyable, Swimmable {

    @Override
    public void fly() {

    }

    @Override
    public int getMaxAltitude() {
        return Flyable.super.getMaxAltitude();
    }

    @Override
    public void swim() {

    }

    @Override
    public int getMaxDepth() {
        return Swimmable.super.getMaxDepth();
    }
}


//interfaces are public. They cannot be private.
//you can inherit from interfaces, but it's not very common