public class Warrior extends Character {
    private String weaponType;

    public Warrior(String name, int health, int attackPower, String weaponType) {
        super(name, health, attackPower);
        this.weaponType = weaponType;
    }

    @Override
    public void attack() {
        System.out.println(name + " swings their " + weaponType + " with great force!");
    }

    @Override
    public boolean takeDamage(int amount) {
        return super.takeDamage(amount);  // warrior-specific logic
    }
}
