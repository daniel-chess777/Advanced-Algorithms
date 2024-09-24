import java.util.Scanner;

public class RodCutting {

    // Method to find the maximum revenue by cutting the rod into smaller pieces
    public static int maxRevenue(int n, int[] prices) {
        // revenue array to store maximum revenue that can be obtained for each length
        int[] revenue = new int[n + 1];
        // Initializing the base case: no revenue from a rod of length 0
        revenue[0] = 0;

        // Build the revenue array for rods of all lengths up to n
        for (int i = 1; i <= n; i++) {
            // Initialize max revenue for current length i to a very small number to ensure it is updated
            int maxRev = Integer.MIN_VALUE;
            // Try different cuts for rod of length i
            for (int j = 1; j <= i; j++) {
                // Update max revenue for rod of length i by considering the price of cutting it into a piece of length j
                // and the revenue from the remaining length i-j
                maxRev = Math.max(maxRev, prices[j - 1] + revenue[i - j]);
            }
            // Store the maximum revenue obtainable for rod of length i
            revenue[i] = maxRev;
        }

        // Return the maximum revenue for the entire length of the rod
        return revenue[n];
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the length of the rod:");
        int n = scanner.nextInt();  // Reading the length of the rod

        System.out.println("Enter the prices for each length from 1 to " + n + ":");
        int[] prices = new int[n];
        // Read the prices for each piece length from 1 to n
        for (int i = 0; i < n; i++) {
            prices[i] = scanner.nextInt();
        }

        // Calculate and display the maximum revenue obtainable by cutting up the rod
        int maxRevenue = maxRevenue(n, prices);
        System.out.println("The maximum revenue obtainable is: " + maxRevenue);
    }
}
