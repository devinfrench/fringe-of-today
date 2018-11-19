package fringeoftoday.entities;

public abstract class ActiveEntity extends Entity {

    private int health;

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public abstract Projectile[] attack(double targetX, double targetY);

}
