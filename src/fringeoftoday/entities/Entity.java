package fringeoftoday.entities;

import acm.graphics.GObject;

public class Entity {

    private GObject gObj;

    public Entity() {
    }

    public Entity(GObject gObj) {
        this.gObj = gObj;
    }

    public double getX() {
        return gObj != null ? gObj.getX() : 0;
    }

    public void setX(double x) {
        if (gObj != null) {
            gObj.move(x, 0);
        }
    }

    public double getY() {
        return gObj != null ? gObj.getY() : 0;
    }

    public void setY(double y) {
        if (gObj != null) {
            gObj.move(0, y);
        }
    }

    public GObject getGObject() {
        return gObj;
    }

    public void setGObject(GObject gObj) {
        this.gObj = gObj;
    }

}
