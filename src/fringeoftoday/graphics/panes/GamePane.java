package fringeoftoday.graphics.panes;

import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.GObject;
import acm.graphics.GImage;
import acm.graphics.GRect;
import acm.graphics.GLabel;
import fringeoftoday.MainApplication;
import fringeoftoday.graphics.GButton;
import fringeoftoday.graphics.GParagraph;
import fringeoftoday.graphics.panes.GraphicsPane;

public class GamePane extends GraphicsPane {
	private MainApplication program; // you will use program to get access to
										// all of the GraphicsProgram calls
	public static final int BUTTON_WIDTH = 200;
	public static final int BUTTON_HEIGHT = 100;
	public static final int HEADER_WIDTH = MainApplication.WINDOW_WIDTH/3;
	public static final int HEADER_HEIGHT = 200;
	private int level = -1;
	private int mDamage = -1;//Check variable names/change for consistency
	private int rDamage = -1;
	private int moveSpeed = -1;
	private GButton btnDie;
	private GRect minimapBox;
	private GRect infoBox;
	private GParagraph infoText;
	private GRect healthBox;
	
	public GamePane(MainApplication app) {
		super();
		program = app;
		
		//HEADER
		minimapBox = new GRect(0, 0, HEADER_WIDTH, HEADER_HEIGHT);
		
		infoBox = new GRect(HEADER_WIDTH,0,HEADER_WIDTH,HEADER_HEIGHT);
		infoText = new GParagraph(
				"Level: "+level+"\nMelee Damage: "+mDamage+"\nRanged Damage: "+rDamage+"\nMove Speed: "+moveSpeed,0,0);
		//TODO: figure out how to set font
		//infoText.setFont(Font("Times", BOLD,40));
		infoText.move(infoBox.getX()+(infoBox.getWidth()-infoText.getWidth())/2, infoBox.getY()+infoText.getHeight());
		
		healthBox = new GRect(HEADER_WIDTH*2,0,HEADER_WIDTH,HEADER_HEIGHT);
		
		//FIELD
		
		//OTHER
		btnDie = new GButton("DIE", (MainApplication.WINDOW_WIDTH - BUTTON_WIDTH) / 2, (MainApplication.WINDOW_HEIGHT - BUTTON_HEIGHT) / 2, BUTTON_WIDTH, BUTTON_HEIGHT);
	}

	@Override
	public void showContents() {//split showContents into showHeader and showField for clarity
		showHeader(); //Top bar
		showField(); //Game field
		program.add(btnDie);//Testing death screen, remove when things are added
	}

	@Override
	public void hideContents() {
		removeHeader();
		removeField();
		program.remove(btnDie);//Testing death screen, remove when things are added
	}
	
	public void showHeader() {
		program.add(minimapBox);
		program.add(infoBox);
		program.add(infoText);
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
	
	public void onDeath() {//Trigger this when player is dead, should add other functions - tally score, etc.
		program.switchToDeath();
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == btnDie) {
			onDeath();
		}
	}
}
