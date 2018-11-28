package fringeoftoday.entities;

import acm.graphics.GObject;
import acm.graphics.GPoint;
import fringeoftoday.core.CollisionManager;
import fringeoftoday.floor.Floor;
import fringeoftoday.floor.FloorManager;

import java.awt.*;
import java.awt.geom.Point2D;

public abstract class Enemy extends ActiveEntity {

    private float dmgMult;
    private double fireRate;
    private double velocity;

    public float getDmgMult() {
        return dmgMult;
    }

    public void setDmgMult(float dmgMult) {
        this.dmgMult = dmgMult;
    }

    public double getFireRate() {
        return fireRate;
    }

    public void setFireRate(double d) {
        this.fireRate = d;
    }
    
    public double getVelocity() {
    	return velocity;
    }
    
    public void setVelocity(double velocity) {
    	this.velocity = velocity;
    }
    
    public void move(CollisionManager collisionManager, Entity target) {
		if (target == null || collisionManager == null) {
			return;
		}
		double targetX = target.getX();
		double targetY = target.getY();
		double enemyX = getX();
		double enemyY = getY();
		double closest = Double.MAX_VALUE;
		GPoint p = new GPoint(0, 0);
        for (int deltaX = -1; deltaX <= 1; deltaX++) {
            for (int deltaY = -1; deltaY <= 1; deltaY++) {
                if (deltaX == 0 && deltaY == 0 || deltaX != 0 && deltaY != 0) {
                    continue;
                }
                double dx = enemyX + (deltaX * FloorManager.SPACE_SIZE);
                double dy = enemyY + (deltaY * FloorManager.SPACE_SIZE);
                double x = dx + (FloorManager.SPACE_SIZE / 2);
                double y = dy + (FloorManager.SPACE_SIZE / 2);
                double dist = Math.hypot(targetX - dx, targetY - dy);
                if (dist < closest && collisionManager.enemyCanMove(x, y)) {
                    closest = dist;
                    p = getMovePoint(dx, dy);
                    this.setIsMoving(true);
                    if (dx == 0 && dy < 0) {
                    	this.setFacing("north");
                    }
                    else if (dx == 0 && dy > 0) {
                    	this.setFacing("south");
                    }
                    else if (dx < 0 && dy == 0) {
                    	this.setFacing("west");
                    }
                    else if (dx > 0 && dy == 0) {
                    	this.setFacing("east");
                    }
                }
                else
                {
                	this.setIsMoving(false);
                }
            }
        }
        getGObject().move(p.getX(), p.getY());
    }

    private GPoint getMovePoint(double targetX, double targetY) {
        double deltaX = targetX - getX();
        double deltaY = targetY - getY();
        double angle = Math.atan2(deltaY, deltaX);
        double x = velocity * Math.cos(angle);
        double y = velocity * Math.sin(angle);
        return new GPoint(x, y);
    }

}
