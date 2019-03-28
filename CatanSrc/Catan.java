package CatanSrc;

import java.util.*;

public class Catan {
	
	private Board GameBoard =  null;
	private Player[] Players = null;
	private boolean GameStatus;
	private Player Winner = null;
	private PlayerDecision Decision = null;
	private int Round;
	
	public Catan() {
		
		GameBoard = new Board();
		Players = new Player[] {new Player(GameBoard, this), new Player(GameBoard, this), new Player(GameBoard, this), new Player(GameBoard, this)};
		GameStatus = true;
		Decision = new PlayerDecision();
		Round = 0;
		
	}
	
	public Player[] getPlayers() { return Players; }
	public Board getBoard() { return GameBoard; }
	public Player getWinner() { return Winner; }
	public PlayerDecision getDecision() { return Decision; }
	
	public void playGame() {
		
		initPlayerPlacements();
		while(GameStatus && Winner == null) {
			System.out.println(++Round + " Player0: " + Players[0].getVP() + " Player1: " + Players[1].getVP() + " Player2: " + Players[2].getVP() + " Player3: " + Players[3].getVP());
			for(Player player : Players) {
				int dieRoll = rollDice();
				if(dieRoll == 7) {
					for(Player p : Players) {
						p.checkHand();
					}
					player.moveKnight();
				}
				else {
					allocateResources(dieRoll);
				}
				Player p = player.takeTurn(Decision);
				if(p != null) {
					Winner = p;
					GameStatus = false;
					break;
				}
			}
			collectData();
		}
		
	}
	
	public int rollDice() {
		
		Random rand = new Random();
		return (rand.nextInt(6) + 1) + (rand.nextInt(6) + 1);
		
	}
	
	public void allocateResources(int dieRoll) {
		
		Players[0].allocateRes(dieRoll);
		Players[1].allocateRes(dieRoll);
		Players[2].allocateRes(dieRoll);
		Players[3].allocateRes(dieRoll);
		
	}
	
	public void initPlayerPlacements() {
		
		Players[0].initPlacement();
		Players[1].initPlacement();
		Players[2].initPlacement();
		Players[3].initPlacement();
		Players[3].initPlacement();
		Players[2].initPlacement();
		Players[1].initPlacement();
		Players[0].initPlacement();
		
	}
	
	public void collectData() {
		
		
		
	}
}