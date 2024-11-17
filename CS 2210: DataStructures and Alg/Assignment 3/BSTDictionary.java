public class BSTDictionary implements BSTDictionaryADT {
    private BSTNode root;

    // Constructor for the class that creates a leaf node as the root of the tree.
    public BSTDictionary() {
        this.root = null; // Initially, the tree is empty (root is null)
    }

    // Returns the Record with key k, or null if the Record is not in the dictionary.
    public Record get(Key k) {
        BSTNode node = getNode(root, k);
        return (node != null) ? node.getRecord() : null;
    }

    // Inserts d into the ordered dictionary.
    // Throws a DictionaryException if a Record with the same Key as d is already in the dictionary.
    public void put(Record d) throws DictionaryException {
        if (root == null) {
            root = new BSTNode(d);
            root.setLeftChild(new BSTNode(null)); // Adding null leaf nodes
            root.setRightChild(new BSTNode(null));
        } else {
            insertNode(root, d);
        }
    }

    // Removes the Record with Key k from the dictionary.
    // Throws a DictionaryException if the Record is not in the dictionary.
    public void remove(Key k) throws DictionaryException {
        BSTNode nodeToRemove = getNode(root, k);
        if (nodeToRemove == null || nodeToRemove.getRecord() == null) {
            throw new DictionaryException("Key not found in the dictionary.");
        }
        removeNode(nodeToRemove);
    }

    // Returns the successor of k (the Record with the smallest Key larger than k);
    // returns null if the given Key has no successor.
    public Record successor(Key k) {
        BSTNode current = root;
        BSTNode successor = null;

        while (current != null && current.getRecord() != null) {
            int comparison = k.compareTo(current.getRecord().getKey());
            if (comparison < 0) {
                successor = current;
                current = current.getLeftChild();
            } else {
                current = current.getRightChild();
            }
        }

        return (successor != null) ? successor.getRecord() : null;
    }

    // Returns the predecessor of k (the Record with the largest Key smaller than k);
    // returns null if the given Key has no predecessor.
    public Record predecessor(Key k) {
        BSTNode current = root;
        BSTNode predecessor = null;

        while (current != null && current.getRecord() != null) {
            int comparison = k.compareTo(current.getRecord().getKey());
            if (comparison > 0) {
                predecessor = current;
                current = current.getRightChild();
            } else {
                current = current.getLeftChild();
            }
        }

        return (predecessor != null) ? predecessor.getRecord() : null;
    }

    // Returns the Record with the smallest key in the ordered dictionary.
    // Returns null if the dictionary is empty.
    public Record smallest() {
        BSTNode node = findSmallest(root);
        return (node != null) ? node.getRecord() : null;
    }

    // Returns the Record with the largest key in the ordered dictionary.
    // Returns null if the dictionary is empty.
    public Record largest() {
        BSTNode node = findLargest(root);
        return (node != null) ? node.getRecord() : null;
    }

    // Private helper methods

    // Recursive method to search for a node with the given key
    private BSTNode getNode(BSTNode r, Key k) {
        if (r == null || r.getRecord() == null) {
            return null;
        }

        int comparison = k.compareTo(r.getRecord().getKey());
        if (comparison == 0) {
            return r;
        } else if (comparison < 0) {
            return getNode(r.getLeftChild(), k);
        } else {
            return getNode(r.getRightChild(), k);
        }
    }

    // Recursive method to insert a node into the BST
    private void insertNode(BSTNode r, Record d) throws DictionaryException {
        int comparison = d.getKey().compareTo(r.getRecord().getKey());

        if (comparison == 0) {
            throw new DictionaryException("Key already exists in the dictionary.");
        } else if (comparison < 0) {
            if (r.getLeftChild() == null || r.getLeftChild().getRecord() == null) {
                BSTNode newNode = new BSTNode(d);
                newNode.setParent(r);
                r.setLeftChild(newNode);
                newNode.setLeftChild(new BSTNode(null)); // Adding null leaf nodes
                newNode.setRightChild(new BSTNode(null));
            } else {
                insertNode(r.getLeftChild(), d);
            }
        } else {
            if (r.getRightChild() == null || r.getRightChild().getRecord() == null) {
                BSTNode newNode = new BSTNode(d);
                newNode.setParent(r);
                r.setRightChild(newNode);
                newNode.setLeftChild(new BSTNode(null)); // Adding null leaf nodes
                newNode.setRightChild(new BSTNode(null));
            } else {
                insertNode(r.getRightChild(), d);
            }
        }
    }

    // Method to remove a node from the BST
    private void removeNode(BSTNode node) {
        if (node.isLeaf()) {
            // Case 1: Node is a leaf
            replaceNodeInParent(node, new BSTNode(null));
        } else if (node.getLeftChild() != null && node.getRightChild() != null) {
            // Case 3: Node has two children
            BSTNode successor = findSmallest(node.getRightChild());
            node.setRecord(successor.getRecord());
            removeNode(successor);
        } else {
            // Case 2: Node has one child
            BSTNode child = (node.getLeftChild().getRecord() != null) ? node.getLeftChild() : node.getRightChild();
            replaceNodeInParent(node, child);
        }
    }

    // Helper method to replace a node in its parent
    private void replaceNodeInParent(BSTNode node, BSTNode newNode) {
        if (node.getParent() != null) {
            if (node == node.getParent().getLeftChild()) {
                node.getParent().setLeftChild(newNode);
            } else {
                node.getParent().setRightChild(newNode);
            }
            if (newNode != null) {
                newNode.setParent(node.getParent());
            }
        } else {
            // Node is root
            root = newNode;
            if (root != null) {
                root.setParent(null);
            }
        }
    }

    // Method to find the smallest node in a subtree
    private BSTNode findSmallest(BSTNode r) {
        if (r == null || r.getRecord() == null) {
            return null;
        }
        while (r.getLeftChild() != null && r.getLeftChild().getRecord() != null) {
            r = r.getLeftChild();
        }
        return r;
    }

    // Method to find the largest node in a subtree
    private BSTNode findLargest(BSTNode r) {
        if (r == null || r.getRecord() == null) {
            return null;
        }
        while (r.getRightChild() != null && r.getRightChild().getRecord() != null) {
            r = r.getRightChild();
        }
        return r;
    }
}
