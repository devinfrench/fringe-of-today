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
    private int fireRate;
    private double velocity;

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
        GPoint p = getPoint(targetX, targetY);
        if (!collisionManager.enemyCanMove(this, p.getX(), p.getY())) {
            GPoint closest = getClosestPoint(collisionManager, targetX, targetY);
            p = getPoint(closest.getX(), closest.getY());
        }
            getGObject().move(p.getX(), p.getY());
    }

    private GPoint getPoint(double targetX, double targetY) {
        double deltaX = targetX - getX();
        double deltaY = targetY - getY();
        double angle = Math.atan2(deltaY, deltaX);
        double x = velocity * Math.cos(angle);
        double y = velocity * Math.sin(angle);
        return new GPoint(x, y);
    }

    private GPoint getClosestPoint(CollisionManager collisionManager, double targetX, double targetY) {
        double enemyX = getX();
        double enemyY = getY();
        double x = 0;
        double y = 0;
        double dist = Double.MAX_VALUE;
        double temp = Point2D.distance(enemyX + FloorManager.SPACE_SIZE, enemyY, targetX, targetY);
        if (temp < dist && collisionManager.enemyCanMove(this, FloorManager.SPACE_SIZE, 0)) {
            x = enemyX + FloorManager.SPACE_SIZE;
            y = enemyY;
            dist = temp;
        }
        temp = Point2D.distance(enemyX - FloorManager.SPACE_SIZE, enemyY, targetX, targetY);
        if (temp < dist && collisionManager.enemyCanMove(this, -FloorManager.SPACE_SIZE, 0)) {
            x = enemyX - FloorManager.SPACE_SIZE;
            y = enemyY;
            dist = temp;
        }
        temp = Point2D.distance(enemyX, enemyY + FloorManager.SPACE_SIZE, targetX, targetY);
        if (temp < dist && collisionManager.enemyCanMove(this, 0, FloorManager.SPACE_SIZE)) {
            x = enemyX;
            y = enemyY + FloorManager.SPACE_SIZE;
            dist = temp;
        }
        temp = Point2D.distance(enemyX, enemyY + FloorManager.SPACE_SIZE, targetX, targetY);
        if (temp < dist && collisionManager.enemyCanMove(this, 0, -FloorManager.SPACE_SIZE)) {
            x = enemyX;
            y = enemyY - FloorManager.SPACE_SIZE;
        }
        return new GPoint(x, y);
    }

}
