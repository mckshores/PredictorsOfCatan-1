package CatanSrc;

import java.util.*;

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
	public void setHand(Card[] cards) { hand.add(cards); }
	public void clearHand() { hand = new Hand(); }
	
	public Player takeTurn(PlayerDecision decision) {
		
		int[] actions = decision.makeDecision(this);
		for(int action : actions) {
			switch(action) {
			case 0: 
				playDevCard();
				break;
			case 1:
				upgradePlacement();
				break;
			case 2:
				buildPlacement();
				break;
			case 3:
				drawDevCard();
				break;
			case 4:
				buildRoad();
			case 5:
				trade();
			}
		}
		if(VP == 10) 
			return this;
		return null;
		
	}
	
	public void initPlacement() {
		
		Random rand = new Random();
		boolean unset = true;
		int count = 0;
		while(unset) {
			int randNum = rand.nextInt(54);
			if(isValidPlacement(randNum)) {
				board.setPlacement(randNum);
				unset = false;
			}
			if(count > 53) {
				unset = false;
			}
		}
		
	}
	
	public boolean isValidPlacement(int number) {
		
		if(board.getPossiblePlacements()[number].isAvailable()) {
			PlacementNode node = board.getPlacementAssociations().get(board.getPossiblePlacements()[number]);
			PlacementCoordinate[] surroundings = node.getAssociations();
			for(PlacementCoordinate coord : surroundings) {
				if(!coord.isAvailable()) {
					return false;
				}
			}
			return true;
		}
		return false;
		
	}
	
	public void discard(Card[] cards) {
		
		for(Card card : cards) {
			board.discard(card);
		}
		
	}
	
	public void playDevCard() {
		
		
		
	}
	
	public void drawDevCard() {
		
		hand.add(new Card[] {board.draw("development")});
		
	}
	
	public void buildPlacement() {
		
		
		
	}
	
	public void upgradePlacement() {
		
		for(Placement place : placements) {
			if(place.getWorth() == 1) {
				place.setWorth(2);
			}
		}
		
	}
	
	public void buildRoad() {
		
		
		
	}
	
	public void trade() {
		
		
		
	}
	
}