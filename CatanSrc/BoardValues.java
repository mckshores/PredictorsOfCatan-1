package CatanSrc;

public class BoardValues {
	
	private int[] boardValues;
	private final int[] maxAllowed;
	
	public BoardValues() {
		
		boardValues = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		maxAllowed = new int[] {0, 0, 1, 2, 2, 2, 2, 0, 2, 2, 2, 2, 1};
		
	}
	
	public int[] getBoardValues() { return boardValues; }
	public int[] getMaxAllowed() { return maxAllowed; }
	
	public boolean setBoardValue(int index, int value) {
		
		if(index != 7 && index != 1 && index != 0) {
			if(!(boardValues[index] + (value - boardValues[index]) > maxAllowed[index])) {
				boardValues[index] = value;
				return true;
			}
		}
		return false;
		
	}
	
}