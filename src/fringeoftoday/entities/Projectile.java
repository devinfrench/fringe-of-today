package fringeoftoday.entities;

public class Projectile extends Entity {

    private int damage;

    public Projectile(int posX, int posY, String sprite) {
        super(posX, posY, sprite);
    }

    public Projectile(int posX, int posY, String sprite, int damage) {
        super(posX, posY, sprite);
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

}
