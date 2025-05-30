public class Main {
    enum OrderStatus {

        ORDERED,
        WAREHOUSE,
        SHIPPED,
        DELIVERED,
        RETURNED
    }

    enum TrafficLight {
        RED,
        YELLOW,
        GREEN
    }

    enum GameStatus {
        TIE,
        LOSS,
        WIN
    }


    public static void main(String[] args) {
        //order status, 1 ordered, 2 warehouse, 3 shipped , 4 delivered, 5 returned

        OrderStatus status = OrderStatus.WAREHOUSE;
        // do stuff for warehouse processing
        System.out.println(OrderStatus.DELIVERED);
        System.out.println(OrderStatus.DELIVERED.ordinal());

        // if (status == OrderStatus.WAREHOUSE) {
            //do stuff for warehouse processing
            // }

        GameStatus[] statuses = GameStatus.values();
        GameStatus tie = statuses[0];

        System.out.println(tie);

        TrafficLight[] indexes = TrafficLight.values();

        TrafficLight light = indexes[2];

        System.out.println(light);


    }
}
