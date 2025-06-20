public class Character {
    protected String name;
    protected int health;
    protected int attackPower;

    public Character(String name, int health, int attackPower) {
        this.name = name;
        this.health = health;
        this.attackPower = attackPower;
    }

    public String getName() {
        return this.name;

    }

    public int getHealth() {
        return health;

    }

    public boolean takeDamage(int amount) {
        health -= amount;
        if (health <= 0) {
            health = 0;
            return false; // dead
        }
        return true; // still alive
    }

    public void attack() {
        System.out.println(name + " performs a basic attack!");
    }
}



