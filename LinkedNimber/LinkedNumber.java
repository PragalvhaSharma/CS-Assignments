public class LinkedNumber {
	
	private int base;
	private DLNode<Digit> front;
	private DLNode<Digit> rear;
	
	public LinkedNumber (String num, int baseNum) {
		this.base = baseNum;
		if (num == null || num.isEmpty())
			throw new LinkedNumberException ("no digits given");
		
		 for (int i = 0; i < num.length(); i++) {
	            char digitChar = num.charAt(i);
	            Digit digit = new Digit(digitChar);
	            DLNode<Digit> newNode = new DLNode<Digit>(digit);
	            if (this.front == null) {
	            	front = newNode; // Set front to point to the new node
	            	rear = newNode;
	            } else {
	                this.rear.setNext(newNode);
	                newNode.setPrev(this.rear);
	                this.rear = newNode;
	            }
        }
	}

	public LinkedNumber (int num) {
		this(String.valueOf(num), 10); 
	}
	
	public boolean isValidNumber() {
		DLNode<Digit> currentNode = front;
		  while (currentNode != null) {
		        int digitValue = currentNode.getElement().getValue();
		        if (digitValue < 0 || digitValue >= base) {
		            return false; 
		        }
		        currentNode = currentNode.getNext();
		    }
		    return true;
	}
	
	public int getBase() {
		return base;
	}
	
	public DLNode<Digit> getFront(){
		return front;
	}
	
	public DLNode<Digit> getRear(){
		return rear;
	}
	
	public int getNumDigits() {
		int digitCount = 0;
		DLNode<Digit> currentNode = front;
	    while (currentNode != null) {
	    	digitCount++;
	        currentNode = currentNode.getNext();
	    }
	    return digitCount;
	}
	
	public String toString() {
	    String outputString = "";
	    DLNode<Digit> currentNode = front;
	    while (currentNode != null) {
	        outputString += currentNode.getElement().toString();
	        currentNode = currentNode.getNext();
	    }
	    return outputString;
	}
	
	public boolean equals(LinkedNumber other) {
	    if (this.base != other.getBase()) {
	        return false;
	    }
	    
	    DLNode<Digit> currentLink = this.front;
	    DLNode<Digit> otherLink = other.getFront();

	    while (currentLink != null && otherLink != null) {
		    if (!currentLink.getElement().equals(otherLink.getElement())) {
	            return false;
	     }
		    currentLink = currentLink.getNext();
		    otherLink = otherLink.getNext();
	    }
	    
	    
	    if (currentLink != null || otherLink != null) {
	        return false;
	    }
	    
	    return true;

	}

	public LinkedNumber convert (int newBase) {
	    if (!this.isValidNumber()) {
	        throw new LinkedNumberException("cannot convert invalid number");
	    }
	    
	    if (this.base == newBase) {
	        return this; // Or return a new instance with the same value if required
	    }
	    
	    // If the current base is decimal, directly convert to the new base.
	    if (this.base == 10) {
	        return decimalToNonDecimal(newBase);
	    } else if (newBase == 10) {
	        // If the new base is decimal, directly convert from the current base to decimal.
	        return nonDecimalToDecimal(this.base);
	    } else {
	        // If both the current base and new base are non-decimal,
	        // first convert to decimal then to the new non-decimal base.
	        LinkedNumber decimalNumber = nonDecimalToDecimal(this.base);
	        return decimalNumber.decimalToNonDecimal(newBase);
	    }
	}
	

	//Application: Decimal number 1,529 converted to hexadecimal
    private LinkedNumber decimalToNonDecimal(int newBase) {
    	int val = 0;
        int nodePosition = 0;
        DLNode<Digit> currentNode = rear; 

        // Step 1: Set variable val equal to the whole decimal number
        while (currentNode != null) {
            int digitValue = currentNode.getElement().getValue(); 
            val += digitValue * Math.pow(10, nodePosition); 
            nodePosition++; 
            currentNode = currentNode.getPrev(); 
        }
        
        // Step 2: Convert to newBase using divisions and remainders to determine the new number
        String convertedValue = "";
        while (val > 0) {
            int remainder = val % newBase;
            if (remainder < 10) {
            	convertedValue = remainder + convertedValue;
            } else {
            	convertedValue = (char) ('A' + remainder - 10) + convertedValue; // Converts 10-15 to A-F
            }
            val /= newBase;
        }

        //Step 3: Initialize a new LinkedNumber object with this completed String and the newBase as its
        return new LinkedNumber(convertedValue, newBase);

    }
    
    
    //Application: Binary number 110101 converted to decimal
    private LinkedNumber nonDecimalToDecimal(int base) {
    	int val = 0;
        int nodePosition = 0;
        DLNode<Digit> currentNode = rear; 
        

        //Step 1: Set variable val equal to the whole number
        while (currentNode != null) {
            int digitValue = currentNode.getElement().getValue(); 
            val += digitValue * Math.pow(base, nodePosition);
            nodePosition++;
            currentNode = currentNode.getPrev();
        }


        //Step 2: Initialize a new LinkedNumber object with this variable val and the number 10 as its parameters
        return new LinkedNumber(String.valueOf(val), 10); 
    }

    
    public void addDigit (Digit digit, int position) {
    	int listSize = getNumDigits();
    	
    	// Validate position
        if (position < 0 || position > listSize) {
            throw new IllegalArgumentException("Position out of bounds");
        }
        
        DLNode<Digit> newNode = new DLNode<>(digit);
        
     // Insertion at the beginning
        if (position == 0) {
            if (front == null) { // If the list is empty
                front = rear = newNode;
            } else {
                newNode.setNext(front);
                front.setPrev(newNode);
                front = newNode;
            }
        } else if (position == listSize) { // Insertion at the end
            rear.setNext(newNode);
            newNode.setPrev(rear);
            rear = newNode;
        } else { // Insertion in the middle
            DLNode<Digit> current = front;
            for (int i = 0; i < position - 1; i++) {
                current = current.getNext();
            }
            newNode.setNext(current.getNext());
            newNode.setPrev(current);
            current.getNext().setPrev(newNode);
            current.setNext(newNode);
        }
    }
    

    public int removeDigit(int position) {
        // Check if the position is valid
    	int listSize = getNumDigits();
        if (position < 0 || position > listSize - 1) {
            throw new LinkedNumberException("invalid position");
        } else {
            // Traverse from rear to the required position
            DLNode<Digit> current = rear;
            for (int i = 0; i < position; i++) {
                current = current.getPrev();
            }

            // Calculate the decimal value of the node
            int decimalValue = current.getElement().getValue() * (int)Math.pow(10, position);

            // Remove the node from the list
            if (current.getPrev() != null) {
                current.getPrev().setNext(current.getNext());
            } else {
                // current is the rear
                rear = current.getNext();
                if (rear != null) {
                    rear.setPrev(null);
                }
            }
            if (current.getNext() != null) {
                current.getNext().setPrev(current.getPrev());
            } else {
                // current is the front
                front = current.getPrev();
                if (front != null) {
                    front.setNext(null);
                }
            }

            return decimalValue;
        }
      }
   
}
