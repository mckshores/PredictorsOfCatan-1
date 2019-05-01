package CatanSrc;

public class Card {
	
	private String type;
	
	//Constructor
	public Card(String t) {
		type = t;
	}
	
	//Getter
	public String getType() { return type; }
	
	//Overriding the equals method allow us to make comparisons between cards
	@Override
	public boolean equals(Object o) {
		if (o == this) 
			return true;
		if(!(o instanceof Card))
			return false;
		Card c = (Card) o;
		return type.equals(c.getType());
	}
	
	//Any time you override the equals method you must also override the hashCode method or objects of this type will not hash properly
	@Override
	public int hashCode() {
		int hash = 0;
		for(int i = 0; i < type.length(); i++) {
			hash += (int)type.charAt((i));
		}
		return hash;
	}
	
}
