package CatanSrc;



public class Catan {
	
	private static Board GameBoard = new Board();
	private static Player[] Players = new Player[] {new Player(GameBoard), new Player(GameBoard), new Player(GameBoard), new Player(GameBoard)};
	private static boolean GameStatus = true;
	private static Player Winner = null;
	private static PlayerDecision decision = new PlayerDecision();
	
	public static void main() {
		
		initPlayerPlacements();
		while(GameStatus) {
			for(Player player : Players) {
				//roll dice
				//collect resources
				Player p = player.takeTurn(decision);
				if(p != null) {
					Winner = p;
					GameStatus = false;
					break;
				}
			}
			collectData();
		}
		
	}
	
	public static void initPlayerPlacements() {
		
		Players[0].initPlacement();
		Players[1].initPlacement();
		Players[2].initPlacement();
		Players[3].initPlacement();
		Players[3].initPlacement();
		Players[2].initPlacement();
		Players[1].initPlacement();
		Players[0].initPlacement();
		
	}
	
	public static void collectData() {
		
		
		
	}
}