package fringeoftoday;

import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.GObject;

public class MenuPane extends GraphicsPane {
	private MainApplication program; // you will use program to get access to
										// all of the GraphicsProgram calls
	private GButton playButton;
	private GButton shopButton;
	private GButton exitButton;
	private GParagraph title;
	public MenuPane(MainApplication app) {
		super();
		program = app;
		
		//Title banner - maybe use glabel instead?
		title = new GParagraph("Fringe of Today", 150, 300);
		
		//Play button
		playButton = new GButton("Play", 600, 200, 200, 100);
		playButton.setFillColor(Color.RED);
		
		//Shop button
		shopButton = new GButton("Shop", 600, 400, 200, 100);
		shopButton.setFillColor(Color.RED);
		
		//Exit button
		exitButton = new GButton("Exit", 600, 600, 200, 100);
		shopButton.setFillColor(Color.RED);
	}

	@Override
	public void showContents() {
		program.add(title);
		program.add(playButton);
		program.add(shopButton);
		program.add(exitButton);
	}

	@Override
	public void hideContents() {
		program.remove(playButton);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == playButton) {
			//program.switchToGame();
		}
		else if (obj == shopButton) {
			program.switchToShop();
		}
	}
}
