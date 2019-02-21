package CatanSrc;

public class RoadCoordinate {
	
	private int x;
	private int y;
	private boolean available;
	
	public RoadCoordinate(int X, int Y) {
		
		x = X;
		y = Y;
		available = true;
		
	}
	
	public int[] getCoordinate() { return new int[] {x, y}; }
	public int getX() { return x; }
	public int getY() { return y; }
	public boolean isAvailable() { return available; }
	public void setAvailability(boolean avail) { available = avail; }
	
}
