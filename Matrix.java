import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Matrix {
    private int n_row;
    private int n_col;
    private float[][] data;

    public Matrix(int n_row, int n_col) {
        this.n_row= n_row;
        this.n_col = n_col;
        this.data = new float[n_row][n_col];
    }

    public int get_row() {
        return this.n_row;
    }

    public int get_col() {
        return this.n_col;
    }

    public void set_row(int new_n_row) {
        this.n_row = new_n_row;
    }

    public void set_col(int new_n_col) {
        this.n_col = new_n_col;
    }

    public void set_elmt(int row, int col, float value) {
        if (row >= 0 && row < this.get_row() && col >= 0 && col < get_col()) {
            this.data[row][col] = value;
        } else {
            System.out.println("Invalid col/row!");
        }
    }

    public void read_matrix_scan() {  
        Scanner scanner = new Scanner(System.in);
        System.out.println(String.format("Masukkan matriks %dx%d : ", this.get_row(), this.get_col()));
        for (int i = 0; i < this.get_row(); i++) {
            for (int j = 0; j < this.get_col(); j++) {
                this.data[i][j] = scanner.nextFloat();
            }
        }
        scanner.close();
    }

    public void read_matrix_from_file(String file_name) {
        try { 
            File file = new File(file_name);
            Scanner scanner = new Scanner(file);

            for (int i = 0; i < this.get_row(); i++) {
                for (int j = 0; j < this.get_col(); j++) {
                    if (scanner.hasNextFloat()) {
                        this.set_elmt(i, j, scanner.nextFloat());
                    } else {
                        scanner.close();
                        return;
                    }
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File " + file_name + " tidak ditemukan.");
        }
    }
}
