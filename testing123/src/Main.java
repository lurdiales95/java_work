import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner io = new Scanner(System.in);

//
//        System.out.println("Enter the day number: ");
//        int dayNum = Integer.parseInt(io.nextLine());

//        if (dayNum == 1) {
//            System.out.println("Monday");
//        } else if (dayNum == 2) {
//            System.out.println("Tuesday");
//        } else {
//            System.out.println("That is not a valid day.");
//        }
//
//        switch (dayNum) {
//            // switch cases are formated with break to end it
//            case 1:
//                System.out.println("Monday");
//                break;
//            case 2:
//                System.out.println("Tuesday");
//                break;
//            case 3:
//                System.out.println("Wednesday");
//                break;
//            default:
//                System.out.println("That is not a valid day");
//                break;
//
//        }
//
//        switch (dayNum) {
//            // fall-through case. This is more effective. The previous one is basically using if/else.
//            case 1:
//            case 2:
//            case 3:
//            case 4:
//            case 5:
//                System.out.println("It's a weekday");
//                break;
//            case 6:
//            case 7:
//                System.out.println("It's the weekend");
//                break;
//            default:
//                System.out.println("That is not a valid day.");
//                break;
//        }
//        // it's a preference on whether a switch is more effective than this bottom example
//        if (dayNum == 1 || dayNum == 2 | dayNum == 3 | dayNum == 4 || dayNum ==5)
//        {
//            System.out.println("It's a weekday");
//        } else if (dayNum == 6 || dayNum == 7) {
//            System.out.println("It's the weekend");
//
//        } else {
//            System.out.println("That is not a valid day.");
//        }
//        // New. You cannot find this anywhere else. This is completely new synthax.
//        String message = switch (dayNum) {
//            case  1, 2, 3, 4, 5 -> "it's a week day";
//            case 6, 7 -> "It's the weekend";
//            default -> "That's not a valid day.";
//        };
//
//
//
//
//            }
//        }

//        int alarm = 1;
//
//        // in if () you can write any expression so long as it's true or false. if() is already a boolean, you don't have to repeat it.
//        // no limit on else if statements, just like Python.
//        // (&&) this is and "||" is or
//        if (alarm == 1) {
//
//        } else if (alarm == 2) {
//        } else {
//
//        int x = 10
//        int y = 20;
//
//        if (x  > 5) {
//            if (y < 30) {
//        }
//    }
//        //        boolean isActive = true;
//        // (!) is a "NOT" exp: if (!isActive)
//
//    public static boolean getValue () { **** This is the same as using if statements *****
//        return true;

        // This is for 3 things: variable (loop control) , expression (when to stop), increment expression (how to change the loop variable)
//        System.out.println("Enter a word: ");
//        String word = io.nextLine();

//        // i-- to make the word go backwards
//        for (int i = word.length() - 1; i >= 0; i--) {
//            if (i == 2) {
//                break;
//
//            switch (word.charAt(i)) {
//                case 'a':
//                case 'e':
//                case 'i':
//                case 'o':
//                case 'u':
//                    continue;
//
//            }
//            System.out.println(word.charAt(i));
        // Arrays
//        int[] nums = new int[3];
//
//        nums[0] = 42;
//        nums[1] = 13;
//        nums[2] = 7;
//
//        int[] nums2 = {1, 2, 3, 4, 5};
//
//        for (int i = 0; i < nums.length; i++) {
//            System.out.println(nums2[i]);
//        }
//        //standard formula for looping arrays
//        for (int i = nums2.length - 1; i >= 0; i--) {
//            System.out.println(nums2[i]);
//
//        }
//        String[] colors;


//        String[] colors = new String[2];
//        int totalColors = 0;
//
//        while (true) {
//            System.out.print("Ente+" +
//                    "-2r a color (Q to quit): ");
//            String color = io.nextLine();
//
//            // they're done entering data, quit
//            if (color.equals("Q")) {
//                break;
//            } else {
//                totalColors++;
//            }
//
//            // check if we have more colors than the bounds of the array
//            if (totalColors > colors.length) {
//                //create a new temp array that's 2x bigger
//                String[] temp = new String[colors.length * 2];
//
//                for (int i = 0; i < colors.length; i++) {
//                    temp[i] = colors[i];
//                }
//
//                // replace colors with temp
//                colors = temp;
//                colors[totalColors - 1] = color;
//            } else { // we have enough space to add the color
//                colors[totalColors - 1] = color;
//            }
//        }
//
//        for (int i = 0; i < colors.length; i++) {
//            if (colors[i] != null) {
//                System.out.println(colors[i]);
//            }
//        }
//    }
//}
        Reservation[] waitList = new Reservation[10];
        int index = 0;

        while (true) {
            System.out.println("Enter a name, (Q to quit): ");
            String name = io.nextLine();

            if (name.equals("Q")) {
                break;
            }

            System.out.print("Enter the part size: ");
            int size = Integer.parseInt(io.nextLine());

            Reservation r = new Reservation(name, size);
            waitList[index] = r;
            index++;

            if (index > waitList.length - 1) {
                System.out.println("The list is full!");
                break;
            }
        }

        // print the list
        for(int i = 0; i < waitList.length; i++) {
            if (waitList[i] != null) {
                waitList[i].printInfo();
            }
        }
    }
}



// Reasons to create a new class:
    /*
    1. Workflow(main)
    2. Tasks(Waitlist, add, remove, callNext,etc.)
    3. Data bucket (Reservation)
     */







