package fringeoftoday.floor;

import fringeoftoday.MainApplication;
import fringeoftoday.graphics.panes.GamePane;

import java.util.*;
import java.lang.Math;

/**
 * Holds all floor templates and the current floor. Generates new floors from
 * the templates stored
 * 
 * @author Jacob Shour
 *
 */
public class FloorManager {
	public static final int ROOM_ROWS = 11;
	public static final int ROOM_COLS = 25;
	public static final int FLOOR_ROWS = 4;
	public static final int FLOOR_COLS = 5;
	public static final int SPACE_SIZE = (MainApplication.WINDOW_HEIGHT - GamePane.HEADER_HEIGHT)/FloorManager.ROOM_ROWS;

	private static Room spawnRoom;
	private static ArrayList<char[][]> floorLayouts = new ArrayList<char[][]>();
	private static ArrayList<Room> roomLayouts = new ArrayList<Room>();
	private static ArrayList<Room> bossRoomLayouts = new ArrayList<Room>();
	private static Floor currentFloor;

	public Floor getFloor() {
		return currentFloor;
	}

	public static void setSpawnRoom(char layout[][]) {
		spawnRoom = generateRoom(layout);
	}

	/**
	 * Adds floor layout to ArrayList of possible floors to use
	 * 
	 * @param layout - Layout to add
	 */
	public static void addFloorLayout(char layout[][]) {
		floorLayouts.add(layout);
	}

	/**
	 * Adds room layout to ArrayList of possible rooms to use
	 * 
	 * @param layout - Layout to add
	 */
	public static void addRoomLayout(char layout[][]) {
		roomLayouts.add(generateRoom(layout));
	}

	/**
	 * Adds boss room layout to ArrayList of possible boss rooms to use
	 * 
	 * @param layout - Layout to add
	 */
	public static void addBossRoomLayout(char layout[][]) {
		bossRoomLayouts.add(generateRoom(layout));
	}

	/**
	 * Generates a new floor that is automatically stored in currentFloor
	 */
	public static void generateNewFloor() {
		int levelCount = 1;
		if (currentFloor != null)
			levelCount = currentFloor.getLevel();
		currentFloor = new Floor(levelCount);

		char floorToGenerate[][] = floorLayouts.get((int) (Math.random() * (floorLayouts.size() - 1)));

		for (int i = 0; i < FLOOR_ROWS; i++) {
			for (int j = 0; j < FLOOR_COLS; j++) {
				switch (floorToGenerate[i][j]) {
				case 'R':
					ArrayList<Room> viableRooms = getViableRooms(i, j, floorToGenerate);
					currentFloor.setRoom(i, j, viableRooms.get((int) (Math.random() * (viableRooms.size() - 1))));
					break;

				case 'S':
					currentFloor.setRoom(i, j, spawnRoom);
					currentFloor.setSpawnRow(i);
					currentFloor.setSpawnCol(j);
					break;

				case 'B':
					currentFloor.setRoom(i, j,
							bossRoomLayouts.get((int) (Math.random() * (bossRoomLayouts.size() - 1))));
					break;

				default:
					break;
				}
			}
		}
	}

	/**
	 * Converts a room layout array into a room object
	 * 
	 * @param layout - The char array that determines the layout of a room
	 * @return A new room generated from the layout
	 */
	public static Room generateRoom(char layout[][]) {
		Room r = new Room();

		for (int i = 0; i < ROOM_ROWS; i++) {
			for (int j = 0; j < ROOM_COLS; j++) {
				switch (layout[i][j]) {
				case '*':
					r.setSpace(i, j, SpaceType.GROUND);
					break;

				case '~':
					r.setSpace(i, j, SpaceType.WATER);
					break;

				case 'B':
					r.setSpace(i, j, SpaceType.BASIC_SPAWN);
					break;

				case 'N':
					r.setSpace(i, j, SpaceType.SNIPER_SPAWN);
					break;

				case 'H':
					r.setSpace(i, j, SpaceType.SHOTGUN_SPAWN);
					break;

				case 'S':
					r.setSpace(i, j, SpaceType.STAIRS);
					break;

				case 'D':
					r.setSpace(i, j, SpaceType.DOOR);
					break;

				case 'W':
					r.setSpace(i, j, SpaceType.WALL);
					break;
					
				case 'X':
					r.setSpace(i, j, SpaceType.BLANK);

				default:
					break;
				}
			}
		}

		if (r.getSpace(0, (int) (Math.ceil(ROOM_COLS / 2))).getType() == SpaceType.DOOR)
			r.addExit(Exit.NORTH);
		if (r.getSpace(ROOM_ROWS - 1, (int) (Math.ceil(ROOM_COLS / 2))).getType() == SpaceType.DOOR)
			r.addExit(Exit.SOUTH);
		for (int i = ROOM_COLS - 1;; i--) {
			if (r.getSpace((int) (Math.ceil(ROOM_ROWS / 2)), i).getType() == SpaceType.DOOR) {
				r.addExit(Exit.EAST);
				break;
			}
			else if (r.getSpace((int) (Math.ceil(ROOM_ROWS / 2)), i).getType() == SpaceType.WALL)
				break;
		}
		for (int i = 0;; i++) {
			if (r.getSpace((int) (Math.ceil(ROOM_ROWS / 2) ), i).getType() == SpaceType.DOOR) {
				r.addExit(Exit.WEST);
				break;
			}
			else if (r.getSpace((int) (Math.ceil(ROOM_ROWS / 2)), i).getType() == SpaceType.WALL)
				break;
		}

		return r;
	}

	/**
	 * Takes a given empty room in a floor and finds the list of possible room
	 * layouts it could use
	 * 
	 * @param row - X coordinate of the room being analyzed
	 * @param col - Y coordinate of the room being analyzed
	 * @param f   - floor that the room is in
	 * @return ArrayList of viable room layouts
	 */
	public static ArrayList<Room> getViableRooms(int row, int col, char f[][]) {
		ArrayList<Exit> exits = new ArrayList<Exit>();
		ArrayList<Room> viableRooms = new ArrayList<Room>();

		if (row < FLOOR_ROWS - 1 && f[row + 1][col] != '*')
			exits.add(Exit.NORTH);
		if (row > 0 && f[row - 1][col] != '*')
			exits.add(Exit.SOUTH);
		if (col < FLOOR_COLS - 1 && f[row][col + 1] != '*')
			exits.add(Exit.EAST);
		if (col > 0 && f[row][col - 1] != '*')
			exits.add(Exit.WEST);

		for (Room r : roomLayouts) {
			if (r.getExits().containsAll(exits))
				viableRooms.add(r);
		}

		return viableRooms;
	}

	/*
	 * prints floors for checking if it imported it right
	 */
	public static void printLayout(char[][] out, int rows, int cols, String type) {
		System.out.println("Here is a " + type + " Layout:");
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				System.out.print(out[row][col]);
			}
			System.out.println();
		}
	}
	
	//For testing
	public Room getSpawnRoom() {
		return currentFloor.getRoom(currentFloor.getSpawnRow(), currentFloor.getSpawnCol());
	}
}
