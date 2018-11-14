package fringeoftoday.floor;

import java.util.*;

/**
 * Holds data on a single room
 * 
 * @author Jacob Shour
 *
 */
public class Room {
	public static final int ROOM_ROWS = FloorManager.ROOM_ROWS;
	public static final int ROOM_COLS = FloorManager.ROOM_COLS;

	private Space roomLayout[][];
	private ArrayList<Exit> exits;

	public Room() {
		roomLayout = new Space[ROOM_ROWS][ROOM_COLS];
		exits = new ArrayList<Exit>();
	}

	public Space getSpace(int row, int col) {
		return roomLayout[row][col];
	}

	public ArrayList<Exit> getExits() {
		return exits;
	}

	/**
	 * Sets the space at the given X/Y to the given space
	 * 
	 * @param row - X coordinate in the room
	 * @param col - Y coordinate in the room
	 * @param r   - Space to insert
	 */
	public void setSpace(int row, int col, SpaceType type) {
		roomLayout[row][col] = new Space(row, col, type);
	}

	/**
	 * Adds the given exit to the list of exits the room has
	 * 
	 * @param e - Exit to add
	 */
	public void addExit(Exit e) {
		exits.add(e);
	}
	
	public void setFilePaths() {
		String path;
		
		for (int i = 0; i < ROOM_ROWS; i++) {
			for (int j = 0; j < ROOM_COLS; j++) {
				switch (roomLayout[i][j].getType()) {
				case WATER:
					path = pathGenerator("water", i, j, SpaceType.WATER);
					break;
				
				case WALL:
					path = pathGenerator("wall", i, j, SpaceType.WALL);
					break;
					
				case DOOR:
					path = "wall.png";
					break;
					
				case BLANK:
					path = "blank";
					break;
					
				default:
					path = "ground";
					
					//Northwest
					if (i > 0 && j > 0 && roomLayout[i-1][j-1].getType() != SpaceType.WATER && roomLayout[i-1][j-1].getType() != SpaceType.WALL)
						path = path + "_nw";
					//North
					if (i > 0 && roomLayout[i-1][j].getType() != SpaceType.WATER && roomLayout[i-1][j].getType() != SpaceType.WALL)
						path = path + "_n";
					//Northeast
					if (i > 0 && j < ROOM_COLS-1 && roomLayout[i-1][j+1].getType() != SpaceType.WATER && roomLayout[i-1][j+1].getType() != SpaceType.WALL)
						path = path + "_ne";
					//West
					if (j > 0 && roomLayout[i][j-1].getType() != SpaceType.WATER && roomLayout[i][j-1].getType() != SpaceType.WALL)
						path = path + "_w";
					//East
					if (j < ROOM_COLS-1 && roomLayout[i][j+1].getType() != SpaceType.WATER && roomLayout[i][j+1].getType() != SpaceType.WALL)
						path = path + "_e";
					//Southwest
					if (i < ROOM_ROWS-1 && j > 0 && roomLayout[i+1][j-1].getType() != SpaceType.WATER && roomLayout[i+1][j-1].getType() != SpaceType.WALL)
						path = path + "_sw";
					//South
					if (i < ROOM_ROWS-1 && roomLayout[i+1][j].getType() != SpaceType.WATER && roomLayout[i+1][j].getType() != SpaceType.WALL)
						path = path + "_s";
					//Southeast
					if (i < ROOM_ROWS-1 && j < ROOM_COLS-1 && roomLayout[i+1][j+1].getType() != SpaceType.WATER && roomLayout[i+1][j+1].getType() != SpaceType.WALL)
						path = path + "_se";
					
					path = path + ".png";
					break;
				}
				
				roomLayout[i][j].setFilePath(path);
			}
		}
	}
	
	private String pathGenerator(String path, int i, int j, SpaceType type) {
		//Northwest
		if (i > 0 && j > 0 && roomLayout[i-1][j-1].getType() == type)
			path = path + "_nw";
		//North
		if (i > 0 && roomLayout[i-1][j].getType() == type)
			path = path + "_n";
		//Northeast
		if (i > 0 && j < ROOM_COLS-1 && roomLayout[i-1][j+1].getType() == type)
			path = path + "_ne";
		//West
		if (j > 0 && roomLayout[i][j-1].getType() == type)
			path = path + "_w";
		//East
		if (j < ROOM_COLS-1 && roomLayout[i][j+1].getType() == type)
			path = path + "_e";
		//Southwest
		if (i < ROOM_ROWS-1 && j > 0 && roomLayout[i+1][j-1].getType() == type)
			path = path + "_sw";
		//South
		if (i < ROOM_ROWS-1 && roomLayout[i+1][j].getType() == type)
			path = path + "_s";
		//Southeast
		if (i < ROOM_ROWS-1 && j < ROOM_COLS-1 && roomLayout[i+1][j+1].getType() == type)
			path = path + "_se";
		
		return path + ".png";
	}
}
