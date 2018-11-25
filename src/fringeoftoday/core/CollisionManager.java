package fringeoftoday.core;

import acm.graphics.GObject;
import fringeoftoday.entities.Enemy;
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
        if (player == null) {
            return false;
        }
        x = player.getX() + x + (player.getWidth() / 2);
        y = player.getY() + y + (player.getHeight() / 2);
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
                            return  false;
                    }
                }
            }
        }
        return false;
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
                            return  false;
                    }
                }
            }
        }
        return false;
    }

    public boolean isTerrainCollision(Projectile p) {
        GObject sprite = p.getGObject();
        if (sprite == null) {
            return true;
        }
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

    public boolean isPlayerCollision(Projectile p) {
        if (p.isPlayer()) {
            return false;
        }
        GObject sprite = p.getGObject();
        GObject player = entityManager.getPlayer().getGObject();
        if (sprite == null || player == null) {
            return false;
        }
        double x = sprite.getX() + (sprite.getWidth() / 2);
        double y = sprite.getY() + (sprite.getHeight() / 2);
        return player.getBounds().contains(x, y);
    }

    public boolean isEnemyCollision(Enemy enemy, Projectile p) {
        if (!p.isPlayer()) {
            return false;
        }
        GObject pSprite = p.getGObject();
        GObject eSprite = enemy.getGObject();
        if (pSprite == null || eSprite == null) {
            return false;
        }
        double x = pSprite.getX() + (pSprite.getWidth() / 2);
        double y = pSprite.getY() + (pSprite.getHeight() / 2);
        return eSprite.contains(x, y);
    }
}
