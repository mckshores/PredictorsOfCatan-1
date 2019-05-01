package CatanSrc;

public class Placement {
	
	//This object is what keeps track of the BoardHexes a placement is touching and is held by only the Player
	private PlacementCoordinate coordinate;
	private BoardHex tile1;
	private BoardHex tile2;
	private BoardHex tile3;
	private int worth;
	
	//Constructor
	public Placement(PlacementCoordinate c, Board gameBoard, int w) {
		coordinate = c;
		tile1 = gameBoard.getBoardTiles()[c.getX()];
		tile2 = gameBoard.getBoardTiles()[c.getY()];
		tile3 = gameBoard.getBoardTiles()[c.getZ()];
		worth = w;
	}
	
	//Getters and Setters
	public PlacementCoordinate getCoordinate() { return coordinate; }
	public BoardHex[] getTiles() { return new BoardHex[] {tile1, tile2, tile3 }; }
	public int getWorth() { return worth; } 
	public void setWorth(int w) { worth = w; }
	
}
