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
import fringeoftoday.floor.Room;
import fringeoftoday.PlayerData;
import fringeoftoday.entities.Player;
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
	public static final String FILE_PATH = "../media/textures/";
	public int numTimes=0; //Timer stuff
	public static final int DELAY_MS = 25;
	public boolean keyPressed = false; //Keyboard input stuff
	public String direction;

	private int level = -1;	//Work on this when we get it in
	private GButton btnDie; //Debug, remove when done
	private GRect minimapBox; //Minimap, left header
	private GRect infoBox; //Center header
	private GParagraph infoText;//Center header content
	private GRect healthBox; //Right header
	private GLabel healthLabel;
	private GImage[][] room;
	private Player player;

	public GamePane(MainApplication app) {
		super();
		program = app;

		//HEADER
		minimapBox = new GRect(0, 0, HEADER_WIDTH, HEADER_HEIGHT);

		infoBox = new GRect(HEADER_WIDTH,0,HEADER_WIDTH,HEADER_HEIGHT);

		healthBox = new GRect(HEADER_WIDTH*2,0,HEADER_WIDTH,HEADER_HEIGHT);

		//FIELD

		//OTHER
		btnDie = new GButton("DIE", (MainApplication.WINDOW_WIDTH - BUTTON_WIDTH) / 2, (MainApplication.WINDOW_HEIGHT - BUTTON_HEIGHT) / 2, BUTTON_WIDTH, BUTTON_HEIGHT);

		//Player
		player = program.getEntityManager().getPlayer();

		//Timer
		Timer t = new Timer(DELAY_MS, this);
		t.start();
	}

	private void infoDrawing() {
		player.setMeleeDamage(1 + Integer.parseInt(PlayerData.getMap().get("MeleeUpgrades")));
		player.setRangedDamage(1 + Integer.parseInt(PlayerData.getMap().get("RangedUpgrades")));
		player.setMoveSpeed(1 + Integer.parseInt(PlayerData.getMap().get("SpeedUpgrades")));

		infoText = new GParagraph(
				"Level: " + level
				+ "\nMelee Damage: " + player.getMeleeDamage()
				+ "\nRanged Damage: " + player.getRangedDamage()
				+ "\nMove Speed: " + player.getMoveSpeed(),0,0);

		infoText.setFont("Arial-24");
		infoText.move(infoBox.getX()+(infoBox.getWidth()-infoText.getWidth())/2, (infoBox.getY()+infoText.getHeight())/2);

		program.add(infoText);
	}

	private void changeHealth(boolean up) {
		if (up) {
			player.setHealth(player.getHealth() + 1);
		}
		else {
			player.setHealth(player.getHealth() - 1);
		}
		healthLabel.setLabel("Health: " + player.getHealth());
	}

	private void initHealth() {
		player.setHealth(player.getMaxHealth());
		drawHealth(player.getHealth());
	}

	private void drawHealth(int health) {
		healthLabel = new GLabel("Health: " + health, HEADER_WIDTH*2.25, HEADER_HEIGHT/1.9);
		healthLabel.setFont("Arial-48");
		program.add(healthLabel);
	}

	@Override
	public void showContents() {//split showContents into showHeader and showField for clarity
		player.setMaxHealth(Integer.parseInt(PlayerData.getMap().get("HPUpgrades")) + 3);
		showHeader(); //Top bar
		createImageList();
		showField(); //Game field
		//		program.add(btnDie);//Testing death screen, remove when things are added
		initHealth();
		infoDrawing();
	}

	@Override
	public void hideContents() {
		removeHeader();
		removeField();
		//		program.remove(btnDie);//Testing death screen, remove when things are added
		program.remove(healthLabel);
		program.remove(infoText);
	}

	public void showHeader() {
		program.add(minimapBox);
		program.add(infoBox);
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
		GImage temp;
		String path = FILE_PATH + "RockPath/";
		//TODO Add switch cases for different file paths based on level

		Room testRoom = program.getFloorManager().getSpawnRoom();
		testRoom.setFilePaths();

		room = new GImage[rows][cols];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				temp = new GImage(
						path + testRoom.getSpace(i, j).getFilePath(),
						(j * FloorManager.SPACE_SIZE), 
						(i * FloorManager.SPACE_SIZE) + HEADER_HEIGHT);
				temp.setSize(FloorManager.SPACE_SIZE, FloorManager.SPACE_SIZE);
				room[i][j] = temp;
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
		if (player.getHealth() == 0) {
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
