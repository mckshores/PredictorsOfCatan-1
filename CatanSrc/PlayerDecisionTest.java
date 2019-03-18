package CatanSrc;

import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlayerDecisionTest {
	
	private Board board = new Board();
	private Player player = new Player(board);
	private PlayerDecision decision = new PlayerDecision();

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testMakeDecision() {
		player.setHand(new Card[] {});
		assertTrue(decision.makeDecision(player) == null);
		player.clearHand();
		player.setHand(new Card[] {new Card("knight")});
		assertTrue(Arrays.equals(decision.makeDecision(player), new int[] {0}));
		player.clearHand();
		player.setHand(new Card[] {new Card("victorypoint")});
		assertTrue(decision.makeDecision(player) == null);
		player.clearHand();
		player.setHand(new Card[] {new Card("victorypoint"), new Card("knight"), new Card("victorypoint")});
		assertTrue(Arrays.equals(decision.makeDecision(player), new int[] {0}));
		player.clearHand();
		player.setHand(new Card[] {new Card("grain"), new Card("grain"), new Card("ore"), new Card("ore"), new Card("ore")});
		assertTrue(Arrays.equals(decision.makeDecision(player), new int[] {1}));
		player.clearHand();
		player.setHand(new Card[] {new Card("grain"), new Card("lumber"), new Card("brick"), new Card("livestock")});
		assertTrue(Arrays.equals(decision.makeDecision(player), new int[] {2}));
		player.clearHand();
		player.setHand(new Card[] {new Card("grain"), new Card("ore"), new Card("brick"), new Card("livestock")});
		assertTrue(Arrays.equals(decision.makeDecision(player), new int[] {3}));
		player.clearHand();
		player.setHand(new Card[] {new Card("ore"), new Card("lumber"), new Card("brick"), new Card("livestock")});
		assertTrue(Arrays.equals(decision.makeDecision(player), new int[] {4}));
		player.clearHand();
		player.setHand(new Card[] {new Card("lumber"), new Card("lumber"), new Card("lumber"), new Card("lumber")});
		assertTrue(Arrays.equals(decision.makeDecision(player), new int[] {5}));
		player.clearHand();
		player.setHand(new Card[] {new Card("grain"), new Card("lumber"), new Card("brick"), new Card("livestock"), new Card("lumber"), new Card("lumber"), new Card("brick"), new Card("brick"), new Card("grain"), new Card("ore"), new Card("brick"), new Card("livestock")});
		assertTrue(Arrays.equals(decision.makeDecision(player), new int[] {2, 3, 4}));
	}

}