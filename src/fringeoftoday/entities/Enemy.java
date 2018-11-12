package fringeoftoday.entities;

public abstract class Enemy extends ActiveEntity {

    private float dmgMult;
    private int fireRate;
    private double velocity;

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
    
    public void move(Entity target) {
		if (target == null) {
			return;
		}
		double deltaX = target.getX() - getX();
		double deltaY = target.getY() - getY();
		double angle = Math.atan2(deltaY, deltaX);
		getGObject().move(velocity * Math.cos(angle), velocity * Math.sin(angle));
    }

}
