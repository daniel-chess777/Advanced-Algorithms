import java.util.Scanner;

public class CandyCollector {

    // Method to find the maximum sentimental value John can carry
    public static int maxSentimentalValue(int[] values, int[] weights, int capacity) {
        int n = values.length;
        // Create a DP array where dp[i] will represent the maximum value achievable with weight i
        int[] dp = new int[capacity + 1];

        // Build the dp array bottom-up
        for (int i = 0; i < n; i++) {
            // Traverse backwards to ensure that we consider each item at most once
            for (int w = capacity; w >= weights[i]; w--) {
                dp[w] = Math.max(dp[w], dp[w - weights[i]] + values[i]);
            }
        }
        return dp[capacity]; // The maximum value we can achieve with the given capacity
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter the number of candies: ");
        int n = scanner.nextInt(); // Number of candies

        int[] values = new int[n];
        int[] weights = new int[n];

        System.out.print("Enter the sentimental values of the candies: ");
        // Reading sentimental values of candies
        for (int i = 0; i < n; i++) {
            values[i] = scanner.nextInt();
        }

        System.out.print("Enter the weights of the candies: ");
        // Reading weights of candies
        for (int i = 0; i < n; i++) {
            weights[i] = scanner.nextInt();
        }

        System.out.print("Enter the maximum weight that can be carried: ");
        int capacity = scanner.nextInt(); // Maximum weight that can be carried

        System.out.println("Output: " + maxSentimentalValue(values, weights, capacity));
    }
}
