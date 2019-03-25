package CatanSrc;

import java.util.*;

public class Catan {
	
	private Board GameBoard = new Board();
	private Player[] Players = new Player[] {new Player(GameBoard, this), new Player(GameBoard, this), new Player(GameBoard, this), new Player(GameBoard, this)};
	private boolean GameStatus = true;
	private Player Winner = null;
	private PlayerDecision decision = new PlayerDecision();
	
	public Catan() {}
	
	public Player[] getPlayers() { return Players; }
	
	public void playGame() {
		
		initPlayerPlacements();
		while(GameStatus && Winner != null) {
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
	
	private int rollDice() {
		
		Random rand = new Random();
		return (rand.nextInt(6) + 1) + (rand.nextInt(6) + 1);
		
	}
	
	private void allocateResources(int dieRoll) {
		
		Players[0].allocateRes(dieRoll);
		Players[1].allocateRes(dieRoll);
		Players[2].allocateRes(dieRoll);
		Players[3].allocateRes(dieRoll);
		
	}
	
	private void initPlayerPlacements() {
		
		Players[0].initPlacement();
		Players[1].initPlacement();
		Players[2].initPlacement();
		Players[3].initPlacement();
		Players[3].initPlacement();
		Players[2].initPlacement();
		Players[1].initPlacement();
		Players[0].initPlacement();
		
	}
	
	private void collectData() {
		
		
		
	}
}