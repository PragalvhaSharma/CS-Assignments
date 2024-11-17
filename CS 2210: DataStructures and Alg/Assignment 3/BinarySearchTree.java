public class BinarySearchTree {
    private BSTNode root; // The root node of the binary search tree

    // Constructor that initializes the root to null, creating an empty tree.
    public BinarySearchTree() {
        this.root = null;
    }

    // Returns the root node of the tree.
    public BSTNode getRoot() {
        return root;
    }

    // Finds and returns the node with the given key starting from root r; returns null if the key is not found.
    public BSTNode get(BSTNode r, Key k) {
        if (r == null) {
            return null; // Key not found
        }

        int comparison = k.compareTo(r.getRecord().getKey());
        if (comparison == 0) {
            return r; // Key found
        } else if (comparison < 0) {
            return get(r.getLeftChild(), k); // Search in left subtree
        } else {
            return get(r.getRightChild(), k); // Search in right subtree
        }
    }

    // Adds a record to the tree. Throws a DictionaryException if a record with the same key already exists.
    public void insert(BSTNode r, Record d) throws DictionaryException {
        if (root == null) {
            root = new BSTNode(d); // Set root if tree is empty
            return;
        }

        int comparison = d.getKey().compareTo(r.getRecord().getKey());

        if (comparison == 0) {
            throw new DictionaryException("Key already exists."); // Duplicate key
        }

        // Determine the correct child node to insert into (left or right)
        BSTNode child = (comparison < 0) ? r.getLeftChild() : r.getRightChild();
        
        if (child == null) {
            BSTNode newNode = new BSTNode(d);
            newNode.setParent(r);
            if (comparison < 0) {
                r.setLeftChild(newNode); // Insert as left child
            } else {
                r.setRightChild(newNode); // Insert as right child
            }
        } else {
            insert(child, d); // Recursively insert in the appropriate subtree
        }
    }

    // Deletes the node with the given key from the tree. Throws a DictionaryException if the key is not found.
    public void remove(BSTNode r, Key k) throws DictionaryException {
        BSTNode nodeToDelete = get(r, k);

        if (nodeToDelete == null) {
            throw new DictionaryException("Key not found."); // Key doesn't exist
        } else {
            removeNode(nodeToDelete);
        }
    }

    // Returns the successor node of the given key; returns null if successor doesn't exist.
    public BSTNode successor(BSTNode r, Key k) {
        BSTNode current = get(r, k);
        if (current == null) {
            return null; // Key doesn't exist
        }

        // Case 1: Node has a right child, successor is the smallest node in the right subtree
        if (current.getRightChild() != null) {
            return smallest(current.getRightChild());
        } else {
            // Case 2: No right child, find the first ancestor where current is in the left subtree
            BSTNode parent = current.getParent();
            while (parent != null && current == parent.getRightChild()) {
                current = parent;
                parent = parent.getParent();
            }
            return parent;
        }
    }

    // Returns the predecessor node of the given key; returns null if predecessor doesn't exist.
    public BSTNode predecessor(BSTNode r, Key k) {
        BSTNode targetNode = get(r, k);
        if (targetNode == null) {
            return null; // Key not found
        }

        // Case 1: Node has a left child, predecessor is the largest node in the left subtree
        if (targetNode.getLeftChild() != null) {
            return largest(targetNode.getLeftChild());
        }

        // Case 2: No left child, find the first ancestor where targetNode is in the right subtree
        BSTNode ancestor = targetNode.getParent();
        while (ancestor != null && targetNode == ancestor.getLeftChild()) {
            targetNode = ancestor;
            ancestor = ancestor.getParent();
        }
        return ancestor;
    }

    // Returns the node with the smallest key in the subtree rooted at r.
    public BSTNode smallest(BSTNode r) {
        if (r == null) {
            return null;
        }
        while (r.getLeftChild() != null) {
            r = r.getLeftChild(); // Traverse to the leftmost node
        }
        return r;
    }

    // Returns the node with the largest key in the subtree rooted at r.
    public BSTNode largest(BSTNode r) {
        if (r == null) {
            return null;
        }
        while (r.getRightChild() != null) {
            r = r.getRightChild(); // Traverse to the rightmost node
        }
        return r;
    }

    // Removes a specific node from the tree.
    private void removeNode(BSTNode node) {
        // Case 1: Node is a leaf
        if (node.isLeaf()) {
            replaceNodeInParent(node, null);
            return;
        }

        // Case 2: Node has two children
        if (node.getLeftChild() != null && node.getRightChild() != null) {
            BSTNode successor = smallest(node.getRightChild());
            node.setRecord(successor.getRecord()); // Copy successor's data
            removeNode(successor); // Recursively remove the successor
            return;
        }

        // Case 3: Node has one child
        BSTNode child;
        if (node.getLeftChild() != null) {
            child = node.getLeftChild();
        } else {
            child = node.getRightChild();
        }
        replaceNodeInParent(node, child); // Replace the node with its child
    }

    // Private helper method to replace a node in its parent.
    private void replaceNodeInParent(BSTNode node, BSTNode newNode) {
        BSTNode parent = node.getParent();

        if (parent != null) {
            // Update the parent's child pointer
            if (node == parent.getLeftChild()) {
                parent.setLeftChild(newNode);
            } else {
                parent.setRightChild(newNode);
            }
        } else {
            // Node is the root of the tree
            root = newNode;
        }

        // Update new node’s parent pointer if newNode is not null
        if (newNode != null) {
            newNode.setParent(parent);
        }
    }
}
