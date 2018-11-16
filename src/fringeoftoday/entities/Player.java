package fringeoftoday.entities;

public class Player extends ActiveEntity {

    private int money;
    private int meleeDamage;
    private int rangedDamage;
    private int movementSpeed;
    private int health;

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getMeleeDamage() {
        return meleeDamage;
    }

    public void setMeleeDamage(int damage) {
        this.meleeDamage = damage;
    }

    public int getRangedDamage() {
        return rangedDamage;
    }

    public void setRangedDamage(int damage) {
        this.rangedDamage = damage;
    }

    public int getMoveSpeed() {
        return movementSpeed;
    }

    public void setMoveSpeed(int speed) {
        this.movementSpeed = speed;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
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
