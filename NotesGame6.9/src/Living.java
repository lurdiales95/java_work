public class Living {
    protected int maxHP;
    protected int currentHP;
    protected String name;

    public Living(int maxHP, String name) {
        this.maxHP = maxHP;
        this.currentHP = currentHP;
        this.name = name;

    }
    public String getHPBar() {
        return  String.format("%d / %d", currentHP, maxHP);
    }
    public String getName() {
        return this.name;

    }

    public int getMaxHP() {
        return this.maxHP;
    }

    public boolean takeDamage(int amount) {
        if (currentHP - amount < 0) {
            currentHP = 0;
            // dead
            return false;
        }
        return true;

    }
    public boolean attack(Living target, int amount) {
        return target.takeDamage(amount);
    }

    public boolean addHP(int amount) {
        if (amount < 0) {
            return false;
        }

        if (currentHP + amount > maxHP) {
            currentHP = maxHP;
        } else {
            currentHP += amount;
        }

        return true;
    }

}

