import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class StronglyConnectedComponents {

    // Performs Depth First Search (DFS) on the graph from a given vertex
    // 'v' is the starting vertex
    // 'visited' tracks visited vertices
    // 'stack' stores the vertices in order of completion
    private static void dfs(ArrayList<Integer>[] graph, int v, boolean[] visited, Stack<Integer> stack) {
        visited[v] = true;  // Mark the current node as visited
        // Explore each adjacent vertex recursively
        for (int neighbor : graph[v]) {
            if (!visited[neighbor]) {
                dfs(graph, neighbor, visited, stack);
            }
        }
        // All neighbors processed, push current vertex to stack
        stack.push(v);
    }

    // Reverses the direction of all arcs in the graph to get the transpose graph
    // 'n' is the number of vertices in the graph
    private static ArrayList<Integer>[] reverseGraph(ArrayList<Integer>[] graph, int n) {
        ArrayList<Integer>[] reverse = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            reverse[i] = new ArrayList<>();
        }
        // For each vertex and each edge, reverse the direction
        for (int v = 0; v < n; v++) {
            for (int neighbor : graph[v]) {
                reverse[neighbor].add(v);
            }
        }
        return reverse;
    }

    // Checks if the graph is strongly connected using Kosaraju's two-pass DFS method
    public static boolean isStronglyConnected(ArrayList<Integer>[] graph, int n) {
        boolean[] visited = new boolean[n];
        Stack<Integer> stack = new Stack<>();

        // First DFS to fill the stack with the order of vertices finished
        dfs(graph, 0, visited, stack);

        // Check if the first DFS visited all vertices
        for (boolean wasVisited : visited) {
            if (!wasVisited) {
                return false; // Not all vertices were visited
            }
        }

        // Create the reversed graph
        ArrayList<Integer>[] reverse = reverseGraph(graph, n);
        visited = new boolean[n]; // Reset visited for the second DFS

        // Second DFS on the reversed graph
        dfs(reverse, stack.pop(), visited, new Stack<>());

        // Check if the second DFS visited all vertices
        for (boolean wasVisited : visited) {
            if (!wasVisited) {
                return false; // Not all vertices were visited in the reversed graph
            }
        }

        return true; // Graph is strongly connected
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter number of vertices and edges:");
        int n = scanner.nextInt(); // Number of vertices
        int e = scanner.nextInt(); // Number of edges
        
        ArrayList<Integer>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        System.out.println("Enter the edges in the format 'from to':");
        for (int i = 0; i < e; i++) {
            int from = scanner.nextInt(); // Start vertex of the edge
            int to = scanner.nextInt();   // End vertex of the edge
            graph[from].add(to);          // Add edge to the graph
        }

        // Check if the graph is strongly connected and print the result
        //The idea is, if every node can be reached from a vertex v, and every 
        //node can reach v, then the graph is strongly connected
        System.out.println(isStronglyConnected(graph, n) ? "yes" : "no");
    }
}
