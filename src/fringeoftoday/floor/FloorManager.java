package fringeoftoday.floor;

import java.util.*;

/**
 * Class responsible for handling the generation of rooms and floors, 
 * as well as storing the current floor
 * 
 * @author Jacob Shour
 *
 */
public class FloorManager {
	public static final int NUM_ROOM_ROWS = 11;
	public static final int NUM_ROOM_COLS = 17;
	
	private ArrayList<char[][]> floorLayouts;
	private ArrayList<char[][]> roomLayouts;
	private Floor currentFloor;
	
	public Floor getFloor() {
		return currentFloor;
	}
	
	public void addFloorLayout(char layout[][]) {
		floorLayouts.add(layout);
	}
	
	public void addRoomLayout(char layout[][]) {
		roomLayouts.add(layout);
	}
	
	public void generateNewFloor() {
		//TODO Complete function
		return;
	}
	
	/**
	 * Generates a room from a layout array
	 * 
	 * @param layout - The char array that determines the layout of a room
	 * @return - A new room to be inserted into a floor
	 */
	public Room generateRoom(char layout[][]) {
		Room r = new Room();
		
		for (int i = 0; i < NUM_ROOM_ROWS; i++) {
			for (int j = 0; j < NUM_ROOM_COLS; j++) {
				switch(layout[i][j]) {
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
