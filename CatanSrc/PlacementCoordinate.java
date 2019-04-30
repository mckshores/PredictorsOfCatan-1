package CatanSrc;

public class PlacementCoordinate {
	
	//These integers are indices in the possiblePlacement array held by the board
	private final int x;
	private final int y;
	private final int z;
	private final int id;
	private boolean available;
	
	//Constructor
	public PlacementCoordinate(int X, int Y, int Z, int ID) {
		x = X;
		y = Y;
		z = Z;
		id = ID;
		available = true;
	}
	
	//Getters and Setters
	public int[] getFullCoordinate() { return new int[] {x, y, z}; }
	public int getX() { return x; }
	public int getY() { return y; }
	public int getZ() { return z; }
	public int getID() { return id; }
	public boolean isAvailable() { return available; }
	public void setAvailability(boolean avail) { available = avail; }
	
}