//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    enum OrderStatus {
        PENDING,
        PROCESSED,
        SHIPPED,
        DELIVERED
    }
    enum ShippingStatus {
        STANDARD,
        TWO_DAY,
        OVERNIGHT

    }

    public static void main(String[] args) {
        OrderStatus status = OrderStatus.SHIPPED;
        System.out.println("\nOrder status: " + status);

        ShippingStatus shipping = ShippingStatus.OVERNIGHT;
        System.out.println("Shipping status: " + shipping);


        }
    }
