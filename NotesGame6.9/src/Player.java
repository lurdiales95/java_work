import java.util.ArrayList;

public class Player extends Living {
    private ArrayList<Spell> spells;

    public Player(int maxHP, String name) {
        super(maxHP, name);
        spells = new ArrayList<>();
    }

    /*public boolean castFireball(Living target) {
        return attack(target, 25); */

    public boolean castSpell(String name, Living target) {
        for (int i = 0; i < spells.size(); i++) {
            if (name.equalsIgnoreCase(spells.get(i).getName())) {
               return spells.get(i).cast(target);
            }
        }

        System.out.println("You try to cast a spell, but it fizzles.");
        return true;
    }

    public void learnSpell(String name, int minDamage, int maxDamage) {
        spells.add(new Spell(name, minDamage, maxDamage));
    }
}
