package fringeoftoday.entities;

public class Projectile extends Entity {

    private int damage;
    private double angle;

    public Projectile(int posX, int posY) {
        super(posX, posY);
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

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }
}
