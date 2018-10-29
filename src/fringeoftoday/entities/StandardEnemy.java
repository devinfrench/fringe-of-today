package fringeoftoday.entities;

public class StandardEnemy extends Enemy {

    public StandardEnemy(int posX, int posY, String sprite, int health, float dmgMult, int fireRate) {
        super(posX, posY, sprite, health, dmgMult, fireRate);
    }

    @Override
    public Projectile[] attack() {
        // TODO implement
        return new Projectile[0];
    }
}
