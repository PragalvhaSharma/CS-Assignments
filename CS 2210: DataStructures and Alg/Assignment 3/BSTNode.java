public class BSTNode {
    private Record record;
    private BSTNode leftChild;
    private BSTNode rightChild;
    private BSTNode parent;

    // Constructor initializes node with a record, no children or parent
    public BSTNode(Record item) {
        this.record = item;
        this.leftChild = null;
        this.rightChild = null;
        this.parent = null;
    }

    // Returns the record
    public Record getRecord() {
        return record;
    }

    // Sets the record
    public void setRecord(Record d) {
        this.record = d;
    }

    // Returns the left child
    public BSTNode getLeftChild() {
        return leftChild;
    }

    // Sets the left child
    public void setLeftChild(BSTNode u) {
        this.leftChild = u;
    }

    // Returns the right child
    public BSTNode getRightChild() {
        return rightChild;
    }

    // Sets the right child
    public void setRightChild(BSTNode u) {
        this.rightChild = u;
    }

    // Returns the parent node
    public BSTNode getParent() {
        return parent;
    }

    // Sets the parent node
    public void setParent(BSTNode u) {
        this.parent = u;
    }

    // Checks if the node is a leaf (has no children)
    public boolean isLeaf() {
        return leftChild == null && rightChild == null;
    }
}
