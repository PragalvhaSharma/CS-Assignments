
public class GraphNode {
    private int name;
    private boolean marked;

    // Constructor: creates a node with the given name
    public GraphNode(int name) {
        this.name = name;
        this.marked = false; // Nodes are initially unmarked
    }

    // Marks the node with the specified value
    public void mark(boolean mark) {
        this.marked = mark;
    }

    // Returns the value with which the node has been marked
    public boolean isMarked() {
        return this.marked;
    }

    // Returns the name of the node
    public int getName() {
        return this.name;
    }
}
