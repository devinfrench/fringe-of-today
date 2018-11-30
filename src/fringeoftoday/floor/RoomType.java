package fringeoftoday.floor;


/**
 * Catagories of rooms
 *
 * @author Jacob Shour
 */
public enum RoomType {
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
