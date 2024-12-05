import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Maze {

    // Instance variables
    private Graph graph;           // Graph representing the maze structure
    private int startNodeId;       // ID of the starting node ('s')
    private int endNodeId;         // ID of the ending node ('x')
    private int coins;             // Number of coins available to pass through doors
    private int width;             // Width of the maze (number of columns)
    private int length;            // Length of the maze (number of rows)
    private List<GraphNode> path;  // List to store the path during Depth-First Search (DFS)

    // Constructor to initialize the maze by reading the input file
    public Maze(String inputFile) throws MazeException {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            readInput(reader); // Use helper method to read input from file
            reader.close();
        } catch (IOException | GraphException e) {
            throw new MazeException("Error reading input file: " + e.getMessage());
        }
    }

    // Getter for the graph
    public Graph getGraph() {
        return this.graph;
    }

    // Method to solve the maze using DFS and return the path as an Iterator
    public Iterator<GraphNode> solve() {
        path = new ArrayList<>(); // Initialize the path list to store the solution path
        try {
            // Reset the marks on all nodes before starting DFS
            for (int i = 0; i < width * length; i++) {
                graph.getNode(i).mark(false); // Unmark all nodes to ensure a fresh start
            }
            // Start DFS from the entrance node
            return DFS(coins, graph.getNode(startNodeId));
        } catch (GraphException e) {
            return null; // Return null if there's an issue with the graph or nodes
        }
    }

    // Private helper method to perform DFS to find a path from start to end
    private Iterator<GraphNode> DFS(int k, GraphNode currentNode) throws GraphException {
        currentNode.mark(true); // Mark the current node as visited to avoid cycles
        path.add(currentNode);  // Add the current node to the path

        // Base case: If the current node is the end node, return the path iterator
        if (currentNode.getName() == endNodeId) {
            return path.iterator(); // Successfully found a path to the end node
        }

        // Get all edges incident to the current node to explore neighbors
        Iterator<GraphEdge> edges = graph.incidentEdges(currentNode);
        if (edges != null) {
            while (edges.hasNext()) {
                GraphEdge edge = edges.next();
                GraphNode neighbor = edge.firstEndpoint().equals(currentNode)
                        ? edge.secondEndpoint()
                        : edge.firstEndpoint();

                if (!neighbor.isMarked()) { // If the neighbor has not been visited
                    int edgeType = edge.getType(); // Number of coins required to pass through this edge
                    if (k >= edgeType) { // If there are enough coins to pass through
                        // Recursively proceed to the neighbor node
                        Iterator<GraphNode> result = DFS(k - edgeType, neighbor);
                        if (result != null) {
                            return result; // Return the path if the end node is found
                        }
                    }
                }
            }
        }

        // Backtrack if no path is found from the current node
        currentNode.mark(false); // Unmark the node to allow other potential paths to use it
        path.remove(path.size() - 1); // Remove the current node from the path
        return null; // No valid path found from this node
    }

    // Helper method to read the maze input from the file
    private void readInput(BufferedReader inputReader) throws IOException, GraphException {
        inputReader.readLine(); // Skip the scale factor line (not used in the maze generation)

        // Read the values A (width), L (length), and k (coins)
        try {
            width = Integer.parseInt(inputReader.readLine().trim()); // Read the width of the maze
            length = Integer.parseInt(inputReader.readLine().trim()); // Read the length of the maze
            coins = Integer.parseInt(inputReader.readLine().trim()); // Read the number of coins available
        } catch (NumberFormatException e) {
            throw new IOException("Invalid format for A, L, or k");
        }

        // Initialize the graph with the number of nodes (width * length)
        int numNodes = width * length;
        graph = new Graph(numNodes);

        // Initialize startNodeId and endNodeId to invalid values (to check later if set correctly)
        startNodeId = -1;
        endNodeId = -1;

        // Read the maze lines and store them in a list
        List<String> mazeLines = new ArrayList<>();
        String line;
        while ((line = inputReader.readLine()) != null) {
            mazeLines.add(line.trim()); // Add each line of the maze to the list
        }

        // Validate the number of lines in the maze input
        if (mazeLines.size() != 2 * length - 1) {
            throw new IOException("Invalid number of maze lines");
        }

        // Process each line to build the graph representing the maze
        for (int i = 0; i < mazeLines.size(); i++) {
            String mazeLine = mazeLines.get(i);
            // Validate the length of each line
            if (mazeLine.length() != 2 * width - 1) {
                throw new IOException("Invalid maze line length at line " + (i + 5));
            }
            if (i % 2 == 0) {
                // Process rooms and horizontal links (even lines)
                processRoomLine(mazeLine, i / 2);
            } else {
                // Process vertical links and walls (odd lines)
                processLinkLine(mazeLine, (i - 1) / 2);
            }
        }

        // Ensure the maze has a defined start and end room
        if (startNodeId == -1 || endNodeId == -1) {
            throw new IOException("Maze does not have a start or end room");
        }
    }

    // Helper method to process each room line and add nodes/edges to the graph
    private void processRoomLine(String line, int row) throws GraphException, IOException {
        for (int col = 0; col < line.length(); col++) {
            char ch = line.charAt(col);
            if (col % 2 == 0) {
                // Room position (even columns represent rooms)
                int nodeId = row * width + col / 2;
                // Handle room character ('s' for start, 'x' for end, 'o' for regular room)
                switch (ch) {
                    case 's':
                        startNodeId = nodeId; // Set the start node ID
                        break;
                    case 'x':
                        endNodeId = nodeId; // Set the end node ID
                        break;
                    case 'o':
                        // Regular room, no special handling needed
                        break;
                    default:
                        throw new IOException("Invalid room character: '" + ch + "' at row " + row + ", column " + (col / 2));
                }
            } else {
                // Horizontal link between nodes (odd columns represent links)
                int node1Id = row * width + (col - 1) / 2;
                int node2Id = node1Id + 1; // Node to the right of node1
                processLink(node1Id, node2Id, ch); // Process the link between nodes
            }
        }
    }

    // Helper method to process each link line and add edges to the graph
    private void processLinkLine(String line, int row) throws GraphException, IOException {
        for (int col = 0; col < line.length(); col++) {
            char ch = line.charAt(col);
            if (col % 2 == 0) {
                // Vertical link between nodes (even columns represent links)
                int node1Id = row * width + col / 2;
                int node2Id = node1Id + width; // Node directly below node1
                processLink(node1Id, node2Id, ch); // Process the link between nodes
            } else {
                // Wall position (odd columns represent walls, should be 'w')
                if (ch != 'w') {
                    throw new IOException("Invalid wall character: '" + ch + "' at line " + (row * 2 + 1) + ", column " + (col / 2));
                }
            }
        }
    }

    // Helper method to process links between nodes (either corridors or doors)
    private void processLink(int node1Id, int node2Id, char linkChar) throws GraphException, IOException {
        int linkType;
        String label;
        if (linkChar == 'c') {
            // Corridor link (no cost to pass through)
            linkType = 0;
            label = "corridor";
        } else if (linkChar == 'w') {
            // Wall, no edge should be created between nodes
            return;
        } else if (Character.isDigit(linkChar)) {
            // Door that requires coins to pass through
            linkType = Character.getNumericValue(linkChar); // Cost in coins to pass through the door
            label = "door";
        } else {
            throw new IOException("Invalid link character: '" + linkChar + "'");
        }

        // Insert the edge into the graph connecting the two nodes
        insertEdge(node1Id, node2Id, linkType, label);
    }

    // Helper method to insert an edge between two nodes in the graph
    private void insertEdge(int node1, int node2, int linkType, String label) throws GraphException {
        GraphNode u = graph.getNode(node1); // Get the first node
        GraphNode v = graph.getNode(node2); // Get the second node
        graph.insertEdge(u, v, linkType, label); // Insert an edge between nodes u and v with the given type and label
    }
}
