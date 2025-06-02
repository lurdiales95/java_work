import javax.xml.transform.Source;

public class Main {

    public static void main(String[] args) {

        //Strings for Basic String Operations
        String firstName = "Harry";
        String lastName = "Potter";
        String fullName = firstName + " " + lastName; // " " provides space in between words.
        int length = fullName.length();
        char firstChar = fullName.charAt(0);    //works on identying the characters in the world carArt() identifies
        // character in that specific index spot
        //Print for Basic String Ops (Name)
        System.out.println("Full Name: " + fullName);
        System.out.println("Length: " + length);

        System.out.println("First Character: " + firstChar);
        System.out.println("The location of the letter r: " + fullName.indexOf('r'));

        //Strings for Extracting Parts of a String
        String sentence = "Learning java is fun!";
        String word1 = sentence.substring(0, 8);
        String word2 = sentence.substring(9, 13);
        String word3 = sentence.substring(17); // takes all remaining characters starting at 17

        //Print Extracting Parts of a Strings
        System.out.println("\nFirst word: " + word1);
        System.out.println("Second word: " + word2);
        System.out.println("Last word: " + word3);

        //Splitting strings: fruits
        String csvData = "apple,banana,grape,orange";
        String[] fruits = csvData.split(","); // Will split commas into an array
        for (int i = 0; i < fruits.length; i++) {
            System.out.println("Fruit " + (i + 1) + ": " + fruits[i]);

        }

        String fox = "\nThe quick brown fox.";
        System.out.println(fox.replace("quick", "slow").replace(' ', '_'));

        String mNull = null;

        if (mNull == null) {
            System.out.println("\nThe string is null, cannot compute length");
        } else {
            System.out.println("Length: " + mNull.length());
        }
            String emptyString = "";
        System.out.println("Empty string length: " + emptyString.length());
    }
}