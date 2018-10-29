package fringeoftoday;

import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.GObject;

public class MenuPane extends GraphicsPane {
	private MainApplication program; // you will use program to get access to
										// all of the GraphicsProgram calls
	private GButton btnPlay;
	private GButton btnShop;
	private GButton btnExit;
	private GParagraph title;
	public MenuPane(MainApplication app) {
		super();
		program = app;
		
		//Title banner - maybe use glabel instead?
		title = new GParagraph("Fringe of Today", 150, 300);
		
		//Play button
		btnPlay = new GButton("Play", 600, 200, 200, 100);
		btnPlay.setFillColor(Color.RED);
		
		//Shop button
		btnShop = new GButton("Shop", 600, 400, 200, 100);
		btnShop.setFillColor(Color.RED);
		
		//Exit button
		btnExit = new GButton("Exit", 600, 600, 200, 100);
		btnShop.setFillColor(Color.RED);
	}

	@Override
	public void showContents() {
		program.add(title);
		program.add(btnPlay);
		program.add(btnShop);
		program.add(btnExit);
	}

	@Override
	public void hideContents() {
		program.remove(title);
		program.remove(btnPlay);
		program.remove(btnShop);
		program.remove(btnExit);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == btnPlay) {
			//program.switchToGame();
		}
		else if (obj == btnShop) {
			program.switchToShop();
		}
	}
}
