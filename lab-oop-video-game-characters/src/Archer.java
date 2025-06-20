public class Archer extends Character {
    private String arrowStrike;

    public Archer(String name, int health, int attackPower, String arrowStrike) {
        super(name, health, attackPower);
        this.arrowStrike = arrowStrike;

    }

    @Override
    public void attack() {
        System.out.println(name + " shoots an " + arrowStrike + " arrow with precision!");
    }

    @Override
    public boolean takeDamage(int amount) {
        return super.takeDamage(amount);  //warrior-specific logic
    }
}
