package CatanSrc;

public class Placement {
	
	private PlacementCoordinate coordinate;
	private BoardHex tile1;
	private BoardHex tile2;
	private BoardHex tile3;
	private Port port;
	private int worth;
	
	public Placement(PlacementCoordinate c, Port p, Board gameBoard, int w) {
		
		coordinate = c;
		port = p;
		tile1 = gameBoard.getBoardTiles()[c.getX()];
		tile2 = gameBoard.getBoardTiles()[c.getY()];
		tile3 = gameBoard.getBoardTiles()[c.getZ()];
		worth = w;
		
	}
	
	public PlacementCoordinate getCoordinate() { return coordinate; }
	public BoardHex[] getTiles() { return new BoardHex[] {tile1, tile2, tile3 }; }
	public Port getPort() { return port; }
	public int getWorth() { return worth; } 
	public void setWorth(int w) { worth = w; }
	public boolean isOnPort() { return port != null; }
	
}
