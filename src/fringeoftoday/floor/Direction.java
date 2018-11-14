package fringeoftoday.floor;

public enum Direction {
	NORTH, SOUTH, EAST, WEST, NORTHEAST, NORTHWEST, SOUTHEAST, SOUTHWEST;
	
	public String toString() {
		switch(this) {
			case NORTH: return "_n";
			case SOUTH: return "_s";
			case EAST: return "_e";
			case WEST: return "_w";
			case NORTHEAST: return "_ne";
			case NORTHWEST: return "_nw";
			case SOUTHEAST: return "_se";
			case SOUTHWEST: return "_sw";
		}
		return "n/a";
	}
}
