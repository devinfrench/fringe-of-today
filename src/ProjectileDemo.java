import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GOval;
import acm.program.GraphicsProgram;
import fringeoftoday.entities.Projectile;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ProjectileDemo extends GraphicsProgram {
	
    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 600;
    public static final double VELOCITY = 5.0;

    private List<Projectile> projectiles;
    private GLabel count;

    public void run() {
        projectiles = new CopyOnWriteArrayList<>();
        count = new GLabel("Projectile Count: 0");
        count.setLocation(20, 20);
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

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == 1) {
            Projectile p = new Projectile(WINDOW_WIDTH / 2, WINDOW_HEIGHT / 2);
            p.setAngle(Math.atan2(e.getX() - p.getX(), e.getY() - p.getY()));
            GOval o = new GOval(p.getX(), p.getY(), 5, 5);
            p.setGObject(o);
            projectiles.add(p);
            add(o);
        }
    }

    public void init() {
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        requestFocus();
    }

}
