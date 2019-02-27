package CatanSrc;

public class Card {
	
	private String type;
	
	public Card(String t) {
		
		type = t;
		
	}
	
	public String getType() { return type; }
	
	@Override
	public boolean equals(Object o) {
		
		if (o == this) 
			return true;
		if(!(o instanceof Card))
			return false;
		Card c = (Card) o;
		return type.equals(c.getType());
		
	}
	
	@Override
	public int hashCode() {
		int hash = 0;
		for(int i = 0; i < type.length(); i++) {
			hash += (int)type.charAt((i));
		}
		return hash;
	}
	
}