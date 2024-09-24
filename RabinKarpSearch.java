/**
 * Rabin-Karp Substring Search Algorithm Implementation
 *
 * This Java program implements the Rabin-Karp algorithm for substring search, known for its use of hashing 
 * to find an average and worst-case running time faster than a simple search. This version is case-insensitive 
 * and can detect all occurrences of the pattern in the provided text.
 * 
 * Features:
 * - Case Insensitivity: Implements a case-insensitive search by internally converting both the text and the
 *   pattern to lowercase. This approach ensures robustness across different input cases.
 * 
 * - All Occurrences: Capable of identifying every occurrence of the pattern within the text, thanks to
 *   meticulous hash comparisons followed by exact substring verification (Las Vegas version).
 * 
 * - Efficiency: Utilizes efficient hashing to speed up the search process and minimize comparisons, 
 *   especially suitable for long texts or multiple queries.
 *
 * Usage:
 * The user is prompted to input the text and the pattern, after which the program outputs the starting
 * indices where the pattern is found, or a message if it is not found.
 */

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class RabinKarpSearch {

    private static final long PRIME = 101; // A prime number for the hash function

    /**
     * Computes the hash of a string using a polynomial rolling hash function.
     * @param s the string to hash
     * @param m the length of the string
     * @return the computed hash value
     */
    private static long hash(String s, int m) {
        long h = 0;
        for (int i = 0; i < m; i++) {
            h = h * PRIME + s.charAt(i);
        }
        return h;
    }

    /**
     * Implements the Rabin-Karp substring search algorithm (Las Vegas version).
     * This function searches for all occurrences of 'pattern' in 'text' and returns their starting indices.
     * Both text and pattern are converted to lowercase to ensure case-insensitivity.
     * 
     * @param text the text to search within
     * @param pattern the pattern to find
     * @return a list of starting indices where the pattern is found in the text
     */
    public static List<Integer> search(String text, String pattern) {
        text = text.toLowerCase();  // Convert text to lowercase for case-insensitive search
        pattern = pattern.toLowerCase();  // Convert pattern to lowercase for case-insensitive search
        int n = text.length();
        int m = pattern.length();
        List<Integer> positions = new ArrayList<>();
        long patternHash = hash(pattern, m);
        long textHash = hash(text, m);

        long pow = 1; // PRIME^m-1 for use in removing old character
        for (int i = 0; i < m - 1; i++) pow *= PRIME;

        for (int i = 0; i <= n - m; i++) {
            // Check if hash values match, if so, check for actual substring match
            if (patternHash == textHash && text.substring(i, i + m).equals(pattern)) {
                positions.add(i); // Add position if match is confirmed
                // Continue to check for more occurrences beyond the first match
            }

            // Compute hash of next window of text: Remove leading digit, add trailing digit
            if (i < n - m) {
                textHash = (textHash - text.charAt(i) * pow) * PRIME + text.charAt(i + m);
            }
        }

        return positions;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the text: ");
        String text = scanner.nextLine();
        System.out.print("Enter the pattern: ");
        String pattern = scanner.nextLine();

        List<Integer> positions = search(text, pattern);
        if (positions.isEmpty()) {
            System.out.println("Pattern not found");
        } else {
            System.out.print("Pattern '" + pattern + "' found at indices ");
            for (int i = 0; i < positions.size(); i++) {
                if (i > 0) System.out.print(", ");
                System.out.print(positions.get(i));
            }
            System.out.println(".");
        }
    }
}
