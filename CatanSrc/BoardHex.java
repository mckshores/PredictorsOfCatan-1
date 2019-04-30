package CatanSrc;

import java.util.*;

public class BoardHex {
	
	private int value = 0;
	private int probability = 0;
	private String type;
	private boolean border = false;
	
	//Constructor
	public BoardHex(String t, Board gameBoard) {
		type = t;
		if(type != "desert") {
			value = createBoardValue(gameBoard);
			probability = createBoardProbability(value);
		}	
	}
	
	//Getters and Setters
	public int getValue() { return value; }
	public int getProbability() { return probability; }
	public String getType() { return type; }
	public boolean isBorder() { return border; }
	public void setBorder(boolean b) { border = b; }
	
	//Create a value to be associated with this BoardHex
	//There are only a set number of each value on the board so this method also makes sure those number are repected.
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
	
	//The probability of a BoardHex is the number of ways two dice can be rolled to produce that value. 
	//EX: Probability for 2 is 1 (Die1 = 1, Die2 = 1) and probability for 4 is 3 (Die1 = 1, Die2 = 3) or (Die1 = 3, Die2 = 1) or (Die1 = 2, Die2 = 2), etc.
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