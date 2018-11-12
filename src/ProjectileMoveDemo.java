import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GOval;
import acm.program.GraphicsProgram;
import fringeoftoday.entities.Projectile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ProjectileMoveDemo  extends GraphicsProgram implements ActionListener {
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 600;
	public static final int BALL_SIZE = 100;
	public static final int BREAK_MS = 30;
	public static final double VELOCITY = 5.0;

	private GOval ball;
	public String direction;
	private List<Projectile> projectiles;
	private GLabel count;

	public void run() {
		ball = new GOval(WINDOW_HEIGHT/2-BALL_SIZE/2, WINDOW_WIDTH/2-BALL_SIZE/2, BALL_SIZE, BALL_SIZE);
		ball.setColor(Color.RED);
		ball.setFilled(true);
		add(ball);
		addKeyListeners();
		addMouseListeners();
		Timer t = new Timer(BREAK_MS, this);
		t.start();

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

	public void actionPerformed(ActionEvent e) {
		if (direction == "up") {
			ball.move(0, -5);
		}
		else if (direction =="down") {
			ball.move(0, 5);
		}
		else if (direction == "left") {
			ball.move(-5, 0);
		}
		else if (direction == "right") {
			ball.move(5, 0);
		}

	}



	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_W) {
			direction = "up";
		}
		else if (key == KeyEvent.VK_S) {
			direction = "down";
		}
		else if (key == KeyEvent.VK_A) {
			direction = "left";
		}
		else if (key == KeyEvent.VK_D) {
			direction = "right";
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		direction = "stop";
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == 1) {
			Projectile p = new Projectile(new GOval(
				ball.getX() + ball.getWidth() / 2,
				ball.getY() + ball.getHeight() / 2, 5, 5)
			);
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