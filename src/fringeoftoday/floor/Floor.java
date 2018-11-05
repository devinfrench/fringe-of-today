package fringeoftoday.floor;

/**
 * Holds data on a single floor
 * 
 * @author Jacob Shour
 *
 */
public class Floor {
	public static final int NUM_FLOOR_ROWS = 5;
	public static final int NUM_FLOOR_COLS = 7;

	private Room floorLayout[][];
	private int levelCount;

	public Floor(int levelCount) {
		floorLayout = new Room[NUM_FLOOR_ROWS][NUM_FLOOR_COLS];
		this.levelCount = levelCount;
	}

	public Room getRoom(int row, int col) {
		return floorLayout[row][col];
	}

	public int getLevel() {
		return levelCount;
	}

	/**
	 * Sets the room at the given X/Y to the given room
	 * 
	 * @param row - X coordinate in the floor
	 * @param col - Y coordinate in the floor
	 * @param r   - Room to insert
	 */
	public void setRoom(int row, int col, Room r) {
		floorLayout[row][col] = r;
	}
}
