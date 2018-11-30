package demo;

import acm.graphics.GOval;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;
import fringeoftoday.entities.Enemy;
import fringeoftoday.entities.Player;
import fringeoftoday.entities.StandardEnemy;

import javax.swing.*;
import java.awt.*;

public class EnemyMovementDemo extends GraphicsProgram {

    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 600;
    public static final int ROOM_ROWS = 11;
    public static final int ROOM_COLS = 17;

    private Player player;
    private Enemy enemy;
    private RandomGenerator rgen;

    public void run() {
        rgen = new RandomGenerator();
        initPlayer();
        initEnemy();
        Timer timer = new Timer(20, e -> {
            enemy.move(null, player);
            if (Math.abs(enemy.getX() - player.getX()) <= 10 || Math.abs(enemy.getY() - player.getY()) <= 10) {
                player.getGObject().setLocation(rgen.nextInt(10) * spaceWidth(), rgen.nextInt(15) * spaceHeight());
            }
        });
        timer.start();
    }

    private void initPlayer() {
        player = new Player();
        GOval o = new GOval(rgen.nextInt(10) * spaceWidth(), rgen.nextInt(15) * spaceHeight(), spaceWidth(), spaceHeight());
        o.setFilled(true);
        o.setFillColor(Color.GREEN);
        o.setColor(Color.GREEN);
        player.setGObject(o);
        add(o);
    }

    private void initEnemy() {
        enemy = new StandardEnemy();
        enemy.setVelocity(3.0);
        GRect r = new GRect(enemy.getX() * spaceWidth(), enemy.getY() * spaceHeight(), spaceWidth(), spaceHeight());
        r.setFilled(true);
        r.setColor(Color.RED);
        r.setFillColor(Color.RED);
        enemy.setGObject(r);
        add(r);
    }

    private double canvasWidth() {
        return getGCanvas().getWidth();
    }

    private double canvasHeight() {
        return getGCanvas().getHeight();
    }

    private double spaceWidth() {
        return (canvasWidth() / ROOM_ROWS);
    }

    private double spaceHeight() {
        return (canvasHeight() / ROOM_COLS);
    }

    public void init() {
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
    }
}
