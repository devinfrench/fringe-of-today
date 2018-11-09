package fringeoftoday.entities;

public abstract class ActiveEntity extends Entity {

    private int health;

    public ActiveEntity(int posX, int posY) {
        super(posX, posY);
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public abstract Projectile[] attack();

}
