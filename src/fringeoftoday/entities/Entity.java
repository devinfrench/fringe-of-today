package fringeoftoday.entities;

import acm.graphics.GObject;

public class Entity {

    private GObject gObj;

    public Entity() {
    }

    public Entity(GObject gObj) {
        this.gObj = gObj;
    }

    /**
     * Gets the x-coordinate of the GObject
     * @return the x-coordinate of the GObject or 0 if GObject is null
     */
    public double getX() {
        return gObj != null ? gObj.getX() : 0;
    }

    /**
     * Sets the x-coordinate of the GObject and updates the GObject's location
     * @param x the x-coordinate to move to.
     */
    public void setX(double x) {
        if (gObj != null) {
            gObj.move(x, 0);
        }
    }

    /**
     * Gets the y-coordinate of the GObject
     * @return the y-coordinate of the GObject or 0 if GObject is null
     */
    public double getY() {
        return gObj != null ? gObj.getY() : 0;
    }

    /**
     * Sets the y-coordinate of the GObject and updates the GObject's location
     * @param y the x-coordinate to move to.
     */
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
