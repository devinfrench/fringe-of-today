package demo;

import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.imageio.ImageIO;
import javax.swing.Timer;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.program.GraphicsProgram;
import fringeoftoday.entities.Projectile;

public class ProjectileDemo extends GraphicsProgram {
	
    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 600;
    public static final double VELOCITY = 5.0;
    public static final String BULLET_FILE_PATH = "projectiles/bullet_red.png";

    private List<Projectile> projectiles;
    private GLabel count;
    private Image sprite;

    public void run() {
        projectiles = new CopyOnWriteArrayList<>();
        count = new GLabel("Projectile Count: 0");
        count.setLocation(20, 20);
        sprite = loadSprite(BULLET_FILE_PATH);
        add(count);
        addMouseListeners();
        Timer timer = new Timer(50, e -> {
            count.setLabel("Projectile Count: " + projectiles.size());
            projectiles.forEach((p) -> {
                GObject o = p.getGObject();
                if (o.getX() <= 0 || o.getY() <= 0 || o.getY() >= WINDOW_HEIGHT || o.getX() >= WINDOW_WIDTH) {
                    remove(o);
                    projectiles.remove(p);
                } else {
                    o.move(VELOCITY * Math.sin(p.getAngle()), VELOCITY * Math.cos(p.getAngle()));
                }
            });
        });
        timer.start();
    }

    public static BufferedImage loadSprite(String file) {
        BufferedImage sprite = null;
        try {
            sprite = ImageIO.read(new File(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sprite;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == 1) {
            Projectile p = new Projectile(new GImage(sprite, WINDOW_WIDTH / 2, WINDOW_HEIGHT / 2));
            p.setAngle(Math.atan2(e.getX() - p.getX(), e.getY() - p.getY()));
            projectiles.add(p);
            add(p.getGObject());
        }
    }

    public void init() {
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        requestFocus();
    }

}
