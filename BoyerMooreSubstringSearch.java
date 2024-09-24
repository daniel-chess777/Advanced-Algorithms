/**
 * Boyer-Moore Substring Search Algorithm Implementation
 * 
 * This implementation of the Boyer-Moore substring search algorithm provides an efficient method for finding
 * all occurrences of a pattern within a given text. The Boyer-Moore algorithm is known for its capability
 * to skip over portions of the text, making it more efficient than conventional search algorithms in many scenarios.
 * 
 * Features:
 * - Case-Insensitive Search: The algorithm performs the search in a case-insensitive manner by converting
 *   both the text and the pattern to lowercase internally. This feature allows it to effectively handle
 *   variations in text and pattern capitalization.
 * 
 * - Exact Case Output Match: While the search is case-insensitive, the output preserves the original case of the
 *   pattern as entered by the user. This ensures that the results are displayed exactly as the user expects,
 *   enhancing usability and clarity.
 * 
 * - Comprehensive Output: The algorithm identifies and returns all starting indices of the pattern within the text,
 *   ensuring a thorough search result.
 * 
 * Usage:
 * The program prompts the user to enter the text and the pattern. It then displays the indices at which the pattern
 * is found in the text. If the pattern is not found, it indicates so.
 */

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class BoyerMooreSubstringSearch {

    // Function to preprocess the pattern to create the bad character shift table
    private static int[] preprocessBadCharacterTable(String pattern) {
        int[] badChar = new int[256]; // ASCII table size for simplicity
        for (int i = 0; i < 256; i++) {
            badChar[i] = -1; // Initialize all occurrences as -1
        }
        for (int i = 0; i < pattern.length(); i++) {
            badChar[(int) Character.toLowerCase(pattern.charAt(i))] = i; // Set the index of the character in the pattern
        }
        return badChar;
    }

    // Boyer-Moore substring search method
    public static List<Integer> search(String text, String pattern) {
        text = text.toLowerCase(); // Convert text to lowercase for case-insensitive search
        String lowerCasePattern = pattern.toLowerCase(); // Convert pattern to lowercase for case-insensitive search
        int[] badChar = preprocessBadCharacterTable(lowerCasePattern);
        int s = 0; // s is the shift of the pattern with respect to text
        List<Integer> positions = new ArrayList<>();

        while (s <= (text.length() - lowerCasePattern.length())) {
            int j = lowerCasePattern.length() - 1;

            while (j >= 0 && lowerCasePattern.charAt(j) == text.charAt(s + j)) {
                j--;
            }

            if (j < 0) {
                positions.add(s); // If the pattern is present at current shift, add the position
                // Move the pattern to the next position after the current match
                s += (s + lowerCasePattern.length() < text.length()) ? lowerCasePattern.length() - badChar[text.charAt(s + lowerCasePattern.length())] : 1;
            } else {
                // Shift the pattern so that the bad character in text aligns with the last occurrence of it in pattern
                s += Math.max(1, j - badChar[text.charAt(s + j)]);
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
