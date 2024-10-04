// LetterCrush class is used to represent a tile grid board
public class LetterCrush {
	
	// grid stores the board
	private char[][] grid;
	// EMPTY is to represent empty tiles on the board
	public static final char EMPTY = ' ';
	
	// Constructor initializes the board with dimensions and letters
	public LetterCrush(int width, int height, String initial) {
		// Create the grid based on user dimensions
		this.grid = new char[height][width];

        // Initialize all indexes as empty
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                grid[row][col] = EMPTY;
            }
        }
        
        // Place the initial string of letters onto the board, starting from the top-left
        int row = 0;
        int col = 0;
        for (int character = 0; character < initial.length(); character++) {
            grid[row][col] = initial.charAt(character); 

            col++;
            // Move to the next row if the current row is filled
            if (col == grid[row].length) { 
                col = 0; 
                row++; 
            }
            // Stop if all rows are filled
            if (row == grid.length) { 
                break; 
            }
        }
	}
	
	// Converts the strings and empty tiles to display specified format
	public String toString() {
		String output = "LetterCrush\n";
		// Iterate over each row and column 
		for (int row = 0; row < grid.length; row++) {
			output += "|"; 
	        for (int col = 0; col < grid[row].length; col++) {
	        	output += grid[row][col]; 
	        }
	        output += "|" + row + "\n"; 
	    }
	    // Add column indexes
		output += "+";
	    for (int col = 0; col < grid[0].length; col++) {
	    	output += col; 
	    }
	    // For the plus at the end based on the formatting.
	    output += "+"; 
	    return output;
	}
	
	// Checks if if there is any position in grid where a non-EMPTY character is above an EMPTY character
	public boolean isStable() {
	    // Iterate over each column of the grid
	    for (int col = 0; col < grid[0].length; col++) { 
	        // Iterate from the bottom row of the grid to the second row
	        for (int row = grid.length - 1; row > 0; row--) { 
	            // Check if the current tile is empty and the tile directly above it is not empty
	            if (grid[row][col] == EMPTY && grid[row - 1][col] != EMPTY) {
	                return false;
	            }
	        }
	    }
	    // The board is stable
	    return true;
	}

	
	// Applies gravity to the board, making letters fall into empty spaces below
	public void applyGravity() {
		//Iterate over the grid from the bottom and stop at the second row
	    for (int col = 0; col < grid[0].length; col++) {
	        for (int row = grid.length - 1; row > 0; row--) {
	        	// Check if the current position is EMPTY and the position above it is not EMPTY
	            if (grid[row][col] == EMPTY && grid[row - 1][col] != EMPTY) {
	                // Move letters down to fill empty spaces
	                int nonEmptyRow = row - 1;
	                while (nonEmptyRow >= 0 && grid[nonEmptyRow][col] != EMPTY) {
	                    grid[nonEmptyRow + 1][col] = grid[nonEmptyRow][col];
	                    nonEmptyRow--; 
	                }
	                // Mark the last moved position as EMPTY
	                if (nonEmptyRow != row - 1) {
	                    grid[nonEmptyRow + 1][col] = EMPTY;
	                }
	            }
	        }
	    }
	}

	// Removes a line of letters from the board
	public boolean remove(Line theLine) {
	    // Determine the start and end points 
	    int[] start = theLine.getStart();
	    int endRow, endCol;
	    // Calculate end points
	    if (theLine.isHorizontal()) {
	        endRow = start[0]; 
	        endCol = start[1] + theLine.length() - 1; 
	    } else {
	        endRow = start[0] + theLine.length() - 1; 
	        endCol = start[1];
	    }
	    // Validate line boundaries.
	    if (start[0] < 0 || start[1] < 0 || endRow < 0 || endCol < 0 ||
	        start[0] >= grid.length || endRow >= grid.length ||
	        start[1] >= grid[0].length || endCol >= grid[0].length) {
	        return false; 
	    }
	    // Remove the line by setting the tiles to EMPTY
	    if (theLine.isHorizontal()) {
	        for (int col = start[1]; col <= endCol; col++) {
	            grid[start[0]][col] = EMPTY;
	        }
	    } else {
	    	// For vertical line
	        for (int row = start[0]; row <= endRow; row++) {
	            grid[row][start[1]] = EMPTY;
	        }
	    }
	    return true; 
	}

    // Overloaded toString method but with a parameter of a Line instance
    public String toString(Line theLine) {
    	String output = "CrushLine\n";
    	// Iterate through each row
    	for (int row = 0; row < grid.length; row++) {
    	    output += "|"; 
    	    //Iterate through each column
    	    for (int col = 0; col < grid[row].length; col++) {
    	        char currentChar = grid[row][col];
    	        //Check if current position is part of the line
    	        if (theLine.inLine(row, col)) {
    	        	//Convert to lower case
    	            currentChar = Character.toLowerCase(currentChar);
    	        }
    	        output += currentChar;
    	    }
    	    //More formatting
    	    output += "|" + row + "\n";
    	}
    	output += "+";
    	// For column numbers
    	for (int col = 0; col < grid[0].length; col++) {
    	    output += col;
    	}
    	output += "+\n";
    	return output;
    }
    
    // Identifies the longest line of identical letters on the board
    public Line longestLine() {
	    // Initial placeholder
	    Line longLine = new Line(0,0,true,1); 
	    int largest = 0;
	    // Search for the longest horizontal line
	    //Loop through rows starting from the bottom
	    for (int row = grid.length - 1; row >= 0; row--) {
	    	//Starting letter
	        char letter = grid[row][0]; 
	        // Counter for adjacent identical letters
	        int adjacent = 1;
	        // Loop through columns in the row
	        for (int col = 1; col < grid[row].length; col++) { 
	        	//Check if current cell matches the starting letter and is not empty
	            if (grid[row][col] == letter && letter != EMPTY) {
	                adjacent++; 
	                //Check if adjacent count exceeds the largest
	                if (adjacent > largest) {
	                    largest = adjacent;
	                    // Update longLine with the new longest line
	                    longLine = new Line(row, col - adjacent + 1, true, adjacent);
	                }
	            } else {
	            	//if current cell does not match the starting letter
	                letter = grid[row][col];
	                adjacent = 1;
	            }
	        }
	    }
	    
	    // Search for the longest vertical line
	    // Iterate through each column left to right
	    for (int col = 0; col < grid[0].length; col++) { 
	    	// Starting letter 
	        char letter = grid[grid.length - 1][col]; 
	        // Counter for adjacent identical letters
	        int adjacent = 1;
	        // Loop from the second row from the bottom and moving to the top
	        for (int row = grid.length - 2; row >= 0; row--) { 
	        	//Check if current cell matches the starting letter and is not empty
	            if (grid[row][col] == letter && letter != EMPTY) {
	                adjacent++;
	              //Check if adjacent count exceeds the largest
	                if (adjacent > largest) {
	                    largest = adjacent;
	                    // Update longLine with the new longest line
	                    longLine = new Line(row, col, false, adjacent); 
	                }
	            } else {
	            	//if current cell does not match the starting letter
	                letter = grid[row][col]; 
	                adjacent = 1; 
	            }
	        }
	    }
	    // Return the longest line found, or null if no line is long enough
	    if (longLine.length() > 2) {
	        return longLine;
	    } else {
	        return null;
	    }
	}
	
    
 // Executes methods LongestLine(), remove() and applyGravity() until the board is stable
    public void cascade() {
    	//Start by assuming the board is unstable
        boolean cascadeIncomplete = true;
        while (cascadeIncomplete) {
            // Find the longest line on the board
            Line longestLine = longestLine();
            
            // Check if there is a longest line and if its length is at least 3
            if (longestLine != null && longestLine.length() >= 3) {
                // Remove the longest line
                remove(longestLine);
                
                // Apply gravity until the board is stable
                while (!isStable()) {
                    applyGravity();
                }
            } else {
                // If no more lines can be removed set to false
                cascadeIncomplete = false;
            }
        }
    }

}
