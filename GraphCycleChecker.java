import java.util.*;

public class GraphCycleChecker {
    private int numVertices; // Number of vertices in the graph
    private ArrayList<ArrayList<Integer>> adjList; // Adjacency list to store the graph

    // Constructor to initialize the graph
    public GraphCycleChecker(int numVertices) {
        this.numVertices = numVertices;
        this.adjList = new ArrayList<>();
        for (int i = 0; i < numVertices; i++) {
            adjList.add(new ArrayList<>());
        }
    }

    // Method to add an edge to the graph
    public void addEdge(int src, int dest) {
        adjList.get(src).add(dest);
        adjList.get(dest).add(src); // Since the graph is undirected, add both connections
    }

    // Helper method to perform DFS to check for a cycle in the graph
    private boolean dfs(int v, boolean[] visited, int parent) {
        visited[v] = true;

        // Check adjacent vertices
        for (Integer adj : adjList.get(v)) {
            if (!visited[adj]) {
                if (dfs(adj, visited, v)) {
                    return true;
                }
            } else if (adj != parent) {
                // If an adjacent is visited and not parent of current vertex, then there is a cycle.
                return true;
            }
        }
        return false;
    }

    // Method to check if the graph contains a cycle
    public boolean isCyclic() {
        boolean[] visited = new boolean[numVertices]; // Track visited vertices

        // Call the recursive helper function to detect cycle in different DFS trees
        for (int i = 0; i < numVertices; i++) {
            if (!visited[i]) { // Don't recur for already visited vertex
                if (dfs(i, visited, -1)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numVertices = scanner.nextInt(); // Number of vertices
        int numEdges = scanner.nextInt(); // Number of edges

        GraphCycleChecker graph = new GraphCycleChecker(numVertices);

        for (int i = 0; i < numEdges; i++) {
            int src = scanner.nextInt();
            int dest = scanner.nextInt();
            graph.addEdge(src, dest);
        }

        // Output result based on cycle detection
        System.out.println(graph.isCyclic() ? "no" : "yes");
    }
}
