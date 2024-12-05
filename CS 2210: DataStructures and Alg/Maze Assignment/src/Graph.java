import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Graph implements GraphADT {

    // Map to store nodes by their ID
    private Map<Integer, GraphNode> nodes;
    // Adjacency list to store edges for each node
    private Map<GraphNode, List<GraphEdge>> adjacencyList;

    // Constructor: creates an empty graph with n nodes named from 0 to n-1
    public Graph(int n) {
        // Initialize representation with empty adjacency lists
        nodes = new HashMap<>();
        adjacencyList = new HashMap<>();

        // Create nodes and add them to the graph
        for (int i = 0; i < n; i++) {
            GraphNode node = new GraphNode(i);
            nodes.put(i, node);
            adjacencyList.put(node, new ArrayList<GraphEdge>()); // Initialize adjacency list for each node
        }
    }

    // Method to insert an edge between two nodes in the graph
    @Override
    public void insertEdge(GraphNode nodeu, GraphNode nodev, int type, String label) throws GraphException {
        // Check if both nodes exist in the graph
        if (!nodes.containsValue(nodeu) || !nodes.containsValue(nodev)) {
            throw new GraphException("One or both nodes do not exist.");
        }

        // Check if an edge already exists between nodeu and nodev
        if (areAdjacent(nodeu, nodev)) {
            throw new GraphException("An edge between the nodes already exists.");
        }

        // Create a new edge between the nodes
        GraphEdge edge = new GraphEdge(nodeu, nodev, type, label);

        // Add the edge to both nodes' adjacency lists (since it's an undirected graph)
        adjacencyList.get(nodeu).add(edge);
        adjacencyList.get(nodev).add(edge);
    }

    // Method to get a node by its ID
    @Override
    public GraphNode getNode(int u) throws GraphException {
        // Retrieve the node with the given ID
        GraphNode node = nodes.get(u);
        if (node == null) {
            throw new GraphException("Node " + u + " does not exist.");
        }
        return node;
    }

    // Method to get all edges incident to a node
    @Override
    public Iterator<GraphEdge> incidentEdges(GraphNode u) throws GraphException {
        // Check if the node exists in the graph
        if (!nodes.containsValue(u)) {
            throw new GraphException("Node does not exist.");
        }

        // Retrieve the list of edges incident to the node
        List<GraphEdge> edges = adjacencyList.get(u);
        if (edges.isEmpty()) {
            return null; // Return null if there are no incident edges
        }
        return edges.iterator();
    }

    // Method to get an edge between two nodes
    @Override
    public GraphEdge getEdge(GraphNode u, GraphNode v) throws GraphException {
        // Check if both nodes exist in the graph
        if (!nodes.containsValue(u) || !nodes.containsValue(v)) {
            throw new GraphException("One or both nodes do not exist.");
        }

        // Iterate through the incident edges of node u to find the edge connecting u and v
        for (GraphEdge edge : adjacencyList.get(u)) {
            GraphNode first = edge.firstEndpoint();
            GraphNode second = edge.secondEndpoint();

            // Check if the edge connects nodes u and v
            if ((first.equals(u) && second.equals(v)) || (first.equals(v) && second.equals(u))) {
                return edge;
            }
        }

        throw new GraphException("No edge exists between the specified nodes.");
    }

    // Method to check if two nodes are adjacent 
    @Override
    public boolean areAdjacent(GraphNode u, GraphNode v) throws GraphException {
        // Use the getEdge method to determine if an edge exists between u and v
        try {
            getEdge(u, v);
            return true; // Edge exists, so the nodes are adjacent
        } catch (GraphException e) {
            return false; // No edge exists, so the nodes are not adjacent
        }
    }
}
