package fringeoftoday.floor;

import acm.graphics.GObject;

/**
 * Holds data on a single space of a room
 *
 * @author Jacob Shour
 */
public class Space {
    private int numRow;
    private int numCol;
    private SpaceType type;
    private String filePath;
    private GObject gObject;

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

    public void setNumRow(int numRow) {
        this.numRow = numRow;
    }

    public int getNumCol() {
        return numCol;
    }

    public void setNumCol(int numCol) {
        this.numCol = numCol;
    }

    public SpaceType getType() {
        return type;
    }

    public void setType(SpaceType type) {
        this.type = type;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public GObject getGObject() {
        return gObject;
    }

    public void setGObject(GObject gObject) {
        this.gObject = gObject;
    }
}
