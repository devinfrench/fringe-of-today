package fringeoftoday.floor;

/**
 * Categories of spaces
 * 
 * @author Jacob Shour
 * 
 */
public enum SpaceType {
	/**
	 * '*'; Standard space
	 */
	STANDARD,

	/**
	 * 'I'; Space that can be shot over but not walked on
	 */
	IMPASSIBLE,

	/**
	 * 'B'; Appears as a standard space but spawns a basic enemy
	 */
	BASIC_SPAWN,

	/**
	 * 'N'; Appears as a standard space but spawns a sniper enemy
	 */
	SNIPER_SPAWN,

	/**
	 * 'H'; Appears as a standard space but spawns a shotgun enemy
	 */
	SHOTGUN_SPAWN,

	/**
	 * 'S'; Appears as a standard space but spawns stairs when the room is cleared
	 */
	STAIRS,

	/**
	 * 'D'; Warps player to adjacent room; initially locked
	 */
	DOOR,

	/**
	 * 'W'; Space that can't be shot over or walked on
	 */
	WALL;
}
