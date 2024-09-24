import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class MaximumBipartiteMatching {

    // Performs a breadth-first search to find the shortest augmenting path in the residual graph.
    // Returns true if there is a path from source to sink; this path is stored in the 'parent' array.
    private static boolean bfs(int[][] residualGraph, int source, int sink, int[] parent) {
        boolean[] visited = new boolean[residualGraph.length]; // Tracks visited vertices
        Queue<Integer> queue = new LinkedList<>(); // Queue for BFS
        queue.add(source); // Start from the source
        visited[source] = true; // Mark source as visited
        parent[source] = -1; // Source has no parent

        // Standard BFS loop
        while (!queue.isEmpty()) {
            int u = queue.poll();

            for (int v = 0; v < residualGraph.length; v++) {
                if (!visited[v] && residualGraph[u][v] > 0) { // Check for unvisited vertices with a positive residual capacity
                    queue.add(v);
                    visited[v] = true;
                    parent[v] = u; // Set the parent to reconstruct the path later
                    if (v == sink) {
                        return true;  // If sink is reached, stop and return true
                    }
                }
            }
        }
        return false; // Return false if no augmenting path is found
    }

    // Ford-Fulkerson algorithm to compute maximum flow in a flow network, which is equal to the maximum bipartite matching in this context.
    private static int fordFulkerson(int[][] graph, int source, int sink) {
        int u, v;
        int[] parent = new int[graph.length]; // Array to store the augmenting path
        int maxFlow = 0; // Initialize max flow to 0

        // Create a residual graph where the capacity is the same as the original graph
        int[][] residualGraph = new int[graph.length][graph.length];
        for (u = 0; u < graph.length; u++) {
            for (v = 0; v < graph.length; v++) {
                residualGraph[u][v] = graph[u][v];
            }
        }

        // Augment the flow while there is a path from the source to the sink
        while (bfs(residualGraph, source, sink, parent)) {
            int pathFlow = Integer.MAX_VALUE; // Find the maximum flow through the path found by BFS

            // Calculate the minimum capacity in the found path
            for (v = sink; v != source; v = parent[v]) {
                u = parent[v];
                pathFlow = Math.min(pathFlow, residualGraph[u][v]);
            }

            // Update the residual capacities of the edges and the reverse edges along the path
            for (v = sink; v != source; v = parent[v]) {
                u = parent[v];
                residualGraph[u][v] -= pathFlow;
                residualGraph[v][u] += pathFlow;
            }

            maxFlow += pathFlow; // Add path flow to overall flow
        }

        return maxFlow; // Return the maximum flow found
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of applicants:");
        int m = scanner.nextInt();
        System.out.println("Enter the number of jobs:");
        int n = scanner.nextInt();

        // Early exit if there are no jobs or applicants to match
        if (m == 0 || n == 0) {
            System.out.println("The maximum number of applicants matching the jobs is: 0");
            return;
        }

        // Initialize the graph with additional two vertices for source and sink
        int[][] graph = new int[m + n + 2][m + n + 2];
        int source = 0; // Index of source in graph
        int sink = m + n + 1; // Index of sink in graph

        System.out.println("Enter the adjacency matrix:");
        // Read the adjacency matrix input and configure capacities from applicants to jobs
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                graph[i][j + m] = scanner.nextInt();
            }
        }

        // Set capacities of edges from source to applicants and from jobs to sink
        for (int i = 1; i <= m; i++) {
            graph[source][i] = 1; // Source to applicants
        }
        for (int j = 1; j <= n; j++) {
            graph[j + m][sink] = 1; // Jobs to sink
        }

        // Calculate maximum matching using the Ford-Fulkerson algorithm
        int result = fordFulkerson(graph, source, sink);
        System.out.println("The maximum number of applicants matching the jobs is: " + result);
    }
}
