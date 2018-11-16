package fringeoftoday.entities;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class EntityManager {

    private Player player;
    private List<Enemy> enemies;
    private List<Projectile> projectiles;

    public EntityManager() {
        player = new Player();
        // using CopyOnWriteArrayList for thread safety
        enemies = new CopyOnWriteArrayList<Enemy>();
        projectiles = new CopyOnWriteArrayList<Projectile>();
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public List<Projectile> getProjectiles() {
        return projectiles;
    }

}
