/**
 * @author CS1027
 *
 *         Defines the interface of a priority queue that must contain unique items (no duplicates).
 */

public interface UniquePriorityQueueADT<T> {
	/**
	 * If the given data item does not already exist anywhere in the priority queue,
	 * add it in keeping the data items sorted in non-decreasing order of priority
	 * @param data item to be added onto the priority queue
	 * @param prio priority of the new data item being added
	 */
	public void add(T data, double prio);

	
	/**
	 * Returns true if the given data item already exists somewhere in the priority queue.
	 * 
	 * @return boolean whether or not this priority queue contains the given data item
	 */
	public boolean contains(T data);
	

	/**
	 * Returns (without removing) the data item from the priority queue with smallest priority.
	 * 
	 * @return T smallest priority data item in the priority queue
	 */
	public T peek() throws CollectionException;
	
	
	
	/**
	 * Removes and returns the data item from the priority queue with smallest
	 * priority
	 * @return T smallest priority data item removed from the priority queue
	 */
	public T removeMin() throws CollectionException;

	
	
	
	/**
	 * Updates the priority of the given data item to the new value
	 * 
	 * @param data item whose priority is to be changed
	 * @param newPriority  value of the new priority for this data item
	 */
	public void updatePriority(T data, double newPrio) throws CollectionException;

	
	/**
	 * Returns true if this priority queue contains no data items.
	 * 
	 * @return boolean whether or not this priority queue is empty
	 */
	public boolean isEmpty();

	
	/**
	 * Returns the number of data items in this priority queue.
	 * 
	 * @return int number of data items in this priority queue
	 */
	public int size();

	
	/**
	 * Returns a string representation of this priority queue.
	 * 
	 * @return String representation of this priority queue
	 */
	public String toString();
}
