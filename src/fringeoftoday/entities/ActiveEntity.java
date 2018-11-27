package fringeoftoday.entities;

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
    
    public abstract Projectile[] attack(double targetX, double targetY);

}
