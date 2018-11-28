package fringeoftoday.entities;

import acm.graphics.GImage;
import acm.graphics.GObject;
import fringeoftoday.graphics.Sprites;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class ShotgunEnemy extends Enemy {

    private static final String PROJECTILE_IMAGE_PATH = "projectiles/bullet_orange.png";
    private static final int ATTACK_DELAY = 200;
    private static final int BASE_DAMAGE = 2;
    private static final int FIRST_ATTACK_DELAY = 2500;
    private static final int SPREAD = 50;
    private Image projectileImage;
    private long lastAttackTime;


    public ShotgunEnemy() {
        projectileImage = Sprites.loadSprite(PROJECTILE_IMAGE_PATH);
        Image sprite = Sprites.loadSprite("../media/sprites/pikachu/pikachu_standing_" + this.getFacing() + ".png");
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
        List<Projectile> projectiles = new ArrayList<>();
        for (int i = -1; i < 2; i++) {
            GImage sprite = new GImage(projectileImage, x, y);
            Projectile p = new Projectile(sprite);
            p.setAngle(Math.atan2((targetX + (i * SPREAD)) - p.getX(), (targetY + (i * SPREAD)) - p.getY()));
            p.setVelocity(2.5);
            p.setPlayer(false);
            p.setDamage((int) (BASE_DAMAGE * getDmgMult()));
            projectiles.add(p);
        }
        lastAttackTime = System.currentTimeMillis();
        return projectiles.toArray(new Projectile[projectiles.size()]);
    }

}
