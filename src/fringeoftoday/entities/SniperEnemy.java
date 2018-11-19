package fringeoftoday.entities;

public class SniperEnemy extends Enemy {

    @Override
    public Projectile[] attack(double targetX, double targetY) {
        // TODO implement
        return new Projectile[0];
    }

	@Override
	public void move(Entity target) {
		return; // sniper enemy cannot move
	}
    
    
}
