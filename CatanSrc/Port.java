package CatanSrc;

import java.util.*;

public class Port {
	
	private final String type;
	private final PlacementCoordinate placement1;
	private final PlacementCoordinate placement2;
	
	public Port(String t, PlacementCoordinate p1, PlacementCoordinate p2) {
		
		type = t;
		placement1 = p1;
		placement2 = p2;
		
	}
	
	public String getType() { return type; }
	public PlacementCoordinate[] getPlacements() { return new PlacementCoordinate[] {placement1, placement2}; }
	
	public Card portTrade(Card[] input, Stack<Card> desiredDrawDeck, Stack<Card> replaceDeck) {
		

		if(isValidTrade(input)) {
			for(Card card : input)
			replaceDeck.push(card);
		return desiredDrawDeck.pop();
		}
		return null;
		
	}
	
	public boolean isValidTrade(Card[] input) {
		
		if(type == "triple" && input.length != 3) {
			return false;
		}
		if(type != "triple" && input.length != 2) {
			return false;
		}
		for(int i = 0; i < input.length - 1; i++) {
			if(input[i].getType() != input[i+1].getType()) {
				return false;
			}
		}
		if(type != "triple" && input[0].getType() != type) {
			return false;
		}
		return true;
		
	}
	
}