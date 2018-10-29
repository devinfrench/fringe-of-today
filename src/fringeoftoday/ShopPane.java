package fringeoftoday;
import java.awt.event.MouseEvent;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;

public class ShopPane extends GraphicsPane {
	private MainApplication program; // you will use program to get access to
										// all of the GraphicsProgram calls
	private GLabel title;
	private GButton btnBack;

	public ShopPane(MainApplication app) {
		this.program = app;
		title = new GLabel("Shop", MainApplication.WINDOW_WIDTH/2 - 50, 50);
		title.setFont("Arial-46");
		btnBack = new GButton("Back", 0, 0, 125, 75);
	}

	@Override
	public void showContents() {
		program.add(title);
		program.add(btnBack);
	}

	@Override
	public void hideContents() {		
		program.remove(title);
		program.remove(btnBack);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == btnBack) {
			program.switchToMenu();
		}
	}
}
