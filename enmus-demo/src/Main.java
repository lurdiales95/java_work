//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    enum OrderStatus {
        ORDERED,
        WAREHOUSE,
        SHIPPED,
        DELIVERED,
        RETURNED,
    }

    enum TrafficLight {
        RED,
        YELLOW,
        GREEN,
    }

    enum  GamesStatus {
        WIN,
        LOSS,
        TIE
    }

    public static void main(String[] args) {

        TrafficLight[] indexes = TrafficLight.values();

        TrafficLight light = indexes[2];

        System.out.println(light);
    }
}