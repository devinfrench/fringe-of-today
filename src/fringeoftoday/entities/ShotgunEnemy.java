package fringeoftoday.entities;

public class ShotgunEnemy extends Enemy {

    public ShotgunEnemy(int posX, int posY, String sprite, int health, float dmgMult, int fireRate) {
        super(posX, posY, sprite, health, dmgMult, fireRate);
    }

    @Override
    public Projectile[] attack() {
        // TODO implement
        return new Projectile[0];
    }
}
