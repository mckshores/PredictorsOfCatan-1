package CatanSrc;

public class PlacementCoordinate {
	
	private final int x;
	private final int y;
	private final int z;
	private boolean available;
	
	public PlacementCoordinate(int X, int Y, int Z) {
		
		x = X;
		y = Y;
		z = Z;
		available = true;
	}
	
	public int[] getFullCoordinate() { return new int[] {x, y, z}; }
	public int getX() { return x; }
	public int getY() { return y; }
	public int getZ() { return z; }
	public boolean isAvailable() { return available; }
	public void setAvailability(boolean avail) { available = avail; }

	
}