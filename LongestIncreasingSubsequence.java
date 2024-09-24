import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class LongestIncreasingSubsequence {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Prompt the user for input
        System.out.println("Enter the sequence of numbers separated by space:");
        // Read the entire line of numbers as a string and split it into an array of strings
        String[] tokens = scanner.nextLine().split(" ");
        // Convert the string array into an integer array
        int[] sequence = Arrays.stream(tokens).mapToInt(Integer::parseInt).toArray();

        // Call the findLIS method to determine the longest increasing subsequence
        int[] lis = findLIS(sequence);
        // Output the length of the LIS
        System.out.println("LIS = " + lis.length);
        // Output the elements of the LIS
        System.out.print("LIS is: ");
        for (int i = 0; i < lis.length; i++) {
            if (i > 0) System.out.print(" ");
            System.out.print(lis[i]);
        }
            System.out.println();
    }

    private static int[] findLIS(int[] sequence) {
        int n = sequence.length;
        // dp[i] will hold the length of the longest increasing subsequence ending with sequence[i]
        int[] dp = new int[n];
        // Predecessor array to reconstruct the path of the LIS
        int[] predecessor = new int[n];
        // Initialize each position's LIS length to 1 because the minimum length of LIS is 1
        Arrays.fill(dp, 1);
        // Initialize the predecessor array to -1 indicating no predecessors initially
        Arrays.fill(predecessor, -1);

        int maxLen = 0, endIndex = -1;

        // Iterate through each element to calculate the LIS up to that element
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                // If sequence[j] is less than sequence[i], it can be a part of the increasing subsequence ending with sequence[i]
                if (sequence[j] < sequence[i] && dp[j] + 1 > dp[i]) {
                    dp[i] = dp[j] + 1;
                    predecessor[i] = j;  // Update the predecessor to j
                }
            }
            // Update the overall maximum length of LIS and the index at which it ends
            if (dp[i] > maxLen) {
                maxLen = dp[i];
                endIndex = i;
            }
        }

        // Reconstruct the LIS by backtracking from the endIndex using the predecessor array
        ArrayList<Integer> lis = new ArrayList<>();
        while (endIndex != -1) {
            lis.add(0, sequence[endIndex]);  // Insert the element at the start of the list
            endIndex = predecessor[endIndex];  // Move to the next element in the LIS
        }

        // Convert the ArrayList to an array and return it
        return lis.stream().mapToInt(i -> i).toArray();
    }
}

