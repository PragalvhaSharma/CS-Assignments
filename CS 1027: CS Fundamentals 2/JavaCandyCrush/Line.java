// Line class is used to represent a horizontal or vertical line in an array.
public class Line {

    // Declare private instance variables to store the start and end coordinates of the line.
    private int[] start = new int[2];
    private int[] end = new int[2];
    
    // Constructor 
    public Line(int row, int col, boolean horizontal, int length) {
        // Initialize the start point of the line.
        this.start[0] = row;
        this.start[1] = col;
        // Determine the end point if the line is horizontal or vertical
        if (horizontal) {
            // For a horizontal line
            this.end[0] = row;
            this.end[1] = col + length - 1;
        } else {
            // For a vertical line
            this.end[0] = row + length - 1;
            this.end[1] = col;
        }
    }
    
 // Returns a new array with the same data s the start array
    public int[] getStart() {
        // Create a new array with the same length as the start array
        int[] arrayCopy = new int[start.length];
        
        // Copy elements from the start array to the new array
        for(int i = 0; i < start.length; i++) {
            arrayCopy[i] = start[i];
        }
        
        // Return the copied array
        return arrayCopy;
    }

    
    // Returns the length of the line 
    public int length() {
        // For horizontal
        if (start[0] == end[0]) {
            return Math.abs(end[1] - start[1]) + 1;
        } else {
        // For vertical
            return Math.abs(end[0] - start[0]) + 1;
        }
    }
    
    // Check if the line is horizontal
    public boolean isHorizontal() {
        return start[0] == end[0];
    }
    
    // Check if a given point is contained within the line segment.
    public boolean inLine(int row, int col) {
        if (start[0] == end[0]) {
            // For a horizontal line
            return row == start[0] && col >= start[1] && col <= end[1];
        } else {
            // For a vertical line
            return col == start[1] && row >= start[0] && row <= end[0];
        }
    }
    
    // Returning a string with the specified format
    public String toString() {
        return "Line:[" + start[0] + "," + start[1] + "]->[" + end[0] + "," + end[1] + "]";
    }
}





