/* This class stored a pair of values: 
   - front is a reference to the first node in a linked list
   - count is the number of nodes in the list
   This class allows method findMatching in class QuadrantTree to
   return two values: a list and the number of nodes in the list */
public class Duple {
	private ListNode<QTreeNode> front;
	private int count;
	
	public Duple () {
		front = null;
		count = 0;
	}
	
	public Duple (ListNode<QTreeNode> first, int size) {
		front = first;
		count = size;
	}
	
	public ListNode<QTreeNode> getFront() {
		return front;
	}
	
	public int getCount() {
		return count;
	}
	
	public void setFront (ListNode<QTreeNode> first) {
		front = first;
	}
	
	public void setCount (int total) {
		count = total;
	}
}
