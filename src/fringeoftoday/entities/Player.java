package fringeoftoday.entities;

import acm.graphics.GImage;
import acm.graphics.GObject;
import fringeoftoday.graphics.Sprites;

import java.awt.*;

public class Player extends ActiveEntity {

	private static final String PROJECTILE_IMAGE_PATH = "projectiles/bullet_blue.png";
	private static final int RANGED_ATTACK_DELAY = 500;

	private int money;
	private int fireSpeed;
	private int rangedDamage;
	private int movementSpeed;
	private int maxHealth;
	private int health;
	private Image projectileImage;
	private long lastAttackTime;

	public Player() {
		projectileImage = Sprites.loadSprite(PROJECTILE_IMAGE_PATH);
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public int getFireSpeed() {
		return fireSpeed;
	}

	public void setFireDamage(int damage) {
		this.fireSpeed = damage;
	}

	public int getRangedDamage() {
		return rangedDamage;
	}

	public void setRangedDamage(int damage) {
		this.rangedDamage = damage;
	}

	public int getMoveSpeed() {
		return movementSpeed;
	}

	public void setMoveSpeed(int speed) {
		this.movementSpeed = speed;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(int max) {
		this.maxHealth = max;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	@Override
	public Projectile[] attack(double targetX, double targetY) {
		// 30 as a temp, making it so that at max upgrades, the player fires once per
		// 200ms, I think that maybe less is better, or we raise the price on the
		// upgrade
		if (System.currentTimeMillis() - lastAttackTime < RANGED_ATTACK_DELAY - 30 * fireSpeed) {
			return new Projectile[0];
		}
		GObject gObj = getGObject();
		double x = gObj.getX() + gObj.getWidth() / 2;
		double y = gObj.getY() + getGObject().getHeight() / 2;
		GImage sprite = new GImage(projectileImage, x, y);
		Projectile p = new Projectile(sprite);
		p.setAngle(Math.atan2(targetX - p.getX(), targetY - p.getY()));
		p.setPlayer(true);
		p.setDamage(rangedDamage);
		lastAttackTime = System.currentTimeMillis();
		return new Projectile[] { p };
	}

	public void melee() {
		// TODO implement
	}

}
