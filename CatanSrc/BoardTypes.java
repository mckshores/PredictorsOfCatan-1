package CatanSrc;

import java.util.Hashtable;

public class BoardTypes {
	
	private final Hashtable<String, Integer> typesAllowed = new Hashtable<String, Integer>();
	private Hashtable<String, Integer> currentTypes = new Hashtable<String, Integer>();
	
	public BoardTypes() {
		
		typesAllowed.put("livestock",4);
		typesAllowed.put("lumber", 4);
		typesAllowed.put("brick", 3);
		typesAllowed.put("grain", 4);
		typesAllowed.put("ore", 3);
		typesAllowed.put("desert", 1);
		
		currentTypes.put("livestock",0);
		currentTypes.put("lumber", 0);
		currentTypes.put("brick", 0);
		currentTypes.put("grain", 0);
		currentTypes.put("ore", 0);
		currentTypes.put("desert", 0);
		
	}
	
	public Hashtable<String, Integer> getTypesAllowed() { return typesAllowed; }
	public Hashtable<String, Integer> getCurrentTypes() { return currentTypes; }
	
	public boolean setBoardType(String value) {
		
		if(currentTypes.get(value) + 1 <= typesAllowed.get(value)) {
			currentTypes.put(value, currentTypes.get(value) + 1);
			return true;
		}
		return false;
		
	}
	
}