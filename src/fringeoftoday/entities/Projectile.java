package fringeoftoday.entities;

import acm.graphics.GObject;

public class Projectile extends Entity {

    private int damage;
    private double angle;

    public Projectile(GObject gObj) {
        super(gObj);
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
