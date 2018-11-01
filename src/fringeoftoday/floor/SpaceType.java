package fringeoftoday.floor;

/**
 * This enum controls behavior of spaces.
 * Descriptions are shown next to each type.
 * 
 * @author Jacob Shour
 * 
 */
public enum SpaceType {
	STANDARD,      //'*', Standard space
	IMPASSIBLE,    //'I', Space that can be shot over but not walked on
	COLLIDABLE,    //'C', Space that can't be shot over or walked on
	BASIC_SPAWN,   //'B', Appears as a standard space but spawns a basic enemy
	SNIPER_SPAWN,  //'N', Appears as a standard space but spawns a sniper enemy
	SHOTGUN_SPAWN, //'H', Appears as a standard space but spawns a shotgun enemy
	STAIRS,        //'S', Appears as a standard space but spawns stairs when the room is cleared
	DOOR,          //'D', Warps player to adjacent room; initially locked
	WALL;          //'W', Space on edge of room that can't be shot over or walked on
}
