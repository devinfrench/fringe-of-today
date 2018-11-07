package fringeoftoday.graphics.panes;

import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.GObject;
import acm.graphics.GImage;
import acm.graphics.GLabel;
import fringeoftoday.MainApplication;
import fringeoftoday.graphics.GButton;
import fringeoftoday.graphics.panes.GraphicsPane;

public class GamePane extends GraphicsPane {
	private MainApplication program; // you will use program to get access to
										// all of the GraphicsProgram calls
	public static final int BUTTON_WIDTH = 200;
	public static final int BUTTON_HEIGHT = 100;
	private GButton btnPlay;
	public GamePane(MainApplication app) {
		super();
		program = app;
		btnPlay = new GButton("Play", (MainApplication.WINDOW_WIDTH - BUTTON_WIDTH) / 2, 400, BUTTON_WIDTH,
				BUTTON_HEIGHT);
		btnPlay.setFillColor(Color.BLUE);
		
	}

	@Override
	public void showContents() {
		program.add(btnPlay);
	}

	@Override
	public void hideContents() {
		program.remove(btnPlay);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		program.switchToMenu();	//Delete this when things start happening here
	}
}
