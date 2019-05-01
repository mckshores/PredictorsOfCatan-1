package CatanSrc;

public class PlacementNode {
	
	//This object keeps track of 3 PlacementCoordinates at once and is used to understand the associations between them
	private final PlacementCoordinate association1;
	private final PlacementCoordinate association2;
	private final PlacementCoordinate association3;
	
	//Constructor
	public PlacementNode(PlacementCoordinate asoc1, PlacementCoordinate asoc2, PlacementCoordinate asoc3) {
		association1 = asoc1;
		association2 = asoc2;
		association3 = asoc3;
	}
	
	//Getter
	public PlacementCoordinate[] getAssociations() { return new PlacementCoordinate[] {association1, association2, association3}; } 
	
}