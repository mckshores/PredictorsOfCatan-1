package CatanSrc;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Board {
	
	private Hashtable<PlacementCoordinate, PlacementNode> placementAssociations = new Hashtable<PlacementCoordinate, PlacementNode>();
	private Hashtable<RoadCoordinate, RoadNode> roadAssociations = new Hashtable<RoadCoordinate, RoadNode>();
	private Hashtable<RoadCoordinate, PlacementNode> boardAssociations = new Hashtable<RoadCoordinate, PlacementNode>();
	private PlacementCoordinate[] possiblePlacements;
	private RoadCoordinate[] possibleRoads;
	private BoardValues boardValues = new BoardValues();
	private BoardTypes boardTypes = new BoardTypes();
	private Stack<Card> grainDeck = new Stack<Card>();
	private Stack<Card> oreDeck = new Stack<Card>();
	private Stack<Card> lumberDeck = new Stack<Card>();
	private Stack<Card> livestockDeck = new Stack<Card>();
	private Stack<Card> brickDeck = new Stack<Card>();
	private Stack<Card> developmentDeck = new Stack<Card>();
	private BoardHex robber;
	private BoardHex[] board;
	
	public Board() {
		
		for(int i=0; i < 19; i++) {
			grainDeck.push(new Card("grain"));
			oreDeck.push(new Card("ore"));
			lumberDeck.push(new Card("lumber"));
			livestockDeck.push(new Card("livestock"));
			brickDeck.push(new Card("brick"));
		}
		initDevelopmentDeck();
		initBoard();
		initPossiblePlacements();
		initPlacementAssociations();
		initPossibleRoads();
		initRoadAssociations();
		initBoardAssociations();
		BoardHex des = findDesert();
		if(des != null)
			robber = des;
		else
			throw new NullPointerException("Could not locate desert: line 32 in Board.java");
	}
	
	public BoardValues getBoardValues() { return boardValues; }
	public Stack<Card> getGrainDeck() { return grainDeck; } 
	public Stack<Card> getOreDeck() { return oreDeck; } 
	public Stack<Card> getLumberDeck() { return lumberDeck; } 
	public Stack<Card> getLivestockDeck() { return livestockDeck; } 
	public Stack<Card> getBrickDeck() { return brickDeck; } 
	public Stack<Card> getDevelopmentDeck() { return developmentDeck; } 
	public BoardHex[] getBoardTiles() { return board; } 
	public BoardHex getRobber() { return robber; } 
	public PlacementCoordinate[] getPossiblePlacements() { return possiblePlacements; }
	public Hashtable<PlacementCoordinate, PlacementNode> getPlacementAssociations() { return placementAssociations; }
	public RoadCoordinate[] getPossibleRoads() { return possibleRoads; }
	public Hashtable<RoadCoordinate, RoadNode> getRoadAssociations() { return roadAssociations; }
	public Hashtable<RoadCoordinate, PlacementNode> getBoardAssociations() { return boardAssociations; }
	
	public void setRobber(BoardHex hex) { robber = hex; }
	public void setPlacementAvail(int number) { possiblePlacements[number].setAvailability(false); }
	public void setRoadAvail(int number) { possibleRoads[number].setAvailability(false); }
	
	public RoadCoordinate findRoadCoordinate(PlacementCoordinate coord) {
		
		for(Map.Entry<RoadCoordinate, PlacementNode> entry : boardAssociations.entrySet()) {
			PlacementCoordinate[] placements = entry.getValue().getAssociations();
			if(placements[0] == coord || placements[1] == coord || placements[2] == coord) {
				return entry.getKey();
			}
		}
		return null;
		
	}
	
	public void discard(Card card) {
		
		switch(card.getType()) {
		case "grain":
			grainDeck.push(card);
			return;
		case "lumber":
			lumberDeck.push(card);
			return;
		case "livestock":
			livestockDeck.push(card);
			return;
		case "ore":
			oreDeck.push(card);
			return;
		case "brick":
			brickDeck.push(card);
			return;
		}
		
		
	}
	
	public Card draw(String type) {
		
		switch(type) {
		case "grain":
			return grainDeck.pop();
		case "lumber":
			return lumberDeck.pop();
		case "livestock":
			return livestockDeck.pop();
		case "ore":
			return oreDeck.pop();
		case "brick":
			return brickDeck.pop();
		case "development":
			if(!developmentDeck.isEmpty())
				return developmentDeck.pop();
		}
		return null;
		
	}
	
	public void initDevelopmentDeck() {
		
		Card[] devDeck = new Card[] { 
				new Card("monopoly"),new Card("monopoly"),
				new Card("yearofplenty"),new Card("yearofplenty"),
				new Card("roadbuilding"),new Card("roadbuilding"),
				new Card("victorypoint"),new Card("victorypoint"),new Card("victorypoint"),new Card("victorypoint"),new Card("victorypoint"),
				new Card("knight"),new Card("knight"),new Card("knight"),new Card("knight"),new Card("knight"),new Card("knight"),new Card("knight"),
				new Card("knight"),new Card("knight"),new Card("knight"),new Card("knight"),new Card("knight"),new Card("knight"),new Card("knight")
		};
		developmentDeck = shuffleDeck(devDeck);
		
	}
	
	public Stack<Card> shuffleDeck(Card[] deck) {
		
		Random rand = ThreadLocalRandom.current();
		for(int i = deck.length - 1; i > 0; i--) {
			int index = rand.nextInt(i + 1);
			Card card = deck[index];
			deck[index] = deck[i];
			deck[i] = card;
		}
		Stack<Card> Deck = new Stack<Card>();
		for(int i = 0; i < 25; i++) {
			Deck.push(deck[i]);
		}
		return Deck;
		
	}
	
	public void initBoard() {
		
		board = new BoardHex[] {
			/*00*/ new BoardHex("desert", this), /*01*/ new BoardHex("desert", this), /*02*/ new BoardHex("desert", this), /*03*/ new BoardHex("desert", this), /*04*/ new BoardHex("desert", this), /*05*/ new BoardHex(createHexType(), this),
			/*06*/ new BoardHex(createHexType(), this), /*07*/ new BoardHex(createHexType(), this), /*08*/ new BoardHex("desert", this), /*09*/ new BoardHex("desert", this), /*10*/ new BoardHex(createHexType(), this), /*11*/ new BoardHex(createHexType(), this),
			/*12*/ new BoardHex(createHexType(), this), /*13*/ new BoardHex(createHexType(), this), /*14*/ new BoardHex("desert", this), /*15*/ new BoardHex("desert", this), /*16*/ new BoardHex(createHexType(), this), /*17*/ new BoardHex(createHexType(), this),
			/*18*/ new BoardHex(createHexType(), this), /*19*/ new BoardHex(createHexType(), this), /*20*/ new BoardHex(createHexType(), this), /*21*/ new BoardHex("desert", this), /*22*/ new BoardHex("desert", this), /*23*/ new BoardHex(createHexType(), this),
			/*24*/ new BoardHex(createHexType(), this), /*25*/ new BoardHex(createHexType(), this), /*26*/ new BoardHex(createHexType(), this), /*27*/ new BoardHex("desert", this), /*28*/ new BoardHex("desert", this), /*29*/ new BoardHex(createHexType(), this),
			/*30*/ new BoardHex(createHexType(), this), /*31*/ new BoardHex(createHexType(), this), /*32*/ new BoardHex("desert", this), /*33*/ new BoardHex("desert", this), /*34*/ new BoardHex("desert", this), /*35*/ new BoardHex("desert", this),
			/*36*/ new BoardHex("desert", this)
		};
		board[0].setBorder(true);
		board[1].setBorder(true);
		board[2].setBorder(true);
		board[3].setBorder(true);
		board[4].setBorder(true);
		board[8].setBorder(true);
		board[9].setBorder(true);
		board[14].setBorder(true);
		board[15].setBorder(true);
		board[21].setBorder(true);
		board[22].setBorder(true);
		board[27].setBorder(true);
		board[28].setBorder(true);
		board[32].setBorder(true);
		board[33].setBorder(true);
		board[34].setBorder(true);
		board[35].setBorder(true);
		board[36].setBorder(true);
		
	}
	
	public String createHexType() {
		
		String retVal = "desert";
		String[] possibleHexTypes = new String[] { "livestock","lumber","brick","grain","ore","desert" };
		while(true) {
			Random rand = new Random();
			int randNum = rand.nextInt(6);
			if (boardTypes.setBoardType(possibleHexTypes[randNum])) {
				retVal = possibleHexTypes[randNum];
				break;
			}
			if(boardTypes.getCurrentTypes().equals(boardTypes.getTypesAllowed())) {
				break;
			}
		}
		return retVal;
		
	}
	
	public BoardHex findDesert() {
		
		BoardHex retVal = null;
		for(BoardHex hex : board) {
			if(!hex.isBorder() && hex.getType() == "desert")
				retVal = hex;
		}
		return retVal;
		
	}
	
	public void initPossiblePlacements() {
		
		possiblePlacements = new PlacementCoordinate[] {
			/*00*/ new PlacementCoordinate(0, 4, 5, 0), /*01*/ new PlacementCoordinate(0, 1, 5, 1), /*02*/ new PlacementCoordinate(1, 5, 6, 2), /*03*/ new PlacementCoordinate(1, 2, 6, 3), /*04*/ new PlacementCoordinate(2, 6, 7, 4), /*05*/ new PlacementCoordinate(2, 3, 7, 5),
			/*06*/ new PlacementCoordinate(3, 7, 8, 6), /*07*/ new PlacementCoordinate(4, 9, 10, 7), /*08*/ new PlacementCoordinate(4, 5, 10, 8), /*09*/ new PlacementCoordinate(5, 10, 11, 9), /*10*/ new PlacementCoordinate(5, 6, 11, 10), /*11*/ new PlacementCoordinate(6, 11, 12, 11),
			/*12*/ new PlacementCoordinate(6, 7, 12, 12), /*13*/ new PlacementCoordinate(7, 12, 13, 13), /*14*/ new PlacementCoordinate(7, 8, 13, 14), /*15*/ new PlacementCoordinate(8, 13, 14, 15), /*16*/ new PlacementCoordinate(9, 15, 16, 16), /*17*/ new PlacementCoordinate(9, 10, 16, 17),
			/*18*/ new PlacementCoordinate(10, 16, 17, 18), /*19*/ new PlacementCoordinate(10, 11, 17, 19), /*20*/ new PlacementCoordinate(11, 17, 18, 20), /*21*/ new PlacementCoordinate(11, 12, 18, 21), /*22*/ new PlacementCoordinate(12, 18, 19, 22), /*23*/ new PlacementCoordinate(12, 13, 19, 23),
			/*24*/ new PlacementCoordinate(13, 19, 20, 24), /*25*/ new PlacementCoordinate(13, 14, 20, 25), /*26*/ new PlacementCoordinate(14, 20, 21, 26), /*27*/ new PlacementCoordinate(15, 16, 22, 27), /*28*/ new PlacementCoordinate(16 ,22 ,23, 28), /*29*/ new PlacementCoordinate(16, 17, 23, 29),
			/*30*/ new PlacementCoordinate(17, 23, 24, 30), /*31*/ new PlacementCoordinate(17, 18, 24, 31), /*32*/ new PlacementCoordinate(18, 24, 25, 32), /*33*/ new PlacementCoordinate(18, 19, 25, 33), /*34*/ new PlacementCoordinate(19, 25, 26, 34), /*35*/ new PlacementCoordinate(19, 20, 26, 35),
			/*36*/ new PlacementCoordinate(20, 26, 27, 36), /*37*/ new PlacementCoordinate(20, 21, 27, 37), /*38*/ new PlacementCoordinate(22, 23, 28, 38), /*39*/ new PlacementCoordinate(23, 28, 29, 39), /*40*/ new PlacementCoordinate(23, 24, 29, 40), /*41*/ new PlacementCoordinate(24, 29, 30, 41),
			/*42*/ new PlacementCoordinate(24, 25, 30, 42), /*43*/ new PlacementCoordinate(25, 30, 31, 43), /*44*/ new PlacementCoordinate(25, 26, 31, 44), /*45*/ new PlacementCoordinate(26, 31, 32, 45), /*46*/ new PlacementCoordinate(26, 27, 32, 46), /*47*/ new PlacementCoordinate(28, 29, 33, 47),
			/*48*/ new PlacementCoordinate(29, 33, 34, 48), /*49*/ new PlacementCoordinate(29, 30, 34, 49), /*50*/ new PlacementCoordinate(30, 34, 35, 50), /*51*/ new PlacementCoordinate(30, 31, 35, 51), /*52*/ new PlacementCoordinate(31, 35, 36, 52), /*53*/ new PlacementCoordinate(31, 32, 36, 53)
		};
		
	}
	
	public void initPlacementAssociations() {
		
		placementAssociations.put(possiblePlacements[0], new PlacementNode(possiblePlacements[1], possiblePlacements[8], null)); 
		placementAssociations.put(possiblePlacements[1], new PlacementNode(possiblePlacements[0], possiblePlacements[2], null)); 
		placementAssociations.put(possiblePlacements[2], new PlacementNode(possiblePlacements[1], possiblePlacements[3], possiblePlacements[10])); 
		placementAssociations.put(possiblePlacements[3], new PlacementNode(possiblePlacements[2], possiblePlacements[4], null)); 
		placementAssociations.put(possiblePlacements[4], new PlacementNode(possiblePlacements[3], possiblePlacements[5], possiblePlacements[12])); 
		placementAssociations.put(possiblePlacements[5], new PlacementNode(possiblePlacements[4], possiblePlacements[6], null)); 
		placementAssociations.put(possiblePlacements[6], new PlacementNode(possiblePlacements[5], possiblePlacements[14], null)); 
		placementAssociations.put(possiblePlacements[7], new PlacementNode(possiblePlacements[8], possiblePlacements[17], null)); 
		placementAssociations.put(possiblePlacements[8], new PlacementNode(possiblePlacements[0], possiblePlacements[7], possiblePlacements[9])); 
		placementAssociations.put(possiblePlacements[9], new PlacementNode(possiblePlacements[8], possiblePlacements[10], possiblePlacements[19])); 
		placementAssociations.put(possiblePlacements[10], new PlacementNode(possiblePlacements[2], possiblePlacements[9], possiblePlacements[11])); 
		placementAssociations.put(possiblePlacements[11], new PlacementNode(possiblePlacements[10], possiblePlacements[12], possiblePlacements[21])); 
		placementAssociations.put(possiblePlacements[12], new PlacementNode(possiblePlacements[4], possiblePlacements[11], possiblePlacements[13])); 
		placementAssociations.put(possiblePlacements[13], new PlacementNode(possiblePlacements[12], possiblePlacements[14], possiblePlacements[23])); 
		placementAssociations.put(possiblePlacements[14], new PlacementNode(possiblePlacements[6], possiblePlacements[13], possiblePlacements[15])); 
		placementAssociations.put(possiblePlacements[15], new PlacementNode(possiblePlacements[14], possiblePlacements[25], null)); 
		placementAssociations.put(possiblePlacements[16], new PlacementNode(possiblePlacements[17], possiblePlacements[27], null)); 
		placementAssociations.put(possiblePlacements[17], new PlacementNode(possiblePlacements[7], possiblePlacements[16], possiblePlacements[18])); 
		placementAssociations.put(possiblePlacements[18], new PlacementNode(possiblePlacements[17], possiblePlacements[19], possiblePlacements[29])); 
		placementAssociations.put(possiblePlacements[19], new PlacementNode(possiblePlacements[9], possiblePlacements[18], possiblePlacements[20])); 
		placementAssociations.put(possiblePlacements[20], new PlacementNode(possiblePlacements[19], possiblePlacements[21], possiblePlacements[31])); 
		placementAssociations.put(possiblePlacements[21], new PlacementNode(possiblePlacements[11], possiblePlacements[20], possiblePlacements[22])); 
		placementAssociations.put(possiblePlacements[22], new PlacementNode(possiblePlacements[21], possiblePlacements[23], possiblePlacements[33])); 
		placementAssociations.put(possiblePlacements[23], new PlacementNode(possiblePlacements[13], possiblePlacements[22], possiblePlacements[24])); 
		placementAssociations.put(possiblePlacements[24], new PlacementNode(possiblePlacements[23], possiblePlacements[25], possiblePlacements[35])); 
		placementAssociations.put(possiblePlacements[25], new PlacementNode(possiblePlacements[15], possiblePlacements[24], possiblePlacements[26])); 
		placementAssociations.put(possiblePlacements[26], new PlacementNode(possiblePlacements[25], possiblePlacements[37], null)); 
		placementAssociations.put(possiblePlacements[27], new PlacementNode(possiblePlacements[16], possiblePlacements[28], null)); 
		placementAssociations.put(possiblePlacements[28], new PlacementNode(possiblePlacements[27], possiblePlacements[29], possiblePlacements[38])); 
		placementAssociations.put(possiblePlacements[29], new PlacementNode(possiblePlacements[18], possiblePlacements[28], possiblePlacements[30])); 
		placementAssociations.put(possiblePlacements[30], new PlacementNode(possiblePlacements[29], possiblePlacements[31], possiblePlacements[40])); 
		placementAssociations.put(possiblePlacements[31], new PlacementNode(possiblePlacements[20], possiblePlacements[30], possiblePlacements[32])); 
		placementAssociations.put(possiblePlacements[32], new PlacementNode(possiblePlacements[31], possiblePlacements[33], possiblePlacements[42])); 
		placementAssociations.put(possiblePlacements[33], new PlacementNode(possiblePlacements[22], possiblePlacements[32], possiblePlacements[34])); 
		placementAssociations.put(possiblePlacements[34], new PlacementNode(possiblePlacements[33], possiblePlacements[35], possiblePlacements[44])); 
		placementAssociations.put(possiblePlacements[35], new PlacementNode(possiblePlacements[24], possiblePlacements[34], possiblePlacements[36])); 
		placementAssociations.put(possiblePlacements[36], new PlacementNode(possiblePlacements[35], possiblePlacements[37], possiblePlacements[46])); 
		placementAssociations.put(possiblePlacements[37], new PlacementNode(possiblePlacements[26], possiblePlacements[36], null)); 
		placementAssociations.put(possiblePlacements[38], new PlacementNode(possiblePlacements[28], possiblePlacements[39], null)); 
		placementAssociations.put(possiblePlacements[39], new PlacementNode(possiblePlacements[38], possiblePlacements[40], possiblePlacements[47])); 
		placementAssociations.put(possiblePlacements[40], new PlacementNode(possiblePlacements[30], possiblePlacements[39], possiblePlacements[41])); 
		placementAssociations.put(possiblePlacements[41], new PlacementNode(possiblePlacements[40], possiblePlacements[42], possiblePlacements[49])); 
		placementAssociations.put(possiblePlacements[42], new PlacementNode(possiblePlacements[32], possiblePlacements[41], possiblePlacements[43])); 
		placementAssociations.put(possiblePlacements[43], new PlacementNode(possiblePlacements[42], possiblePlacements[44], possiblePlacements[51])); 
		placementAssociations.put(possiblePlacements[44], new PlacementNode(possiblePlacements[34], possiblePlacements[43], possiblePlacements[45])); 
		placementAssociations.put(possiblePlacements[45], new PlacementNode(possiblePlacements[44], possiblePlacements[46], possiblePlacements[53])); 
		placementAssociations.put(possiblePlacements[46], new PlacementNode(possiblePlacements[36], possiblePlacements[45], null)); 
		placementAssociations.put(possiblePlacements[47], new PlacementNode(possiblePlacements[39], possiblePlacements[48], null)); 
		placementAssociations.put(possiblePlacements[48], new PlacementNode(possiblePlacements[47], possiblePlacements[49], null)); 
		placementAssociations.put(possiblePlacements[49], new PlacementNode(possiblePlacements[41], possiblePlacements[48], possiblePlacements[50])); 
		placementAssociations.put(possiblePlacements[50], new PlacementNode(possiblePlacements[49], possiblePlacements[51], null)); 
		placementAssociations.put(possiblePlacements[51], new PlacementNode(possiblePlacements[43], possiblePlacements[50], possiblePlacements[52])); 
		placementAssociations.put(possiblePlacements[52], new PlacementNode(possiblePlacements[51], possiblePlacements[53], null)); 
		placementAssociations.put(possiblePlacements[53], new PlacementNode(possiblePlacements[45], possiblePlacements[52], null)); 
		
	}
	
	public void initPossibleRoads() {
		  
		possibleRoads = new RoadCoordinate[] {
			/*00*/ new RoadCoordinate(0, 5, 0), /*01*/ new RoadCoordinate(1, 5, 1), /*02*/ new RoadCoordinate(1, 6, 2), /*03*/ new RoadCoordinate(2, 6, 3), 
			/*04*/ new RoadCoordinate(2, 7, 4), /*05*/ new RoadCoordinate(3, 7, 5), /*06*/ new RoadCoordinate(4, 5, 6), /*07*/ new RoadCoordinate(5, 6, 7), 
			/*08*/ new RoadCoordinate(6, 7, 8), /*09*/ new RoadCoordinate(7, 8, 9), /*10*/ new RoadCoordinate(4, 10, 10), /*11*/ new RoadCoordinate(5, 10, 11), 
			/*12*/ new RoadCoordinate(5, 11, 12), /*13*/ new RoadCoordinate(6, 11, 13), /*14*/ new RoadCoordinate(6, 12, 14), /*15*/ new RoadCoordinate(7, 12, 15), 
			/*16*/ new RoadCoordinate(7, 13, 16), /*17*/ new RoadCoordinate(8, 13, 17), /*18*/ new RoadCoordinate(9, 10, 18), /*19*/ new RoadCoordinate(10, 11, 19), 
			/*20*/ new RoadCoordinate(11, 12, 20), /*21*/ new RoadCoordinate(12, 13, 21), /*22*/ new RoadCoordinate(13, 14, 22), /*23*/ new RoadCoordinate(9, 16, 23), 
			/*24*/ new RoadCoordinate(10, 16, 24), /*25*/ new RoadCoordinate(10, 17, 25), /*26*/ new RoadCoordinate(11, 17, 26), /*27*/ new RoadCoordinate(11, 18, 27), 
			/*28*/ new RoadCoordinate(12, 18, 28), /*29*/ new RoadCoordinate(12, 19, 29), /*30*/ new RoadCoordinate(13, 19, 30), /*31*/ new RoadCoordinate(13, 20, 31), 
			/*32*/ new RoadCoordinate(20, 14, 32), /*33*/ new RoadCoordinate(15, 16, 33), /*34*/ new RoadCoordinate(16, 17, 34), /*35*/ new RoadCoordinate(17, 18, 35), 
			/*36*/ new RoadCoordinate(18, 19, 36), /*37*/ new RoadCoordinate(19, 20, 37), /*38*/ new RoadCoordinate(20, 21, 38), /*39*/ new RoadCoordinate(16, 22, 39), 
			/*40*/ new RoadCoordinate(16, 23, 40), /*41*/ new RoadCoordinate(17, 23, 41), /*42*/ new RoadCoordinate(17, 24, 42), /*43*/ new RoadCoordinate(18, 24, 43), 
			/*44*/ new RoadCoordinate(18, 25, 44), /*45*/ new RoadCoordinate(19, 25, 45), /*46*/ new RoadCoordinate(19, 26, 46), /*47*/ new RoadCoordinate(20, 26, 47), 
			/*48*/ new RoadCoordinate(20, 27, 48), /*49*/ new RoadCoordinate(22, 23, 49), /*50*/ new RoadCoordinate(23, 24, 50), /*51*/ new RoadCoordinate(24, 25, 51), 
			/*52*/ new RoadCoordinate(25, 26, 52), /*53*/ new RoadCoordinate(26, 27, 53), /*54*/ new RoadCoordinate(23, 28, 54), /*55*/ new RoadCoordinate(23, 29, 55),
			/*56*/ new RoadCoordinate(24, 29, 56), /*57*/ new RoadCoordinate(24, 30, 57), /*58*/ new RoadCoordinate(25, 30, 58), /*59*/ new RoadCoordinate(25, 31, 59), 
			/*60*/ new RoadCoordinate(26, 31, 60), /*61*/ new RoadCoordinate(26, 32, 61), /*62*/ new RoadCoordinate(28, 29, 62), /*63*/ new RoadCoordinate(29, 30, 63),
			/*64*/ new RoadCoordinate(30, 31, 64), /*65*/ new RoadCoordinate(31, 32, 65), /*66*/ new RoadCoordinate(29, 33, 66), /*67*/ new RoadCoordinate(29, 34, 67), 
			/*68*/ new RoadCoordinate(30, 34, 68), /*69*/ new RoadCoordinate(30, 35, 69), /*70*/ new RoadCoordinate(31, 35, 70), /*71*/ new RoadCoordinate(31, 36, 71)
		};
		
	}
	
	public void initRoadAssociations() {
		
		roadAssociations.put(possibleRoads[0], new RoadNode(null,possibleRoads[1], null, possibleRoads[6]));
		roadAssociations.put(possibleRoads[1], new RoadNode(possibleRoads[0], null, possibleRoads[2], possibleRoads[7]));
		roadAssociations.put(possibleRoads[2], new RoadNode(null, possibleRoads[1], possibleRoads[3], possibleRoads[7]));
		roadAssociations.put(possibleRoads[3], new RoadNode(possibleRoads[2], null, possibleRoads[4], possibleRoads[8]));
		roadAssociations.put(possibleRoads[4], new RoadNode(null, possibleRoads[3], possibleRoads[5], possibleRoads[8]));
		roadAssociations.put(possibleRoads[5], new RoadNode(possibleRoads[4], null, possibleRoads[9], null));
		roadAssociations.put(possibleRoads[6], new RoadNode(null, possibleRoads[0], possibleRoads[10], possibleRoads[11]));
		roadAssociations.put(possibleRoads[7], new RoadNode(possibleRoads[1], possibleRoads[2], possibleRoads[12], possibleRoads[13]));
		roadAssociations.put(possibleRoads[8], new RoadNode(possibleRoads[3], possibleRoads[4], possibleRoads[14], possibleRoads[15]));
		roadAssociations.put(possibleRoads[9], new RoadNode(possibleRoads[5], null, possibleRoads[16], possibleRoads[17]));
		roadAssociations.put(possibleRoads[10], new RoadNode(possibleRoads[6], possibleRoads[11], null, possibleRoads[18]));
		roadAssociations.put(possibleRoads[11], new RoadNode(possibleRoads[6], possibleRoads[10], possibleRoads[12], possibleRoads[19]));
		roadAssociations.put(possibleRoads[12], new RoadNode(possibleRoads[7], possibleRoads[11], possibleRoads[13], possibleRoads[19]));
		roadAssociations.put(possibleRoads[13], new RoadNode(possibleRoads[7], possibleRoads[12], possibleRoads[14], possibleRoads[20]));
		roadAssociations.put(possibleRoads[14], new RoadNode(possibleRoads[8], possibleRoads[13], possibleRoads[15], possibleRoads[20]));
		roadAssociations.put(possibleRoads[15], new RoadNode(possibleRoads[8], possibleRoads[14], possibleRoads[16], possibleRoads[21]));
		roadAssociations.put(possibleRoads[16], new RoadNode(possibleRoads[9], possibleRoads[15], possibleRoads[17], possibleRoads[21]));
		roadAssociations.put(possibleRoads[17], new RoadNode(possibleRoads[9], possibleRoads[16], possibleRoads[22], null));
		roadAssociations.put(possibleRoads[18], new RoadNode(null, possibleRoads[10], possibleRoads[23], possibleRoads[24]));
		roadAssociations.put(possibleRoads[19], new RoadNode(possibleRoads[11], possibleRoads[12], possibleRoads[25], possibleRoads[26]));
		roadAssociations.put(possibleRoads[20], new RoadNode(possibleRoads[13], possibleRoads[14], possibleRoads[27], possibleRoads[28]));
		roadAssociations.put(possibleRoads[21], new RoadNode(possibleRoads[15], possibleRoads[16], possibleRoads[29], possibleRoads[30]));
		roadAssociations.put(possibleRoads[22], new RoadNode(possibleRoads[17], null, possibleRoads[31], possibleRoads[32]));
		roadAssociations.put(possibleRoads[23], new RoadNode(possibleRoads[18], possibleRoads[24], null, possibleRoads[33]));
		roadAssociations.put(possibleRoads[24], new RoadNode(possibleRoads[18], possibleRoads[23], possibleRoads[25], possibleRoads[34]));
		roadAssociations.put(possibleRoads[25], new RoadNode(possibleRoads[19], possibleRoads[24], possibleRoads[26], possibleRoads[34]));
		roadAssociations.put(possibleRoads[26], new RoadNode(possibleRoads[19], possibleRoads[25], possibleRoads[27], possibleRoads[35]));
		roadAssociations.put(possibleRoads[27], new RoadNode(possibleRoads[20], possibleRoads[26], possibleRoads[28], possibleRoads[35]));
		roadAssociations.put(possibleRoads[28], new RoadNode(possibleRoads[20], possibleRoads[27], possibleRoads[29], possibleRoads[36]));
		roadAssociations.put(possibleRoads[29], new RoadNode(possibleRoads[21], possibleRoads[28], possibleRoads[30], possibleRoads[36]));
		roadAssociations.put(possibleRoads[30], new RoadNode(possibleRoads[21], possibleRoads[29], possibleRoads[31], possibleRoads[37]));
		roadAssociations.put(possibleRoads[31], new RoadNode(possibleRoads[22], possibleRoads[30], possibleRoads[32], possibleRoads[37]));
		roadAssociations.put(possibleRoads[32], new RoadNode(possibleRoads[22], possibleRoads[31], possibleRoads[38], null));
		roadAssociations.put(possibleRoads[33], new RoadNode(null, possibleRoads[23], null,  possibleRoads[39]));
		roadAssociations.put(possibleRoads[34], new RoadNode(possibleRoads[24], possibleRoads[25], possibleRoads[40], possibleRoads[41]));
		roadAssociations.put(possibleRoads[35], new RoadNode(possibleRoads[26], possibleRoads[27], possibleRoads[42], possibleRoads[43]));
		roadAssociations.put(possibleRoads[36], new RoadNode(possibleRoads[28], possibleRoads[29], possibleRoads[44], possibleRoads[45]));
		roadAssociations.put(possibleRoads[37], new RoadNode(possibleRoads[30], possibleRoads[31], possibleRoads[46], possibleRoads[47]));
		roadAssociations.put(possibleRoads[38], new RoadNode(possibleRoads[32], null, possibleRoads[48], null));
		roadAssociations.put(possibleRoads[39], new RoadNode(null, possibleRoads[33], possibleRoads[40], possibleRoads[49]));
		roadAssociations.put(possibleRoads[40], new RoadNode(possibleRoads[34], possibleRoads[39], possibleRoads[41], possibleRoads[49]));
		roadAssociations.put(possibleRoads[41], new RoadNode(possibleRoads[34], possibleRoads[40], possibleRoads[42], possibleRoads[50]));
		roadAssociations.put(possibleRoads[42], new RoadNode(possibleRoads[35], possibleRoads[41], possibleRoads[43], possibleRoads[50]));
		roadAssociations.put(possibleRoads[43], new RoadNode(possibleRoads[35], possibleRoads[42], possibleRoads[44], possibleRoads[51]));
		roadAssociations.put(possibleRoads[44], new RoadNode(possibleRoads[36], possibleRoads[43], possibleRoads[45], possibleRoads[51]));
		roadAssociations.put(possibleRoads[45], new RoadNode(possibleRoads[36], possibleRoads[44], possibleRoads[46], possibleRoads[52]));
		roadAssociations.put(possibleRoads[46], new RoadNode(possibleRoads[37], possibleRoads[45], possibleRoads[47], possibleRoads[52]));
		roadAssociations.put(possibleRoads[47], new RoadNode(possibleRoads[37], possibleRoads[46], possibleRoads[48], possibleRoads[53]));
		roadAssociations.put(possibleRoads[48], new RoadNode(possibleRoads[38], null, possibleRoads[47], possibleRoads[53]));
		roadAssociations.put(possibleRoads[49], new RoadNode(possibleRoads[39], possibleRoads[40], null, possibleRoads[54]));
		roadAssociations.put(possibleRoads[50], new RoadNode(possibleRoads[41], possibleRoads[42], possibleRoads[55], possibleRoads[56]));
		roadAssociations.put(possibleRoads[51], new RoadNode(possibleRoads[43], possibleRoads[44], possibleRoads[57], possibleRoads[58]));
		roadAssociations.put(possibleRoads[52], new RoadNode(possibleRoads[45], possibleRoads[46], possibleRoads[59], possibleRoads[60]));
		roadAssociations.put(possibleRoads[53], new RoadNode(possibleRoads[47], possibleRoads[48], possibleRoads[61], null));
		roadAssociations.put(possibleRoads[54], new RoadNode(null, possibleRoads[49], possibleRoads[55], possibleRoads[62]));
		roadAssociations.put(possibleRoads[55], new RoadNode(possibleRoads[50], possibleRoads[54], possibleRoads[56], possibleRoads[62]));
		roadAssociations.put(possibleRoads[56], new RoadNode(possibleRoads[50], possibleRoads[55], possibleRoads[57], possibleRoads[63]));
		roadAssociations.put(possibleRoads[57], new RoadNode(possibleRoads[51], possibleRoads[56], possibleRoads[58], possibleRoads[63]));
		roadAssociations.put(possibleRoads[58], new RoadNode(possibleRoads[51], possibleRoads[57], possibleRoads[59], possibleRoads[64]));
		roadAssociations.put(possibleRoads[59], new RoadNode(possibleRoads[52], possibleRoads[58], possibleRoads[60], possibleRoads[64]));
		roadAssociations.put(possibleRoads[60], new RoadNode(possibleRoads[52], possibleRoads[59], possibleRoads[61], possibleRoads[65]));
		roadAssociations.put(possibleRoads[61], new RoadNode(possibleRoads[53], null, possibleRoads[60], possibleRoads[65]));
		roadAssociations.put(possibleRoads[62], new RoadNode(possibleRoads[54], possibleRoads[55], null, possibleRoads[66]));
		roadAssociations.put(possibleRoads[63], new RoadNode(possibleRoads[56], possibleRoads[57], possibleRoads[67], possibleRoads[68]));
		roadAssociations.put(possibleRoads[64], new RoadNode(possibleRoads[58], possibleRoads[59], possibleRoads[69], possibleRoads[70]));
		roadAssociations.put(possibleRoads[65], new RoadNode(possibleRoads[60], possibleRoads[61], possibleRoads[71], null));
		roadAssociations.put(possibleRoads[66], new RoadNode(null, possibleRoads[62], null, possibleRoads[67]));
		roadAssociations.put(possibleRoads[67], new RoadNode(possibleRoads[63], possibleRoads[66], possibleRoads[68], null));
		roadAssociations.put(possibleRoads[68], new RoadNode(possibleRoads[63], possibleRoads[67], null, possibleRoads[69]));
		roadAssociations.put(possibleRoads[69], new RoadNode(possibleRoads[64], possibleRoads[68], possibleRoads[70], null));
		roadAssociations.put(possibleRoads[70], new RoadNode(possibleRoads[64], possibleRoads[69], null, possibleRoads[71]));
		roadAssociations.put(possibleRoads[71], new RoadNode(possibleRoads[65], null, possibleRoads[70], null));
	}
	
	public void initBoardAssociations() {
		
		boardAssociations.put(possibleRoads[0], new PlacementNode(possiblePlacements[0], possiblePlacements[1], null));
		boardAssociations.put(possibleRoads[1], new PlacementNode(possiblePlacements[1], possiblePlacements[2], null));
		boardAssociations.put(possibleRoads[2], new PlacementNode(possiblePlacements[2], possiblePlacements[3], null));
		boardAssociations.put(possibleRoads[3], new PlacementNode(possiblePlacements[3], possiblePlacements[4], null));
		boardAssociations.put(possibleRoads[4], new PlacementNode(possiblePlacements[4], possiblePlacements[5], null));
		boardAssociations.put(possibleRoads[5], new PlacementNode(possiblePlacements[5], possiblePlacements[6], null));
		boardAssociations.put(possibleRoads[6], new PlacementNode(possiblePlacements[0], possiblePlacements[8], null));
		boardAssociations.put(possibleRoads[7], new PlacementNode(possiblePlacements[2], possiblePlacements[10], null));
		boardAssociations.put(possibleRoads[8], new PlacementNode(possiblePlacements[4], possiblePlacements[12], null));
		boardAssociations.put(possibleRoads[9], new PlacementNode(possiblePlacements[6], possiblePlacements[14], null));
		boardAssociations.put(possibleRoads[10], new PlacementNode(possiblePlacements[7], possiblePlacements[8], null));
		boardAssociations.put(possibleRoads[11], new PlacementNode(possiblePlacements[8], possiblePlacements[9], null));
		boardAssociations.put(possibleRoads[12], new PlacementNode(possiblePlacements[9], possiblePlacements[10], null));
		boardAssociations.put(possibleRoads[13], new PlacementNode(possiblePlacements[10], possiblePlacements[11], null));
		boardAssociations.put(possibleRoads[14], new PlacementNode(possiblePlacements[11], possiblePlacements[12], null));
		boardAssociations.put(possibleRoads[15], new PlacementNode(possiblePlacements[12], possiblePlacements[13], null));
		boardAssociations.put(possibleRoads[16], new PlacementNode(possiblePlacements[13], possiblePlacements[14], null));
		boardAssociations.put(possibleRoads[17], new PlacementNode(possiblePlacements[14], possiblePlacements[15], null));
		boardAssociations.put(possibleRoads[18], new PlacementNode(possiblePlacements[7], possiblePlacements[17], null));
		boardAssociations.put(possibleRoads[19], new PlacementNode(possiblePlacements[9], possiblePlacements[19], null));
		boardAssociations.put(possibleRoads[20], new PlacementNode(possiblePlacements[11], possiblePlacements[21], null));
		boardAssociations.put(possibleRoads[21], new PlacementNode(possiblePlacements[13], possiblePlacements[23], null));
		boardAssociations.put(possibleRoads[22], new PlacementNode(possiblePlacements[15], possiblePlacements[25], null));
		boardAssociations.put(possibleRoads[23], new PlacementNode(possiblePlacements[16], possiblePlacements[17], null));
		boardAssociations.put(possibleRoads[24], new PlacementNode(possiblePlacements[17], possiblePlacements[18], null));
		boardAssociations.put(possibleRoads[25], new PlacementNode(possiblePlacements[18], possiblePlacements[19], null));
		boardAssociations.put(possibleRoads[26], new PlacementNode(possiblePlacements[19], possiblePlacements[20], null));
		boardAssociations.put(possibleRoads[27], new PlacementNode(possiblePlacements[20], possiblePlacements[21], null));
		boardAssociations.put(possibleRoads[28], new PlacementNode(possiblePlacements[21], possiblePlacements[22], null));
		boardAssociations.put(possibleRoads[29], new PlacementNode(possiblePlacements[22], possiblePlacements[23], null));
		boardAssociations.put(possibleRoads[30], new PlacementNode(possiblePlacements[23], possiblePlacements[24], null));
		boardAssociations.put(possibleRoads[31], new PlacementNode(possiblePlacements[24], possiblePlacements[25], null));
		boardAssociations.put(possibleRoads[32], new PlacementNode(possiblePlacements[25], possiblePlacements[26], null));
		boardAssociations.put(possibleRoads[33], new PlacementNode(possiblePlacements[16], possiblePlacements[27], null));
		boardAssociations.put(possibleRoads[34], new PlacementNode(possiblePlacements[18], possiblePlacements[29], null));
		boardAssociations.put(possibleRoads[35], new PlacementNode(possiblePlacements[20], possiblePlacements[31], null));
		boardAssociations.put(possibleRoads[36], new PlacementNode(possiblePlacements[22], possiblePlacements[33], null));
		boardAssociations.put(possibleRoads[37], new PlacementNode(possiblePlacements[24], possiblePlacements[35], null));
		boardAssociations.put(possibleRoads[38], new PlacementNode(possiblePlacements[26], possiblePlacements[37], null));
		boardAssociations.put(possibleRoads[39], new PlacementNode(possiblePlacements[27], possiblePlacements[28], null));
		boardAssociations.put(possibleRoads[40], new PlacementNode(possiblePlacements[28], possiblePlacements[29], null));
		boardAssociations.put(possibleRoads[41], new PlacementNode(possiblePlacements[29], possiblePlacements[30], null));
		boardAssociations.put(possibleRoads[42], new PlacementNode(possiblePlacements[30], possiblePlacements[31], null));
		boardAssociations.put(possibleRoads[43], new PlacementNode(possiblePlacements[31], possiblePlacements[32], null));
		boardAssociations.put(possibleRoads[44], new PlacementNode(possiblePlacements[32], possiblePlacements[33], null));
		boardAssociations.put(possibleRoads[45], new PlacementNode(possiblePlacements[33], possiblePlacements[34], null));
		boardAssociations.put(possibleRoads[46], new PlacementNode(possiblePlacements[34], possiblePlacements[35], null));
		boardAssociations.put(possibleRoads[47], new PlacementNode(possiblePlacements[35], possiblePlacements[36], null));
		boardAssociations.put(possibleRoads[48], new PlacementNode(possiblePlacements[36], possiblePlacements[37], null));
		boardAssociations.put(possibleRoads[49], new PlacementNode(possiblePlacements[28], possiblePlacements[38], null));
		boardAssociations.put(possibleRoads[50], new PlacementNode(possiblePlacements[30], possiblePlacements[40], null));
		boardAssociations.put(possibleRoads[51], new PlacementNode(possiblePlacements[32], possiblePlacements[42], null));
		boardAssociations.put(possibleRoads[52], new PlacementNode(possiblePlacements[34], possiblePlacements[44], null));
		boardAssociations.put(possibleRoads[53], new PlacementNode(possiblePlacements[36], possiblePlacements[46], null));
		boardAssociations.put(possibleRoads[54], new PlacementNode(possiblePlacements[38], possiblePlacements[39], null));
		boardAssociations.put(possibleRoads[55], new PlacementNode(possiblePlacements[39], possiblePlacements[40], null));
		boardAssociations.put(possibleRoads[56], new PlacementNode(possiblePlacements[40], possiblePlacements[41], null));
		boardAssociations.put(possibleRoads[57], new PlacementNode(possiblePlacements[41], possiblePlacements[42], null));
		boardAssociations.put(possibleRoads[58], new PlacementNode(possiblePlacements[42], possiblePlacements[43], null));
		boardAssociations.put(possibleRoads[59], new PlacementNode(possiblePlacements[43], possiblePlacements[44], null));
		boardAssociations.put(possibleRoads[60], new PlacementNode(possiblePlacements[44], possiblePlacements[45], null));
		boardAssociations.put(possibleRoads[61], new PlacementNode(possiblePlacements[45], possiblePlacements[46], null));
		boardAssociations.put(possibleRoads[62], new PlacementNode(possiblePlacements[39], possiblePlacements[47], null));
		boardAssociations.put(possibleRoads[63], new PlacementNode(possiblePlacements[41], possiblePlacements[49], null));
		boardAssociations.put(possibleRoads[64], new PlacementNode(possiblePlacements[43], possiblePlacements[51], null));
		boardAssociations.put(possibleRoads[65], new PlacementNode(possiblePlacements[45], possiblePlacements[53], null));
		boardAssociations.put(possibleRoads[66], new PlacementNode(possiblePlacements[47], possiblePlacements[48], null));
		boardAssociations.put(possibleRoads[67], new PlacementNode(possiblePlacements[48], possiblePlacements[49], null));
		boardAssociations.put(possibleRoads[68], new PlacementNode(possiblePlacements[49], possiblePlacements[50], null));
		boardAssociations.put(possibleRoads[69], new PlacementNode(possiblePlacements[50], possiblePlacements[51], null));
		boardAssociations.put(possibleRoads[70], new PlacementNode(possiblePlacements[51], possiblePlacements[52], null));
		boardAssociations.put(possibleRoads[71], new PlacementNode(possiblePlacements[52], possiblePlacements[53], null));
		
	}

}
