package fringeoftoday.entities;

import acm.graphics.GImage;
import fringeoftoday.graphics.Sprites;

import java.awt.*;

public class StandardEnemy extends Enemy {

	public StandardEnemy() {
		Image sprite = Sprites.loadSprite("enemy_sprite_temp.png");
		setGObject(new GImage(sprite));
	}

	@Override
	public Projectile[] attack(double targetX, double targetY) {
		// TODO implement
		return new Projectile[0];
	}

}
