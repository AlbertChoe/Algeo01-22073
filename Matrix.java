import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.lang.Math;

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
        this.n_row = n_row;
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

    /* Scanner standard untuk semua case (bebas baris dan kolom) bisa untuk apa aja,
    Scanner khusus untuk SPL nanti */
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

    //Scanner matriks untuk matriks persegi
    public void read_square_matrix_scan(Scanner scanner) {  
        int dimension = valid_int_input(scanner, "Masukkan dimensi matriks persegi : ");
        this.set_new_size(dimension, dimension);
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
            System.out.println("Baca file gagal. File " + file_name + " tidak ditemukan.");
        }
    }

    public void read_matrix_from_file(Scanner scanner) {
        System.out.print("Masukkan nama file beserta extension (.txt) : ");
        String file_name = scanner.nextLine();
        file_name.strip();
        if (file_name.contains(".txt")) {
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
        } else {
            System.out.println("Baca file gagal. File bukan file txt.");
        }
    }

    public void print_matrix() {
        for (int i = 0; i < this.get_row(); i++) {
            for (int j = 0; j < this.get_col(); j++) {
                System.out.print(String.format("%.2f ", this.get_elmt(i, j)));
            }
            System.out.println();
        }
        System.out.println();
    }

    private void el_row_op(int row_operated, int row_operator, double factor) {
        for (int j = 0; j < this.get_col(); j++) {
            this.data[row_operated][j] += factor * this.get_elmt(row_operator, j);
            this.data[row_operated][j] = round_three_decimals(this.data[row_operated][j]);
        }
    };
    
    public static double round_three_decimals(double val) {
        val *= 1000;
        val = Math.round(val);
        val /= 1000;
        return val;
    }

    public void determinant_row_reduction() {
        System.out.println();
        System.out.println("Matriks yang dimasukkan");
        this.print_matrix();
        int swap = 0;
        for (int j = 0; j < this.get_col() - 1; j++) {

            int row_to_swap = -1;
            if (this.get_elmt(j, j) == 0) {
                for (int i = j; i < this.get_row(); i++) {
                    if (this.get_elmt(i, j) != 0) {
                        row_to_swap = i;
                        break;
                    }
                }                
            }
            
            if (row_to_swap != -1) {
                double[] temp = this.data[j];
                this.data[j] = this.data[row_to_swap];
                this.data[row_to_swap] = temp;
                swap += 1;
                System.out.println(String.format("Baris %d ditukar dengan Baris %d", j + 1, row_to_swap + 1));
                this.print_matrix();
            }
            
            for (int row = j + 1; row < this.get_row(); row++) {
                System.out.println(row);
                if (row != row_to_swap || this.get_elmt(row, j) != 0) {
                    double x = Math.abs(this.get_elmt(row, j));
                    double y = Math.abs(this.get_elmt(j, j));
                    if (y == 0 || x == 0) {
                        continue;
                    }
                    double factor = x/y;
                    if ((this.get_elmt(row, j) > 0 && this.get_elmt(j, j) > 0) || (this.get_elmt(row, j) < 0 && this.get_elmt(j, j) < 0)) {
                        this.el_row_op(row, j, -1 * factor);
                        if (factor - Math.round(factor) == 0) {
                            System.out.println(String.format("Baris %d - (%.0f)Baris %d", row + 1, factor, j + 1));
                        } else if (factor != 1) {
                            System.out.println(String.format("Baris %d - (%.2f/%.2f)Baris %d", row + 1, x, y, j + 1));
                        } else {
                            System.out.println(String.format("Baris %d - Baris %d", row + 1, j + 1));
                        }
                        this.print_matrix();
                    } else {
                        this.el_row_op(row, j, factor);
                        if (factor - Math.round(factor) == 0) {
                            System.out.println(String.format("Baris %d + (%.0f)Baris %d", row + 1, factor, j + 1));
                        } else if (factor != 1) {
                            System.out.println(String.format("Baris %d + (%.2f/%.2f)Baris %d", row + 1, x, y, j + 1));
                        } else {
                            System.out.println(String.format("Baris %d + Baris %d", row + 1, j + 1));
                        }
                        this.print_matrix();
                    }
                }
            }
        }
        System.out.println(String.format("Total pertukaran baris yang dilakukan : %d", swap));
        System.out.print(String.format("Determinan = (-1)^%d x ", swap));
        double determinant = this.get_elmt(0, 0);
        System.out.print(determinant);
        for (int i = 1; i < this.get_row(); i++) {
            System.out.print(String.format(" x %.2f", this.get_elmt(i, i)));
            determinant *= this.get_elmt(i, i);
        }
        determinant *= Math.pow(-1, swap);
        System.out.println(String.format(" = %.2f", determinant));
    }

    public void determinant_cofactor_expansion() {
        System.out.println();
        System.out.println("Matriks masukan");
        this.print_matrix();
        System.out.println("Matriks Kofaktor");
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

    public Matrix transpose() {
        Matrix m2 = new Matrix(this.n_row, this.n_col);
        for (int i = 0; i < get_row(); i++) {
            for (int j = 0; j < get_col(); j++) {
                m2.data[i][j] = this.data[j][i];
            }
        }
        return m2;
    }
}
