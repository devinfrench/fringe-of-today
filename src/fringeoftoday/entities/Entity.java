package fringeoftoday.entities;

import acm.graphics.GObject;

public class Entity {

    private int posX;
    private int posY;
    private GObject gObj;

    public Entity(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public int getX() {
        return posX;
    }

    public void setX(int posX) {
        this.posX = posX;
    }

    public int getY() {
        return posY;
    }

    public void setY(int posY) {
        this.posY = posY;
    }

    public GObject getGObject() {
        return gObj;
    }

    public void setGObject(GObject gObj) {
        this.gObj = gObj;
    }

}
