package fringeoftoday.core;

import acm.graphics.GObject;
import fringeoftoday.entities.Enemy;
import fringeoftoday.entities.EntityManager;
import fringeoftoday.entities.Player;
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

    public void setRoom(Room room) {
        this.room = room;
    }

    public boolean playerCanMove(double x, double y) {
        SpaceType type = getPlayerSpaceType(x, y);
        switch (type) {
            case GROUND:
            case BASIC_SPAWN:
            case SHOTGUN_SPAWN:
            case SNIPER_SPAWN:
            case STAIRS:
                return true;
            default:
                return false;
        }
    }

    /**
     * Returns the SpaceType if the player were to move the given x and y distances.
     *
     * @param x amount of movement on the x axis.
     * @param y amount of movement on the y axis.
     * @return the SpaceType if the player were to move the given x and y distances.
     */
    public SpaceType getPlayerSpaceType(double x, double y) {
        Player player = entityManager.getPlayer();
        if (player == null) {
            return SpaceType.BLANK;
        }
        x = player.getCenterX() + x;
        y = player.getCenterY() + y;
        for (int i = 0; i < FloorManager.ROOM_ROWS; i++) {
            for (int j = 0; j < FloorManager.ROOM_COLS; j++) {
                Space space = room.getSpace(i, j);
                if (space.getGObject().contains(x, y)) {
                    return space.getType();
                }
            }
        }
        return SpaceType.BLANK;
    }

    public boolean enemyCanMove(double x, double y) {
        for (int i = 0; i < FloorManager.ROOM_ROWS; i++) {
            for (int j = 0; j < FloorManager.ROOM_COLS; j++) {
                Space space = room.getSpace(i, j);
                if (space.getGObject().contains(x, y)) {
                    switch (space.getType()) {
                        case GROUND:
                        case BASIC_SPAWN:
                        case SHOTGUN_SPAWN:
                        case SNIPER_SPAWN:
                            return true;
                        default:
                            return false;
                    }
                }
            }
        }
        return false;
    }

    public boolean isTerrainCollision(Projectile p) {
        double x = p.getCenterX();
        double y = p.getCenterY();
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

    public boolean isPlayerCollision(Projectile p) {
        if (p.getSource() instanceof Player) {
            return false;
        }
        GObject player = entityManager.getPlayer().getGObject();
        if (player == null) {
            return false;
        }
        return player.getBounds().contains(p.getCenterX(), p.getCenterY());
    }

    public boolean isEnemyCollision(Enemy enemy, Projectile p) {
        if (!(p.getSource() instanceof Player)) {
            return false;
        }
        GObject obj = enemy.getGObject();
        if (obj == null) {
            return false;
        }
        return obj.contains(p.getCenterX(), p.getCenterY());
    }
}
