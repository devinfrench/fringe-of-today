package fringeoftoday.entities;

public class ShotgunEnemy extends Enemy {

    public ShotgunEnemy(int posX, int posY) {
        super(posX, posY);
    }

    @Override
    public Projectile[] attack() {
        // TODO implement
        return new Projectile[0];
    }
}
