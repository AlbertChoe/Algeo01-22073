import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Matrix {
    private int n_row;
    private int n_col;
    private double[][] data;

    //default constructor
    public Matrix() {
        this.n_row = 0;
        this.n_col = 0;
        this.data = new double[0][0];  
    }

    public Matrix(int n_row, int n_col) {
        this.n_row= n_row;
        this.n_col = n_col;
        this.data = new double[n_row][n_col];
    }

    public int get_row() {
        return this.n_row;
    }

    public int get_col() {
        return this.n_col;
    }

    public double[][] get_data() {
        return this.data;
    }

    public double get_elmt(int row, int col) {
        return this.data[row][col];
    }

    public void set_new_size(int new_n_row, int new_n_col) {
        this.n_row = new_n_row;
        this.n_col = new_n_col;
        this.data = new double[new_n_row][new_n_col];
    }

    public void set_elmt(int row, int col, double value) {
        if (row >= 0 && row < this.get_row() && col >= 0 && col < this.get_col()) {
            this.data[row][col] = value;
        } else {
            System.out.println("Invalid col/row!");
        }
    }

    public boolean is_empty() {
        return (this.n_row == 0) && (this.n_col == 0);
    }

    public boolean is_not_empty() {
        return !this.is_empty();
    }

    public static int valid_int_input(Scanner scanner, String message) {
        String err_msg = String.format(">> Tidak valid. Hanya menerima input bilangan bulat positif!");

        while (true) {
            System.out.print(message);
            String input = scanner.nextLine();

            try {
                int num = Integer.parseInt(input);
                if (num > 0) {
                    return num;
                }
                System.out.println(err_msg);
            } catch (NumberFormatException e) {
                System.out.println(err_msg);
            }
        }
    }

    public void read_matrix_scan(Scanner scanner) {  
        int row = valid_int_input(scanner, "Masukkan jumlah baris matriks : ");
        int col = valid_int_input(scanner, "Masukkan jumlah kolom matriks : ");
        this.set_new_size(row, col);
        System.out.println(String.format("Masukkan matriks %dx%d : ", this.get_row(), this.get_col()));
        for (int i = 0; i < this.get_row(); i++) {
            for (int j = 0; j < this.get_col(); j++) {
                this.data[i][j] = scanner.nextDouble();
            }
        }
        scanner.nextLine();
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
            this.set_new_size(row_count, col_count);
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File " + file_name + " tidak ditemukan.");
        }
    }

    public void read_matrix_from_file(Scanner scanner) {
        System.out.print("Masukkan nama file beserta extension (.txt) : ");
        String file_name = scanner.nextLine();
        try {
            determine_matrix_size_from_file(file_name);
            File file = new File(file_name);
            Scanner file_scanner = new Scanner(file);
            for (int i = 0; i < this.get_row(); i++) {
                for (int j = 0; j < this.get_col(); j++) {
                    this.set_elmt(i, j, file_scanner.nextDouble());
                }
            }
            file_scanner.close();
            System.out.println("Matrix berhasil terbaca.");
        } catch (FileNotFoundException e) {
            return;
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

    public void op_to_triangle(boolean instruction) {
        
    }

    public int op_to_triangle_return_swap(boolean instruction) {
        return 0;
    }

    public boolean is_matrix_upper_triangle() {
        boolean triangle = true;
        for (int i = 1; i < this.get_row(); i++) {
            for (int j = 0; j < i; j ++) {
                if (this.get_elmt(i, j) != 0) {
                    triangle = false;
                    break;
                }
            }
            if (triangle == false) {
                break;
            }
        }
        return triangle;
    }
}
