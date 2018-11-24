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
	private int spawnRow;
	private int spawnCol;
	
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

	public int getSpawnRow() {
		return spawnRow;
	}

	public int getSpawnCol() {
		return spawnCol;
	}

	public void setSpawnRow(int spawnRow) {
		this.spawnRow = spawnRow;
	}

	public void setSpawnCol(int spawnCol) {
		this.spawnCol = spawnCol;
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
