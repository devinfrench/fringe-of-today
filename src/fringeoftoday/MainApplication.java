package fringeoftoday;


import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.*; 

import fringeoftoday.audio.AudioPlayer;
import fringeoftoday.graphics.GraphicsApplication;
import fringeoftoday.graphics.panes.MenuPane;
import fringeoftoday.graphics.panes.ShopPane;
import fringeoftoday.graphics.panes.TutorialPane;

public class MainApplication extends GraphicsApplication {
	public static final int WINDOW_WIDTH = 1600;
	public static final int WINDOW_HEIGHT = 900;
	public static final String MUSIC_FOLDER = "sounds";
	private static final String[] SOUND_FILES = { "r2d2.mp3", "somethinlikethis.mp3" };

	private ShopPane shopPane;
	private MenuPane menu;
	private TutorialPane tutorial;
	private int count;

	public void init() {
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
	}

	public void run() {
		tutorial = new TutorialPane(this);
		shopPane = new ShopPane(this);
		menu = new MenuPane(this);
		switchToMenu();
		playerFileSetup();
	}

	public void switchToMenu() {
		//playRandomSound();
		count++;
		switchToScreen(menu);
	}

	public void switchToShop() {
		//playRandomSound();
		switchToScreen(shopPane);
	}
	
	public void switchToTutorial() {
		switchToScreen(tutorial);
	}
	
	public void exitProgram() {
		System.exit(0); 
	}

	private void playRandomSound() {
		AudioPlayer audio = AudioPlayer.getInstance();
		audio.playSound(MUSIC_FOLDER, SOUND_FILES[count % SOUND_FILES.length]);
	}

	public static int getWindowWidth() {
		return WINDOW_WIDTH;
	}

	public static int getWindowHeight() {
		return WINDOW_HEIGHT;
	}
	

	private void playerFileSetup() {
		File playerF = new File("player.txt");
		if (!playerF.exists()) {
			try {
				// Make new file if one doesn't exist
				playerF.createNewFile();
			} catch (IOException e1) {
				System.out.println("Couldn't create file");
				e1.printStackTrace();
			}

			try {
				// Fill it in with default values of 0
				FileWriter fw = new FileWriter("player.txt");
				fw.write("Coin:0" + System.getProperty("line.separator"));
				fw.write("HP up:0" + System.getProperty("line.separator"));
				fw.write("Melee up:0" + System.getProperty("line.separator"));
				fw.write("Ranged up:0" + System.getProperty("line.separator"));
				fw.write("Speed up:0" + System.getProperty("line.separator"));
				fw.write("Previous:0" + System.getProperty("line.separator"));
				fw.write("GOAT:0");
				fw.close();
			} catch (IOException e2) {
				System.out.println("No write");
				e2.printStackTrace();
			}
		}
		String text = null;
		try {
			Scanner sc = new Scanner(new File("player.txt"));
			text = sc.useDelimiter("\\A").next();
			sc.close();
		} catch (FileNotFoundException e) {
			System.out.println("Can't find the file");
			e.printStackTrace();
		}
		//System.out.println(text);
	}
}
