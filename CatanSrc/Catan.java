package CatanSrc;

import java.io.FileWriter;
import java.io.IOException;
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
	
	public void playGame() throws IOException {
		FileWriter writer = new FileWriter("predictorsDataPoints.csv");
		writer.append("Resource,Hand,VP,Cities,Dev Cards,Round,Win\n");
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
				if(GameBoard.allDecksEmpty())
					return;
				collectData(player, writer);
			}
			
		}
	}
	
	public int rollDice() {
		
		Random rand = new Random();
		int die = (rand.nextInt(6) + 1) + (rand.nextInt(6) + 1);
		return die;
		
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
	
	public void collectData(Player p, FileWriter writer) throws IOException {
		int[] features = new int[7];
		/*
		 * [0] resource strength
		 * [1] hand strength
		 * [2] VP total
		 * [3] Cities
		 * [4] Dev Cards
		 * [5] Round
		 * [6] Win
		 */
		features[0] = p.getResourceStrength();
		//features[1] = p. getHandStrength();
		features[2] = p.getVP();
		features[3] = p.getCities();
		features[4] = p.getHand().getDevelopmentVector().size();
		features[5] = p.nextRound();
		if(p.getVP() >= 10)
			features[6] = 1;
		for(int i = 0; i < features.length; i++)
			writer.append(String.valueOf(features[i] + ","));
		writer.append("\n");
		
	}
}