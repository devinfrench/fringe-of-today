package fringeoftoday;


/**
 * Class responsible for holding data on a single room
 * 
 * @author Jacob Shour
 *
 */
public class Room {
	private Space roomLayout[][];
	private Exit exits[];
	
	public Space getSpace(int row, int col) {
		return roomLayout[row][col];
	}
	
	public Exit[] getExits() {
		return exits;
	}
	
	public void setSpace(Space s) {
		roomLayout[s.getNumRow()][s.getNumCol()] = s;
	}
}
