package fringeoftoday;

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
	
	public Room generateRoom(char layout[][]) {
		Room r = new Room();
		
		for (int i = 0; i < NUM_ROOM_ROWS; i++) {
			for (int j = 0; j < NUM_ROOM_COLS; j++) {
				switch(layout[i][j]) {
					//TODO Add switch cases
				}
			}
		}
		
		return r;
	}
}
