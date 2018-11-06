import java.awt.event.MouseEvent;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.Timer;

import acm.graphics.GLabel;
import acm.graphics.GOval;
import acm.program.GraphicsProgram;
import fringeoftoday.entities.Projectile;

public class ProjectileDemo extends GraphicsProgram {
	
    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 600;
    public static final double VELOCITY = 5.0;

    private Map<GOval, Projectile> projectiles;
    private GLabel count;

    public void run() {
        projectiles = new ConcurrentHashMap<>();
        count = new GLabel("Projectile Count: 0");
        count.setLocation(20, 20);
        add(count);
        addMouseListeners();
        Timer timer = new Timer(50, e -> {
            count.setLabel("Projectile Count: " + projectiles.size());
            projectiles.forEach((key, value) -> {
                if (key.getX() <= 0 || key.getY() <= 0 || key.getY() >= WINDOW_HEIGHT || key.getX() >= WINDOW_WIDTH) {
                    remove(key);
                    projectiles.remove(key);
                } else {
                    key.move(VELOCITY * Math.sin(value.getAngle()), VELOCITY * Math.cos(value.getAngle()));
                }
            });
        });
        timer.start();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == 1) {
            Projectile p = new Projectile(WINDOW_WIDTH / 2, WINDOW_HEIGHT / 2);
            p.setAngle(Math.atan2(e.getX() - p.getPosX(), e.getY() - p.getPosY()));
            GOval o = new GOval(p.getPosX(), p.getPosY(), 5, 5);
            projectiles.put(o, p);
            add(o);
        }
    }

    public void init() {
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        requestFocus();
    }

}
