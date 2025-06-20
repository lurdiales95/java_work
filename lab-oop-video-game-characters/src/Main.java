
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Character> characters = new ArrayList<>();

        characters.add(new Mage("John", 100, 25, "Psystike"));
        characters.add(new Warrior("Rey", 150, 30, "Sword"));
        characters.add(new Archer("Sofia", 120, 20, "Strike"));

        //Loop
        for (Character character : characters) {
            character.attack();
        }
    }
}

/* Create a Character superclass with:
○ Fields: name, health, attackPower
○ Constructor to initialize fields
○ Methods: attack() (prints a generic attack message)
2. Create three subclasses (Warrior, Mage, Archer) that extend Character:
○ Each subclass should override attack() with a unique attack message
○ Each subclass should have a unique field (e.g., weaponType for Warrior,
spell for Mage)
3. In GameApp.java, create a list of different Character objects, loop through
them, and call their attack() method. */