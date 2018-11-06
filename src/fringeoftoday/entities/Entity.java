package fringeoftoday.entities;

import acm.graphics.GImage;

public class Entity {

    private int posX;
    private int posY;
    private int deltaX;
    private int deltaY;
    private GImage sprite;

    public Entity(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public Entity(int posX, int posY, String sprite) {
        this.posX = posX;
        this.posY = posY;
        this.sprite = new GImage(sprite, posX, posY);
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getDeltaX() {
        return deltaX;
    }

    public void setDeltaX(int deltaX) {
        this.deltaX = deltaX;
    }

    public int getDeltaY() {
        return deltaY;
    }

    public void setDeltaY(int deltaY) {
        this.deltaY = deltaY;
    }

    public GImage getSprite() {
        return sprite;
    }

    public void setSprite(String sprite) {
        this.sprite = new GImage(sprite, this.sprite.getX(), this.sprite.getY());
    }

}
