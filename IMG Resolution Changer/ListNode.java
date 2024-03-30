/* Nodes for a singly linked list */
public class ListNode<T> {
	private T data;
	private ListNode<T> next;
	
	public ListNode(T dataItem) {
		data = dataItem;
		next = null;
	}
	
	public ListNode<T> getNext() {
		return next;
	}
	
	public void setNext(ListNode<T> nextNode) {
		next = nextNode;
	}
	
	public T getData() {
		return data;
	}
	
	public void setData (T newData) {
		data = newData;
	}
}
