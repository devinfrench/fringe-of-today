package fringeoftoday.floor;

/**
 * Class responsible for holding data on a single floor
 * 
 * @author Jacob Shour
 *
 */
public class Floor {
	private Room floorLayout[][];
	private int levelCount;
	
	public Floor(int levelCount) {
		this.levelCount = levelCount;
	}
	
	public Room getRoom(int row, int col) {
		return floorLayout[row][col];
	}
	
	public int getLevel() {
		return levelCount;
	}
	
	public void setRoom(int row, int col, Room r) {
		floorLayout[row][col] = r;
	}
}
