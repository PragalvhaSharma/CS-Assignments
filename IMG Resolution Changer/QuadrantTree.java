**
 * The QuadrantTree class represents the quadrant tree data structure
 */
public class QuadrantTree {
	
	// Root of the tree
	private QTreeNode root;
	
	// Creating the tree
	public QuadrantTree(int[][]thePixels) {
		int size = thePixels.length;
		root = createTree(thePixels, 0, 0, size);
	}
	
	// Recursive algorithm to actually create the tree
	private QTreeNode createTree(int[][] pixels, int x, int y, int size) {
		// Base Case
		if(size == 1) {
			//Getting the color and QTree node for base case
			int color = Gui.averageColor(pixels, x, y, size);
			return new QTreeNode(null, x, y, size, color);
		}else {
		// Recursive Case
			// New size and color for new quadrant
			int newSize = size/2;
			int color = Gui.averageColor(pixels, x, y, size); 
			
			//Creating 4 quadrants
			QTreeNode[] quadrant = new QTreeNode[4];
			
			// Recursive calls until size is = 1 for each quadrant
			quadrant[0] = createTree(pixels, x, y, newSize); // Top-left quadrant
			quadrant[1] = createTree(pixels, x + newSize, y, newSize); // Top-right quadrant
			quadrant[2] = createTree(pixels, x, y + newSize, newSize); // Bottom-left quadrant
			quadrant[3] = createTree(pixels, x + newSize, y + newSize, newSize); // Bottom-right quadrant
			    
			// Getting node for the Quadrant Tree
			QTreeNode node = new QTreeNode(quadrant, x, y, size, color);
			return node;
		}
	}
	
	// Getter method for returning Root of the Quadrant Tree
	public QTreeNode getRoot() {
		return root;
	}
	
	// Returns a list containing all nodes at a specified level with r being the current node being examined.
	//(Note: if level is larger then height, we return the nodes at the last level)
	public ListNode<QTreeNode> getPixels(QTreeNode r, int theLevel){
		
		// Base case: Check if node is root node or is a leaf node
		if(theLevel == 0 || r.isLeaf() ) {
			return new ListNode<QTreeNode>(r);
		} else {
		// Recursive case
			
			 // Initializing ListNodes
			 ListNode<QTreeNode> head = null;
			 ListNode<QTreeNode> tail = null; 
			 
			 // Getting the 4 children of the given node
			 for(int i = 0; i < 4; i ++) {
				 
				 // Recursively call getPixels on each child, decreasing the level by one.
				 ListNode<QTreeNode> childList = getPixels(r.getChild(i), theLevel - 1);
				 // Setting the first node
				 if(head == null) {
					 head = childList;
					 tail = head;
					 // Move tail pointer to end of the list
					 while (tail.getNext() != null) {
						 tail = tail.getNext();
					 }
				 } else {
					// If there is already a node at head
					// Append the child list to the current list.
					 tail.setNext(childList);
					 // Move the tail pointer to the end of the list.
					 while (tail.getNext() != null) {
						 tail = tail.getNext();
						 }
					 }
				 }
			 // Return the head of the list which is connected to the other nodes.
			 return head;
		}
	}
	
	// Returns a Duple (holds 2 elements: List of nodes at theLevel matching r's color and the count of such nodes)
	public Duple findMatching(QTreeNode r, int theColor, int theLevel) {
		// Initialize an empty Duple to accumulate matching nodes and their count
		Duple output = new Duple()
				;
		// Base Case: If the current node is a root node or is a leaf node.
		if(theLevel == 0 || r.isLeaf()) {
			// Check if the current nodes color is similar to the target color.
			if (Gui.similarColor(r.getColor(), theColor)) {
				// If similar, return a Duple with this node and count 1.
				return new Duple(new ListNode<QTreeNode>(r), 1);
			} else {
				// If the colors do not match, return an empty Duple.
				return output;
			}
		} else {
		//Recursive case: If not at the target level, explore the children.
			 for (int i = 0; i < 4; i++) {
				 // Recursively find matching nodes in each child, decrementing the level by 1.
				 Duple childDuple = findMatching(r.getChild(i), theColor, theLevel - 1);
				 
				 // Combining the results into output Duple.
				 output = combineList(output, childDuple);
			 	}
			 
			 // Return final results.
			 return output;
		}
	}
	
	
	// Merges the 2 Duple together and returns the combined Duple
	private Duple combineList(Duple output, Duple childDuple) {
		// If output's list is empty, directly set it to childDuple's list.
		if (output.getFront() == null) {
			output.setFront(childDuple.getFront());
		} else {
			// If output already has a list, find its last node.
			ListNode<QTreeNode> current = output.getFront();
			
			// Traverse to the end of the list and update current
			while (current.getNext() != null) {
				current = current.getNext();
			}
			
			// Append childDuple's list to the end of output's list.
			current.setNext(childDuple.getFront());
		}
		// Update the total count of nodes for both lists
		output.setCount(output.getCount() + childDuple.getCount());
		
        return output; // Return the combined Duple
	}

	
	// Locates and returns the node at theLevel within r's subtree that covers the point (x, y).
	public QTreeNode findNode(QTreeNode r, int theLevel, int x, int y) {
		//Base Case: At the Root node
		if (theLevel == 0) {
			return r;
		} else {
			//Recursive Case
	    	//Iterate through child node to see if it contains the point (x, y)
	    	 for (int i = 0; i < 4; i++) {
	    		 QTreeNode child = r.getChild(i);
	    		 // Check node to see if the point is contained
	    		 if (child.contains(x, y)) {
	    			 // If so, recursively search this subtree, decrementing the level by 1.
	    			 return findNode(child, theLevel - 1, x, y);
	    			 }
	    		 }
	    	 }
		// If no child node at the next level contains the point (x, y), return null.
		return null;
	}
	
}
 
