package fringeoftoday.floor;

/**
 * Holds data on a single floor
 * 
 * @author Jacob Shour
 *
 */
public class Floor {
	public static final int FLOOR_ROWS = FloorManager.FLOOR_ROWS;
	public static final int FLOOR_COLS = FloorManager.FLOOR_COLS;

	private Room floorLayout[][];
	private int levelCount;
	private int spawnRow;
	private int spawnCol;

	public Floor(int levelCount) {
		floorLayout = new Room[FLOOR_ROWS][FLOOR_COLS];
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

	/**
	 * Sets entire floor's rooms to be uncleared. Use immediately before generating
	 * a new floor
	 */
	public void resetCleared() {
		for (int i = 0; i < FLOOR_ROWS; i++) {
			for (int j = 0; j < FLOOR_COLS; j++) {
				if (floorLayout[i][j] != null)
					floorLayout[i][j].setCleared(false);
			}
		}
	}
}
