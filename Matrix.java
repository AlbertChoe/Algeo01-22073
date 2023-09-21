import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Matrix {
    private int n_row;
    private int n_col;
    private float[][] contents;

    public Matrix(int n_row, int n_col) {
        this.n_row= n_row;
        this.n_col = n_col;
        this.contents = new float[n_row][n_col];
    }

    public int get_row() {
        return this.n_row;
    }

    public int get_col() {
        return this.n_col;
    }

    public float get_elmt(int row, int col) {
        return this.contents[row][col];
    }

    private void set_new_size(int new_n_row, int new_n_col) {
        this.n_row = new_n_row;
        this.n_col = new_n_col;
        this.contents = new float[new_n_row][new_n_col];
    }

    public void set_elmt(int row, int col, float value) {
        if (row >= 0 && row < this.get_row() && col >= 0 && col < get_col()) {
            this.contents[row][col] = value;
        } else {
            System.out.println("Invalid col/row!");
        }
    }

    public void read_matrix_scan() {  
        Scanner scanner = new Scanner(System.in);
        System.out.println(String.format("Masukkan matriks %dx%d : ", this.get_row(), this.get_col()));
        for (int i = 0; i < this.get_row(); i++) {
            for (int j = 0; j < this.get_col(); j++) {
                this.contents[i][j] = scanner.nextFloat();
            }
        }
        scanner.close();
    }

    private void determine_matrix_size_from_file(String file_name) {
        try {
            File file = new File(file_name);
            Scanner scanner = new Scanner(file);
            int row_count = 0;
            int col_count = 0;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] values = line.trim().split(" ");
                if (col_count == 0) {
                    col_count = values.length;
                }
                row_count += 1;
            }
            set_new_size(row_count, col_count);
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File " + file_name + " tidak ditemukan.");
        }
    }

    public void read_matrix_from_file(String file_name) {
        try {
            determine_matrix_size_from_file(file_name);
            File file = new File(file_name);
            Scanner scanner = new Scanner(file);
            for (int i = 0; i < this.get_row(); i++) {
                for (int j = 0; j < this.get_col(); j++) {
                    this.set_elmt(i, j, scanner.nextFloat());
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File " + file_name + " tidak ditemukan.");
        }
    }

    public void print_matrix() {
        for (int i = 0; i < this.get_row(); i++) {
            for (int j = 0; j < this.get_col(); j++) {
                System.out.print(this.get_elmt(i, j) + " ");
            }
            System.out.println();
        }
    }
}
