package CatanSrc;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PortTest {
	
	private Board board;
	private Port brickPort;
	private Port triplePort;

	@BeforeEach
	void setUp() throws Exception {
		
		board = new Board();
		brickPort = new Port("brick", new PlacementCoordinate(8,13,14), new PlacementCoordinate(7,8,13));
		triplePort = new Port("triple", new PlacementCoordinate(8,13,14), new PlacementCoordinate(7,8,13));
		
	}

	@AfterEach
	void tearDown() throws Exception {
		
		board = null;
		brickPort = null;
		triplePort = null;
		
	}

	@Test
	void testPortTrade() {
		
		int brickSize = board.getBrickDeck().size();
		int grainSize = board.getGrainDeck().size();
		Card retCard = brickPort.portTrade(new Card[] {new Card("brick"), new Card("brick")}, board.getGrainDeck(), board.getBrickDeck());
		assertTrue(retCard.getType() == "grain");
		assertTrue(board.getBrickDeck().size() == brickSize + 2);
		assertTrue(board.getGrainDeck().size() == grainSize - 1);
		
		int livestockSize = board.getLivestockDeck().size();
		int lumberSize = board.getLumberDeck().size();
		retCard = triplePort.portTrade(new Card[] {new Card("livestock"), new Card("livestock"), new Card("livestock")}, board.getLumberDeck(), board.getLivestockDeck());
		assertTrue(retCard.getType() == "lumber");
		assertTrue(board.getLivestockDeck().size() == livestockSize + 3);
		assertTrue(board.getLumberDeck().size() == lumberSize -1);
		
		retCard = brickPort.portTrade(new Card[] {new Card("ore"), new Card("brick")}, board.getGrainDeck(), board.getBrickDeck());
		assertTrue(retCard == null);
		
		retCard = brickPort.portTrade(new Card[] {new Card("ore"), new Card("ore")}, board.getGrainDeck(), board.getBrickDeck());
		assertTrue(retCard == null);
		
		retCard = brickPort.portTrade(new Card[] {new Card("brick"), new Card("brick"), new Card("brick")}, board.getGrainDeck(), board.getBrickDeck());
		assertTrue(retCard == null);
		
		retCard = triplePort.portTrade(new Card[] {new Card("ore"), new Card("ore")}, board.getGrainDeck(), board.getBrickDeck());
		assertTrue(retCard == null);
		
		retCard = triplePort.portTrade(new Card[] {new Card("grain"), new Card("ore"), new Card("ore")}, board.getGrainDeck(), board.getBrickDeck());
		assertTrue(retCard == null);
		
		retCard = triplePort.portTrade(new Card[] {new Card("ore"), new Card("ore"), new Card("ore"), new Card("ore")}, board.getGrainDeck(), board.getBrickDeck());
		assertTrue(retCard == null);
		
	}

}