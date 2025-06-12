import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Living> characters = new ArrayList<>();

        characters.add(new Player(100, "Eric"));
        characters.add(new Shopkeeper(1000, "George"));
        characters.add(new Orc(50, "Peon"));
        characters.add(new Orc(150, "General"));
    }
}