package fringeoftoday.floor;

import java.util.ArrayList;

/**
 * Holds data on a single room
 *
 * @author Jacob Shour
 */
public class Room {
    public static final int ROOM_ROWS = FloorManager.ROOM_ROWS;
    public static final int ROOM_COLS = FloorManager.ROOM_COLS;

    private char charLayout[][];
    private Space roomLayout[][];
    private ArrayList<Exit> exits;
    private ArrayList<Direction> directions;
    private RoomType type;
    private boolean cleared;

    public Room(RoomType type) {
    	charLayout = new char[ROOM_ROWS][ROOM_COLS];
        roomLayout = new Space[ROOM_ROWS][ROOM_COLS];
        exits = new ArrayList<Exit>();
        cleared = false;
        this.type = type;
    }

    public Room(Room copiedRoom) {
        this.roomLayout = copiedRoom.roomLayout;
        this.exits = copiedRoom.exits;
        this.directions = copiedRoom.directions;
        this.type = copiedRoom.type;
        this.cleared = copiedRoom.cleared;
    }

    public Space getSpace(int row, int col) {
        return roomLayout[row][col];
    }

    public ArrayList<Exit> getExits() {
        return exits;
    }

    public RoomType getType() {
        return type;
    }

    public Boolean isCleared() {
        return cleared;
    }

    /**
     * Sets the space at the given X/Y to the given space
     *
     * @param row - X coordinate in the room
     * @param col - Y coordinate in the room
     * @param r   - Space to insert
     */
    public void setSpace(int row, int col, SpaceType type) {
        roomLayout[row][col] = new Space(row, col, type);
    }

    public void setCleared(Boolean cleared) {
        this.cleared = cleared;
    }

    /**
     * Adds the given exit to the list of exits the room has
     *
     * @param e - Exit to add
     */
    public void addExit(Exit e) {
        exits.add(e);
    }

    public void setFilePaths() {
        String path;

        for (int i = 0; i < ROOM_ROWS; i++) {
            for (int j = 0; j < ROOM_COLS; j++) {
                directions = new ArrayList<Direction>();

                switch (roomLayout[i][j].getType()) {
                    case WATER:
                        path = pathGenerator("water", i, j, SpaceType.WATER);
                        break;

                    case WALL:
                        path = pathGenerator("wall", i, j, SpaceType.WALL);
                        break;

                    case DOOR:
                        path = "wall.png";
                        break;

                    case BLANK:
                        path = "blank.png";
                        break;

                    default:
                        path = "ground";

                        // North
                        if (i > 0 && roomLayout[i - 1][j].getType() != SpaceType.WALL)
                            directions.add(Direction.NORTH);
                        // South
                        if (i < ROOM_ROWS - 1 && roomLayout[i + 1][j].getType() != SpaceType.WALL)
                            directions.add(Direction.SOUTH);
                        // East
                        if (j < ROOM_COLS - 1 && roomLayout[i][j + 1].getType() != SpaceType.WALL)
                            directions.add(Direction.EAST);
                        // West
                        if (j > 0 && roomLayout[i][j - 1].getType() != SpaceType.WALL)
                            directions.add(Direction.WEST);
                        // Northeast
                        if (i > 0 && j < ROOM_COLS - 1 && roomLayout[i - 1][j + 1].getType() != SpaceType.WALL)
                            directions.add(Direction.NORTHEAST);
                        // Northwest
                        if (i > 0 && j > 0 && roomLayout[i - 1][j - 1].getType() != SpaceType.WALL)
                            directions.add(Direction.NORTHWEST);
                        // Southeast
                        if (i < ROOM_ROWS - 1 && j < ROOM_COLS - 1 && roomLayout[i + 1][j + 1].getType() != SpaceType.WALL)
                            directions.add(Direction.SOUTHEAST);
                        // Southwest
                        if (i < ROOM_ROWS - 1 && j > 0 && roomLayout[i + 1][j - 1].getType() != SpaceType.WALL)
                            directions.add(Direction.SOUTHWEST);

                        removeDiagonals();

                        if (directions.contains(Direction.NORTHWEST))
                            path = appendDirection(path, Direction.NORTHWEST);
                        if (directions.contains(Direction.NORTH))
                            path = appendDirection(path, Direction.NORTH);
                        if (directions.contains(Direction.NORTHEAST))
                            path = appendDirection(path, Direction.NORTHEAST);
                        if (directions.contains(Direction.WEST))
                            path = appendDirection(path, Direction.WEST);
                        if (directions.contains(Direction.EAST))
                            path = appendDirection(path, Direction.EAST);
                        if (directions.contains(Direction.SOUTHWEST))
                            path = appendDirection(path, Direction.SOUTHWEST);
                        if (directions.contains(Direction.SOUTH))
                            path = appendDirection(path, Direction.SOUTH);
                        if (directions.contains(Direction.SOUTHEAST))
                            path = appendDirection(path, Direction.SOUTHEAST);

                        path = path + ".png";
                        break;
                }

                roomLayout[i][j].setFilePath(path);
            }
        }
    }

    private String pathGenerator(String path, int i, int j, SpaceType type) {
        // North
        if (i > 0 && roomLayout[i - 1][j].getType() == type)
            directions.add(Direction.NORTH);
        // South
        if (i < ROOM_ROWS - 1 && roomLayout[i + 1][j].getType() == type)
            directions.add(Direction.SOUTH);
        // East
        if (j < ROOM_COLS - 1 && roomLayout[i][j + 1].getType() == type)
            directions.add(Direction.EAST);
        // West
        if (j > 0 && roomLayout[i][j - 1].getType() == type)
            directions.add(Direction.WEST);
        // Northeast
        if (i > 0 && j < ROOM_COLS - 1 && roomLayout[i - 1][j + 1].getType() == type)
            directions.add(Direction.NORTHEAST);
        // Northwest
        if (i > 0 && j > 0 && roomLayout[i - 1][j - 1].getType() == type)
            directions.add(Direction.NORTHWEST);
        // Southeast
        if (i < ROOM_ROWS - 1 && j < ROOM_COLS - 1 && roomLayout[i + 1][j + 1].getType() == type)
            directions.add(Direction.SOUTHEAST);
        // Southwest
        if (i < ROOM_ROWS - 1 && j > 0 && roomLayout[i + 1][j - 1].getType() == type)
            directions.add(Direction.SOUTHWEST);

        removeDiagonals();

        if (directions.contains(Direction.NORTHWEST))
            path = appendDirection(path, Direction.NORTHWEST);
        if (directions.contains(Direction.NORTH))
            path = appendDirection(path, Direction.NORTH);
        if (directions.contains(Direction.NORTHEAST))
            path = appendDirection(path, Direction.NORTHEAST);
        if (directions.contains(Direction.WEST))
            path = appendDirection(path, Direction.WEST);
        if (directions.contains(Direction.EAST))
            path = appendDirection(path, Direction.EAST);
        if (directions.contains(Direction.SOUTHWEST))
            path = appendDirection(path, Direction.SOUTHWEST);
        if (directions.contains(Direction.SOUTH))
            path = appendDirection(path, Direction.SOUTH);
        if (directions.contains(Direction.SOUTHEAST))
            path = appendDirection(path, Direction.SOUTHEAST);

        return path + ".png";
    }

    private String appendDirection(String path, Direction d) {
        return path + d.toString();
    }

    private void removeDiagonals() {
        if (directions.contains(Direction.NORTHWEST)
          && !(directions.contains(Direction.NORTH) && directions.contains(Direction.WEST)))
            directions.remove(Direction.NORTHWEST);
        if (directions.contains(Direction.NORTHEAST)
          && !(directions.contains(Direction.NORTH) && directions.contains(Direction.EAST)))
            directions.remove(Direction.NORTHEAST);
        if (directions.contains(Direction.SOUTHWEST)
          && !(directions.contains(Direction.SOUTH) && directions.contains(Direction.WEST)))
            directions.remove(Direction.SOUTHWEST);
        if (directions.contains(Direction.SOUTHEAST)
          && !(directions.contains(Direction.SOUTH) && directions.contains(Direction.EAST)))
            directions.remove(Direction.SOUTHEAST);
    }

	public void setCharArray(char[][] layout) {
		charLayout = layout;
	}
	
	public char[][] getCharArray(){
		return charLayout;
	}
}
