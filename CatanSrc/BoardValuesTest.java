package CatanSrc;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;

class BoardValuesTest {
	
	BoardValues boardValues;

	@BeforeEach
	void setUp() throws Exception {
		
		 boardValues = new BoardValues();
		
	}

	@AfterEach
	void tearDown() throws Exception {
		
		boardValues = null;
		
	}

	@Test
	void testGetBoardValues() {
		
		assertTrue(Arrays.equals(boardValues.getBoardValues(), new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}));
		
	}
	
	@Test
	void testSetBoardValue() {
		
		boardValues.setBoardValue(0, 1);
		assertFalse(Arrays.equals(boardValues.getBoardValues(), new int[] {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}));
		boardValues.setBoardValue(1, 2);
		assertFalse(Arrays.equals(boardValues.getBoardValues(), new int[] {0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}));
		boardValues.setBoardValue(7, 1);
		assertFalse(Arrays.equals(boardValues.getBoardValues(), new int[] {0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0}));
		boardValues.setBoardValue(2, 3);
		assertFalse(Arrays.equals(boardValues.getBoardValues(), new int[] {0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}));
		boardValues.setBoardValue(6, 60);
		assertFalse(Arrays.equals(boardValues.getBoardValues(), new int[] {0, 0, 0, 0, 0, 0, 60, 0, 0, 0, 0, 0, 0}));
		boardValues.setBoardValue(10, 1);
		assertTrue(Arrays.equals(boardValues.getBoardValues(), new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0}));
		boardValues.setBoardValue(3, 2);
		assertTrue(Arrays.equals(boardValues.getBoardValues(), new int[] {0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 1, 0, 0}));
		boardValues.setBoardValue(3, 0);
		assertTrue(Arrays.equals(boardValues.getBoardValues(), new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0}));
		boardValues.setBoardValue(10, 2);
		assertTrue(Arrays.equals(boardValues.getBoardValues(), new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0}));
		
	}

}
