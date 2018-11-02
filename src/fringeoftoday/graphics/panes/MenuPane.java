package fringeoftoday.graphics.panes;



import java.awt.Color;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import acm.graphics.GObject;
import acm.graphics.GImage;
import fringeoftoday.MainApplication;
import fringeoftoday.graphics.GButton;
import fringeoftoday.graphics.panes.GraphicsPane;

public class MenuPane extends GraphicsPane {
	private MainApplication program; // you will use program to get access to
										// all of the GraphicsProgram calls
	public static final int BUTTON_WIDTH = 200;
	public static final int BUTTON_HEIGHT = 100;
	
	private GButton btnPlay;
	private GButton btnShop;
	private GButton btnExit;
	private GImage title;
	public MenuPane(MainApplication app) {
		super();
		program = app;
		
		playerFileSetup();
		
		//Title banner - maybe use GImage instead?
		title = new GImage("logo_transparent.png", (MainApplication.WINDOW_WIDTH - 600)/2, 30);
		title.setSize(600, 300);
		
		//Play button
		btnPlay = new GButton("Play", (MainApplication.WINDOW_WIDTH - BUTTON_WIDTH)/2, 400, BUTTON_WIDTH, BUTTON_HEIGHT);
		btnPlay.setFillColor(Color.BLUE);
		
		//Shop button
		btnShop = new GButton("Shop", (MainApplication.WINDOW_WIDTH - BUTTON_WIDTH)/2, 550, BUTTON_WIDTH, BUTTON_HEIGHT);
		btnShop.setFillColor(Color.RED);
		
		//Exit button
		btnExit = new GButton("Exit", (MainApplication.WINDOW_WIDTH - BUTTON_WIDTH)/2, 700, BUTTON_WIDTH, BUTTON_HEIGHT);
		btnExit.setFillColor(Color.RED);
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
		else if (obj == btnExit) {
			program.exitProgram();
		}
	}

	private void playerFileSetup() {
		File playerF = new File("player.txt");
			if (!playerF.exists()) {
				try {
					playerF.createNewFile();
				} catch (IOException e1) {
					System.out.println("Couldn't create file");
					e1.printStackTrace();
				}
				
				try {
					FileWriter fw = new FileWriter("player.txt");
					fw.write("coin:0"+System.getProperty("line.separator"));
					fw.write("HP up:0"+System.getProperty("line.separator"));
					fw.write("Melee up:0"+System.getProperty("line.separator"));
					fw.write("Ranged up:0"+System.getProperty("line.separator"));
					fw.write("Speed up:0"+System.getProperty("line.separator"));
					fw.write("previous:0"+System.getProperty("line.separator"));
					fw.write("goat:0");
					fw.close();
				} catch (IOException e2) {
					System.out.println("No write");
					e2.printStackTrace();
				}
				
			}
	}
}
