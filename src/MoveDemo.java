import acm.graphics.*;
import acm.program.*;
import acm.util.*;
import java.awt.*;
import java.awt.event.*;
/**
 * Demonstration of keyboard input to onscreen movement
 * 
 * @author Alexander Ng
 *
 */
public class MoveDemo extends GraphicsProgram {
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 600;
	public static final int BALL_SIZE = 100;
	public static final int BREAK_MS = 30;
	public static final int INIT_X_VELOCITY = 5;
	public static final int INIT_Y_VELOCITY = 5;
	
	private GOval ball;
	private int xVelocity;
	private int yVelocity;
	private RandomGenerator rgen;
	
	public String direction;
	public void run() {
		rgen = RandomGenerator.getInstance();
		xVelocity = INIT_X_VELOCITY;
		yVelocity = INIT_Y_VELOCITY;
		ball = new GOval(WINDOW_HEIGHT/2-BALL_SIZE/2, WINDOW_WIDTH/2-BALL_SIZE/2, BALL_SIZE, BALL_SIZE);
		ball.setColor(Color.RED);
		ball.setFilled(true);
		add(ball);
		addKeyListeners();
		animateBall();
	}
	
	private void animateBall() {
		while(true) {
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
			pause(BREAK_MS);
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
	private boolean outOfBoundsX() {
		double x = ball.getX();
		return (x < 0 && xVelocity < 0 || x + BALL_SIZE > WINDOW_WIDTH && xVelocity > 0);
	}
	
	private boolean outOfBoundsY() {
		double y = ball.getY();
		return (y < 0 && yVelocity < 0 || y + BALL_SIZE > WINDOW_HEIGHT && yVelocity > 0);
	}

	public void init() {
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		requestFocus();
	}
	
	
	
}