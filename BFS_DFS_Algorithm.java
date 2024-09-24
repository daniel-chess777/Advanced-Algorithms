import java.util.*;

class Graph {
    private int numVertices; // Number of vertices in the graph
    private LinkedList<Integer>[] adjLists; // Array of lists for adjacency list representation

    // Graph constructor initializes the vertices and adjacency lists
    Graph(int vertices) {
        numVertices = vertices;
        adjLists = new LinkedList[vertices];

        // Initialize linked list for each vertex so it can store adjacent vertices
        for (int i = 0; i < vertices; i++) {
            adjLists[i] = new LinkedList<>();
        }
    }

    // Adds an undirected edge between src and dest
    void addEdge(int src, int dest) {
        adjLists[src].add(dest); // Add dest to src's list
        adjLists[dest].add(src); // Since graph is undirected, add src to dest's list as well
    }

    // Prints all adjacent vertices of a given vertex
    void printAdjVertices(int vertex) {
        System.out.println("Adjacent vertices of vertex " + vertex + ": " + adjLists[vertex]);
    }

    // Returns the degree of the given vertex
    int degreeVertex(int vertex) {
        return adjLists[vertex].size(); // The size of the linked list of vertex is its degree
    }

    // Breadth First Search (BFS) algorithm
    void BFS(int startVertex) {
        boolean[] visited = new boolean[numVertices]; // Track visited vertices
        LinkedList<Integer> queue = new LinkedList<>(); // Queue for BFS

        visited[startVertex] = true; // Mark the start vertex as visited
        queue.add(startVertex); // Enqueue start vertex

        while (!queue.isEmpty()) {
            int v = queue.poll(); // Dequeue a vertex from queue and print it
            System.out.print(v + " ");

            // Process all adjacent vertices of the dequeued vertex v
            for (int adjVertex : adjLists[v]) {
                if (!visited[adjVertex]) { // If an adjacent has not been visited, then mark it visited and enqueue it
                    visited[adjVertex] = true;
                    queue.add(adjVertex);
                }
            }
        }
        System.out.println(); // Newline for formatting output
    }

    // Depth First Search (DFS) algorithm
    void DFS(int startVertex) {
        boolean[] visited = new boolean[numVertices]; // Track visited vertices
        DFSUtil(startVertex, visited); // Call DFS utility function
        System.out.println(); // Newline for formatting output
    }

    // Recursive function used by DFS
    private void DFSUtil(int v, boolean[] visited) {
        visited[v] = true; // Mark the current node as visited
        System.out.print(v + " ");

        // Recur for all vertices adjacent to this vertex
        for (int adjVertex : adjLists[v]) {
            if (!visited[adjVertex]) {
                DFSUtil(adjVertex, visited);
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        // The "6 6" from sample input represents 6 vertices and 6 edges, forming the graph structure
        Graph g = new Graph(6); // Creates a graph with 6 vertices

        // Adding edges as per sample input
        g.addEdge(0, 1);
        g.addEdge(0, 3);
        g.addEdge(1, 2);
        g.addEdge(2, 4);
        g.addEdge(3, 4);
        g.addEdge(3, 5);

        System.out.println("BFS starting from vertex 0:");
        g.BFS(0); // Perform BFS starting from vertex 0

        System.out.println("DFS starting from vertex 0:");
        g.DFS(0); // Perform DFS starting from vertex 0
    }
}
