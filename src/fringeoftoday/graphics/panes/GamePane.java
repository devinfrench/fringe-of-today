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

	public GamePane(MainApplication app) {
		super();
		program = app;

		
	}

	@Override
	public void showContents() {
		
	}

	@Override
	public void hideContents() {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		
	}
}
