
 //Represents an edge in a graph connecting two nodes.
 //Each edge has a type and a label.
public class GraphEdge {
    private GraphNode u;   // First endpoint of the edge
    private GraphNode v;   // Second endpoint of the edge
    private int type;      // Type of the edge (e.g., number of coins for doors)
    private String label;  // Label of the edge (e.g., "corridor" or "door")

    // Constructor: creates an edge with the given endpoints, type, and label
    public GraphEdge(GraphNode u, GraphNode v, int type, String label) {
        this.u = u;
        this.v = v;
        this.type = type;
        this.label = label;
    }

    // Returns the first endpoint of the edge
    public GraphNode firstEndpoint() {
        return this.u;
    }

    // Returns the second endpoint of the edge
    public GraphNode secondEndpoint() {
        return this.v;
    }

    // Returns the type of the edge
    public int getType() {
        return this.type;
    }

    // Sets the type of the edge to the specified value
    public void setType(int newType) {
        this.type = newType;
    }

    // Returns the label of the edge
    public String getLabel() {
        return this.label;
    }

    // Sets the label of the edge to the specified value
    public void setLabel(String newLabel) {
        this.label = newLabel;
    }
}

