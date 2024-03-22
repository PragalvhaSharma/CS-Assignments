
public class FoodHexagon extends Hexagon {
	
	private int numFlies;        // Number of flies represented by this object
	
	public FoodHexagon (int cellID, int numFlies) {
		super(cellID);
		switch (numFlies) {
			case 1:
				setType(CellType.FLY1);
				break;
			case 2:
				setType(CellType.FLIES2);
				break;
			case 3:
				setType(CellType.FLIES3);
				break;
		}
		
		this.numFlies = numFlies;
	}
	
	public int getNumFlies () {
		return numFlies;
	}
	
	public void removeFlies () {
		// This is used in the search algorithm when the frog eats flies from a FoodHexagon.
		numFlies = 0;
	}

}
