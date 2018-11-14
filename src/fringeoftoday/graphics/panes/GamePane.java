package fringeoftoday.graphics.panes;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import acm.graphics.GObject;
import acm.graphics.GImage;
import acm.graphics.GRect;
import acm.graphics.GLabel;
import fringeoftoday.MainApplication;
import fringeoftoday.floor.FloorManager;
import fringeoftoday.PlayerData;
import fringeoftoday.graphics.GButton;
import fringeoftoday.graphics.GParagraph;
import fringeoftoday.graphics.panes.GraphicsPane;
import javax.swing.Timer;

public class GamePane extends GraphicsPane implements ActionListener {
	private MainApplication program; // you will use program to get access to
										// all of the GraphicsProgram calls
	public static final int BUTTON_WIDTH = MainApplication.BUTTON_WIDTH;
	public static final int BUTTON_HEIGHT = MainApplication.BUTTON_HEIGHT;
	public static final int HEADER_WIDTH = MainApplication.WINDOW_WIDTH/3;
	public static final int HEADER_HEIGHT = 196;
	public static final int SPACE_SIZE = (MainApplication.WINDOW_HEIGHT - HEADER_HEIGHT)/FloorManager.ROOM_ROWS;
	public static final String FILE_PATH = "../media/textures/";
	public int numTimes=0; //Timer stuff
	public static final int DELAY_MS = 25;
	public boolean keyPressed = false; //Keyboard input stuff
	public String direction;

	private int level = -1;	//Work on this when we get it in
	private int mDamage;
	private int rDamage;
	private int moveSpeed;
	private int startHealth;
	private int curHealth;
	private GButton btnDie; //Debug, remove when done
	private GRect minimapBox; //Minimap, left header
	private GRect infoBox; //Center header
	private GParagraph infoText;//Center header content
	private GRect healthBox; //Right header
	private GLabel healthLabel;
	private GImage[][] room;
	
	public GamePane(MainApplication app) {
		super();
		program = app;
		//HEADER Values
		mDamage = 1 + Integer.parseInt(PlayerData.getMap().get("MeleeUpgrades"));
		rDamage = 1 + Integer.parseInt(PlayerData.getMap().get("RangedUpgrades"));
		moveSpeed = 1 + Integer.parseInt(PlayerData.getMap().get("SpeedUpgrades"));
		
		
		//HEADER
		minimapBox = new GRect(0, 0, HEADER_WIDTH, HEADER_HEIGHT);
		
		infoBox = new GRect(HEADER_WIDTH,0,HEADER_WIDTH,HEADER_HEIGHT);
		infoText = new GParagraph(
				"Level: "+level+"\nMelee Damage: "+mDamage+"\nRanged Damage: "+rDamage+"\nMove Speed: "+moveSpeed,0,0);
		
		infoText.setFont("Arial-24");
		infoText.move(infoBox.getX()+(infoBox.getWidth()-infoText.getWidth())/2, (infoBox.getY()+infoText.getHeight())/2);
		
		healthBox = new GRect(HEADER_WIDTH*2,0,HEADER_WIDTH,HEADER_HEIGHT);
		
		//FIELD
		
		//OTHER
		btnDie = new GButton("DIE", (MainApplication.WINDOW_WIDTH - BUTTON_WIDTH) / 2, (MainApplication.WINDOW_HEIGHT - BUTTON_HEIGHT) / 2, BUTTON_WIDTH, BUTTON_HEIGHT);
		
		//HEALTH
		
		//Timer
		Timer t = new Timer(DELAY_MS, this);
		t.start();
	}

	private void changeHealth(boolean up) {
		if (up) {
			curHealth++;
		}
		else {
			curHealth--;
		}
		program.remove(healthLabel);
		drawHealth(curHealth);
	}
	
	private void initHealth() {
		curHealth = startHealth;
		drawHealth(curHealth);
	}

	private void drawHealth(int health) {
		healthLabel = new GLabel("Health: " + health, HEADER_WIDTH*2.25, HEADER_HEIGHT/1.9);
		healthLabel.setFont("Arial-48");
		program.add(healthLabel);
	}

	@Override
	public void showContents() {//split showContents into showHeader and showField for clarity
		startHealth = Integer.parseInt(PlayerData.getMap().get("HPUpgrades")) + 3;
		showHeader(); //Top bar
		showField(); //Game field
//		program.add(btnDie);//Testing death screen, remove when things are added
		initHealth();
	}

	@Override
	public void hideContents() {
		removeHeader();
		removeField();
//		program.remove(btnDie);//Testing death screen, remove when things are added
		program.remove(healthLabel);
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
		program.remove(infoText);
		program.remove(healthBox);
	}
	
	public void showField() {
		for (int i = 0; i < FloorManager.ROOM_ROWS; i++) {
			for (int j = 0; j < FloorManager.ROOM_COLS; j++) {
				program.add(room[i][j]);
			}
		}
	}
	
	public void removeField() {
		for (int i = 0; i < FloorManager.ROOM_ROWS; i++) {
			for (int j = 0; j < FloorManager.ROOM_COLS; j++) {
				program.remove(room[i][j]);
			}
		}
	}
	
	public void createImageList() {
		int rows = FloorManager.ROOM_ROWS;
		int cols = FloorManager.ROOM_COLS;
		String path = FILE_PATH + "RockPath/";
		//TODO Add switch cases for different file paths based on level
		
		room = new GImage[rows][cols];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				room[i][j] = new GImage(path + program.getFloorManager().getFloor().getRoom(i, j).getSpace(i, j).getFilePath(), (j * SPACE_SIZE) + HEADER_HEIGHT, (i * SPACE_SIZE));
			}
		}
	}
	
	public void onDeath() {//Trigger this when player is dead, should add other functions - tally score, etc.
		program.switchToDeath();
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
//		if (obj == btnDie) {
//			onDeath();
//		}
//		else {
			changeHealth(false);
//		}
		if (curHealth == 0) {
			onDeath();
		}
	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}
	
	//Timer loop
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
}
