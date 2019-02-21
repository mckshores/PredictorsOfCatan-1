package CatanSrc;

import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlayerTest {
	
	Board gameBoard = new Board();
	Player player1 = new Player(gameBoard);

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testSetUp() {
		
		PlacementCoordinate[] placements = player1.getPlacements();
		assertTrue(Arrays.equals(placements, new PlacementCoordinate[] {null, null, null, null, null, null, null, null, null}));
		int roads = player1.getRoads();
		assertTrue(roads == 0);
		Hand hand = player1.getHand();
		assertTrue(hand.isEmpty());
		int vpTotal = player1.getVP();
		assertTrue(vpTotal == 0);
		
	}

}