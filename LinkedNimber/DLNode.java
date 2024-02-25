public class DLNode<T> {

	private DLNode<T> prev;
	private DLNode<T> next;
	private T element;

    public DLNode () {
        next = null;
        element = null;
    }

    public DLNode (T elem) {
        next = null;
        element = elem;
    }
    
    public DLNode<T> getPrev () {
        return prev;
    }
    
    public void setPrev (DLNode<T> node) {
        prev = node;
    }

    public DLNode<T> getNext () {
        return next;
    }
    
    public void setNext (DLNode<T> node) {
        next = node;
    }

    public T getElement () {
        return element;
    }

    public void setElement (T elem) {
        element = elem;
    }

}
