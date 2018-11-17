package fringeoftoday.core;

import acm.graphics.GObject;
import fringeoftoday.entities.EntityManager;
import fringeoftoday.entities.Projectile;
import fringeoftoday.floor.FloorManager;
import fringeoftoday.floor.Room;
import fringeoftoday.floor.Space;
import fringeoftoday.floor.SpaceType;

public class CollisionManager {

    private EntityManager entityManager;
    private Room room;

    public CollisionManager(EntityManager entityManager, Room room) {
        this.entityManager = entityManager;
        this.room = room;
    }

    public boolean playerCanMove(double x, double y) {
        GObject player = entityManager.getPlayer().getGObject();
        x = player.getX() + x + (player.getWidth() / 2);
        y = player.getY() + y + (player.getHeight() / 2);
        for (int i = 0; i < FloorManager.ROOM_ROWS; i++) {
            for (int j = 0; j < FloorManager.ROOM_COLS; j++) {
                Space space = room.getSpace(i, j);
                if (space.getGObject().contains(x, y)) {
                    if (space.getType() != SpaceType.GROUND) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean isTerrainCollision(Projectile p) {
        GObject sprite = p.getGObject();
        double x = sprite.getX() + (sprite.getWidth() / 2);
        double y = sprite.getY() + (sprite.getHeight() / 2);
        for (int i = 0; i < FloorManager.ROOM_ROWS; i++) {
            for (int j = 0; j < FloorManager.ROOM_COLS; j++) {
                Space space = room.getSpace(i, j);
                if (space.getGObject().contains(x, y)) {
                    if (space.getType() == SpaceType.WALL
                      || space.getType() == SpaceType.BLANK
                      || space.getType() == SpaceType.DOOR) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
