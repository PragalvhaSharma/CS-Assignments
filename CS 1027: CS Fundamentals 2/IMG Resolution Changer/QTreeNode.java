
// QTreeNode represents a node in a QuadrantTree data structure
public class QTreeNode {
	
	//Instance Variables
	private int x;
	private int y;
	private int size;
	private int color;
	private QTreeNode parent;
	private QTreeNode[] children; 
	
	//Constructor without parameter with default values and no children
	public QTreeNode() {
		parent = null;
		children = new QTreeNode[4];
		x= 0;
		y=0;
		size=0;
		color=0;
	}
	
	//Constructor with parameters parameters with specified characteristics and children
	public QTreeNode(QTreeNode[] theChildren, int xcoord, int ycoord, int theSize, int theColor) {
		parent = null;
		children = theChildren;
		x = xcoord;
		y = ycoord;
		size = theSize;
		color = theColor;
	}
	
	// Checks if this node contains a point
	public boolean contains(int xcoord, int ycoord) {
		//Checking x
		if((xcoord  >= x && xcoord <= (x + size -1)) && (ycoord >= y && ycoord <= (y+size-1)) ) {
			return true;}
		
		return false;
	
		}
	
	// Getter for the x-coordinate
	public int getx() {
		return x;
	}
	
	// Getter for the y-coordinate
	public int gety() {
		return y;
	}
	
	//Getter for the size
	public int getSize() {
		return size;
	}
	
	// Getter for the color
	public int getColor() {
		return color;
	}
	
	// Getter for the parent node
	public QTreeNode getParent() {
		return parent;
	}
	
	// Retrieves a child node at the specified index
	public QTreeNode getChild(int index) throws QTreeException {
		if (children == null || index < 0 || index > 3) {
			//if index is out of bounds
			throw new QTreeException("Error for index: " + index);
			}
		return children[index];
	}
	
	// Setter for x
	public void setx(int newx) {
		x = newx;
	}
	
	// Setter for y
	public void sety(int newy) {
		y = newy;
	}
	
	// Setter for size
	public void setSize(int newSize) {
		size = newSize;
	}
	
	// Setter for color
	public void setColor(int newColor) {
		color = newColor;
	}
	
	// Setter for parent
	public void setParent(QTreeNode newParent) {
		parent = newParent;
	}
	
	// Setter for child
	public void setChild (QTreeNode newChild, int index) throws QTreeException {
		// Check if index is out of bounds
		if (children == null || index < 0 || index > 3) {
			throw new QTreeException("Error for index: " + index);
			}
		children[index] = newChild;
	}
	
	// Checks if this node is a leaf node
	public boolean isLeaf() {
		if(children == null) {
			return true;
		}
		//Check if every entry of children is null
		for (int i = 0; i < children.length; i++) {
			if(children[i] != null) {
				return false;
			}
		}
		return true;
	}
}




