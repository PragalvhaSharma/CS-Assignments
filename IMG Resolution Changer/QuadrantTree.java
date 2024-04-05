
public class QuadrantTree {
	private QTreeNode root;
	
	public QuadrantTree(int[][]thePixels) {
		int size = thePixels.length;
		root = buildTree(thePixels, 0, 0, size);

	}
	
	private QTreeNode buildTree(int[][] pixels, int x, int y, int size) {
		//Base Case
		if(size == 1) {
			int color = Gui.averageColor(pixels, x, y, size);
			return new QTreeNode(null, x, y, size, color);
		}else {
			int newSize = size/2;
			int color = Gui.averageColor(pixels, x, y, size); 
			QTreeNode[] children = new QTreeNode[4];
			children[0] = buildTree(pixels, x, y, newSize); // Top-left quadrant
			children[1] = buildTree(pixels, x + newSize, y, newSize); // Top-right quadrant
			children[2] = buildTree(pixels, x, y + newSize, newSize); // Bottom-left quadrant
			children[3] = buildTree(pixels, x + newSize, y + newSize, newSize); // Bottom-right quadrant
			    
			QTreeNode node = new QTreeNode(children, x, y, size, color);
			return node;
		}
	}
	
	
	public QTreeNode getRoot() {
		return root;
	}
	
	public ListNode<QTreeNode> getPixels(QTreeNode r, int theLevel){
		if(theLevel == 0 || r.isLeaf() ) {
			//Returns a new ListNode
			return new ListNode<QTreeNode>(r);
		}else {
			 ListNode<QTreeNode> head = null;
			 ListNode<QTreeNode> tail = null; // Used to efficiently append to the list.
			 //Gettting the child of the node given
			 for(int i = 0; i < 4; i ++) {
				 ListNode<QTreeNode> childList = getPixels(r.getChild(i), theLevel - 1);
				 //Setting the first node
				 if(head == null) {
					 head = childList;
					 tail = head;
					 //Move tail pointer to end of the list
					 while (tail.getNext() != null) {
	                        tail = tail.getNext();
					 }
				 }else {
					//If there is already a node at head
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
	
	//Returns a Duple object (holds two elements:  List of nodes whose color is similar or gathers nodes from the last level of the tree if theLevel exceeds the tree's height and the number of nodes in this list)
	public public Duple findMatching (QTreeNode r, int theColor, int theLevel) {
		
		if(theLevel == 0 || r.isLeaf()) {
			if (Gui.similarColor(r.getColor(), theColor)) {
	            // If similar, return a Duple with this node and count 1.
	            return new Duple(new ListNode<QTreeNode>(r), 1);
			}
			
		}
	}
	
//	public QTreeNode fildNode(QTreeNode r, int theLevel, int x, int y) {
//		
//	}
	
	
	
	
	
	public static void main(String[] args) {
		boolean testPassed = true;
		int[][] pixels = new int[1][1];
		pixels[0][0] = 8;
		System.out.print(Gui.averageColor(pixels, 0, 0, pixels.length));
	}
}
 
