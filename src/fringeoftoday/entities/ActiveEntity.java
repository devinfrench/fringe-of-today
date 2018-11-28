package fringeoftoday.entities;

import acm.graphics.GObject;
import fringeoftoday.floor.FloorManager;

public abstract class ActiveEntity extends Entity {

    private int health;
    private String spriteSet;
    
    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
    
    public String getSpriteSet() {
    	return spriteSet;
    }
    
    public void setSpriteSet(String spriteSet) {
    	this.spriteSet = spriteSet;
    }

    @Override
    public double getCenterX() {
        double center = super.getCenterX();
        if (center != 0) {
            return center + (FloorManager.SPACE_SIZE * 0.25);
        }
        return 0;
    }

    @Override
    public double getCenterY() {
        double center = super.getCenterY();
        if (center != 0) {
            return center + (FloorManager.SPACE_SIZE * 0.25);
        }
        return 0;
    }
    
    public abstract Projectile[] attack(double targetX, double targetY);

}
