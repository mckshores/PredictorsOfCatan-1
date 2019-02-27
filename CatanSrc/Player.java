package CatanSrc;

public class Player {
	
	private Board board;
	private Placement[] placements = new Placement[] {null, null, null, null, null, null, null, null, null};
	private int VP = 0;
	private int roads = 0;
	private int settlements = 0;
	private int cities = 0;
	private Hand hand = new Hand();
	
	public Player(Board gameBoard) {
		board = gameBoard;
	}
	
	public Placement[] getPlacements() { return placements; }
	public Hand getHand() { return hand; }
	public Board getBoard() { return board; }
	public int getVP() { return VP; }
	public int getRoads() { return roads; }
	public int getSettlements() { return settlements; }
	public int getCities() { return cities; }
	
}