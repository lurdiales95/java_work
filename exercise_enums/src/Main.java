import java.util.Arrays;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    enum CoffeeSize {

        SMALL,
        MEDIUM,
        LARGE
    }

    enum SeatSection {
        GENERAL,
        PREMIUM,
        VIP
    }

    enum TrafficLight {
        RED,
        YELLOW,
        GREEN

    }

    enum Weekday {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY

    }

    enum AlertLevel {
        LOW,
        MODERATE,
        HIGH,
        SEVERE

    }


    public static void main(String[] args) {

        CoffeeSize size = CoffeeSize.MEDIUM;
        System.out.println("\nSelected coffee size: " + CoffeeSize.MEDIUM);

        SeatSection seat = SeatSection.GENERAL;
        System.out.println("\nGENERAL is assignedvalue: " + SeatSection.GENERAL.ordinal());
        System.out.println("PREMIUM is assignedvalue: " + SeatSection.PREMIUM.ordinal());
        System.out.println("VIP is assignedvalue: " + SeatSection.VIP.ordinal());

        TrafficLight[] light = TrafficLight.values();

        TrafficLight RED = light[0];

        System.out.println("\nTraffic Light signal: " + light[1]);

        Weekday workday = Weekday.WEDNESDAY;
        System.out.println("\nWorkday selected: " + workday);
        if (workday == Weekday.SATURDAY || workday == Weekday.SUNDAY) {
            System.out.println("Is it a weekend? true");
        } else {
            System.out.println("Is it a weekend? false");
        }

        AlertLevel currentAlert = AlertLevel.HIGH;
        System.out.println("\nCurrent alert level: " + currentAlert);

        switch (currentAlert) {
            case LOW:
                System.out.println("No immediate danger.");
                break;
            case MODERATE:
                System.out.println("Stay alert and aware.");
                break;
            case HIGH:
                System.out.println("Take precautions and stay informed.");
                break;
            case SEVERE:
                System.out.println("Immediate action required!");
                break;
        }
    }

}