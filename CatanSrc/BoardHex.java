package CatanSrc;

import java.util.*;

public class BoardHex {
	
	private int value = 0;
	private int probability = 0;
	private String type;
	private boolean border = false;
	
	public BoardHex(String t, Board gameBoard) {
		
		type = t;
		if(type != "desert") {
			value = createBoardValue(gameBoard);
			probability = createBoardProbability(value);
		}
		
	}
	
	public int getValue() { return value; }
	public int getProbability() { return probability; }
	public String getType() { return type; }
	public boolean isBorder() { return border; }
	public void setBorder(boolean b) { border = b; }
	
	public int createBoardValue(Board gameBoard) {
		
		
		BoardValues values = gameBoard.getBoardValues();
		int retVal = 0;
		while (true) {
			Random rand = new Random();
			int randNum = rand.nextInt(13);
			if(values.setBoardValue(randNum, (values.getBoardValues()[randNum]) + 1)) {
				retVal = randNum;
				break;
			}
			if(Arrays.equals(values.getBoardValues(), values.getMaxAllowed()))
				break;
		}
		return retVal;
		
	}
	
	public int createBoardProbability(int value) {
		
		switch (value) {
		case 2: 
			return 1;
		case 3: 
			return 2;
		case 4: 
			return 3;
		case 5: 
			return 4;
		case 6: 
			return 5;
		case 8: 
			return 5;
		case 9: 
			return 4;
		case 10: 
			return 3;
		case 11: 
			return 2;
		case 12: 
			return 1;
		default: 
			return 0;
		}
		
	}
	
}