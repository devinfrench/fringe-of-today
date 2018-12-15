package fringeoftoday.graphics.panes;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GLine;
import acm.graphics.GObject;
import acm.graphics.GRect;
import fringeoftoday.MainApplication;
import fringeoftoday.PlayerData;
import fringeoftoday.MainApplication.LayoutType;
import fringeoftoday.entities.Enemy;
import fringeoftoday.entities.ShotgunEnemy;
import fringeoftoday.entities.SniperEnemy;
import fringeoftoday.entities.StandardEnemy;
import fringeoftoday.floor.*;
import fringeoftoday.graphics.GButtonMD;

public class LevelMaker extends GraphicsPane {

	private static final String NEW_ROOM_FILE = "../media/layouts/levelMaker.txt";

	public static final int TILESET_OPTIONS = 5;

	public static final int HEADER_WIDTH = MainApplication.WINDOW_WIDTH / 3;
	public static final int HEADER_HEIGHT = 196;
	public static final String FILE_PATH = "../media/textures/";
	public static final String MAXED_IMAGE = "_nw_n_ne_w_e_sw_s_se";
	public static final String ENEMY_ENDING = "_standing_south";
	public static final String FILE_EXTENSION = ".png";
	public static final String ENEMY_PATH = "../media/sprites/";
	private MainApplication program;
	private Room room;
	private GButtonMD btnBack;
	private GButtonMD btnSave;
	private GLabel saveInfoLabel;
	private GRect backingColor;
	private GRect picked;
	private String selected = "";
	private int choice = 1;
	private ArrayList<GObject> uiElements = new ArrayList<GObject>();

	public LevelMaker(MainApplication app) {
		this.program = app;
		room = program.getFloorManager().getDefaultRoom();
		MainApplication.importLayoutsByType(LayoutType.DEFAULT);
		try {
			choice = Integer.parseInt(PlayerData.getMap().get("Choice"));
		} catch (NumberFormatException e) {
			PlayerData.getMap().put("Choice", "1");
			choice = 1;
		}
		picked = new GRect(FloorManager.SPACE_SIZE + 4, FloorManager.SPACE_SIZE + 4);
		picked.setColor(Color.RED);
		picked.setVisible(false);
	}

	@Override
	public void showContents() {
		room = program.getFloorManager().getDefaultRoom();
		initObjects();
		createImageList();
		showField();
		for (GObject o : uiElements) {
			program.add(o);
		}
		program.add(picked);
//		printArr(room.getCharArray());
	}

	// Checking if I get the array right
	private void printArr(char[][] charArray) {
		for (int i = 0; i < FloorManager.ROOM_ROWS; i++) {
			for (int j = 0; j < FloorManager.ROOM_COLS; j++) {
				System.out.print(charArray[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}

	private char[][] changeArr(char[][] charArray, int x, int y) {
		char[][] changed = new char[FloorManager.ROOM_ROWS][FloorManager.ROOM_COLS];
		for (int i = 0; i < FloorManager.ROOM_ROWS; i++) {
			for (int j = 0; j < FloorManager.ROOM_COLS; j++) {
				changed[i][j] = charArray[i][j];
				if (i == x && j == y && changed[i][j] != 'D') {
					changed[i][j] = getChar();
				}
			}
		}
		return changed;
	}

	@Override
	public void hideContents() {
		removeField();
		for (GObject o : uiElements) {
			program.remove(o);
		}
		picked.setVisible(false);
		program.remove(picked);
	}

	private void initObjects() {
		// Black backing color
		backingColor = new GRect(0, 0, MainApplication.WINDOW_WIDTH, HEADER_HEIGHT);
		backingColor.setFillColor(Color.BLACK);
		backingColor.setFilled(true);
		uiElements.add(backingColor);
		backingColor.sendToBack();

		// Back button
		btnBack = new GButtonMD("Back", 1, 1, MainApplication.BUTTON_WIDTH,
				(int) (MainApplication.BUTTON_HEIGHT * .75));
		uiElements.add(btnBack);

		// Save Button
		btnSave = new GButtonMD("Save Room", 1, 77, MainApplication.BUTTON_WIDTH,
				(int) (MainApplication.BUTTON_HEIGHT * .75));
		uiElements.add(btnSave);
		
		// Save Info Label
		saveInfoLabel = new GLabel("Saves to " + NEW_ROOM_FILE + ", will overwrite!", 1, 170);
		saveInfoLabel.setColor(Color.RED);
		uiElements.add(saveInfoLabel);

		// Style buttons
		int size = HEADER_HEIGHT / TILESET_OPTIONS;
		for (int i = 0; i <= TILESET_OPTIONS; i++) {
			GButtonMD btn = new GButtonMD(Integer.toString(i), MainApplication.WINDOW_WIDTH - size, 0 + size * (i - 1),
					size, size);
			uiElements.add(btn);
		}

		// Showing the selected item
		if (picked.getX() != 0) {
			picked.setVisible(true);
		}

		// Tile options
		String path = variablePath(FILE_PATH, choice);
		// Ground
		GImage ground = new GImage(path + "ground" + MAXED_IMAGE + FILE_EXTENSION, 6 * FloorManager.SPACE_SIZE,
				1.5 * FloorManager.SPACE_SIZE);
		ground.setSize(FloorManager.SPACE_SIZE, FloorManager.SPACE_SIZE);
		uiElements.add(ground);
		// Water
		GImage water = new GImage(path + "water" + MAXED_IMAGE + FILE_EXTENSION, 8 * FloorManager.SPACE_SIZE,
				1.5 * FloorManager.SPACE_SIZE);
		water.setSize(FloorManager.SPACE_SIZE, FloorManager.SPACE_SIZE);
		uiElements.add(water);
		// Wall
		GImage wall = new GImage(path + "wall" + "_n_w_e_s" + FILE_EXTENSION, 10 * FloorManager.SPACE_SIZE,
				1.5 * FloorManager.SPACE_SIZE);
		wall.setSize(FloorManager.SPACE_SIZE, FloorManager.SPACE_SIZE);
		uiElements.add(wall);
		// Blank
		GImage blank = new GImage(path + "blank" + FILE_EXTENSION, 12 * FloorManager.SPACE_SIZE,
				1.5 * FloorManager.SPACE_SIZE);
		blank.setSize(FloorManager.SPACE_SIZE, FloorManager.SPACE_SIZE);
		uiElements.add(blank);
		// Pikachu
		GImage pikachu = new GImage(ENEMY_PATH + "pikachu/pikachu" + ENEMY_ENDING + FILE_EXTENSION,
				14 * FloorManager.SPACE_SIZE, 1.5 * FloorManager.SPACE_SIZE);
//        pikachu.setSize(FloorManager.SPACE_SIZE, FloorManager.SPACE_SIZE);
		uiElements.add(pikachu);
		// Squirtle
		GImage squirtle = new GImage(ENEMY_PATH + "squirtle/squirtle" + ENEMY_ENDING + FILE_EXTENSION,
				16 * FloorManager.SPACE_SIZE, 1.5 * FloorManager.SPACE_SIZE);
//        squirtle.setSize(FloorManager.SPACE_SIZE, FloorManager.SPACE_SIZE);
		uiElements.add(squirtle);
		// Charmander
		GImage charmander = new GImage(ENEMY_PATH + "charmander/charmander" + ENEMY_ENDING + FILE_EXTENSION,
				18 * FloorManager.SPACE_SIZE, 1.5 * FloorManager.SPACE_SIZE);
//        charmander.setSize(FloorManager.SPACE_SIZE, FloorManager.SPACE_SIZE);
		uiElements.add(charmander);

	}

	/**
	 * Sets GOBjects stored in the current room's spaces to proper images
	 *
	 * @author Jacob Shour
	 */
	public void createImageList() {
		int rows = FloorManager.ROOM_ROWS;
		int cols = FloorManager.ROOM_COLS;
		GImage temp;

		String path = variablePath(FILE_PATH, choice);

		room.setFilePaths();

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				Space space = room.getSpace(i, j);
				temp = new GImage(path + space.getFilePath(), (j * FloorManager.SPACE_SIZE),
						(i * FloorManager.SPACE_SIZE) + HEADER_HEIGHT);
				temp.setSize(FloorManager.SPACE_SIZE, FloorManager.SPACE_SIZE);
				space.setGObject(temp);
			}
		}
	}

	/**
	 * Selects a tileset for an image based on floor level
	 *
	 * @param path Image path to build off of
	 * @return Image path, now with tileset
	 * @author Jacob Shour
	 */
	private String variablePath(String path, int choice) {
		switch (choice) {
		case (1):
			path = path + "RockPath/";
			break;
		case (2):
			path = path + "SealedRuin/";
			break;
		case (3):
			path = path + "SteamCave/";
			break;
		case (4):
			path = path + "ZeroIsleEast/";
			break;
		case (5):
			path = path + "DarkCrater/";
			break;
		}
		return path;
	}

	/**
	 * Renders the current room
	 */
	public void showField() {
		for (int i = 0; i < FloorManager.ROOM_ROWS; i++) {
			for (int j = 0; j < FloorManager.ROOM_COLS; j++) {
				GObject obj = room.getSpace(i, j).getGObject();
				program.add(obj);
				// Make red lines across the screen for separation of items
				GLine lineY = new GLine(obj.getX(), obj.getY(), obj.getX(), obj.getY() + obj.getHeight());
				GLine lineX = new GLine(obj.getX(), obj.getY(), obj.getX() + obj.getWidth(), obj.getY());
				lineX.setColor(Color.red);
				lineY.setColor(Color.red);
				uiElements.add(lineX);
				uiElements.add(lineY);
			}
		}
		showEnemies();
	}

	/**
	 * Removes the current room
	 */
	public void removeField() {
		for (int i = 0; i < FloorManager.ROOM_ROWS; i++) {
			for (int j = 0; j < FloorManager.ROOM_COLS; j++) {
				program.remove(room.getSpace(i, j).getGObject());
			}
		}
		removeEnemies();
	}

	/**
	 * Spawns in and renders enemies
	 *
	 * @author Devin French (rendering)
	 */
	public void showEnemies() {
		List<Enemy> enemies = program.getEntityManager().getEnemies();
		for (int i = 0; i < FloorManager.ROOM_ROWS; i++) {
			for (int j = 0; j < FloorManager.ROOM_COLS; j++) {
				Space space = room.getSpace(i, j);
				Enemy enemy = null;
				switch (space.getType()) {
				case BASIC_SPAWN:
					enemy = new StandardEnemy();
					enemy.setSpriteSet("charmander");
					break;
				case SHOTGUN_SPAWN:
					enemy = new ShotgunEnemy();
					enemy.setSpriteSet("pikachu");
					break;
				case SNIPER_SPAWN:
					enemy = new SniperEnemy();
					enemy.setSpriteSet("squirtle");
					break;
				default:
					break;
				}
				if (enemy != null) {
					double x = (j * FloorManager.SPACE_SIZE);
					double y = (i * FloorManager.SPACE_SIZE) + HEADER_HEIGHT;
					enemy.getGObject().setLocation(x, y);
					program.add(enemy.getGObject());
					enemies.add(enemy);
				}
			}
		}
	}

	/**
	 * Removes all active enemies
	 */
	public void removeEnemies() {
		List<Enemy> enemies = program.getEntityManager().getEnemies();
		for (Enemy enemy : enemies) {
			program.remove(enemy.getGObject());
		}
		enemies.clear();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == btnBack) {
			program.switchToMenu();
		} else if (obj == btnSave) {
			makeFile();
		} else if (obj instanceof GButtonMD) {
			choice = (Integer.parseInt(((GButtonMD) obj).getLabel().getLabel()));
			PlayerData.updateMap("Choice", choice);
			resetRoom();
		} else if (obj instanceof GImage && obj.getY() < HEADER_HEIGHT) {
			obj.sendToFront();
			setSelected((int) obj.getX());
			picked.setLocation(obj.getX() - 2, obj.getY() - 2);
			picked.setVisible(true);
		} else if (obj instanceof GImage) {
			Space space = getSpaceFromXY(e.getX(), e.getY());
			int x = space.getNumRow();
			int y = space.getNumCol();
			FloorManager.setDefaultRoom(changeArr(room.getCharArray(), x, y));
			resetRoom();
		}
	}

	private void makeFile() {
		File playerF = new File(NEW_ROOM_FILE);
		if (!playerF.exists()) {
			try {
				// Make new file if one doesn't exist
				playerF.createNewFile();
			} catch (IOException e1) {
				System.out.println("Couldn't create file");
				e1.printStackTrace();
			}
		}
		writeFile();
	}

	private void writeFile() {
        try {
            FileWriter fw = new FileWriter(NEW_ROOM_FILE);
    		for (int i = 0; i < FloorManager.ROOM_ROWS; i++) {
    			for (int j = 0; j < FloorManager.ROOM_COLS; j++) {
    				fw.write(room.getCharArray()[i][j] + " ");
    			}
    			fw.write(System.getProperty("line.separator"));
    		}
            fw.close();
        } catch (IOException e2) {
            System.out.println("No write");
            e2.printStackTrace();
        }		
	}

	private void resetRoom() {
		hideContents();
		showContents();
	}

	private char getChar() {
		switch (selected) {
		case ("Ground"):
			return '*';
		case ("Water"):
			return '~';
		case ("Wall"):
			return 'W';
		case ("Blank"):
			return 'X';
		case ("Pikachu"):
			return 'H';
		case ("Squirtle"):
			return 'N';
		case ("Charmander"):
			return 'B';
		case (""):
			return '_';
		default:
			return '_';
		}
	}

	private void setSelected(int x) {
		switch (x) {
		case (384):
			selected = ("Ground");
			break;
		case (512):
			selected = ("Water");
			break;
		case (640):
			selected = ("Wall");
			break;
		case (768):
			selected = ("Blank");
			break;
		case (896):
			selected = ("Pikachu");
			break;
		case (1024):
			selected = ("Squirtle");
			break;
		case (1152):
			selected = ("Charmander");
			break;
		default:
			selected = ("");
			break;
		}
	}

	private Space getSpaceFromXY(int x, int y) {
		for (int i = 0; i < FloorManager.ROOM_ROWS; i++) {
			for (int j = 0; j < FloorManager.ROOM_COLS; j++) {
				Space space = room.getSpace(i, j);
				if (space.getGObject().contains(x, y)) {
					return space;
				}
			}
		}
		return null;
	}
}
