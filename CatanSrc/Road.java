package CatanSrc;

public class Road {

	//This object is what keeps track of the BoardHexes a road is touching and is held by only the Player
	private RoadCoordinate coordinate;
	private BoardHex tile1;
	private BoardHex tile2;
	
	//Constructor
	public Road(RoadCoordinate c, Board gameBoard) {
		coordinate = c;
		tile1 = gameBoard.getBoardTiles()[c.getX()];
		tile2 = gameBoard.getBoardTiles()[c.getY()];
	}
	
	//Getters
	public RoadCoordinate getCoordinate() { return coordinate; }
	public BoardHex[] getTiles() { return new BoardHex[] {tile1, tile2}; }

}
