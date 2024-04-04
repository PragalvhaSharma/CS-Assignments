
public class QTreeNode {
	private int x;
	private int y;
	private int size;
	private int color;
	private QTreeNode parent;
	private QTreeNode[] children; 
	
	public QTreeNode() {
		parent = null;
		children = new QTreeNode[4];
		x= 0;
		y=0;
		size=0;
		color=0;
	}
	
	public QTreeNode(QTreeNode[] theChildren, int xcoord, int ycoord, int theSize, int theColor) {
		parent = null;
		children = theChildren;
		x = xcoord;
		y = ycoord;
		size = theSize;
		color = theColor;
	}
	
	public boolean contains(int xcoord, int ycoord) {
		//Checking x
		if((xcoord  >= x && xcoord <= (x + size -1)) && (ycoord >= y && ycoord <= (y+size-1)) ) {
			return true;}
		
		return false;
	
		}
	
	public int getx() {
		return x;
	}
	
	public int gety() {
		return y;
	}
	
	public int getSize() {
		return size;
	}
	
	public int getColor() {
		return color;
	}
	
	public QTreeNode getParent() {
		return parent;
	}
	
	public QTreeNode getChild(int index) throws QTreeException {
		if (children == null || index < 0 || index > 3) {
			throw new QTreeException("Error for index: " + index);
			}
		return children[index];
	}
	
	public void setx(int newx) {
		x = newx;
	}
	
	public void sety(int newy) {
		y = newy;
	}
	
	public void setSize(int newSize) {
		size = newSize;
	}
	
	public void setColor(int newColor) {
		color = newColor;
	}
	
	public void setParent(QTreeNode newParent) {
		parent = newParent;
	}
	
	public void setChild (QTreeNode newChild, int index) throws QTreeException {
		if (children == null || index < 0 || index > 3) {
			throw new QTreeException("Error for index: " + index);
			}
		children[index] = newChild;
	}
	
	public boolean isLeaf() {
		if(children == null) {
			return true;
		}
		
		for (int i = 0; i < children.length; i++) {
			if(children[i] != null) {
				return false;
			}
		}
		return true;
	}
}


