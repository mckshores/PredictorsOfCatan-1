package CatanSrc;

public class Road {

	private RoadCoordinate coordinate;
	private BoardHex tile1;
	private BoardHex tile2;
	
	public Road(RoadCoordinate c, Board gameBoard) {
		
		coordinate = c;
		tile1 = gameBoard.getBoardTiles()[c.getX()];
		tile2 = gameBoard.getBoardTiles()[c.getY()];
		
	}
	
	public RoadCoordinate getCoordinate() { return coordinate; }
	public BoardHex[] getTiles() { return new BoardHex[] {tile1, tile2}; }

}
