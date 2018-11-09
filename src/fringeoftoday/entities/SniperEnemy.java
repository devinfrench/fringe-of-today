package fringeoftoday.entities;

public class SniperEnemy extends Enemy {

    public SniperEnemy(int posX, int posY) {
        super(posX, posY);
    }

    @Override
    public Projectile[] attack() {
        // TODO implement
        return new Projectile[0];
    }
}
