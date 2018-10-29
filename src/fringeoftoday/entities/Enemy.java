package fringeoftoday.entities;

public abstract class Enemy extends ActiveEntity {

    private float dmgMult;
    private int fireRate;

    public Enemy(int posX, int posY, String sprite) {
        super(posX, posY, sprite);
    }

    public Enemy(int posX, int posY, String sprite, int health) {
        super(posX, posY, sprite, health);
    }

    public Enemy(int posX, int posY, String sprite, int health, float dmgMult, int fireRate) {
        super(posX, posY, sprite, health);
        this.dmgMult = dmgMult;
        this.fireRate = fireRate;
    }

    public float getDmgMult() {
        return dmgMult;
    }

    public void setDmgMult(float dmgMult) {
        this.dmgMult = dmgMult;
    }

    public int getFireRate() {
        return fireRate;
    }

    public void setFireRate(int fireRate) {
        this.fireRate = fireRate;
    }

}
