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
		Players[0].setID(1);
		Players[1].setID(2);
		Players[2].setID(3);
		Players[3].setID(4);
		FileWriter writer = new FileWriter("predictorsDataPoints.csv",true);
		//FileWriter writer = new FileWriter("D:\\predictorsDataPoints.csv",true);
		//writer.append("Player,Resource,Hand,VP,Cities,Dev Cards,Round,Win\n");
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
					collectData(player, writer);
					break;
				}
				if(GameBoard.allDecksEmpty())
					return;
				collectData(player, writer);
			}
			
		}
		writer.close();
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
		int[] features = new int[8];
		/*
		 * [1] resource strength
		 * [2] hand strength
		 * [3] VP total
		 * [4] Cities
		 * [5] Dev Cards
		 * [6] Round
		 * [7] Win
		 */
		features[0] = p.getID();
		features[1] = p.getResourceStrength();
		features[2] = p. getHandStrength();
		features[3] = p.getVP();
		features[4] = p.getCities();
		features[5] = p.getHand().getTotalDev();
		features[6] = p.nextRound();
		if(p.getVP() >= 10)
			features[7] = 1;
		for(int i = 0; i < features.length; i++)
			writer.append(String.valueOf(features[i] + ","));
		writer.append("\n");
		
	}
}