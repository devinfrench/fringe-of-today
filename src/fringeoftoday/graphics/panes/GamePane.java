package fringeoftoday.graphics.panes;

import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.GObject;
import acm.graphics.GImage;
import acm.graphics.GRect;
import acm.graphics.GLabel;
import fringeoftoday.MainApplication;
import fringeoftoday.graphics.GButton;
import fringeoftoday.graphics.panes.GraphicsPane;

public class GamePane extends GraphicsPane {
	private MainApplication program; // you will use program to get access to
										// all of the GraphicsProgram calls
	public static final int BUTTON_WIDTH = 200;
	public static final int BUTTON_HEIGHT = 100;
	public static final int HEADER_WIDTH = MainApplication.WINDOW_WIDTH/3;
	public static final int HEADER_HEIGHT = 200;
	private GButton btnPlay;
	private GRect minimapBox;
	private GRect infoBox;
	private GRect healthBox;
	
	public GamePane(MainApplication app) {
		super();
		program = app;
		minimapBox = new GRect(0, 0, HEADER_WIDTH, HEADER_HEIGHT);
		infoBox = new GRect(HEADER_WIDTH,0,HEADER_WIDTH,HEADER_HEIGHT);
		healthBox = new GRect(HEADER_WIDTH*2,0,HEADER_WIDTH,HEADER_HEIGHT);
	}

	@Override
	public void showContents() {
		showHeader();
		showField();
	}

	@Override
	public void hideContents() {
		program.remove(btnPlay);
	}
	
	public void showHeader() {
		program.add(minimapBox);
		program.add(infoBox);
		program.add(healthBox);
	}
	
	public void removeHeader() {
		program.remove(minimapBox);
		program.remove(infoBox);
		program.remove(healthBox);
	}
	
	public void showField() {
		
	}
	
	public void removeField() {
		
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		program.switchToMenu();	//Delete this when things start happening here
	}
}
