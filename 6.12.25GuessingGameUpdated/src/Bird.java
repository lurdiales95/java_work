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
