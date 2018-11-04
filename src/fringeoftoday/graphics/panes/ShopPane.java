package fringeoftoday.graphics.panes;

import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

import acm.graphics.GLabel;
import acm.graphics.GLine;
import acm.graphics.GObject;
import fringeoftoday.MainApplication;
import fringeoftoday.graphics.GButton;

public class ShopPane extends GraphicsPane {
	private MainApplication program; // you will use program to get access to
										// all of the GraphicsProgram calls
	private GLabel title;
	private GButton btnBack;
	private GLine headerSeparator;
	private GLabel coinCtr;

		
	public ShopPane(MainApplication app) {
		this.program = app;
		
		// Title for the shop
		title = new GLabel("Shop", MainApplication.WINDOW_WIDTH / 2 - 50, MainApplication.WINDOW_HEIGHT / 18);
		title.setFont("Arial-46");
		
		// Back button
		btnBack = new GButton("Back", 0, 0, MainApplication.WINDOW_WIDTH / 13, MainApplication.WINDOW_HEIGHT / 12);
		
		// Header separator
		headerSeparator = new GLine(0, MainApplication.WINDOW_HEIGHT / 11, MainApplication.WINDOW_WIDTH,
				MainApplication.WINDOW_HEIGHT / 11);
		
		// Coin counter at the top left
		coinCtr = new GLabel("Coin: " + MainApplication.getMap().get("Coin"), MainApplication.WINDOW_WIDTH-300, MainApplication.WINDOW_HEIGHT / 18);
		coinCtr.setFont("Arial-46");
	}

	@Override
	public void showContents() {
		program.add(title);
		program.add(btnBack);
		program.add(headerSeparator);
		program.add(coinCtr);
	}

	@Override
	public void hideContents() {
		program.remove(title);
		program.remove(btnBack);
		program.remove(headerSeparator);
		program.remove(coinCtr);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == btnBack) {
			program.switchToMenu();
		}
	}
}
