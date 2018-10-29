package fringeoftoday.entities;

public abstract class ActiveEntity extends Entity {

    private int health;

    public ActiveEntity(int posX, int posY, String sprite) {
        super(posX, posY, sprite);
    }

    public ActiveEntity(int posX, int posY, String sprite, int health) {
        super(posX, posY, sprite);
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public abstract Projectile[] attack();

}
