import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Original cities
        String[] cities = {"Austin", "Chicago", "Los Angeles", "New York", "San Francisco"};

        // Print original cities
        System.out.println("Original Cities:");
        System.out.println(cities[0]);
        System.out.println(cities[1]);
        System.out.println(cities[2]);
        System.out.println(cities[3]);
        System.out.println(cities[4]);

        // index 2 new city
        cities[2] = "Fort Worth";

        // array and loop
        System.out.println("\nUpdated Cities:");
        for (int i = 0; i < cities.length; i++) {
            System.out.println(cities[i]);
        }

        // length of array
        System.out.println("\nTotal number of cities: " + cities.length);

        // ----- Part 2: Iterating Over Arrays -----

        // Print each city using a loop
        System.out.println("\nAll Cities:");
        for (int i = 0; i < cities.length; i++) {
            System.out.println("- " + cities[i]);
        }

        // cities listed in reverse
        System.out.println("\nCities in Reverse:");
        for (int i = cities.length - 1; i >= 0; i--) {
            System.out.println("- " + cities[i]);
        }

        // Ask the user to search for a city
        System.out.println("\nEnter a city name to search:");
        String searchCity = scanner.nextLine();

        boolean found = false;
        for (int i = 0; i < cities.length; i++) {
            if (cities[i].equalsIgnoreCase(searchCity)) {
                found = true;
                break;
            }
        }

        if (found) {
            System.out.println("City found!");
        } else {
            System.out.println("City not found!");
        }

        scanner.close();
    }
}
