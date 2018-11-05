import acm.graphics.*;
import acm.program.*;
import acm.util.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.Timer;
/**
 * Demonstration of keyboard input to onscreen movement
 * 
 * @author Alexander Ng
 *
 */
public class MoveDemo extends GraphicsProgram implements ActionListener{
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 600;
	public static final int BALL_SIZE = 100;
	public static final int BREAK_MS = 30;
	
	private GOval ball;
	public String direction;
	
	public void run() {
		ball = new GOval(WINDOW_HEIGHT/2-BALL_SIZE/2, WINDOW_WIDTH/2-BALL_SIZE/2, BALL_SIZE, BALL_SIZE);
		ball.setColor(Color.RED);
		ball.setFilled(true);
		add(ball);
		addKeyListeners();
		Timer t = new Timer(BREAK_MS, this);
		t.start();
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
	

	public void init() {
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		requestFocus();
	}
	
	
	
}