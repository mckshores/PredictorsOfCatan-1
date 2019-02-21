package CatanSrc;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BoardTypesTest {

	BoardTypes boardTypes;

	@BeforeEach
	void setUp() throws Exception {
		
		 boardTypes = new BoardTypes();
		
	}

	@AfterEach
	void tearDown() throws Exception {
		
		boardTypes = null;
		
	}

	@Test
	void testGetBoardValues() {
		
		assertTrue(boardTypes.getCurrentTypes().get("livestock") == 0);
		assertTrue(boardTypes.getCurrentTypes().get("lumber") == 0);
		assertTrue(boardTypes.getCurrentTypes().get("grain") == 0);
		assertTrue(boardTypes.getCurrentTypes().get("ore") == 0);
		assertTrue(boardTypes.getCurrentTypes().get("brick") == 0);
		assertTrue(boardTypes.getCurrentTypes().get("desert") == 0);
		
	}

	@Test
	void testSetBoardType() {
		boardTypes.setBoardType("livestock");
		assertTrue(boardTypes.getCurrentTypes().get("livestock") == 1);
		boardTypes.setBoardType("livestock");
		assertTrue(boardTypes.getCurrentTypes().get("livestock") == 2);
		boardTypes.setBoardType("livestock");
		assertTrue(boardTypes.getCurrentTypes().get("livestock") == 3);
		boardTypes.setBoardType("livestock");
		assertTrue(boardTypes.getCurrentTypes().get("livestock") == 4);
		assertFalse(boardTypes.setBoardType("livestock"));
		boardTypes.setBoardType("desert");
		assertTrue(boardTypes.getCurrentTypes().get("desert") == 1);
		assertFalse(boardTypes.setBoardType("desert"));
	}

}
