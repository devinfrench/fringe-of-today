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
import fringeoftoday.graphics.panes.DeathPane;
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
	private DeathPane deathPane;
	private int count;
	private FloorManager floorManager;

	public static void main(String[] args) {
		importFloors();
	}

	private enum RoomType {
		STANDARD, BOSS, SPAWN
	};

	public void init() {
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		PlayerData.playerFileSetup();
		floorManager = new FloorManager();
		// importAllLayouts();
	}

	public void run() {
		tutorial = new TutorialPane(this);
		shopPane = new ShopPane(this);
		menu = new MenuPane(this);
		game = new GamePane(this);
		deathPane = new DeathPane(this);
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
		PlayerData.updateMap("Tutorial", Integer.parseInt(PlayerData.getMap().get("Tutorial")) + 100);
		switchToScreen(tutorial);
	}

	public void switchToGame() {
		if (Integer.parseInt(PlayerData.getMap().get("Tutorial")) == 0) {
			PlayerData.updateMap("Tutorial", 1);
			switchToTutorial();
		} else {
			switchToScreen(game);
		}

	}

	public void switchToDeath() {
		switchToScreen(deathPane);
	}

	public void exitProgram() {
		PlayerData.writeFile();
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

	public static void importFloors() {
		String text = null;
		File file = new File("./media/layouts/floors.txt");
		try {

			char textArr[][] = new char[FloorManager.FLOOR_ROWS][FloorManager.FLOOR_COLS];

			Scanner sc = new Scanner(file);
			while (sc.hasNextLine()) {
				for (int row = 0; row < FloorManager.FLOOR_ROWS; row++) {
					text = sc.nextLine();
					String[] textChars = text.split(" ", FloorManager.FLOOR_COLS);
					for (int col = 0; col < FloorManager.FLOOR_COLS; col++) {
						// System.out.print(textChars[col]);
						textArr[row][col] = textChars[col].charAt(0);
					}
					// System.out.println();
				}
				FloorManager.printLayout(textArr, FloorManager.FLOOR_ROWS, FloorManager.FLOOR_COLS, "Floor");
				/*
				 * This below doesnt work, im not sure why, but the array looks right.  Ill need to look at Shour's code some or maybe he can help
				 * --Alex Reynen
				 */
				//FloorManager.addFloorLayout(textArr);
				if (sc.hasNextLine()) {sc.nextLine();}
			}
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void importRooms(RoomType type) {
		switch (type) {
		case STANDARD:

			/*
			 * TODO Grab all layouts from rooms_standard.txt as 2D char arrays and call
			 * floorManager.addRoomLayout(layout) on each one
			 */
			break;

		case BOSS:
			/*
			 * TODO Grab all layouts from rooms_boss.txt as 2D char arrays and call
			 * floorManager.addBossRoomLayout(layout) on each one
			 */
			break;

		case SPAWN:
			/*
			 * TODO Grab the layout from rooms_spawn.txt as 2D char arrays and call
			 * floorManager.setSpawnRoom(layout) on it
			 */
			break;
		}
	}
}
