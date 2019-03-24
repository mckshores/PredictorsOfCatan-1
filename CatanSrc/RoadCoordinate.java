package CatanSrc;

public class RoadCoordinate {
	
	private final int x;
	private final int y;
	private final int id;
	private boolean available;
	
	public RoadCoordinate(int X, int Y, int ID) {
		
		x = X;
		y = Y;
		id = ID;
		available = true;
		
	}
	
	public int[] getCoordinate() { return new int[] {x, y}; }
	public int getX() { return x; }
	public int getY() { return y; }
	public int getID() { return id; }
	public boolean isAvailable() { return available; }
	public void setAvailability(boolean avail) { available = avail; }
	
}
