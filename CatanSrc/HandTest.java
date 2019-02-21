package CatanSrc;

import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HandTest {
	
	Hand hand = new Hand();

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testVectors() {
		
		Vector<Card> grain = hand.getGrainVector();
		Vector<Card> lumber = hand.getLumberVector();
		Vector<Card> livestock = hand.getLivestockVector();
		Vector<Card> brick = hand.getBrickVector();
		Vector<Card> ore = hand.getOreVector();
		Vector<Card> dev = hand.getDevelopmentVector();
		assertTrue(grain.isEmpty());
		assertTrue(lumber.isEmpty());
		assertTrue(livestock.isEmpty());
		assertTrue(brick.isEmpty());
		assertTrue(ore.isEmpty());
		assertTrue(dev.isEmpty());
		
	}
	
	@Test
	void testAdd() {
		
		hand.add(new Card[] {new Card("lumber")});
		assertTrue(hand.getLumberVector().size() == 1);
		assertTrue(hand.getGrainVector().isEmpty());
		assertTrue(hand.getLivestockVector().isEmpty());
		assertTrue(hand.getOreVector().isEmpty());
		assertTrue(hand.getBrickVector().isEmpty());
		assertTrue(hand.getDevelopmentVector().isEmpty());
		
	}
	
	@Test
	void testPlay() {
		
		hand.add(new Card[] {new Card("livestock"), new Card("grain"), new Card("ore"), new Card("lumber")});
		Vector<Card> cards = hand.play(new Card[] {new Card("livestock"), new Card("grain"), new Card("ore")});
		assertTrue(hand.getGrainVector().isEmpty());
		assertTrue(hand.getLumberVector().size() == 1);
		assertTrue(hand.getLivestockVector().isEmpty());
		assertTrue(hand.getOreVector().isEmpty());
		assertTrue(hand.getBrickVector().isEmpty());
		assertTrue(hand.getDevelopmentVector().isEmpty());
		assertTrue(cards.contains(new Card("ore")) && cards.contains(new Card("livestock")) && cards.contains(new Card("grain")));
		
	}
	
	@Test
	void testTooLarge() {
		
		hand.add(new Card[] {new Card("lumber"), new Card("brick"), new Card("brick"), new Card("livestock"), new Card("grain"), new Card("livestock"), new Card("brick")});
		hand.tooLarge();
		assertTrue(hand.size() == 4);
		hand.tooLarge();
		assertTrue(hand.size() == 4);
		
	}
	
	@Test
	void testRobbery() {
		
		hand.robbery();
		assertTrue(hand.size() == 3);
		
	}

}
