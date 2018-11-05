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
import fringeoftoday.graphics.panes.GamePane;
import fringeoftoday.floor.FloorManager;

public class MainApplication extends GraphicsApplication {
	public static final int WINDOW_WIDTH = 1600;
	public static final int WINDOW_HEIGHT = 900;
	public static final String MUSIC_FOLDER = "sounds";
	private final String[] SOUND_FILES = { "r2d2.mp3", "somethinlikethis.mp3" };
	
	private ShopPane shopPane;
	private MenuPane menu;
	private TutorialPane tutorial;
	private GamePane game;
	private int count;
	private FloorManager floorManager;
	private enum RoomType { STANDARD, BOSS, SPAWN };

	public void init() {
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		PlayerData.playerFileSetup();
		floorManager = new FloorManager();
		//importAllLayouts();
	}

	public void run() {
		tutorial = new TutorialPane(this);
		shopPane = new ShopPane(this);
		menu = new MenuPane(this);
		game = new GamePane(this);
		switchToMenu();
	}

	public void switchToMenu() {
		// playRandomSound();
		switchToScreen(menu);
	}

	public void switchToShop() {
		// playRandomSound();
		switchToScreen(shopPane);
	}

	public void switchToTutorial() {
		switchToScreen(tutorial);
	}

	public void switchToGame() {
		if (Integer.parseInt(PlayerData.getMap().get("Tutorial")) == 0) {
			switchToTutorial();
		} else {
			switchToScreen(game);
		}

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
	
	public void importAllLayouts() {
		importFloors();
		importRooms(RoomType.STANDARD);
		importRooms(RoomType.BOSS);
		importRooms(RoomType.SPAWN);
	}
	
	public void importFloors() {
		//TODO Finish function
	}
	
	public void importRooms(RoomType type) {
		//TODO Finish function
	}
}
