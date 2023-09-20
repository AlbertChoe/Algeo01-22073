import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("test");
        //Get nRow + nCol
        System.out.print("Enter the number of rows: ");
        int numRows = scanner.nextInt();
        System.out.print("Enter the number of columns: ");
        int numCols = scanner.nextInt();

        // Initialize matrix
        int[][] matrix = new int[numRows][numCols];

<<<<<<< Updated upstream
        
=======
        System.out.println("(TEST) Enter the elements of the matrix:");
>>>>>>> Stashed changes
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                System.out.print("Enter element at position (" + i + ", " + j + "): ");
                matrix[i][j] = scanner.nextInt();
            }
        }
        int test = 0;

        scanner.close();

        //Output Matrix
        System.out.println("Input Matrix:");
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}
