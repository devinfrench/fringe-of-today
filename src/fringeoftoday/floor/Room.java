package fringeoftoday.floor;

import java.util.*;

/**
 * Class responsible for holding data on a single room
 * 
 * @author Jacob Shour
 *
 */
public class Room {
	public static final int NUM_ROOM_ROWS = 11;
	public static final int NUM_ROOM_COLS = 17;
	
	private Space roomLayout[][];
	private ArrayList<Exit> exits;
	
	public Room() {
		roomLayout = new Space[NUM_ROOM_ROWS][NUM_ROOM_COLS];
		exits = new ArrayList<Exit>();
	}
	
	public Space getSpace(int row, int col) {
		return roomLayout[row][col];
	}
	
	public ArrayList<Exit> getExits() {
		return exits;
	}
	
	public void setSpace(int row, int col, SpaceType type) {
		roomLayout[row][col] = new Space(row, col, type);
	}
	
	public void addExit(Exit e) {
		exits.add(e);
	}
}
