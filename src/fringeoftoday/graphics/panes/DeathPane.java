package fringeoftoday.graphics.panes;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import acm.graphics.GImage;
import fringeoftoday.MainApplication;

public class DeathPane extends GraphicsPane {
	private MainApplication program; 
	
	private GImage deathscreen; 

	public DeathPane(MainApplication app) {
		this.program = app;
		deathscreen = new GImage("deathscreen.jpg",0,0);
	}

	@Override
	public void showContents() {
		program.add(deathscreen);
	}

	@Override
	public void hideContents() {
		program.remove(deathscreen);
	}


	@Override
	public void keyPressed(KeyEvent e) {
		program.switchToMenu();
	}
}
