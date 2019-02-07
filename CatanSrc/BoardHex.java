package CatanSrc;

import java.util.*;

public class BoardHex {
	
	int value = 0;
	int probability = 0;
	int[] maxAllowed;
	String type;
	
	public BoardHex(String t, int[] currentValues) {
		
		type = t;
		if(type != "dessert") {
			value = getValue(currentValues);
			probability = getProb(value);
		}
		maxAllowed = new int[] {0, 0, 1, 2, 2, 2, 2, 0, 2, 2, 2, 2, 1};
	}
	
	public int getValue() { return value; }
	public int getProbability() { return probability; }
	public String getType() { return type; }
	
	public int getValue(int[] currentValues) {
		
		int retVal = 0;
		while (true) {
			Random rand = new Random();
			int randNum = rand.nextInt(13);
			if(!(currentValues[randNum] + 1 > maxAllowed[randNum])) {
				retVal = randNum;
				break;
			}
		}
		return retVal;
		
	}
	
	public int getProb(int value) {
		
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