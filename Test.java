// FILE FOR TESTING ONLY!

import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Matrix test_matrix = new Matrix(0,0);
        test_matrix.read_matrix_scan(scanner);
        test_matrix.op_to_triangle(true);
        test_matrix.print_matrix();
        scanner.close();
    }
}
