package fringeoftoday.entities;

import acm.graphics.GObject;

public class Projectile extends Entity {

    private int damage;
    private double angle;
    private double velocity;
    private Entity source;

    public Projectile(GObject gObj) {
        super(gObj);
        this.velocity = 5.0;
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

    public double getVelocity() {
        return velocity;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    /**
     * Gets the source entity that created the projectile.
     * @return the source entity
     */
    public Entity getSource() {
        return source;
    }

    /**
     * Sets the source of the projectile.
     * @param source the entity that created the projectile.
     */
    public void setSource(Entity source) {
        this.source = source;
    }

    public void move() {
        GObject gObj = getGObject();
        if (gObj != null) {
            gObj.move(velocity * Math.sin(angle), velocity * Math.cos(angle));
        }
    }
}
