// Implementing UniquePriorityQueueADT interface for ArrayUniquePriorityQueue
public class ArrayUniquePriorityQueue<T> implements UniquePriorityQueueADT<T> {
	
	// Instance Variables -- Store the elements along with their priority score and total elements
	private T[] queue;
	private double[] priority;
	private int count;
	
	// Constructor to initialize queue with default values for instance variables
	public ArrayUniquePriorityQueue() {
		count = 0;
		queue = (T[]) new Object[10];
		priority = new double[10];
	}
	
	// Adds an element to the priority queue with a priority score
	public void add(T data, double prio) {
		
		// If element is already present, do not add it.
		if (this.contains(data)) {
			return;
	    }
		
		// Resize the arrays if they are full.
	    if (count == queue.length || count == priority.length) {
	    	T[] newQueue = (T[]) (new Object[queue.length + 5]);
	        double[] newPriority = new double[priority.length + 5];
	        
	        // Copy the elements and priorities to the new arrays.
	        for(int i = 0; i < queue.length; i++) {
	        	newQueue[i] = queue[i];
	        	newPriority[i] = priority[i];
	        }
	        
	        // Change arrays
	        queue = newQueue;
	        priority = newPriority;
	    }
	   
	    int newIndexPos = count - 1;
	    
	    // Insert the new element into the correct position based on its priority.
	    while (newIndexPos >= 0 && priority[newIndexPos] > prio) {
	        queue[newIndexPos + 1] = queue[newIndexPos];
	        priority[newIndexPos + 1] = priority[newIndexPos];
	        newIndexPos--;
	    }

	    queue[newIndexPos + 1] = data;
	    priority[newIndexPos + 1] = prio;
	    count++;
	}
	
	// Checks if a specific element is in the priority queue.
	public boolean contains(T data) {
		for (int i = 0; i < queue.length; i++) {
			if (queue[i] != null && queue[i].equals(data)) {
				return true;
			}
		}
		return false;
	}
	
	// Returns the element with the smallest priority without removing it.
	public T peek() throws CollectionException{
		if (isEmpty()) {
			throw new CollectionException("PQ is empty"); // If Empty
		}
		return queue[0];
	}
	
	// Removes and returns the element with the smallest priority.
	public T removeMin () throws CollectionException{
		if (isEmpty()) {
			throw new CollectionException("PQ is empty");
		}
		
		T itemToRemove = this.peek();
		
		// Shift all elements to the left to fill gap
	    for (int i = 1; i < count; i++) {
	        queue[i - 1] = queue[i];
	        priority[i - 1] = priority[i];
	    }
	    
	    queue[count - 1] = null; // Nullify the last element.
	    count --;
	    
	    // Item with the smallest priority
	    return itemToRemove;
	}
	
	// Update the priority of the existing data to a value of newPrio
	public void updatePriority(T data, double newPrio) throws CollectionException{
		// Check for valid input
		if(!this.contains(data)) {
			throw new CollectionException("Item not found in PQ");
			}
		
		// Find the element to update and remove it temporarily.
		int dataIndex = 0;
		for (int i = 0; i < count; i++) {
			if (data.equals(queue[i])) {
				//Save the index #
				dataIndex = i;
				break;
			}
		}
		
		// Shift elements one position to the right
		for (int i = dataIndex; i < count - 1; i++) {
			queue[i] = queue[i + 1];
			priority[i] = priority[i + 1];
		}
		
		queue[count - 1] = null;
		count--;
	    
	    // Re-add the element with its new priority.
	    add(data, newPrio);
	    
	}
	
	// Checks if the priority queue is empty.
	public boolean isEmpty() {
		if ( count == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	// Returns the number of elements in the priority queue.
	public int size() {
		return count;
	}
	
	// Returns the current capacity of the priority queue.
	public int getLength() {
		return queue.length;
	}
	
	// Constructing a string representation
	public String toString() {
		if (this.isEmpty()) {
			return "The PQ is empty";
		} 
		
		StringBuilder outputString = new StringBuilder();
		for (int i = 0; i < count; i++) {
			// Accounting for the comma
	        if (i > 0) {
	        	outputString.append(", ");
	        }
	        // Formatting the string 
			outputString.append(queue[i].toString());
			outputString.append(" [");
			outputString.append(priority[i]);
			outputString.append("]");
		}
		return outputString.toString();
	}

}
