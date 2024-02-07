// Line class is used to represent a horizontal or vertical line in an array.
public class Line {

    // Declare private instance variables to store the start and end points of the line.
    private int[] start = new int[2];
    private int[] end = new int[2];
    
    // Constructor for creating a new Line object with specified parameters.
    public Line(int row, int col, boolean horizontal, int length) {
        // Initialize the start point of the line.
        this.start[0] = row;
        this.start[1] = col;
        // Determine the end point based on whether the line is horizontal or vertical.
        if (horizontal) {
            // For a horizontal line, the row remains the same and the column is adjusted by the length.
            this.end[0] = row;
            this.end[1] = col + length - 1;
        } else {
            // For a vertical line, the column remains the same and the row is adjusted by the length.
            this.end[0] = row + length - 1;
            this.end[1] = col;
        }
    }
    
    // Method to get a copy of the start point array to prevent direct access/modification.
    public int[] getStart() {
        int[] arrayCopy = new int[start.length];
        for(int i = 0; i < start.length; i++) {
            arrayCopy[i] = start[i];
        }
        return arrayCopy;
    }
    
    // Calculate the length of the line segment.
    public int length() {
        // If the line is horizontal, calculate the length based on column difference.
        if (start[0] == end[0]) {
            return Math.abs(end[1] - start[1]) + 1;
        } else {
        // If the line is vertical, calculate the length based on row difference.
            return Math.abs(end[0] - start[0]) + 1;
        }
    }
    
    // Check if the line is horizontal by comparing the row values of the start and end points.
    public boolean isHorizontal() {
        return start[0] == end[0];
    }
    
    // Check if a given point (row, col) is part of the line segment.
    public boolean inLine(int row, int col) {
        if (start[0] == end[0]) {
            // For a horizontal line, check if the row matches and the column is within the range.
            return row == start[0] && col >= start[1] && col <= end[1];
        } else {
            // For a vertical line, check if the column matches and the row is within the range.
            return col == start[1] && row >= start[0] && row <= end[0];
        }
    }
    
    // Providing a string representation of the line segment.
    public String toString() {
        return "Line:[" + start[0] + "," + start[1] + "]->[" + end[0] + "," + end[1] + "]";
    }
}


