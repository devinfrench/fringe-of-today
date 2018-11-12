import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.Timer;

import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GOval;
import acm.graphics.GPoint;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;
import fringeoftoday.entities.Projectile;

public class ProjectileCollisionDemo extends GraphicsProgram {
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 600;
	public static final double VELOCITY = 5.0;

	private List<Projectile> projectiles;
	private List<GObject> impassables;
	private GLabel count;
	private RandomGenerator rgen = new RandomGenerator();

	public void run() {
		projectiles = new CopyOnWriteArrayList<>();
		count = new GLabel("Projectile Count: 0");
		count.setLocation(20, 20);
		add(count);
		populateImpassables();
		addMouseListeners();
		Timer timer = new Timer(50, e -> {
			count.setLabel("Projectile Count: " + projectiles.size());
			projectiles.forEach((p) -> {
				GObject o = p.getGObject();
				if (isCollision(p) || o.getX() <= 0 || o.getY() <= 0 || o.getY() >= WINDOW_HEIGHT || o.getX() >= WINDOW_WIDTH) {
					remove(o);
					projectiles.remove(p);
				} else {
					o.move(VELOCITY * Math.sin(p.getAngle()), VELOCITY * Math.cos(p.getAngle()));
				}
			});
		});
		timer.start();
	}

	private boolean isCollision(Projectile projectile) {
		GObject o = projectile.getGObject();
		GPoint center = new GPoint(o.getX() + (o.getWidth() / 2), o.getY() + (o.getHeight() / 2));
		for (GObject impassable : impassables) {
			if (impassable.contains(center)) {
				return true;
			}
		}
		return false;
	}

	private void populateImpassables() {
		impassables = new ArrayList<GObject>();
		for (int i = 0; i < 8; i++) {
			GRect r = new GRect(rgen.nextDouble(20, WINDOW_WIDTH - 50), rgen.nextDouble(20, WINDOW_HEIGHT - 50), 50, 50);
			r.setColor(Color.RED);
			r.setFilled(true);
			impassables.add(r);
			add(r);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == 1) {
			Projectile p = new Projectile(new GOval(WINDOW_WIDTH / 2, WINDOW_HEIGHT / 2, 5, 5));
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
