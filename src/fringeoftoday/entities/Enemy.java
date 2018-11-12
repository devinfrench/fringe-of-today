package fringeoftoday.entities;

public abstract class Enemy extends ActiveEntity {

    private float dmgMult;
    private int fireRate;
    protected double velocity;

    public float getDmgMult() {
        return dmgMult;
    }

    public void setDmgMult(float dmgMult) {
        this.dmgMult = dmgMult;
    }

    public int getFireRate() {
        return fireRate;
    }

    public void setFireRate(int fireRate) {
        this.fireRate = fireRate;
    }
    
    public double getVelocity() {
    	return velocity;
    }
    
    public void setVelocity(double velocity) {
    	this.velocity = velocity;
    }
    
    public abstract void move(Entity target);

}
