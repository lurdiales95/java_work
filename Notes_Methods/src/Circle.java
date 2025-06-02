public class Circle {


    public static double pi = 3.14;

    public double radius;

    public Circle(double radius) {
        this.radius = radius;

    }

    public double getArea() {
        return this.radius * this.radius * Circle.pi;
    }
}





