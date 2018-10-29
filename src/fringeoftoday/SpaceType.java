package fringeoftoday;

/**
 * This enum controls behavior of spaces.
 * Descriptions are shown next to each type.
 * 
 * @author Jacob Shour
 * 
 */
public enum SpaceType {
	STANDARD, //Standard space
	IMPASSIBLE, //Space that can be shot over but not walked on
	COLLIDABLE, //Space that can't be shot over or walked on
	BASIC_SPAWN, //Appears as a standard space but spawns a basic enemy
	SNIPER_SPAWN, //Appears as a standard space but spawns a sniper enemy
	SHOTGUN_SPAWN, //Appears as a standard space but spawns a shotgun enemy
	STAIRS, //Appears as a standard space but spawns stairs when the room is cleared
	DOOR, //Warps player to adjacent room; initially locked
	WALL; //Space on edge of room that can't be shot over or walked on
}
