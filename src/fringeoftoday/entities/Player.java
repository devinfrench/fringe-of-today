package fringeoftoday.entities;

public class Player extends ActiveEntity {

    private int money;
    private int meleeUpgrade;
    private int projectileUpgrade;
    private int movementUpgrade;
    private int healthUpgrade;

    public Player(int posX, int posY, String sprite) {
        super(posX, posY, sprite);
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getMeleeUpgrade() {
        return meleeUpgrade;
    }

    public void setMeleeUpgrade(int meleeUpgrade) {
        this.meleeUpgrade = meleeUpgrade;
    }

    public int getProjectileUpgrade() {
        return projectileUpgrade;
    }

    public void setProjectileUpgrade(int projectileUpgrade) {
        this.projectileUpgrade = projectileUpgrade;
    }

    public int getMovementUpgrade() {
        return movementUpgrade;
    }

    public void setMovementUpgrade(int movementUpgrade) {
        this.movementUpgrade = movementUpgrade;
    }

    public int getHealthUpgrade() {
        return healthUpgrade;
    }

    public void setHealthUpgrade(int healthUpgrade) {
        this.healthUpgrade = healthUpgrade;
    }

    @Override
    public Projectile[] attack() {
        // TODO implement
        return new Projectile[0];
    }

    public void melee() {
        // TODO implement
    }

}
