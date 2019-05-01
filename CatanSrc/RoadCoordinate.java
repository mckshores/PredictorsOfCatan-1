package CatanSrc;

public class RoadCoordinate {
	
	//These integers are indices in the possibleRoads array held by the board
	private final int x;
	private final int y;
	private final int id;
	private boolean available;
	
	//Constructor
	public RoadCoordinate(int X, int Y, int ID) {
		x = X;
		y = Y;
		id = ID;
		available = true;
	}
	
	//Getters and Setters
	public int[] getCoordinate() { return new int[] {x, y}; }
	public int getX() { return x; }
	public int getY() { return y; }
	public int getID() { return id; }
	public boolean isAvailable() { return available; }
	public void setAvailability(boolean avail) { available = avail; }
	
}
