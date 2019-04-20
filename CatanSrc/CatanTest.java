package CatanSrc;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CatanTest {
	
	CatanGame game = new CatanGame();

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testDieRoll() {
		
		for(int i = 0; i < 100; i++) {
			int roll = game.rollDice();
			assertTrue(roll > 1 && roll < 13);
		}
		
	}

}