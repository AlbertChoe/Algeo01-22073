import java.util.Scanner;

public class tes {
    public static void main(String[] args) {
        Matrix matriks = new Matrix();
        Matrix m2 = new Matrix();
        Scanner scanner = new Scanner(System.in);
        matriks.read_matrix_scan(scanner);
        // matriks.print_matrix(2);
        matriks.inverse_obe(scanner);
        matriks.print_matrix(2);
        scanner.close();
    }
}
