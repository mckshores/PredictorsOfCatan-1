package CatanSrc;

import java.util.*;

public class Player {
	
	private Board board;
	private Catan game;
	private Vector<Placement> placements = new Vector<Placement>();
	private Vector<Road> roads1 = new Vector<Road>();
	private Vector<Road> roads2 = new Vector<Road>();
	private int VP = 0;
	private int settlements = 0;
	private int cities = 0;
	private Hand hand = new Hand();
	private int round = 0;
	private int id;
	public Player(Board Board, Catan Game) {
		board = Board;
		game = Game;
	}
	
	public Vector<Placement> getPlacements() { return placements; }
	public Hand getHand() { return hand; }
	public Board getBoard() { return board; }
	public int getVP() { return VP; }
	public int getSettlements() { return settlements; }
	public int getCities() { return cities; }
	public int getKnights() {return hand.knightSize(); }
	public int getID() {return id;}
	public void setID(int ID) {
		id = ID;
	}
	public int nextRound() {
		round++;
		return round;
	}
	public int getLongestRoad() {
		if(roads1.size() > roads2.size()) {
			return roads1.size();
		}
		return roads2.size();
	}
	
	public Vector<Integer> getRoadPlacements() {
		Vector<Integer> retVal = new Vector<Integer>();
		for(Road road : roads1) {
			retVal.add(road.getCoordinate().getID());
		}
		for(Road road : roads2) {
			retVal.add(road.getCoordinate().getID());
		}
		return retVal;
	}
	
	public Vector<Integer> getPlacementIDs() {
		Vector<Integer> retVal = new Vector<Integer>();
		for(Placement placement : placements) {
			retVal.add(placement.getCoordinate().getID());
		}
		return retVal;
	}
	
	public void setHand(Card[] cards) { hand.add(cards); }
	public void clearHand() { 
		Vector<Card> cards = hand.clear(); 
		for (Card card : cards) {
			board.discard(card);
		}
	}
	
	public void allocateRes(int dieRoll) {
		
		if(dieRoll == 0) { return; }
		for(Placement place : placements) {
			BoardHex[] tiles = place.getTiles();
			int worth = place.getWorth();
			BoardHex robber = board.getRobber();
			if(tiles[0].getValue() == dieRoll && tiles[0] != robber) {
				for(int i = 0; i < worth; i++) {
					Card card = board.draw(tiles[0].getType());
					if(card != null) {
						hand.add(new Card[] {card});
					}
				}
			}
			if(tiles[1].getValue() == dieRoll && tiles[1] != robber) {
				for(int i = 0; i < worth; i++) {
					Card card = board.draw(tiles[1].getType());
					if(card != null) {
						hand.add(new Card[] {card});
					}
				}
			}
			if(tiles[2].getValue() == dieRoll && tiles[2] != robber) {
				for(int i = 0; i < worth; i++) {
					Card card = board.draw(tiles[2].getType());
					if(card != null) {
						hand.add(new Card[] {card});
					}
				}
			}
		}
		
	}
	
	public void checkHand() {
		
		Vector<Card> cards = hand.tooLarge();
		for(Card card : cards) {
			board.discard(card);
		}
		
	}
	
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
				boolean set = buildPlacement();
				if(!set)
					buildRoad();
				break;
			case 3:
				drawDevCard();
				break;
			case 4:
				buildRoad();
				break;
			case 5:
				trade();
			}
		}
		updateVP();
		if(VP >= 10) 
			return this;
		return null;
		
	}
	
	public void updateVP() {
		
		VP = 0;
		VP = settlements + (2 * cities);
		if(findLongestRoad()) {
			VP += 2;
		}
		if(findLargestArmy()) {
			VP += 2;
		}
		VP += VPcards();
		
	}
	
	public boolean findLongestRoad() {
		
		Player[] players = game.getPlayers();
		int longest = 4;
		Player longestPlayer = null;
		for(Player player : players) {
			if(player.getLongestRoad() > longest) {
				longest = player.getLongestRoad();
				longestPlayer = player;
			}
		}
		if(longestPlayer == this) {
			return true;
		}
		return false;
		
	}
	
	public boolean findLargestArmy() {
		
		Player[] players = game.getPlayers();
		int largest = 2;
		Player largePlayer = null;
		for(Player player : players) {
			if(player.getKnights() > largest) {
				largest = player.getKnights(); 
				largePlayer = player;
			}
		}
		if(largePlayer == this) {
			return true;
		}
		return false;
		
	}
	
	public int VPcards() {
		
		int count = 0;
		for(Card card : hand.getDevelopmentVector()) {
			if(card.getType() == "victorypoint") {
				count ++;
			}
		}
		return count;
		
	}
	
	public void initPlacement() { 
		
		Random rand = new Random();
		boolean unset = true;
		int count = 0;
		while(unset) {
			int randNum = rand.nextInt(54);
			if(isValidPlacement(randNum)) {
				board.setPlacementAvail(randNum);
				Placement placement = new Placement(board.getPossiblePlacements()[randNum], board, 1);
				placements.add(placement);
				RoadCoordinate road = board.findRoadCoordinate(board.getPossiblePlacements()[randNum]);
				board.setRoadAvail(road.getID());
				if(roads1.isEmpty()) {
					roads1.add(new Road(road, board));
				}
				else {
					roads2.add(new Road(road, board));
				}
				initResources(placement);
				settlements++;
				unset = false;
			}
			if(count > 53) {
				unset = false;
			}
			count++;
		}
		
	}
	
	public boolean isValidPlacement(int number) {
		
		if(board.getPossiblePlacements()[number].isAvailable()) {
			PlacementNode node = board.getPlacementAssociations().get(board.getPossiblePlacements()[number]);
			PlacementCoordinate[] surroundings = node.getAssociations();
			for(PlacementCoordinate coord : surroundings) {
				if(coord != null && !coord.isAvailable()) {
					return false;
				}
			}
			return true;
		}
		return false;
		
	}
	
	public void initResources(Placement placement) {
		
		Card card = null;
		BoardHex[] tiles = placement.getTiles();
		for(BoardHex tile : tiles) {
			if(tile.getType() != "desert") {
				card = board.draw(tile.getType());
				if(card != null)
					hand.add(new Card[] {card});
			}
		}
		
	}
	
	public void playDevCard() {
		
		Card card = hand.getPlayableDevCard();
		if(card != null) {
			switch(card.getType()) {
			case "knight":
				moveKnight();
				adjustHand(new Card[] {card});
				return;
			case "yearofplenty":
				grabTwo();
				adjustHand(new Card[] {card});
				return;
			case "monopoly":
				String[] types = new String[] {"grain", "ore", "brick", "lumber", "livestock"};
				Random random = new Random();
				int index = random.nextInt(5);
				monopoly(types[index]);
				adjustHand(new Card[] {card});
				return;
			case "roadbuilding":
				placeRoad();
				placeRoad();
				adjustHand(new Card[] {card});
				return;
			}
		}
		
	}
	
	public void monopoly(String taking) {
		
		Player[] players = game.getPlayers();
		for(Player player: players) {
			if(player != this) {
				Object[] c = player.takeAll(taking).toArray();
				Card[] cards = new Card[c.length];
				for(int i = 0; i < c.length; i++) {
					if(c[i] != null)
						cards[i] = (Card)c[i];
				}
				if(cards.length != 0)
					hand.add(cards);
			}
		}
		
	}
	
	public void grabTwo() { 
		
		String[] types = new String[] {"grain", "lumber", "ore", "brick", "livestock"};
		Random random = new Random();
		int randomNum = random.nextInt(5);
		Card card = board.draw(types[randomNum]);
		if(card != null)
			hand.add(new Card[] {card});
		randomNum = random.nextInt(5);
		card = board.draw(types[randomNum]);
		if(card != null)
			hand.add(new Card[] {card});
		
	}
	
	public void moveKnight() {
		
		Player[] players = game.getPlayers();
		Random random = new Random();
		boolean unset = true;
		int count = 0;
		while(unset && count < 100) {
			count++;
			int index = random.nextInt(4);
			if(players[index] != this) {
				Player hostage = players[index];
				if(!hostage.getHand().isResEmpty()) {
					moveRobber(hostage);
					Card card = players[index].takeOne();
					if(card != null) 
						hand.add(new Card[] {card});
					unset = false;
				}
			}
		}
		
	}
		
	public Card takeOne() {
		
		return hand.robbery();
		
	}
	
	public void moveRobber(Player player) {
		
		Vector<Placement> p = player.getPlacements();
		Random random = new Random();
		boolean unset = true;
		while(unset) {
			int index = random.nextInt(p.size());
			Placement place = (Placement)p.toArray()[index];
			index = random.nextInt(3);
			BoardHex newHex = place.getTiles()[index];
			if(!newHex.isBorder()) {
				board.setRobber(newHex);
				unset = false;
			}
		}
		
	}
	
	public void drawDevCard() {
		
		Card devCard = board.draw("development");
		if(devCard != null) {
			hand.add(new Card[] {devCard});
			adjustHand(new Card[] {new Card("grain"), new Card("livestock"), new Card("ore")});
		}
		
	}
	
	public Vector<Card> takeAll(String type) {
		
		int size = 0;
		Vector<Card> retVal = new Vector<Card>();
		switch(type) {
		case "grain":
			size = hand.getGrainVector().size();
			for(int i = 0; i < size; i++) {
				Vector<Card> cards = hand.play(new Card[] {new Card("grain")});
				for(Card card : cards) {
					retVal.add(card);
				}
			}
			return retVal;
		case "lumber":
			size = hand.getLumberVector().size();
			for(int i = 0; i < size; i++) {
				Vector<Card> cards = hand.play(new Card[] {new Card("lumber")});
				for(Card card : cards) {
					retVal.add(card);
				}
			}
			return retVal;
		case "livestock":
			size = hand.getLivestockVector().size();
			for(int i = 0; i < size; i++) {
				Vector<Card> cards = hand.play(new Card[] {new Card("livestock")});
				for(Card card : cards) {
					retVal.add(card);
				}
			}
			return retVal;
		case "ore":
			size = hand.getOreVector().size();
			for(int i = 0; i < size; i++) {
				Vector<Card> cards = hand.play(new Card[] {new Card("ore")});
				for(Card card : cards) {
					retVal.add(card);
				}
			}
			return retVal;
		case "brick":
			size = hand.getBrickVector().size();
			for(int i = 0; i < size; i++) {
				Vector<Card> cards = hand.play(new Card[] {new Card("brick")});
				for(Card card : cards) {
					retVal.add(card);
				}
			}
			return  retVal;
		}
		return retVal;
		
	}
	
	public boolean buildPlacement() {
		
		if(placements.size() > 8) {
			return false;
		}
		for(Road r : roads1) {
			PlacementNode node = board.getBoardAssociations().get(r.getCoordinate());
			PlacementCoordinate[] associations = node.getAssociations();
			for(int i = 0; i < 2; i++) {
				if(isValidPlacement(associations[i].getID())) {
					board.setPlacementAvail(associations[i].getID());
					placements.add(new Placement(board.getPossiblePlacements()[associations[i].getID()], board, 1));
					adjustHand(new Card[] {new Card("lumber"), new Card("grain"), new Card("livestock"), new Card("brick")});
					settlements++;
					return true;
				}
			}
		}
		for(Road r : roads2) {
			PlacementNode node = board.getBoardAssociations().get(r.getCoordinate());
			PlacementCoordinate[] associations = node.getAssociations();
			for(int i = 0; i < 2; i++) {
				if(isValidPlacement(associations[i].getID())) {
					board.setPlacementAvail(associations[i].getID());
					placements.add(new Placement(board.getPossiblePlacements()[associations[i].getID()], board, 1));
					adjustHand(new Card[] {new Card("lumber"), new Card("grain"), new Card("livestock"), new Card("brick")});
					settlements++;
					return true;
				}
			}
		}
		return false;
		
	}
		
	public void adjustHand(Card[] cards) {
		
		Vector<Card> d = hand.play(cards);
		for(Card card : d) {
			board.discard(card);
		}
		
	}
	
	public void upgradePlacement() {
		
		for(Placement place : placements) {
			if(place.getWorth() == 1) {
				place.setWorth(2);
				settlements--;
				cities++;
				adjustHand(new Card[] {new Card("grain"), new Card("grain"), new Card("ore"), new Card("ore"), new Card("ore")});
				break;
			}
		}
		
	}
	
	public void buildRoad() {
		
		for(int i = 0; i < 13; i++) {
			if(hand.getBrickVector().size() >= 1 && hand.getLumberVector().size() >= 1 && (roads1.size() + roads2.size()) <= 15) {
				placeRoad();
			}
		}
		
	}
	
	public void placeRoad() {
		
		if((roads1.size() + roads2.size()) >= 15) {
			return;
		}
		RoadNode node = board.getRoadAssociations().get(roads1.lastElement().getCoordinate());
		RoadCoordinate newRoad = whereToPlaceRoad(node, roads1);
		Road roadPiece = null;
		if(newRoad != null) {
			roadPiece = new Road(newRoad, board);
			if(roads2.contains(roadPiece)) {
				connectedRoads();
				return;
			}
			roads1.add(roadPiece);
			board.setRoadAvail(newRoad.getID());
			adjustHand(new Card[] {new Card("brick"), new Card("lumber")});
			return;
		}
		node = board.getRoadAssociations().get(roads1.firstElement().getCoordinate());
		newRoad = whereToPlaceRoad(node, roads1);
		if(newRoad != null) {
			roadPiece = new Road(newRoad, board);
			if(roads2.contains(roadPiece)) {
				connectedRoads();
				return;
			}
			roads1.add(roadPiece);
			board.setRoadAvail(newRoad.getID());
			adjustHand(new Card[] {new Card("brick"), new Card("lumber")});
			return;
		}
		if(!roads2.isEmpty()) {
			node = board.getRoadAssociations().get(roads2.lastElement().getCoordinate());
			newRoad = whereToPlaceRoad(node, roads2);
			if(newRoad != null) {
				roadPiece = new Road(newRoad, board);
				if(roads1.contains(roadPiece)) {
					connectedRoads();
					return;
				}
				roads2.add(roadPiece);
				board.setRoadAvail(newRoad.getID());
				adjustHand(new Card[] {new Card("brick"), new Card("lumber")});
				return;
			}
			node = board.getRoadAssociations().get(roads2.firstElement().getCoordinate());
			newRoad = whereToPlaceRoad(node, roads2);
			if(newRoad != null) {
				roadPiece = new Road(newRoad, board);
				if(roads1.contains(roadPiece)) {
					connectedRoads();
					return;
				}
				roads2.add(roadPiece);
				board.setRoadAvail(newRoad.getID());
				adjustHand(new Card[] {new Card("brick"), new Card("lumber")});
			}
		}
		
	}
	
	public RoadCoordinate whereToPlaceRoad(RoadNode node, Vector<Road> roads) {
		
		RoadCoordinate[] assoc = node.getAssociations();
		Vector<RoadCoordinate> myCoordinates = new Vector<RoadCoordinate>();
		Random random = new Random();
		int randomNum = 0;
		int assocSize = 0;
		for(int i = 0; i < assoc.length; i++) {
			if(assoc[i] != null) {
				assocSize++;
			}
		}
		for(Road road : roads) {
			myCoordinates.add(road.getCoordinate());
		}
		if(assocSize == 4) {
			if(myCoordinates.contains(assoc[0]) || myCoordinates.contains(assoc[1])) {
				if(board.getPossibleRoads()[assoc[2].getID()].isAvailable() && board.getPossibleRoads()[assoc[3].getID()].isAvailable()) {
					randomNum = random.nextInt(11);
					if(randomNum % 2 == 0) {
						return assoc[3];
					}
					else {
						return assoc[2];
					}
				}
				if(board.getPossibleRoads()[assoc[2].getID()].isAvailable()) return assoc[2];
				if(board.getPossibleRoads()[assoc[3].getID()].isAvailable()) return assoc[3];
			}
			if(myCoordinates.contains(assoc[2]) || myCoordinates.contains(assoc[3])) {
				if(board.getPossibleRoads()[assoc[0].getID()].isAvailable() && board.getPossibleRoads()[assoc[1].getID()].isAvailable()) {
					randomNum = random.nextInt(11);
					if(randomNum % 2 == 0) {
						return assoc[1];
					}
					else {
						return assoc[0];
					}
				}
				if(board.getPossibleRoads()[assoc[0].getID()].isAvailable()) return assoc[0];
				if(board.getPossibleRoads()[assoc[1].getID()].isAvailable()) return assoc[1];
			}
			else {
				if(board.getPossibleRoads()[assoc[3].getID()].isAvailable()) return assoc[3];
				if(board.getPossibleRoads()[assoc[2].getID()].isAvailable()) return assoc[2];
				if(board.getPossibleRoads()[assoc[1].getID()].isAvailable()) return assoc[1];
				if(board.getPossibleRoads()[assoc[0].getID()].isAvailable()) return assoc[0];
			}
		}
		if(assocSize == 3) {
			if(assoc[0] == null) {
				if(myCoordinates.contains(assoc[1])) {
					if(board.getPossibleRoads()[assoc[2].getID()].isAvailable() && board.getPossibleRoads()[assoc[3].getID()].isAvailable()) {
						randomNum = random.nextInt(11);
						if(randomNum % 2 == 0) {
							return assoc[3];
						}
						else {
							return assoc[2];
						}
					}
					if(board.getPossibleRoads()[assoc[2].getID()].isAvailable()) return assoc[2];
					if(board.getPossibleRoads()[assoc[3].getID()].isAvailable()) return assoc[3];
				}
				if(myCoordinates.contains(assoc[2]) || myCoordinates.contains(assoc[3])) {
					if(board.getPossibleRoads()[assoc[1].getID()].isAvailable()) return assoc[1];
				}
				else {
					if(board.getPossibleRoads()[assoc[2].getID()].isAvailable()) return assoc[2];
					if(board.getPossibleRoads()[assoc[3].getID()].isAvailable()) return assoc[3];
					if(board.getPossibleRoads()[assoc[1].getID()].isAvailable()) return assoc[1];
				}
			}
			if(assoc[1] == null) {
				if(myCoordinates.contains(assoc[0])) {
					if(board.getPossibleRoads()[assoc[2].getID()].isAvailable() && board.getPossibleRoads()[assoc[3].getID()].isAvailable()) {
						randomNum = random.nextInt(11);
						if(randomNum % 2 == 0) {
							return assoc[3];
						}
						else {
							return assoc[2];
						}
					}
					if(board.getPossibleRoads()[assoc[2].getID()].isAvailable()) return assoc[2];
					if(board.getPossibleRoads()[assoc[3].getID()].isAvailable()) return assoc[3];
				}
				if(myCoordinates.contains(assoc[2]) || myCoordinates.contains(assoc[3])) {
					if(board.getPossibleRoads()[assoc[0].getID()].isAvailable()) return assoc[0];
				}
				else {
					if(board.getPossibleRoads()[assoc[2].getID()].isAvailable()) return assoc[2];
					if(board.getPossibleRoads()[assoc[3].getID()].isAvailable()) return assoc[3];
					if(board.getPossibleRoads()[assoc[0].getID()].isAvailable()) return assoc[0];
				}
			}
			if(assoc[2] == null) {
				if(myCoordinates.contains(assoc[0]) || myCoordinates.contains(assoc[1])) {
					if(board.getPossibleRoads()[assoc[3].getID()].isAvailable()) return assoc[3];
				}
				if(myCoordinates.contains(assoc[3])) {
					if(board.getPossibleRoads()[assoc[0].getID()].isAvailable() && board.getPossibleRoads()[assoc[1].getID()].isAvailable()) {
						randomNum = random.nextInt(11);
						if(randomNum % 2 == 0) {
							return assoc[1];
						}
						else {
							return assoc[0];
						}
					}
					if(board.getPossibleRoads()[assoc[0].getID()].isAvailable()) return assoc[0];
					if(board.getPossibleRoads()[assoc[1].getID()].isAvailable()) return assoc[1];
				}
				else {
					if(board.getPossibleRoads()[assoc[3].getID()].isAvailable()) return assoc[3];
					if(board.getPossibleRoads()[assoc[0].getID()].isAvailable()) return assoc[0];
					if(board.getPossibleRoads()[assoc[1].getID()].isAvailable()) return assoc[1];
				}
			}
			if(assoc[3] == null) {
				if(myCoordinates.contains(assoc[0]) || myCoordinates.contains(assoc[1])) {
					if(board.getPossibleRoads()[assoc[2].getID()].isAvailable()) return assoc[2];
				}
				if(myCoordinates.contains(assoc[2])) {
					if(board.getPossibleRoads()[assoc[0].getID()].isAvailable() && board.getPossibleRoads()[assoc[1].getID()].isAvailable()) {
						randomNum = random.nextInt(11);
						if(randomNum % 2 == 0) {
							return assoc[1];
						}
						else {
							return assoc[0];
						}
					}
					if(board.getPossibleRoads()[assoc[0].getID()].isAvailable()) return assoc[0];
					if(board.getPossibleRoads()[assoc[1].getID()].isAvailable()) return assoc[1];
				}
				else {
					if(board.getPossibleRoads()[assoc[2].getID()].isAvailable()) return assoc[2];
					if(board.getPossibleRoads()[assoc[0].getID()].isAvailable()) return assoc[0];
					if(board.getPossibleRoads()[assoc[1].getID()].isAvailable()) return assoc[1];
				}
			}
		}
		if(assocSize == 2) {
			if(assoc[0] == null && assoc[2] == null) {
				if( myCoordinates.contains(assoc[1])) {
					if(board.getPossibleRoads()[assoc[3].getID()].isAvailable()) return assoc[3];
				}
				if(myCoordinates.contains(assoc[3])) {
					if(board.getPossibleRoads()[assoc[1].getID()].isAvailable()) return assoc[1];
				}
				else {
					if(board.getPossibleRoads()[assoc[3].getID()].isAvailable()) return assoc[3];
					if(board.getPossibleRoads()[assoc[1].getID()].isAvailable()) return assoc[1];
				}
			}
			if(assoc[1] == null && assoc[3] == null) {
				if(myCoordinates.contains(assoc[0])) {
					if(board.getPossibleRoads()[assoc[2].getID()].isAvailable()) return assoc[2];
				}
				if(myCoordinates.contains(assoc[2])) {
					if(board.getPossibleRoads()[assoc[0].getID()].isAvailable()) return assoc[0];
				}
				else {
					if(board.getPossibleRoads()[assoc[2].getID()].isAvailable()) return assoc[2];
					if(board.getPossibleRoads()[assoc[0].getID()].isAvailable()) return assoc[0];
				}
			}
		}
		return null;
		
	}
	
	public void connectedRoads() {
		
		Road temp = roads2.firstElement();
		roads2.remove(temp);
		for(Road road : roads2) {
			roads1.add(road);
		}
		roads1.add(temp);
		roads2 = null;
		
	}
	
	public String tradeFrom() {
		
		if(hand.getBrickVector().size() >= 4) {
			return "brick";
		}
		if(hand.getGrainVector().size() >= 4) {
			return "grain";
		}
		if(hand.getLumberVector().size() >= 4) {
			return "lumber";
		}
		if(hand.getLivestockVector().size() >= 4) {
			return "livestock";
		}
		if(hand.getOreVector().size() >= 4) {
			return "ore";
		}
		return "";
		
	}
	
	public void trade() {
		
		String[] types = new String[] {"grain", "ore", "brick", "lumber", "livestock"};
		Random random = new Random();
		String typeToTrade = tradeFrom();
		boolean unset = true;
		if(typeToTrade == "") {
			return;
		}
		while(unset) {
			int index = random.nextInt(5);
			if(typeToTrade != types[index]) {
				Card card = board.draw(types[index]);
				if(card != null) {
					hand.add(new Card[] {card});
					adjustHand(new Card[] {new Card(typeToTrade), new Card(typeToTrade), new Card(typeToTrade), new Card(typeToTrade)});
					unset = false;
				}
			}
		}
		
	}
	public int getProbability(Vector<Integer> probabilities) {
		int prob = 0;
		for(Integer p : probabilities) {
			if(p == 2 || p == 12)
				prob += 1;
			else if(p == 3 || p == 11)
				prob +=2;
			else if(p == 4 || p == 10)
				prob += 3;
			else if(p == 5 || p == 9)
				prob += 4;
			else if(p == 6 || p == 8)
				prob += 5;
		}
		return prob;
	}
	public int getHandStrength() {
		int variety = 0;
		int total = 0;
		int[] resources = new int[5];
		resources[0] = getHand().getGrainVector().size();
		resources[1] = getHand().getBrickVector().size();
		resources[2] = getHand().getLumberVector().size();
		resources[3] = getHand().getOreVector().size();
		resources[4] = getHand().getLivestockVector().size();
		for(int i = 0; i < resources.length; i++) {
			if(resources[i] > 0) {
				variety++;
				total += resources[i];
			}
		}
		return variety * total;
	}
	public int getResourceStrength() {
		Vector<Integer> probabilities = new Vector<Integer>();
		for(Placement p : getPlacements()) {
			for(BoardHex b : p.getTiles()) {
				probabilities.add(b.getProbability());
			}
		}
		return getProbability(probabilities);
	}
	
}