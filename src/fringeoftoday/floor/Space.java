package fringeoftoday.floor;

/**
 * Holds data on a single space of a room
 * 
 * @author Jacob Shour
 * 
 */
public class Space {
	private int numRow;
	private int numCol;
	private SpaceType type;

	/**
	 * @param numRow - X position in room
	 * @param numCol - Y position in room
	 * @param type   - The type of space; controls behavior
	 */
	public Space(int numRow, int numCol, SpaceType type) {
		this.numRow = numRow;
		this.numCol = numCol;
		this.type = type;
	}

	public int getNumRow() {
		return numRow;
	}

	public int getNumCol() {
		return numCol;
	}

	public SpaceType getType() {
		return type;
	}

	public void setNumRow(int numRow) {
		this.numRow = numRow;
	}

	public void setNumCol(int numCol) {
		this.numCol = numCol;
	}

	public void setType(SpaceType type) {
		this.type = type;
	}
}
