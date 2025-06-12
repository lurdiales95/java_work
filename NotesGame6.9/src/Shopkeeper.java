public class Shopkeeper extends Living {
    public Shopkeeper(int maxHP, String name) {
        super(maxHP, name);
    }

    @Override
    public boolean takeDamage(int amount) {
        return true;
    }

    @Override
    public boolean attack(Living target, int amount) {
        return target.takeDamage(target.getMaxHP());
    }
}
/*This code makes shopkeeper unkillable and if you
dare to try and take them in combat, it'll just kill you.
 */