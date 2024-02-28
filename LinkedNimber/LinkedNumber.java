
// Class to represent numbers using a doubly linked list.
public class LinkedNumber {
    
    // Base of the number and head/tail references for the list.
    private int base;
    private DLNode<Digit> front; 
    private DLNode<Digit> rear; 

    // Constructor: Initializes a LinkedNumber from a string and base.
    public LinkedNumber(String num, int baseNum) {
        this.base = baseNum; 
        
        // Validate input string.
        if (num == null || num.isEmpty())
            throw new LinkedNumberException("no digits given");
        
        // Loop through string characters to build the list.
        for (int i = 0; i < num.length(); i++) {
            char digitChar = num.charAt(i); // Current character.
            Digit digit = new Digit(digitChar); // Wrap character in Digit.
            DLNode<Digit> newNode = new DLNode<>(digit); // New list node.
            
            // If list is empty, initialize head and tail.
            if (this.front == null) {
            	rear = newNode; 
            	front = newNode; 
            } else {
                // Append new node at the end and linking properly
                this.rear.setNext(newNode); 
                newNode.setPrev(this.rear); 
                this.rear = newNode; 
            }
        }
    }


    // Overriding constructor above
    public LinkedNumber (int num) {
        this(String.valueOf(num), 10); // Converts integer to string and reuses main constructor.
    }
    
    // Verifies that all digits in the list are valid 
    public boolean isValidNumber() {
        DLNode<Digit> currentNode = front;
        // Iterate through the list
        while (currentNode != null) {
            int digitValue = currentNode.getElement().getValue();
            // Checks if the digit value is outside the valid range.
            if (digitValue < 0 || digitValue >= base) {
                return false; // Invalid digit found.
            }
            currentNode = currentNode.getNext(); // Moves to the next node.
        }
        return true; // All digits are valid.
    }

	
	
 // Returns base of the linked number.
    public int getBase() {
        return base;
    }

    // Returns the reference to the first node 
    public DLNode<Digit> getFront(){
        return front;
    }

    // Returns the reference to the last node 
    public DLNode<Digit> getRear(){
        return rear;
    }

	// Returns the number of digits (the number of nodes)
    public int getNumDigits() {
        int digitCount = 0;
        DLNode<Digit> currentNode = front;
        // Loop through each node in the list until the end
        while (currentNode != null) {
            digitCount++; // Increment count for each node
            currentNode = currentNode.getNext(); // Move to the next node
        }
        return digitCount; // Return the total number of digits
    }

    // Returns a String containing all the digits of the number 
    public String toString() {
    	// String placeholder
        String outputString = ""; 
        DLNode<Digit> currentNode = front;
        // Loop through each node from front to end 
        while (currentNode != null) {
            // Append the outputString 
            outputString += currentNode.getElement().toString();
            currentNode = currentNode.getNext(); // Move to the next node
        }
        return outputString; // Return concatenated string
    }

    // Comparing LinkedNumber object to another
    public boolean equals(LinkedNumber other) {
        // Check if the base is different
        if (this.base != other.getBase()) {
            return false;
        }
        
        // Initialize pointers for looping
        DLNode<Digit> currentLink = this.front;
        DLNode<Digit> otherLink = other.getFront();

        // Continue looping until pointers have reached the end of their list
        while (currentLink != null && otherLink != null) {
            // If the current elements are not equal return false
            if (!currentLink.getElement().equals(otherLink.getElement())) {
                return false;
            }
            // Move to the next nodes in both lists
            currentLink = currentLink.getNext();
            otherLink = otherLink.getNext();
        }
        
        // if either list has remaining elements then they are not the same
        if (currentLink != null || otherLink != null) {
            return false;
        }
        
        // If all checks passed
        return true;
    }


 // Convert to a different base
    public LinkedNumber convert (int newBase) {
        // Check if valid
        if (!this.isValidNumber()) {
            throw new LinkedNumberException("cannot convert invalid number");
        }
        
        // If new base is the same as the current base
        if (this.base == newBase) {
            return this;
        }
        
        // If the current base is 10, convert from decimal to the new non-decimal base
        if (this.base == 10) {
            return decimalToNonDecimal(newBase);
        } else if (newBase == 10) {
            // If the new base is 10, convert from the current non-decimal base to decimal
            return nonDecimalToDecimal(this.base);
        } else {
            // If both the current and new bases are non-decimal,
            // first convert to decimal, then to the new non-decimal base
            LinkedNumber decimalNumber = nonDecimalToDecimal(this.base);
            return decimalNumber.decimalToNonDecimal(newBase);
        }
    }

	
    // Private method implemented in .convert() for decimalToNonDecimal conversion
    private LinkedNumber decimalToNonDecimal(int newBase) {
        int val = 0; // Initialize the decimal value of the number
        int nodePosition = 0; // Track the position
        DLNode<Digit> currentNode = rear; 

        // Looping through list from rear
        while (currentNode != null) {
            int digitValue = currentNode.getElement().getValue(); // Get the digit's value
            val += digitValue * Math.pow(10, nodePosition); // Add to the total value
            nodePosition++; // Move to the next digit position
            currentNode = currentNode.getPrev(); // Move to the previous node 
        }
        
        String convertedValue = "";
        // Convert to newBase using divisions and remainders to determine the new number
        while (val > 0) {
            int remainder = val % newBase; // Find the remainder
            // Convert the remainder to character in the new base
            if (remainder < 10) {
                convertedValue = remainder + convertedValue; // Numbers 0-9 remain unchanged
            } else {
                convertedValue = (char) ('A' + remainder - 10) + convertedValue; // Convert values 10+ to letters
            }
            val /= newBase; // Reduce for next iteration
        }
        
        // Return a new LinkedNumber object
        return new LinkedNumber(convertedValue, newBase);

    }
    
    // Private method implemented in .convert() for nonDecimalToDecimal conversion
    private LinkedNumber nonDecimalToDecimal(int base) {
        int val = 0; // Initialize val
        int nodePosition = 0; // Position of the node being processed, starting from the rear
        DLNode<Digit> currentNode = rear; 

        // Iterate over linked list from rear 
        while (currentNode != null) {
            int digitValue = currentNode.getElement().getValue(); // Get the numeric value of the current digit
            val += digitValue * Math.pow(base, nodePosition); // Convert digit and add to total value
            nodePosition++; // Move to the next digit position
            currentNode = currentNode.getPrev(); // Move to the previous node
        }

        // Return a new LinkedNumber object
        return new LinkedNumber(String.valueOf(val), 10); 
    }

    
    //Adding a digit at the specified position
    public void addDigit(Digit digit, int position) {
        int maxPosition = this.getNumDigits(); // Get the current size of list
        // Check if position is valid
        if (position < 0 || position > maxPosition) { 
            throw new LinkedNumberException("invalid position"); 
        }

        DLNode<Digit> newNode = new DLNode<>(digit); // Create the new node
        // Adding at the rear
        if (position == 0) { 
            newNode.setPrev(rear); 
            rear.setNext(newNode); 
            rear = newNode;
        } else if (position == maxPosition) { // Adding at the front
            newNode.setNext(front);
            front.setPrev(newNode);
            front = newNode;
        } else { // Adding in the middle
            DLNode<Digit> current = rear;
            for (int i = 0; i < position; i++) {
                current = current.getPrev();
            }
            // Linking the node properly with previous and next values
            newNode.setNext(current.getNext());
            current.getNext().setPrev(newNode);
            newNode.setPrev(current);
            current.setNext(newNode);
        }
    }
        
    
    // Removing a digit from a specified position and return decimal equivalent of the value 
    public int removeDigit(int position) {
        int listSize = getNumDigits(); // Get the current size of the list
        // Validate position
        if (position < 0 || position > listSize) { 
            throw new LinkedNumberException("invalid position");
        }

        // Navigate to the specified position
        DLNode<Digit> current = rear;
        for (int i = 0; i < position; i++) {
            current = current.getPrev(); 
        }

        // Adjust links to remove the node
        if (current.getPrev() != null) {
            current.getPrev().setNext(current.getNext()); // Remove from middle or front
        } else {
            front = current.getNext(); // Update front if first element is removed
        }

        if (current.getNext() != null) {
            current.getNext().setPrev(current.getPrev()); // Remove from middle or rear
        } else {
            rear = current.getPrev(); // Update rear if last element is removed
        }
        
        // Return decimal equivalent of the value 
        int removingValue = current.getElement().getValue() ;
        return removingValue * (int)Math.pow(this.getBase(), position);
    }

    
}
