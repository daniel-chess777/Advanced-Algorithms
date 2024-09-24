import java.util.*;

class Edge implements Comparable<Edge> {
    int src, dest, weight;

    public Edge(int src, int dest, int weight) {
        this.src = src;
        this.dest = dest;
        this.weight = weight;
    }

    // To sort edges by weights
    public int compareTo(Edge compareEdge) {
        return this.weight - compareEdge.weight;
    }
}

public class PrimsMST {
    // Utility function to find the minimum spanning tree using Prim's algorithm
    public static void printMST(ArrayList<Edge>[] adjList, int V) {
        boolean[] inMST = new boolean[V];
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        List<Edge> mstEdges = new ArrayList<>();

        // Start from vertex 0: arbitrarily choosing the starting vertex
        int startVertex = 0;
        inMST[startVertex] = true;
        pq.addAll(adjList[startVertex]);

        int mstWeight = 0;

        while (!pq.isEmpty() && mstEdges.size() < V - 1) {
            // Choose the edge with the minimum weight
            Edge edge = pq.poll();

            if (inMST[edge.dest]) continue; // Skip if the destination vertex is already in MST

            // Include this edge in MST
            mstEdges.add(edge);
            mstWeight += edge.weight;
            inMST[edge.dest] = true;

            // Add all edges from this vertex to the queue
            for (Edge e : adjList[edge.dest]) {
                if (!inMST[e.dest]) {
                    pq.add(e);
                }
            }
        }

        // Print the result as per the specified format
        mstEdges.forEach(e -> System.out.println("Edge " + e.src + "-" + e.dest + " has a weight of " + e.weight));
        System.out.println("MST = " + mstWeight);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int V = scanner.nextInt(); // Number of vertices
        int E = scanner.nextInt(); // Number of edges

        // Adjacency list representation of the graph
        ArrayList<Edge>[] adjList = new ArrayList[V];
        for (int i = 0; i < V; i++) {
            adjList[i] = new ArrayList<>();
        }

        for (int i = 0; i < E; i++) {
            int src = scanner.nextInt();
            int dest = scanner.nextInt();
            int weight = scanner.nextInt();

            // As the graph is undirected, add both directions
            adjList[src].add(new Edge(src, dest, weight));
            adjList[dest].add(new Edge(dest, src, weight));
        }

        // Calling the function to find and print the MST
        printMST(adjList, V);
    }
}

