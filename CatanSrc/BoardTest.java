package CatanSrc;

import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BoardTest {
	
	private Board gameBoard;

	@BeforeEach
	void setUp() throws Exception {
		gameBoard = new Board();
	}

	@AfterEach
	void tearDown() throws Exception {
		gameBoard = null;
	}

	@Test
	void testGetDecks() {
		
		Stack<Card> gameDeck = gameBoard.getLivestockDeck();
		assertTrue(gameDeck.pop().getType() == "livestock");
		gameDeck = gameBoard.getLumberDeck();
		assertTrue(gameDeck.pop().getType() == "lumber");
		gameDeck = gameBoard.getOreDeck();
		assertTrue(gameDeck.pop().getType() == "ore");
		gameDeck = gameBoard.getGrainDeck();
		assertTrue(gameDeck.pop().getType() == "grain");
		gameDeck = gameBoard.getBrickDeck();
		assertTrue(gameDeck.pop().getType() == "brick");
		gameDeck = gameBoard.getDevelopmentDeck();
		String gameCard = gameDeck.pop().getType();
		assertTrue(gameCard == "monopoly" || gameCard == "yearofplenty" || gameCard == "roadbuilding" || gameCard == "victorypoint" || gameCard == "knight");
		
	}
	
	@Test
	void testBoard() {
		
		BoardHex[] board = gameBoard.getBoardTiles();
		assertTrue(board.length == 37);
		int livestockCount = 0, lumberCount = 0, grainCount = 0, oreCount = 0, brickCount = 0, desertCount = 0, borderCount = 0;
		for(BoardHex hex : board) {
			if(hex.getType() == "livestock") {
				livestockCount ++;
				assertTrue(hex.getValue() > 1 && hex.getValue() < 13 && hex.getValue() != 7);
				assertTrue(correctProbability(hex.getValue(), hex.getProbability()));
			}
			if(hex.getType() == "lumber") {
				lumberCount ++;
				assertTrue(hex.getValue() > 1 && hex.getValue() < 13 && hex.getValue() != 7);
				assertTrue(correctProbability(hex.getValue(), hex.getProbability()));
			}
			if(hex.getType() == "grain") {
				grainCount ++;
				assertTrue(hex.getValue() > 1 && hex.getValue() < 13 && hex.getValue() != 7);
				assertTrue(correctProbability(hex.getValue(), hex.getProbability()));
			}
			if(hex.getType() == "ore") {
				oreCount ++;
				assertTrue(hex.getValue() > 1 && hex.getValue() < 13 && hex.getValue() != 7);
				assertTrue(correctProbability(hex.getValue(), hex.getProbability()));
			}
			if(hex.getType() == "brick") {
				brickCount ++;
				assertTrue(hex.getValue() > 1 && hex.getValue() < 13 && hex.getValue() != 7);
				assertTrue(correctProbability(hex.getValue(), hex.getProbability()));
			}
			if(hex.getType() == "desert") {
				desertCount ++;
				if(hex.isBorder())
					borderCount ++;
				assertTrue(hex.getValue() == 0);
			}
		}
		assertTrue(livestockCount == 4 && lumberCount == 4 && grainCount == 4 && oreCount == 3 && brickCount == 3 && desertCount == 19 && borderCount == 18);
		
	}
	
	public boolean correctProbability(int value, int prob) {
		
		if(value == 2 && prob == 1)
			return true;
		if(value == 3 && prob == 2)
			return true;
		if(value == 4 && prob == 3)
			return true;
		if(value == 5 && prob == 4)
			return true;
		if(value == 6 && prob == 5)
			return true;
		if(value == 8 && prob == 5)
			return true;
		if(value == 9 && prob == 4)
			return true;
		if(value == 10 && prob == 3)
			return true;
		if(value == 11 && prob == 2)
			return true;
		if(value == 12 && prob == 1)
			return true;
		
		return false;
		
	}
	
	@Test
	public void testRobber() {
		
		assertTrue(gameBoard.getRobber().getType() == "desert");
		gameBoard.setRobber(gameBoard.getBoardTiles()[18]);
		assertFalse(gameBoard.getRobber().getType() == "desert");
		
	}

}
