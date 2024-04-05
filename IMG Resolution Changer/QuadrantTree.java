
public class QuadrantTree {
	QTreeNode root;
	
	public QuadrantTree(int[][]thePixels) {
		int size = thePixels.length;
		root = buildTree(thePixels, 0, 0, size);

	}
	
	private QTreeNode buildTree(int[][] pixels, int x, int y, int size) {
		//Base Case
		if(size == 1) {
			int color = pixels[x][y];
			return new QTreeNode(null, x, x, size, color);
		}else {
			int newSize = size/2;
			QTreeNode[] children = new QTreeNode[4];
			children[0] = buildTree(pixels, x, y, newSize); // Top-left quadrant
		    children[1] = buildTree(pixels, x + newSize, y, newSize); // Top-right quadrant
		    children[2] = buildTree(pixels, x, y + newSize, newSize); // Bottom-left quadrant
		    children[3] = buildTree(pixels, x + newSize, y + newSize, newSize); // Bottom-right quadrant
		    
		   int newColor = Gui.averageColor(pixels, x, y, size); 
		   QTreeNode node = new QTreeNode(children, x, y, size, newColor);
		   return node;
		}
		
	}
	
	
	public QTreeNode getRoot() {
		return root;
	}
	
	public ListNode<QTreeNode> getPixels(QTreeNode r, int theLevel){
		if(theLevel == 0 || r.isLeaf() ) {
			return new ListNode<QTreeNode>(r);
		}else {
			 ListNode<QTreeNode> head = null;
			 ListNode<QTreeNode> tail = null; // Used to efficiently append to the list.
			 //Gettting the child of the node given
			 for(int i = 0; i < 4; i ++) {
				 ListNode<QTreeNode> childList = getPixels(r.getChild(i), theLevel - 1);
				 if(head == null) {
					 head = childList;
					 tail = head;
					 while (tail.getNext() != null) {
	                        tail = tail.getNext();
					 }
				 }else {
				 	tail.setNext(childList); // Append the child list to the current list.
                 // Move the tail pointer to the end of the list.
                 	while (tail.getNext() != null) {
                     	tail = tail.getNext();
                 	}
				 }
			 }
			 return head;
		}
	}
	
	public public Duple findMatching (QTreeNode r, int theColor, int theLevel) {
		if(theLevel == 0 || r.isLeaf()) {
			
		}
	}
	
//	public QTreeNode fildNode(QTreeNode r, int theLevel, int x, int y) {
//		
//	}
	
	
	
}
 
