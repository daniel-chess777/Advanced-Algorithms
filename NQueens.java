import java.util.Arrays;
import java.util.Scanner;

public class NQueens {
    private int[] board; // This array will store the position of queens in each row
    private int N; // Size of the chess board (N x N)

    // Constructor to initialize the board
    public NQueens(int N) {
        this.N = N;
        this.board = new int[N];
        Arrays.fill(board, -1); // Initialize all positions to -1 indicating no queen is placed
    }

    // Method to check if placing the queen in the current row is safe
    private boolean isSafe(int row, int col) {
        for (int i = 0; i < col; i++) {
            if (board[i] == row || Math.abs(board[i] - row) == Math.abs(i - col)) {
                // Check if two queens are in the same row or in diagonal positions
                return false;
            }
        }
        return true;
    }

    // Method to place queens in the board using backtracking
    private boolean placeQueens(int col) {
        if (col >= N) {
            // All queens are placed successfully
            return true;
        }

        for (int i = 0; i < N; i++) {
            if (isSafe(i, col)) {
                board[col] = i; // Place the queen at (i, col)
                if (placeQueens(col + 1)) { // Recursively place the rest of the queens
                    return true;
                }
                board[col] = -1; // Backtrack if placing queen does not lead to a solution
            }
        }
        return false; // No valid place for this queen
    }

    // Method to output the board configuration
    public void displayBoard() {
        if (!placeQueens(0)) {
            System.out.println("No solution exists for " + N + " queens.");
            return;
        }
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print((board[j] == i ? 1 : 0) + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of queens: ");
        int N = scanner.nextInt();

        NQueens nQueens = new NQueens(N);
        nQueens.displayBoard();
    }
}
