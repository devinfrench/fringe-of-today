package fringeoftoday.floor;

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
	public static final int ROOM_COLS = 17;
	public static final int FLOOR_ROWS = 4;
	public static final int FLOOR_COLS = 5;

	private Room spawnRoom;
	private ArrayList<char[][]> floorLayouts;
	private ArrayList<char[][]> roomLayouts;
	private ArrayList<char[][]> bossRoomLayouts;
	private Floor currentFloor;

	public FloorManager() {
		floorLayouts = new ArrayList<char[][]>();
		roomLayouts = new ArrayList<char[][]>();
		bossRoomLayouts = new ArrayList<char[][]>();
	}

	public Floor getFloor() {
		return currentFloor;
	}

	public void setSpawnRoom(char layout[][]) {
		spawnRoom = generateRoom(layout);
	}

	public void addFloorLayout(char layout[][]) {
		floorLayouts.add(layout);
	}

	public void addRoomLayout(char layout[][]) {
		roomLayouts.add(layout);
	}
	
	public void addBossRoomLayout(char layout[][]) {
		bossRoomLayouts.add(layout);
	}

	/**
	 * Generates a new floor that is automatically stored in currentFloor
	 */
	public void generateNewFloor() {
		int levelCount = 1;
		if (currentFloor != null)
			levelCount = currentFloor.getLevel();
		currentFloor = new Floor(levelCount);

		char floorToGenerate[][] = floorLayouts.get((int) (Math.random() * (floorLayouts.size() - 1)));

		for (int i = 0; i < FLOOR_ROWS; i++) {
			for (int j = 0; j < FLOOR_COLS; j++) {
				switch (floorToGenerate[i][j]) {
				case 'R':
					ArrayList<char[][]> viableRooms = roomLayouts;
					// TODO: Create viableRooms()
					// ArrayList<char[][]> viableRooms = getViableRooms(i, j, floorToGenerate);
					currentFloor.setRoom(i, j,
							generateRoom(viableRooms.get((int) (Math.random() * (viableRooms.size() - 1)))));
					break;

				case 'S':
					currentFloor.setRoom(i, j, spawnRoom);
					break;

				case 'B':
					// TODO: Create bossRooms arrayList
					currentFloor.setRoom(i, j,
							generateRoom(bossRoomLayouts.get((int) (Math.random() * (bossRoomLayouts.size() - 1)))));
					break;

				default:
					break;
				}
			}
		}
	}

	/**
	 * Generates a room from a layout array
	 * 
	 * @param layout - The char array that determines the layout of a room
	 * @return - A new room to be inserted into a floor
	 */
	public Room generateRoom(char layout[][]) {
		Room r = new Room();

		for (int i = 0; i < ROOM_ROWS; i++) {
			for (int j = 0; j < ROOM_COLS; j++) {
				switch (layout[i][j]) {
				case '*':
					r.setSpace(i, j, SpaceType.STANDARD);
					break;

				case 'I':
					r.setSpace(i, j, SpaceType.IMPASSIBLE);
					break;

				case 'C':
					r.setSpace(i, j, SpaceType.COLLIDABLE);
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

				default:
					break;
				}
			}
		}

		return r;
	}
}
