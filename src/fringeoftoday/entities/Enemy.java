package fringeoftoday.entities;

public abstract class Enemy extends ActiveEntity {

    private float dmgMult;
    private int fireRate;

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
