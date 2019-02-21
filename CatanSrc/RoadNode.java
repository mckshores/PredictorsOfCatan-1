package CatanSrc;

public class RoadNode {
	
	private final RoadCoordinate association1;
	private final RoadCoordinate association2;
	private final RoadCoordinate association3;
	private final RoadCoordinate association4;
	
	public RoadNode(RoadCoordinate asoc1, RoadCoordinate asoc2, RoadCoordinate asoc3, RoadCoordinate asoc4) {
		
		association1 = asoc1;
		association2 = asoc2;
		association3 = asoc3;
		association4 = asoc4;
		
	}
	
	public RoadCoordinate[] getAssociations() { return new RoadCoordinate[] {association1, association2, association3, association4}; } 
	
}