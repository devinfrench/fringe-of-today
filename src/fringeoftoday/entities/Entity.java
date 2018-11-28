package fringeoftoday.entities;

import acm.graphics.GObject;

public class Entity {

    private GObject gObj;
    private boolean isMoving;
    private String facing;
    
    public Entity() {
    	this.isMoving = false;
        this.facing = "south";
    }

    public Entity(GObject gObj) {
        this.gObj = gObj;
        this.isMoving = false;
        this.facing = "south";
    }

    /**
     * Gets the x-coordinate of the GObject
     * @return the x-coordinate of the GObject or 0 if GObject is null
     */
    public double getX() {
        return gObj != null ? gObj.getX() : 0;
    }

    /**
     * Gets the y-coordinate of the GObject
     * @return the y-coordinate of the GObject or 0 if GObject is null
     */
    public double getY() {
        return gObj != null ? gObj.getY() : 0;
    }

    public double getCenterX() {
        GObject obj = getGObject();
        if (obj != null) {
            return obj.getX() + (obj.getWidth() / 2) ;
        }
        return 0;
    }

    public double getCenterY() {
        GObject obj = getGObject();
        if (obj != null) {
            return obj.getY() + (obj.getHeight() / 2);
        }
        return 0;
    }

    public void move(double x, double y) {
        if (gObj != null) {
            gObj.move(x, y);
        }
    }

    public GObject getGObject() {
        return gObj;
    }

    public void setGObject(GObject gObj) {
        this.gObj = gObj;
    }
    
    public void setIsMoving(boolean isMoving) {
    	this.isMoving = isMoving;
    }
    
    public boolean getIsMoving() {
    	return isMoving;
    }
    
    public void setFacing(String facing) {
    	this.facing = facing;
    }
    
    public String getFacing() {
    	return facing;
    }
}
