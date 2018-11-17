package fringeoftoday.entities;

import acm.graphics.GObject;

public class Projectile extends Entity {

    private int damage;
    private double angle;
    private double velocity;
    private boolean isPlayer;

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
     * Determines whether or not the projectile originated from the player.
     * @return true if player projectile, false otherwise.
     */
    public boolean isPlayer() {
        return isPlayer;
    }

    /**
     * Sets the source of the projectile.
     * @param player true if the player shot the projectile.
     */
    public void setPlayer(boolean player) {
        isPlayer = player;
    }

    public void move() {
        GObject gObj = getGObject();
        if (gObj != null) {
            gObj.move(velocity * Math.sin(angle), velocity * Math.cos(angle));
        }
    }
}
