package CatanSrc;

import java.util.*;

public class Hand {
	
	private Vector<Card> grain = new Vector<Card>();
	private Vector<Card> lumber = new Vector<Card>();
	private Vector<Card> livestock = new Vector<Card>();
	private Vector<Card> ore = new Vector<Card>();
	private Vector<Card> brick = new Vector<Card>();
	private Vector<Card> development = new Vector<Card>();
	private int resTotal = 0;
	private int knights = 0;
	private int devTotal = 0;
	private int totalDev = 0;
	
	//Empty Constructor
	public Hand() {}
	
	//Getters
	public Vector<Card> getGrainVector() { return grain; }
	public Vector<Card> getLumberVector() { return lumber; }
	public Vector<Card> getLivestockVector() { return livestock; }
	public Vector<Card> getOreVector() { return ore; }
	public Vector<Card> getBrickVector() { return brick; }
	public Vector<Card> getDevelopmentVector() { return development; }
	public int getTotalDev() {return totalDev;}
	public int size() { return resTotal; }
	public int devSize() { return devTotal; }
	public int knightSize() { return knights; }
	
	//Determine if the entire hand is empty
	public boolean isEmpty() {
		return grain.isEmpty() && lumber.isEmpty() && livestock.isEmpty() && ore.isEmpty() && brick.isEmpty() && development.isEmpty();
	}
	//Determine if the only cards in the hand are development cards
	public boolean isResEmpty() {
		return grain.isEmpty() && lumber.isEmpty() && livestock.isEmpty() && ore.isEmpty() && brick.isEmpty();
	}
	
	//This method allows us to add cards to the hand
	public void add(Card[] cards) {
		for(Card card : cards) {
			switch(card.getType()) {
			case "grain":
				grain.add(card);
				resTotal ++;
				break;
			case "lumber":
				lumber.add(card);
				resTotal ++;
				break;
			case "livestock":
				livestock.add(card);
				resTotal ++;
				break;
			case "ore": 
				ore.add(card);
				resTotal ++;
				break;
			case "brick":
				brick.add(card);
				resTotal ++;
				break;
			case "monopoly":
				development.add(card);
				devTotal ++;
				totalDev++;
				break;
			case "yearofplenty":
				development.add(card);
				devTotal ++;
				totalDev++;
				break;
			case "roadbuilding":
				development.add(card);
				devTotal ++;
				totalDev++;
				break;
			case "victorypoint":
				development.add(card);
				devTotal ++;
				totalDev++;
				break;
			case "knight":
				development.add(card);
				knights ++;
				devTotal ++;
				totalDev++;
				break;
			}
		}
	}
	
	//This is how you remove cards from the hand. It takes them out of the hand and puts them into a Vector which is returned to Player who then tells the Board to discard them.
	public Vector<Card> play(Card[] cards) {
		Vector<Card> retVal = new Vector<Card>();
		for(Card card : cards) {
			switch(card.getType()) {
			case "grain": 
				if(grain.remove(card)) {
					retVal.add(card);
					resTotal --; 
				}
				break;
			case "lumber": 
				if(lumber.remove(card)) {
					retVal.add(card);
					resTotal --; 
				}
				break;
			case "livestock": 
				if(livestock.remove(card)) {
					retVal.add(card);
					resTotal --; 
				}
				break;
			case "ore": 
				if(ore.remove(card)) {
					retVal.add(card);
					resTotal --; 
				}
				break;
			case "brick": 
				if(brick.remove(card)) {
					retVal.add(card);
					resTotal --; 
				}
				break;
			case "monopoly": 
				if(development.remove(card)) {
					retVal.add(card);
					devTotal --; 
				}
				break;
			case "yearofplenty": 
				if(development.remove(card)) {
					retVal.add(card);
					devTotal --; 
				}
				break;
			case "roadbuilding":
				if(development.remove(card)) {
					retVal.add(card);
					devTotal --; 
				}
				break;
			case "knight": 
				if(development.remove(card)) {
					retVal.add(card);
					devTotal --; 
					knights ++;
				}
				break;
			}
		}
		return retVal;
	}
	
	//When a 7 is rolled a Player must discard half of their hand if they have more than 7 cards. This method handles the random discarding of half of the resource cards if it's necessary.
	public Vector<Card> tooLarge() {
		if(resTotal > 7) {
			int discardTotal = resTotal / 2;
			int counter = 0;
			Random rand = new Random();
			Vector<Card> discards = new Vector<Card>();
			while(counter < discardTotal) {
				int randNum = rand.nextInt(5);
				switch(randNum) {
				case 0:
					if(grain.remove(new Card("grain"))) {
						discards.add(new Card("grain"));
						resTotal--;
						counter++;
						break;
					}
				case 1:
					if(lumber.remove(new Card("lumber"))) {
						discards.add(new Card("lumber"));
						resTotal--;
						counter++;
						break;
					}
				case 2:
					if(livestock.remove(new Card("livestock"))) {
						discards.add(new Card("livestock"));
						resTotal--;
						counter++;
						break;
					}
				case 3:
					if(ore.remove(new Card("ore"))) {
						discards.add(new Card("ore"));
						resTotal--;
						counter++;
						break;
					}
				case 4:
					if(brick.remove(new Card("brick"))) {
						discards.add(new Card("brick"));
						resTotal--;
						counter++;
						break;
					}
				}
			}
			return discards;
		}
		return new Vector<Card>();
	}
	
	//Randomly pick one of the cards in your hand, if you have any, and give it to whichever Player is robbing you.
	public Card robbery() {
		Random rand = new Random();
		if(grain.isEmpty() && brick.isEmpty() && lumber.isEmpty() && livestock.isEmpty() && ore.isEmpty())
			return null;
		while(true) {
			int randNum = rand.nextInt(5);
			switch(randNum) {
			case 0:
				if(grain.remove(new Card("grain"))) {
					resTotal--;
					return new Card("grain");
				}
			case 1:
				if(lumber.remove(new Card("lumber"))) {
					resTotal--;
					return new Card("lumber");
				}
			case 2:
				if(livestock.remove(new Card("livestock"))) {
					resTotal--;
					return new Card("livestock");
				}
			case 3: 
				if(ore.remove(new Card("ore"))) {
					resTotal--;
					return new Card("ore");
				}
			case 4:
				if(brick.remove(new Card("brick"))) {
					resTotal--;
					return new Card("brick");
				}
			}
		}
	}
	
	//This method is only used in testing
	public Vector<Card> clear() {
		Vector<Card> retVal = new Vector<Card>();
		for(Card card : grain) {
			retVal.add(card);
		}
		for(Card card : lumber) {
			retVal.add(card);
		}
		for(Card card : ore) {
			retVal.add(card);
		}
		for(Card card : livestock) {
			retVal.add(card);
		}
		for(Card card : brick) {
			retVal.add(card);
		}
		for(Card card : development) {
			retVal.add(card);
		}
		grain = new Vector<Card>();
		lumber = new Vector<Card>();
		ore = new Vector<Card>();
		livestock = new Vector<Card>();
		brick = new Vector<Card>();
		development = new Vector<Card>();
		return retVal;
	}
	
	//Not all development cards are playable
	public Card getPlayableDevCard() {
		for(Card card : development) {
			if(card.getType() != "victorypoint")
				return card;
		}
		return null;
	}

}
