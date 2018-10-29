package fringeoftoday;

/**
 * Class responsible for data stored in each individual space / tile of a room
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
	 * @param type - The type of space; controls behavior
	 */
	public Space(int numRow, int numCol, SpaceType type) {
		super();
		this.numRow = numRow;
		this.numCol = numCol;
		this.type = type;
	}
	
}
