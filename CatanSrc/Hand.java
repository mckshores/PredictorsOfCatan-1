package CatanSrc;

import java.util.*;

public class Hand {
	
	private Vector<Card> grain = new Vector<Card>();
	private Vector<Card> lumber = new Vector<Card>();
	private Vector<Card> livestock = new Vector<Card>();
	private Vector<Card> ore = new Vector<Card>();
	private Vector<Card> brick = new Vector<Card>();
	private Vector<Card> development = new Vector<Card>();
	private int total = 0;
	private int knights = 0;
	private int devTotal = 0;
	
	public Hand() {}
	
	public Vector<Card> getGrainVector() { return grain; }
	public Vector<Card> getLumberVector() { return lumber; }
	public Vector<Card> getLivestockVector() { return livestock; }
	public Vector<Card> getOreVector() { return ore; }
	public Vector<Card> getBrickVector() { return brick; }
	public Vector<Card> getDevelopmentVector() { return development; }
	public int size() { return total; }
	public int devSize() { return devTotal; }
	public int knightSize() { return knights; }

	
	public void add(Card[] cards) {
		
		for(Card card : cards) {
			switch(card.getType()) {
			case "grain":
				grain.add(card);
				total ++;
				break;
			case "lumber":
				lumber.add(card);
				total ++;
				break;
			case "livestock":
				livestock.add(card);
				total ++;
				break;
			case "ore": 
				ore.add(card);
				total ++;
				break;
			case "brick":
				brick.add(card);
				total ++;
				break;
			case "monopoly":
				development.add(card);
				devTotal ++;
				break;
			case "yearofplenty":
				development.add(card);
				devTotal ++;
				break;
			case "roadbuilding":
				development.add(card);
				devTotal ++;
				break;
			case "victorypoint":
				development.add(card);
				devTotal ++;
				break;
			case "knight":
				development.add(card);
				knights ++;
				devTotal ++;
				break;
			}
		}
		
	}
	
	public Vector<Card> play(Card[] cards) {
		
		Vector<Card> retVal = new Vector<Card>();
		for(Card card : cards) {
			switch(card.getType()) {
			case "grain": 
				if(grain.remove(card)) {
					retVal.add(card);
					total --; 
				}
				break;
			case "lumber": 
				if(lumber.remove(card)) {
					retVal.add(card);
					total --; 
				}
				break;
			case "livestock": 
				if(livestock.remove(card)) {
					retVal.add(card);
					total --; 
				}
				break;
			case "ore": 
				if(ore.remove(card)) {
					retVal.add(card);
					total --; 
				}
				break;
			case "brick": 
				if(brick.remove(card)) {
					retVal.add(card);
					total --; 
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
			case "victorypoint": 
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
	
	public Card[] tooLarge() {
		
		return null;
		
	}
	
	public Card robbery() {
		
		Card retCard = null;
		
		return retCard;
		
	}

}
