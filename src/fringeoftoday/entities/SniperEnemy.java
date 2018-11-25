package fringeoftoday.entities;

import fringeoftoday.core.CollisionManager;

public class SniperEnemy extends Enemy {

    @Override
    public Projectile[] attack(double targetX, double targetY) {
        // TODO implement
        return new Projectile[0];
    }

	@Override
	public void move(CollisionManager collisionManager, Entity target) {
		return; // sniper enemy cannot move
	}
    
    
}
