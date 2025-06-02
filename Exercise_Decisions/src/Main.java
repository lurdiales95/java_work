import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nWelcome to the Secret Tunnel! Secret Tunnel!");
        System.out.println("\nDo you wish to enter the tunnel? (yes/no)?");

        String choice = scanner.nextLine();

        if (choice.equalsIgnoreCase("yes")) {
            System.out.println("You've decided to enter! Welcome :)");
            System.out.println("\nThere are two paths ahead: One on the left and one on the right.");
            System.out.println("Which path do you wish to take? (left/right)");

            String path = scanner.nextLine();

            if (path.equalsIgnoreCase("left")) {
                System.out.println("\nAs you walk down the left tunnel, a shadow begins to rise!");
                System.out.println("Do you want to fight or flee?");
                String action = scanner.nextLine();
                //left: monster showdown
                if (action.equalsIgnoreCase("fight")) {
                    System.out.println("You bravely fight the creature and win! You've earned 10 silver coins!");
                } else if (action.equalsIgnoreCase("flee")) {
                    System.out.println("You ran away! Please try harder next time.");
                } else {
                    System.out.println("You froze in fear and the creature disappears. Yay!");
                }
                //right: treasure room
            } else if (path.equalsIgnoreCase("right")) {
                System.out.println("As you walk down the right tunnel you discover...");
                System.out.println("A treasure room!!!");
                System.out.println("\nThere are three items that captivate you:");
                System.out.println("a gem, a key, and a book.");
                System.out.println("Which item will you choose?");
                String artifact = scanner.nextLine();

                switch (artifact.toLowerCase()){
                    case "gem":
                        System.out.println("You'll make unlimited bank! But you'll live forever alone. Sad.");
                        break;
                    case  "key":
                        System.out.println("You've unlocked a hidden exit! Freedom! Freedom! Freedom!");
                        break;
                    case "book":
                        System.out.println("You'll be eternally wise, but will suffer from memory loss. Can't catch a break.");
                        break;
                    default:
                        System.out.println("You hesitated. Everything vanished. Poof!");
                }
                //standstill
            } else {
                System.out.println("That's not a direction you can take. You must suffer from analysis paralysis.");
            }
        //conclusions
        } else if (choice.equalsIgnoreCase("no")) {
            System.out.println("You decided to play it safe. Y'all come back now ya hear!");
        } else {
            System.out.println("That's not a valid answer. Try again!");
        }
        System.out.println("\nWell that concludes our adventure. Y'all come back now ya hear!"); //out-tro

        scanner.close();
    }
}