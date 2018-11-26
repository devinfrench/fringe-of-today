package fringeoftoday.floor;


/**
 * Catagories of floors
 * 
 * @author Jacob Shour
 *
 */
public enum FloorType {
	/**
	 * Standard room
	 */
	STANDARD,
	
	/**
	 * Room that the staircase to the next floor appears in
	 */
	BOSS,
	
	/**
	 * Room the player spawns in (one per floor)
	 */
	SPAWN;
}
