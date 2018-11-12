import java.awt.Color;

import javax.swing.Timer;

import acm.graphics.GLine;
import acm.graphics.GObject;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;
import fringeoftoday.entities.Enemy;
import fringeoftoday.entities.StandardEnemy;

public class EnemyMovementDemo extends GraphicsProgram {

	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 600;
	public static final int ROOM_ROWS = 11;
	public static final int ROOM_COLS = 17;

	private Enemy enemy;
	private RandomGenerator rgen;

	public void run() {
		rgen = new RandomGenerator();
		enemy = new StandardEnemy(1, 1);
		GRect r = new GRect(enemy.getX() * spaceWidth(), enemy.getY() * spaceHeight(), spaceWidth(), spaceHeight());
		r.setFilled(true);
		r.setColor(Color.RED);
		r.setFillColor(Color.RED);
		enemy.setGObject(r);
		drawGrid();
		add(enemy.getGObject());
		Timer timer = new Timer(600, e -> {
			GObject obj = enemy.getGObject();
			double x;
			double y;
			do {
				x = obj.getX() + rgen.nextInt(-1, 1) * spaceWidth();
				y = obj.getY() + rgen.nextInt(-1, 1) * spaceHeight();
			} while (x < 0 || x > canvasWidth() || y < 0 || y > canvasHeight());
			enemy.getGObject().setLocation(x, y);
		});
		timer.start();
	}

	private void drawGrid() {
		for (int x = 0; x <= ROOM_ROWS; x++) {
			add(new GLine(x * spaceWidth(), 0, x * spaceWidth(), canvasHeight()));
		}
		for (int y = 0; y <= ROOM_COLS; y++) {
			add(new GLine(0, y * spaceHeight(), canvasWidth(), y * spaceHeight()));
		}
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
