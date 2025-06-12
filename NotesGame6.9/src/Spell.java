import java.util.Random;

public class Spell {
    private String name;
    private int minDamage;
    private int maxDamage;

    private Random rng;

    public Spell(String name, int minDamage, int maxDamage) {
        this.name = name;
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;

        rng = new Random();
    }

    public String getName() {
        return this.name;
    }
    public boolean cast(Living target) {
        return target.takeDamage(rng.nextInt(maxDamage) + minDamage);
    }
}
