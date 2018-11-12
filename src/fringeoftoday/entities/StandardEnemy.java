package fringeoftoday.entities;

public class StandardEnemy extends Enemy {

	@Override
	public Projectile[] attack() {
		// TODO implement
		return new Projectile[0];
	}

	@Override
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
