package fringeoftoday.entities;

public class StandardEnemy extends Enemy {

    public StandardEnemy(int posX, int posY) {
        super(posX, posY);
    }

    @Override
    public Projectile[] attack() {
        // TODO implement
        return new Projectile[0];
    }
}
