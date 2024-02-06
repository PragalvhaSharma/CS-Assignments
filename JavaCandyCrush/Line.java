
public class Line {
	//Instance Variables
	private int[] start = new int[2];
	private int[] end = new int[2];
	
	// Constructor takes 3 arguments: starting row, column, boolean if its a horizontal or vertical match and length to show how long the 
	// line is 
	public Line(int row, int col, boolean horizontal, int length) {
		// Starting Coordinates in an array 
		this.start[0] = row;
		this.start[1] = col;
		if (horizontal) {
			this.end[0] = row;
			this.end[1]= col + length - 1;
		} else {
			this.end[0] = row + length - 1; // Adjust the row based on the length for a vertical line
			this.end[1] = col;}// The column remains the same
         }
	
	public int[] getStart() {
		int[] arrayCopy = new int[start.length];
		for(int i = 0; i < start.length; i++) {
			arrayCopy[i] = start[i];
		}
		return arrayCopy;
	}
	
	public int length() {
	    if (start[0] == end[0]) {
	        // The line is horizontal, so calculate the length based on the columns
	        return Math.abs(end[1] - start[1]) + 1;
	    } else {
	        return Math.abs(end[0] - start[0]) + 1;
	    }
	}
	
	public boolean isHorizontal() {
		
		if (start[0] == end[0]) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean inLine(int row, int col) {
		
		//If horizontal
	    if (start[0] == end[0]) {
	    	//Checking Horizontal range
	    	if (row == start[0] && col >= start[1] && col <= end[1]) {
	    		return true;
	    	}else {
	    		return false;
	    	}
	        

	    } else {
	    	//Checking Vertical Range
	        if(col == start[1] && row >= start[0] && row <= end[0]) {
	        	return true;
	        }else{
	        	return false;
	        }
	    }
	}
	
	public String toString() {
	    // Constructs the string representation using the start and end points
	    return "Line:[" + start[0] + "," + start[1] + "]->[" + end[0] + "," + end[1] + "]";
	}



}




