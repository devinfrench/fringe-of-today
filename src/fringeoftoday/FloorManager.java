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
}
