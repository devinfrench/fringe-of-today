package fringeoftoday.entities;

import java.awt.Image;

import acm.graphics.GImage;
import acm.graphics.GObject;
import fringeoftoday.graphics.Sprites;

public class StandardEnemy extends Enemy {

	private static final String PROJECTILE_IMAGE_PATH = "projectiles/bullet_red.png";
	private static final int ATTACK_DELAY = 200;
	private static final int BASE_DAMAGE = 2;
	private static final int FIRST_ATTACK_DELAY = 2000;
	private Image projectileImage;
	private long lastAttackTime;

	public StandardEnemy() {
		projectileImage = Sprites.loadSprite(PROJECTILE_IMAGE_PATH);
		Image sprite = Sprites.loadSprite("enemy_sprite_temp.png");
		setGObject(new GImage(sprite));
		lastAttackTime = System.currentTimeMillis() + FIRST_ATTACK_DELAY;
	}

	@Override
	public Projectile[] attack(double targetX, double targetY) {
		if ((System.currentTimeMillis() - lastAttackTime) < (ATTACK_DELAY * getFireRate())) {
			return new Projectile[0];
		}
		GObject gObj = getGObject();
		double x = gObj.getX() + gObj.getWidth() / 2;
		double y = gObj.getY() + getGObject().getHeight() / 2;
		GImage sprite = new GImage(projectileImage, x, y);
		Projectile p = new Projectile(sprite);
		p.setAngle(Math.atan2(targetX - p.getX(), targetY - p.getY()));
		p.setVelocity(2.5);
		p.setPlayer(false);
		p.setDamage((int) (BASE_DAMAGE * getDmgMult()));
		lastAttackTime = System.currentTimeMillis();
		return new Projectile[] { p };
	}

}
