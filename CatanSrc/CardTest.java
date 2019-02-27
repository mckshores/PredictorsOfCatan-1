package CatanSrc;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CardTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testEquals() {
		Card card1 = new Card("livestock");
		Card card2 = new Card("grain");
		assertTrue(card1.equals(new Card("livestock")));
		assertTrue(card2.equals(new Card("grain")));
		assertFalse(card1.equals(card2));
	}
	
	@Test
	void testHashCode() {
		Card card1 = new Card("livestock");
		Card card2 = new Card("grain");
		int code1 = card1.hashCode();
		int code2 = card2.hashCode();
		assertFalse(code1 == code2);
	}

}
