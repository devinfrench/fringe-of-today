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
		importAllLayouts();
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

	public static void importAllLayouts() {
		importFloors();
		System.out.println();
		importRooms(RoomType.STANDARD);
		System.out.println();
		importRooms(RoomType.BOSS);
		System.out.println();
		importRooms(RoomType.SPAWN);
		System.out.println();
		
	}

	public static void importer(String fileLocation, int numRows, int numCols) {
		String text = null;
		File file = new File("./media/layouts/" + fileLocation + ".txt");
		char textArr[][] = new char[numRows][numCols];

		Scanner sc;
		try {
			sc = new Scanner(file);
			while (sc.hasNextLine()) {
				for (int row = 0; row < numRows; row++) {
					text = sc.nextLine();
					String[] textChars = text.split(" ", numCols);
					for (int col = 0; col < numCols; col++) {
						// System.out.print(textChars[col]);
						textArr[row][col] = textChars[col].charAt(0);
					}
					// System.out.println();
				}
				//FloorManager.printLayout(textArr, numRows, numCols, fileLocation);
				FloorManager.addFloorType(fileLocation, textArr);
				if (sc.hasNextLine()) {
					sc.nextLine();
				}
			}
			sc.close();
		} catch (FileNotFoundException e) {
			System.out.println("Error doing this");
			e.printStackTrace();
		}
	}

	public static void importFloors() {
		importer("floors", FloorManager.FLOOR_ROWS, FloorManager.FLOOR_COLS);
	}

	public static void importRooms(RoomType type) {
		switch (type) {
		case STANDARD:
			importer("rooms_standard", FloorManager.ROOM_ROWS, FloorManager.ROOM_COLS);
			/*
			 * TODO Grab all layouts from rooms_standard.txt as 2D char arrays and call
			 * floorManager.addRoomLayout(layout) on each one
			 */
			break;

		case BOSS:
			importer("rooms_boss", FloorManager.ROOM_ROWS, FloorManager.ROOM_COLS);
			/*
			 * TODO Grab all layouts from rooms_boss.txt as 2D char arrays and call
			 * floorManager.addBossRoomLayout(layout) on each one
			 */
			break;

		case SPAWN:
			importer("rooms_spawn", FloorManager.ROOM_ROWS, FloorManager.ROOM_COLS);
			/*
			 * TODO Grab the layout from rooms_spawn.txt as 2D char arrays and call
			 * floorManager.setSpawnRoom(layout) on it
			 */
			break;
		}
	}
}
