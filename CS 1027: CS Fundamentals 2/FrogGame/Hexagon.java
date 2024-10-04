import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

/**
 * This class represents a pointed-top Hexagon cell used to make up a pond.
 * The neighbors of a cell are accessed using getNeighbour(index) with an index 0-5 inclusive.
 * The hexagons are pointed-top in orientation, the 0 index is the
 * upper-right side
 * Indexes for the sides progress incrementally clockwise from the 0 index,
 * to 5 on the upper-left side
 * Eg.
 *     5 /     \ 0
 *     4 |     | 1
 *     3 \     / 2
 * 
 * @author CS1027
 *
 */

public class Hexagon extends CellComponent {

	private int ID; // Give an ID to each cell.
	private boolean isStart;
	private boolean isEnd;
	private boolean isWater;
	private boolean isLilypad;
	private boolean isReeds;
	private boolean isAlligator;
	private boolean isMud;
	private Hexagon[] neighbors; // Stores the cells neighboring THIS one
	private int numNeighbours = 6; 
	private boolean inStack;
	private boolean outStack;
	
	
	public static int TIME_DELAY = 1000; // Time that the program waits before moving to an adjacent cell
	
	public Hexagon (int cellID) {
		ID = cellID;

		
		int size = 172;
		double heightToWidthRatio = 0.75; //0.866;
		setSize((int)(size*heightToWidthRatio), size);
		calculateCoords();
		
		inStack = false;
		outStack = false;
		
		// allocate space for the neighbor array
		this.neighbors = new Hexagon[numNeighbours];
		
		this.setToolTipText("ID: " + ID);
		this.add(new JLabel("ID: " + ID));
	}
	
	public Hexagon (int cellID, CellType cType) {
		this(cellID);
		setType(cType);
	}
	
	public void setType(CellType type) {
		super.setType(type);
		isStart = (type == CellType.START);
		isEnd = (type == CellType.END);
		isWater = (type == CellType.WATER);
		 // Starting cell and ending cell are also lilypad cells.
		isLilypad = (type == CellType.LILYPAD) || (type == CellType.START) || (type == CellType.END);
		isReeds = (type == CellType.REEDS);
		isMud = (type == CellType.MUD);
		isAlligator = (type == CellType.GATOR);
	}
	
	
	
	
	/**
	 * Set the neighbor for this cell using the neighbor index.
	 * 
	 * The index for the neighbor indicates which side of the square this new
	 * neighbor is on: 0-3 inclusive.
	 * 
	 * @param neighbor
	 *            The new cell neighbor
	 * @param i
	 *            The index specifying which side this neighbor is on (0-3 inclusive)
	 * @throws InvalidNeighbourIndexException
	 *             When an index is specified that is not 0-3.
	 */
	public void setNeighbour(Hexagon neighbor, int i) throws InvalidNeighbourIndexException {
		if (0 <= i && i < numNeighbours)
			this.neighbors[i] = neighbor;
		else
			throw new InvalidNeighbourIndexException(i);
	}
	
	/**
	 * Returns the neighbor for this cell using the neighbor index.
	 * 
	 * The index for the neighbor indicates in which side of the cell the
	 * neighbor is: 0-3.
	 * 
	 * @param i
	 *            The index of the neighbor
	 * @return The cell that is on the i-th side of the current cell, or null if
	 *         no neighbor
	 * @throws InvalidNeighbourIndexException
	 *             When an index is specified that is not 0-3.
	 */
	public Hexagon getNeighbour(int i) throws InvalidNeighbourIndexException {
		if (0 <= i && i < numNeighbours)
			return this.neighbors[i];
		else
			throw new InvalidNeighbourIndexException(i);
	}
	


	public boolean isMarked() {
		// Checks if cell is marked in stack OR out of stack.
		return inStack || outStack;
	}
	
	public boolean isMarkedInStack() {
		return inStack;
	}
	
	public boolean isMarkedOutStack() {
		return outStack;
	}


	public boolean isStart() {
		return this.isStart;
	}
	
	public boolean isEnd() {
		return this.isEnd;
	}
	
	public boolean isAlligator() {
		return this.isAlligator;
	}
	
	public boolean isLilyPadCell() {
		return this.isLilypad;
	}
	
	public boolean isReedsCell() {
		return this.isReeds;
	}
	
	public boolean isWaterCell() {
		return this.isWater;
	}
	
	public boolean isMudCell() {
		return this.isMud;
	}

	
	
	/**
	 * This method re-draws the current cell.
	 */
	private void reDraw() {
		try {
			// Time delay is used to animate the algorithm so we can watch it step by step.
			Thread.sleep(Hexagon.TIME_DELAY);
		} catch (Exception e) {
			System.err.println("Error while issuing time delay\n" + e.getMessage());
		}
		super.repaint();
	}

	/**
	 * This method marks the cell as in-stack and updates the cell's border colour
	 */
	public void markInStack() {
		inStack = true;
		super.markInStack();
		reDraw();
	}

	/**
	 * This method marks the cell as popped and updates the cell's border colour
	 */
	public void markOutStack() {
		inStack = false;
		outStack = true;
		super.markOutStack();
		reDraw();
	}
	
	//public void removeMark () {
	//	inStack = false;
	//	outStack = false;
	//	super.markOutStack();
	//	reDraw();
	//}
	
	public String toString () {
		return String.valueOf(ID);
	}
	
	public int getID () {
		return ID;
	}
	
}
