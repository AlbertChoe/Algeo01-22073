import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.lang.Math;

public class Matrix {
    private int n_row;
    private int n_col;
    private double[][] data;

    // default constructor
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

    private static String format_x_precision(double val, int x) {
        String format_string = String.format("%%.%df", x);
        String formatted_value = String.format(format_string, val);
        return formatted_value;
    }

    public static int valid_int_input(Scanner scanner, String message, int range_from) {
        String err_msg = String.format(">> Tidak valid. Hanya menerima input bilangan bulat lebih besar dari %d!",
                range_from);

        while (true) {
            System.out.print(message);
            String input = scanner.nextLine();

            try {
                int num = Integer.parseInt(input);
                if (num > range_from) {
                    return num;
                }
                System.out.println(err_msg);
            } catch (NumberFormatException e) {
                System.out.println(err_msg);
            }
        }
    }

    public static double valid_double_input(Scanner scanner, String message) {
        String err_msg = ">> Tidak valid. Hanya menerima input suatu bilangan riil";

        while (true) {
            System.out.print(message);
            String input = scanner.nextLine();

            try {
                double num = Double.parseDouble(input);
                return num;
            } catch (NumberFormatException e) {
                System.out.println(err_msg);
            }
        }
    }

    /*
     * Scanner standard untuk semua case (bebas baris dan kolom) bisa untuk apa aja,
     * Scanner khusus untuk SPL nanti
     */
    public void read_matrix_scan(Scanner scanner) {
        int row = valid_int_input(scanner, "Masukkan jumlah baris matriks : ", 0);
        int col = valid_int_input(scanner, "Masukkan jumlah kolom matriks : ", 0);
        this.set_new_size(row, col);
        System.out.println(String.format("Masukkan matriks %dx%d : ", this.get_row(), this.get_col()));
        for (int i = 0; i < this.get_row(); i++) {
            for (int j = 0; j < this.get_col(); j++) {
                this.data[i][j] = scanner.nextDouble();
            }
        }
        scanner.nextLine();
    }

    // Scanner matriks untuk SPL
    public void read_matrix_spl(Scanner scanner) {
        int row = valid_int_input(scanner, "Masukkan jumlah persamaan  : ", 0);
        int col = valid_int_input(scanner, "Masukkan jumlah variabel x : ", 0);
        this.set_new_size(row, col + 1);
        System.out.println();
        System.out.println("Masukkan data tiap persamaan!");
        for (int i = 0; i < this.get_row(); i++) {
            System.out.println();
            System.out.println(String.format("Persamaan ke-%d :", i + 1));
            for (int j = 0; j < this.get_col(); j++) {
                String msg;
                if (j != this.get_col() - 1) {
                    msg = String.format("Konstanta x%d : ", j + 1);
                } else {
                    msg = String.format("Hasil persamaan %d : ", i + 1);
                }
                this.data[i][j] = valid_double_input(scanner, msg);
            }
        }
    }

    // Scanner matriks untuk matriks persegi
    public void read_square_matrix_scan(Scanner scanner, int min_dimension) {
        int dimension = valid_int_input(scanner, "Masukkan dimensi matriks persegi : ", min_dimension - 1);
        this.set_new_size(dimension, dimension);
        while (true) {
            try {
                System.out.println(String.format("Masukkan matriks %dx%d : ", this.get_row(), this.get_col()));
                int loop = 0;
                for (int i = 0; i < this.get_row(); i++) {
                    String[] temp = scanner.nextLine().split(" ");
                    if (temp.length != this.get_col()) {
                        System.out.println(
                                String.format("Data dalam tiap baris hanya boleh sebanyak %d.", this.get_col()));
                        System.out.println("Proses memasukkan matriks diulang dari awal!\n");
                        loop = 0;
                        break;
                    }
                    loop += 1;
                    for (int j = 0; j < this.get_col(); j++) {
                        this.data[i][j] = Double.parseDouble(temp[j]);
                    }
                }
                if (loop == this.get_row()) {
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println("Masukan matriks tidak boleh selain bilangan riil.");
                System.out.println("Proses memasukkan matriks diulang dari awal!\n");
            }
        }
    }

    private void determine_matrix_size_from_file(String file_name) {
        try {
            File file = new File("../test/" + file_name);
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
        String file_name = scanner.nextLine().strip();
        boolean txt_extension = file_name.endsWith(".txt");
        if (txt_extension) {
            try {
                this.determine_matrix_size_from_file(file_name);
                File file = new File("../test/" + file_name);
                Scanner file_scanner = new Scanner(file);
                for (int i = 0; i < this.get_row(); i++) {
                    for (int j = 0; j < this.get_col(); j++) {
                        this.set_elmt(i, j, file_scanner.nextDouble());
                    }
                }
                file_scanner.close();
                System.out.println("File berhasil terbaca.");
            } catch (FileNotFoundException e) {
                return;
            } catch (InputMismatchException e) {
                System.out.println("Baca file gagal. Isi file terdapat elemen bukan bilangan riil.");
                System.out.println("Tiap elemen dalam file hanya boleh bilangan riil!");
                this.set_new_size(0, 0);
            }
        } else {
            System.out.println("Baca file gagal. File bukan file txt.");
        }
    }

    public void print_matrix(int x_decimal_places) {
        for (int i = 0; i < this.get_row(); i++) {
            for (int j = 0; j < this.get_col(); j++) {
                String out = format_x_precision(this.get_elmt(i, j), x_decimal_places);
                System.out.print(String.format(out + " "));
            }
            System.out.println();
        }
        System.out.println();
    }

    private void el_row_op(int row_operated, int row_operator, double factor) {
        for (int j = 0; j < this.get_col(); j++) {
            this.data[row_operated][j] += factor * this.get_elmt(row_operator, j);
            this.data[row_operated][j] = this.data[row_operated][j];
        }
    };

    public static double round_x_decimals(double val, int x) {
        val *= Math.pow(10, x);
        val = Math.round(val);
        val /= Math.pow(10, x);
        return val;
    }

    public void determinant_row_reduction(Scanner scanner) {
        System.out.println();
        System.out.println("Matriks yang dimasukkan");
        this.print_matrix(2);
        if (this.get_row() != this.get_col()) {
            System.out.println("Matriks masukan bukan matriks persegi!");
            System.out.println("Tidak dapat menghitung determinan.");
            System.out.println("Determinan hanya dapat dihitung jika matriks adalah matriks persegi.");
            System.out.println("NOTE: Matriks persegi adalah ketika jumlah baris = jumlah kolom.");
            return;
        }
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
                this.print_matrix(2);
            }

            for (int row = j + 1; row < this.get_row(); row++) {
                if (row != row_to_swap || this.get_elmt(row, j) != 0) {
                    double x = Math.abs(this.get_elmt(row, j));
                    double y = Math.abs(this.get_elmt(j, j));
                    if (y == 0 || x == 0) {
                        continue;
                    }
                    double factor = x / y;
                    if ((this.get_elmt(row, j) > 0 && this.get_elmt(j, j) > 0)
                            || (this.get_elmt(row, j) < 0 && this.get_elmt(j, j) < 0)) {
                        this.el_row_op(row, j, -1 * factor);
                        if (factor - Math.round(factor) == 0) {
                            System.out.println(String.format("Baris %d - (%.0f)Baris %d", row + 1, factor, j + 1));
                        } else if (factor != 1) {
                            System.out.println(String.format("Baris %d - (%.2f/%.2f)Baris %d", row + 1, x, y, j + 1));
                        } else {
                            System.out.println(String.format("Baris %d - Baris %d", row + 1, j + 1));
                        }
                        this.print_matrix(2);
                    } else {
                        this.el_row_op(row, j, factor);
                        if (factor - Math.round(factor) == 0) {
                            System.out.println(String.format("Baris %d + (%.0f)Baris %d", row + 1, factor, j + 1));
                        } else if (factor != 1) {
                            System.out.println(String.format("Baris %d + (%.2f/%.2f)Baris %d", row + 1, x, y, j + 1));
                        } else {
                            System.out.println(String.format("Baris %d + Baris %d", row + 1, j + 1));
                        }
                        this.print_matrix(2);
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
        determinant = round_x_decimals(determinant, 3);
        System.out.println(String.format(" = %.2f", determinant));
        String[] data_to_file = new String[0];
        data_to_file = push_arr_string(data_to_file, String.format("%.2f", determinant));
        option_output_to_file(data_to_file, scanner);
    }

    private static double find_determinant_obe(Matrix m0) {
        Matrix m = new Matrix(m0.get_row(), m0.get_col());
        for (int i = 0; i < m.get_row(); i++) {
            for (int j = 0; j < m.get_col(); j++) {
                m.set_elmt(i, j, m0.get_elmt(i, j));
            }
        }
        int swap = 0;
        for (int j = 0; j < m.get_col() - 1; j++) {
            int row_to_swap = -1;
            if (m.get_elmt(j, j) == 0) {
                for (int i = j; i < m.get_row(); i++) {
                    if (m.get_elmt(i, j) != 0) {
                        row_to_swap = i;
                        break;
                    }
                }
            }

            if (row_to_swap != -1) {
                double[] temp = m.data[j];
                m.data[j] = m.data[row_to_swap];
                m.data[row_to_swap] = temp;
                swap += 1;
            }

            for (int row = j + 1; row < m.get_row(); row++) {
                if (row != row_to_swap || m.get_elmt(row, j) != 0) {
                    double x = Math.abs(m.get_elmt(row, j));
                    double y = Math.abs(m.get_elmt(j, j));
                    if (y == 0 || x == 0) {
                        continue;
                    }
                    double factor = x / y;
                    if ((m.get_elmt(row, j) > 0 && m.get_elmt(j, j) > 0)
                            || (m.get_elmt(row, j) < 0 && m.get_elmt(j, j) < 0)) {
                        m.el_row_op(row, j, -1 * factor);
                    } else {
                        m.el_row_op(row, j, factor);
                    }
                }
            }
        }
        double determinant = m.get_elmt(0, 0);
        for (int i = 1; i < m.get_row(); i++) {
            determinant *= m.get_elmt(i, i);
        }
        determinant *= Math.pow(-1, swap);
        return determinant;
    }

    private static double find_determinant(Matrix m) {
        if (m.get_row() == 1 && m.get_col() == 1) {
            return m.get_elmt(0, 0);
        }
        if (m.get_row() == 2 && m.get_col() == 2) {
            return (m.get_elmt(0, 0) * m.get_elmt(1, 1)) - (m.get_elmt(0, 1) * m.get_elmt(1, 0));
        } else {
            double determinant_total = 0;
            int i;
            for (i = 0; i < m.get_row(); i++) {
                int length = m.get_row() - 1;
                Matrix submatrix = new Matrix(length, length);
                int a, b;
                int subi = 0;
                for (a = 0; a < m.get_row(); a++) {
                    if (a == i) {
                        continue;
                    }
                    int subj = 0;
                    for (b = 1; b < m.get_col(); b++) {
                        submatrix.data[subi][subj] = m.get_elmt(a, b);
                        subj += 1;
                    }
                    subi += 1;
                }

                double temp = m.get_elmt(i, 0) * find_determinant(submatrix);
                if (i % 2 == 1) {
                    temp *= -1;
                }
                determinant_total += temp;
            }
            return determinant_total;
        }
    };

    private Matrix find_cofactor_matrix() {
        Matrix cofactor = new Matrix(this.get_row(), this.get_col());
        if (this.get_row() == 1 && this.get_col() == 1) {
            cofactor.set_elmt(0, 0, this.get_elmt(0, 0));
            return cofactor;
        }
        for (int i = 0; i < this.get_row(); i++) {
            for (int j = 0; j < this.get_col(); j++) {
                int length = this.get_row() - 1;
                Matrix submatrix = new Matrix(length, length);
                int subi, subj;
                subi = 0;
                for (int a = 0; a < length + 1; a++) {
                    if (a == i) {
                        continue;
                    }
                    subj = 0;
                    for (int b = 0; b < length + 1; b++) {
                        if (b == j) {
                            continue;
                        }
                        submatrix.set_elmt(subi, subj, this.get_elmt(a, b));
                        subj += 1;
                    }
                    subi += 1;
                }
                double temp = round_x_decimals(Math.pow(-1, i + j) * find_determinant(submatrix), 3);
                cofactor.set_elmt(i, j, temp);
            }
        }
        return cofactor;
    }

    private Matrix find_cofactor_matrix_obe() {
        Matrix cofactor = new Matrix(this.get_row(), this.get_col());
        if (this.get_row() == 1 && this.get_col() == 1) {
            cofactor.set_elmt(0, 0, this.get_elmt(0, 0));
            return cofactor;
        }
        for (int i = 0; i < this.get_row(); i++) {
            for (int j = 0; j < this.get_col(); j++) {
                int length = this.get_row() - 1;
                Matrix submatrix = new Matrix(length, length);
                int subi, subj;
                subi = 0;
                for (int a = 0; a < length + 1; a++) {
                    if (a == i) {
                        continue;
                    }
                    subj = 0;
                    for (int b = 0; b < length + 1; b++) {
                        if (b == j) {
                            continue;
                        }
                        submatrix.set_elmt(subi, subj, this.get_elmt(a, b));
                        subj += 1;
                    }
                    subi += 1;
                }
                double temp = round_x_decimals(Math.pow(-1, i + j) * find_determinant_obe(submatrix), 3);
                cofactor.set_elmt(i, j, temp);
            }
        }
        return cofactor;
    }

    public void determinant_cofactor_expansion(Scanner scanner) {
        System.out.println();
        System.out.println("Matriks yang dimasukkan");
        this.print_matrix(2);
        if (this.get_row() != this.get_col()) {
            System.out.println("Matriks masukan bukan matriks persegi!");
            System.out.println("Tidak dapat menghitung determinan.");
            System.out.println("Determinan hanya dapat dihitung jika matriks adalah matriks persegi.");
            System.out.println("NOTE: Matriks persegi adalah ketika jumlah baris = jumlah kolom.");
            return;
        }
        Matrix cof_matrix = this.find_cofactor_matrix();
        System.out.println("Matriks Kofaktor dari Matriks Masukan");
        cof_matrix.print_matrix(2);
        if (this.get_col() == 1) {
            System.out.println(String.format("Determinan = %.2f", this.get_elmt(0, 0)));
            return;
        }
        System.out.println("Perhitungan akan menggunakan baris pertama.");
        System.out.print("Determinan = ");
        double determinant = this.get_elmt(0, 0) * cof_matrix.get_elmt(0, 0);
        System.out.print(String.format("(%.2f) x (%.2f)", this.get_elmt(0, 0), cof_matrix.get_elmt(0, 0)));
        for (int j = 1; j < this.get_col(); j++) {
            determinant += this.get_elmt(0, j) * cof_matrix.get_elmt(0, j);
            System.out.print(String.format(" + (%.2f) x (%.2f)", this.get_elmt(0, j), cof_matrix.get_elmt(0, j)));
        }
        determinant = round_x_decimals(determinant, 3);
        System.out.println(String.format(" = %.2f", determinant));
        String[] data_to_file = new String[0];
        data_to_file = push_arr_string(data_to_file, String.format("%.2f", determinant));
        option_output_to_file(data_to_file, scanner);
    }

    private Matrix transpose() {
        Matrix m2 = new Matrix(this.n_col, this.n_row);
        for (int i = 0; i < this.get_row(); i++) {
            for (int j = 0; j < this.get_col(); j++) {
                m2.data[j][i] = this.data[i][j];
            }
        }
        return m2;
    }

    public void inverse_adjoint(Scanner scanner) {
        System.out.println();
        System.out.println("Matriks yang dimasukkan");
        this.print_matrix(2);
        if (this.get_row() != this.get_col()) {
            System.out.println("Matriks masukan bukan matriks persegi!");
            System.out.println("Tidak dapat mencari balikan.");
            System.out.println("Matriks balikan hanya dapat dicari jika matriks adalah matriks persegi.");
            System.out.println("NOTE: Matriks persegi adalah ketika jumlah baris = jumlah kolom.");
            return;
        }
        double determinant = find_determinant(this);

        if (determinant == 0) {
            System.out.println("Determinan matriks adalah 0.");
            System.out.println("Sehingga matriks tidak mempunyai matriks balikan.");
            System.out.println("Matriks masukan adalah matriks tunggal(singular).");
            return;
        }
        System.out.println(String.format("Determinan matriks masukan : %.2f", determinant));
        System.out.println();
        Matrix cofactor = this.find_cofactor_matrix_obe();
        Matrix adjoin = cofactor.transpose();
        System.out.println("Adjoin matriks dari matriks masukan");
        adjoin.print_matrix(2);
        double factor = 1 / determinant;
        System.out.println(String.format("Matriks balikan = (1/%.3f) * Adjoin matriks masukan", determinant));
        for (int i = 0; i < adjoin.get_row(); i++) {
            for (int j = 0; j < adjoin.get_col(); j++) {
                adjoin.set_elmt(i, j, factor * adjoin.get_elmt(i, j));
            }
        }
        System.out.println("Matriks balikan (Inverse matriks) yang didapat");
        adjoin.print_matrix(3);
        String[] data_to_file = adjoin.to_string_arr();
        option_output_to_file(data_to_file, scanner);
    }

    /*
     * Return invers dari matrix.
     * Prekondisi : Matriks bukan singular (determinan != 0) dan dimensi >= 2
     */
    public Matrix find_inverse_adj() {
        double determinant = find_determinant(this);
        Matrix cofactor = this.find_cofactor_matrix_obe();
        Matrix adjoin = cofactor.transpose();
        double factor = 1 / determinant;
        for (int i = 0; i < adjoin.get_row(); i++) {
            for (int j = 0; j < adjoin.get_col(); j++) {
                adjoin.set_elmt(i, j, factor * adjoin.get_elmt(i, j));
            }
        }
        return adjoin;
    }

    public void spl_inverse(Scanner scanner) {
        System.out.println();
        System.out.println("Matriks Augmented dari masukan");
        this.print_matrix(2);
        System.out.println("x = (Invers A) * b");
        System.out.println();
        Matrix b = new Matrix(this.get_row(), 1);
        Matrix A = new Matrix(this.get_row(), this.get_col() - 1);
        for (int i = 0; i < this.get_row(); i++) {
            for (int j = 0; j < this.get_col() - 1; j++) {
                A.set_elmt(i, j, this.get_elmt(i, j));
            }
        }
        for (int i = 0; i < this.get_row(); i++) {
            b.set_elmt(i, 0, this.get_elmt(i, this.get_col() - 1));
        }
        System.out.println("Matriks A");
        A.print_matrix(2);
        if (A.get_row() != A.get_col()) {
            System.out.println("Matriks tidak dapat dikerjakan dengan menggunakan metode matriks balikan.");
            System.out.println("Karena matriks A bukan matriks persegi.");
            System.out.println("Gunakan cara lain untuk mencari solusi SPL tersebut!");
            return;
        }
        if (find_determinant(A) == 0) {
            System.out.println("Determinan matriks A = 0. Matriks A tidak memiliki balikan.");
            System.out.println("Gunakan cara lain untuk mencari solusi SPL tersebut!");
            return;
        }
        System.out.println("Invers Matriks A");
        A = A.find_inverse_obe();
        A.print_matrix(2);
        System.out.println("Matriks b");
        b.print_matrix(2);
        System.out.println("x = (Invers A) * b");
        Matrix x = multiply_matrix(A, b);
        System.out.println("Matriks x");
        x.print_matrix(2);
        String[] data_to_file = new String[0];
        for (int i = 0; i < x.get_row(); i++) {
            String output_msg = String.format("x%d = %.2f", i + 1, x.get_elmt(i, 0));
            System.out.println(output_msg);
            data_to_file = push_arr_string(data_to_file, output_msg);
        }
        option_output_to_file(data_to_file, scanner);
    }

    // Mengecek apakah ada baris yang 0 semuanya
    public boolean is_baris_0() {
        boolean flag;
        flag = true;
        for (int i = 0; i < this.get_row(); i++) {
            flag = true;
            for (int j = 0; j < this.get_col(); j++) {
                if (this.data[i][j] != 0) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                return flag;
            }
        }
        return false;
    }

    // Mengecek apakak baris ke i dari matriks bernilai 0 semua
    public boolean is_baris_i_0(int row) {
        boolean flag;
        flag = true;
        int i;
        i = 0;
        for (i = 0; i < this.get_col() - 1; i++) {
            if (this.get_elmt(row, i) != 0) {
                flag = false;

            }
        }
        return flag;
    }

    // Membuat matriks agar tidak ada baris yang 0 semua di tengah tengah
    // contoh
    // 0 0 0 menjadi 0 0 6
    // 0 0 0 menjadi 0 0 0
    // 0 0 6 menjadi 0 0 0

    // 0 0 0 menjadi 0 6 6
    // 0 6 6 menjadi 0 0 6
    // 0 0 6 menjadi 0 0 0
    public void atur_baris_rapi() {
        int[] listidxbukan0 = new int[this.get_row()];
        int idx = 0;
        boolean flag = false;
        for (int i = 0; i < this.get_row(); i++) {
            flag = false;
            for (int j = 0; j < this.get_col(); j++) {
                if (this.get_elmt(i, j) != 0) {
                    listidxbukan0[idx] = this.get_row() - j;
                    flag = true;
                    idx++;
                    break;
                }
            }
            if (!flag) {
                listidxbukan0[idx] = 0;
                idx++;
            }
        }

        for (int i = 0; i < this.get_row() - 1; i++) {
            flag = false;
            for (int j = 0; j < this.get_row() - i - 1; j++) {
                if (listidxbukan0[j] < listidxbukan0[j + 1]) {
                    double[] temp1 = this.data[j];
                    this.data[j] = this.data[j + 1];
                    this.data[j + 1] = temp1;
                    int temp = listidxbukan0[j];
                    System.out.println(String.format("Menukar baris %d dengan baris %d", j + 1, j + 2));
                    this.print_matrix(2);
                    listidxbukan0[j] = listidxbukan0[j + 1];
                    listidxbukan0[j + 1] = temp;
                    flag = true;
                }
            }
            if (!flag) {
                break;
            }
        }
    }

    // Fungsi atur baris rapi yang tidak memilii print apa apa
    public void atur_baris_rapi_silent() {
        int[] listidxbukan0 = new int[this.get_row()];
        int idx = 0;
        boolean flag = false;
        for (int i = 0; i < this.get_row(); i++) {
            flag = false;
            for (int j = 0; j < this.get_col(); j++) {
                if (this.get_elmt(i, j) != 0) {
                    listidxbukan0[idx] = this.get_row() - j;
                    flag = true;
                    idx++;
                    break;
                }
            }
            if (!flag) {
                listidxbukan0[idx] = 0;
                idx++;
            }
        }

        for (int i = 0; i < this.get_row() - 1; i++) {
            flag = false;
            for (int j = 0; j < this.get_row() - i - 1; j++) {
                if (listidxbukan0[j] < listidxbukan0[j + 1]) {
                    double[] temp1 = this.data[j];
                    this.data[j] = this.data[j + 1];
                    this.data[j + 1] = temp1;
                    int temp = listidxbukan0[j];
                    listidxbukan0[j] = listidxbukan0[j + 1];
                    listidxbukan0[j + 1] = temp;
                    flag = true;
                }
            }
            if (!flag) {
                break;
            }
        }
    }

    public void eliminasi_gauss() {
        this.row_eselon();
        this.print_matrix(2);
    }

    public void eliminasi_gauss_jordan() {
        this.row_eselon_reduction();
        this.print_matrix(2);
    }

    public void eliminasi_gauss_jordan_silent() {
        this.row_eselon_reduction_silent();
    }

    // Fungsi untuk mengalikan matrix antara matriks 1 dan 2
    public static Matrix multiply_matrix(Matrix m1, Matrix m2) {
        Matrix hasil = new Matrix(m1.get_row(), m2.get_col());
        int i, j;
        for (i = 0; i < hasil.get_row(); i++) {
            for (j = 0; j < hasil.get_col(); j++) {
                int x;
                double jumlah = 0;
                for (x = 0; x < m1.get_col(); x++) {
                    jumlah += m1.get_elmt(i, x) * m2.get_elmt(x, j);
                }
                hasil.set_elmt(i, j, jumlah);
            }
        }
        return hasil;
    }

    // Fungsi Row eselon untuk menghasilkan matriks dengan bentuk eselon
    public void row_eselon() {
        int i, j, baris, kolom;
        baris = this.get_row();
        kolom = this.get_col();
        this.atur_baris_rapi();
        for (i = 0; i < baris; i++) {
            int idx = -1;
            for (j = 0; j < kolom; j++) {// Mencari Leading one
                if (this.get_elmt(i, j) != 0) {
                    idx = j;
                    break;
                }
            }
            if (idx == -1) {
                continue;
            }
            double value = this.get_elmt(i, idx);
            // Mengubah elemen-elemen pada baris ke-i agar elemen pertama menjadi 1
            for (j = 0; j < kolom; j++) {
                set_elmt(i, j, round_x_decimals(this.get_elmt(i, j) / value, 7));
            }
            // Mengurangkan baris-baris di bawah baris ke-i agar elemen pertama pada setiap
            // baris di bawahnya menjadi nol
            for (int k = i + 1; k < baris; k++) {
                double pengurang = -this.get_elmt(k, idx);
                for (j = 0; j < kolom; j++) {
                    set_elmt(k, j, round_x_decimals(get_elmt(k, j) + pengurang * this.get_elmt(i, j), 5));
                }
            }
        }
        // Mengatur baris rapi kembali
        this.atur_baris_rapi();
    }

    // Fungis row eselon yang tidak print apa apa
    public void row_eselon_silent() {
        int i, j, baris, kolom;
        baris = this.get_row();
        kolom = this.get_col();
        this.atur_baris_rapi_silent();
        for (i = 0; i < baris; i++) {
            int idx = -1;
            // Mencari Leading one
            for (j = 0; j < kolom; j++) {
                if (this.get_elmt(i, j) != 0) {
                    idx = j;
                    break;
                }
            }
            if (idx == -1) {
                continue;
            }
            double value = this.get_elmt(i, idx);
            // Mengubah elemen-elemen pada baris ke-i agar elemen pertama menjadi 1
            for (j = 0; j < kolom; j++) {
                set_elmt(i, j, round_x_decimals(this.get_elmt(i, j) / value, 7));
            }
            // Mengurangkan baris-baris di bawah baris ke-i agar elemen pertama pada setiap
            // baris di bawahnya menjadi nol
            for (int k = i + 1; k < baris; k++) {
                double pengurang = -this.get_elmt(k, idx);
                for (j = 0; j < kolom; j++) {
                    set_elmt(k, j, round_x_decimals(get_elmt(k, j) + pengurang * this.get_elmt(i, j), 5));
                }
            }
        }
        this.atur_baris_rapi_silent();
    }

    // Fungsi row eselon yang menghasilkan matriks row eselon tereduksi
    public void row_eselon_reduction() {
        this.row_eselon(); // Memanggil fungis row eselon agar membuat 0 di semua leading one
        int i, j, baris, kolom;
        baris = this.get_row();
        kolom = this.get_col();
        // Mengurangkan baris-baris di atas baris ke-i agar elemen pertama pada setiap
        // baris di atasnya menjadi nol
        for (i = baris - 1; i >= 0; i--) {
            int idx = -1;
            for (j = 0; j < kolom; j++) { // Mencari leading one
                if (this.get_elmt(i, j) != 0) {
                    idx = j;
                    break;
                }
            }
            if (idx == -1) {
                continue;
            }

            for (int k = i - 1; k >= 0; k--) { // Pengurangan agar kolom di atas leading one menjadi 0
                double pengurang = -this.get_elmt(k, idx);
                for (j = 0; j < kolom; j++) {
                    this.set_elmt(k, j, round_x_decimals(this.get_elmt(k, j) + pengurang * this.get_elmt(i, j), 7));
                }
            }
        }
    }

    // FUNgsi row eselon reduction yang silent
    public void row_eselon_reduction_silent() {
        this.row_eselon_silent();
        int i, j, baris, kolom;
        baris = this.get_row();
        kolom = this.get_col();
        // Mengurangkan baris-baris di atas baris ke-i agar elemen pertama pada setiap
        // baris di atasnya menjadi nol
        for (i = baris - 1; i >= 0; i--) {
            int idx = -1;
            for (j = 0; j < kolom; j++) {
                if (this.get_elmt(i, j) != 0) {
                    idx = j;
                    break;
                }
            }
            if (idx == -1) {
                continue;
            }

            for (int k = i - 1; k >= 0; k--) {
                double pengurang = -this.get_elmt(k, idx);
                for (j = 0; j < kolom; j++) {
                    this.set_elmt(k, j, round_x_decimals(this.get_elmt(k, j) + pengurang * this.get_elmt(i, j), 5));
                }
            }
        }
    }

    public Matrix generateMatrix() {
        int kolom = 4;
        int turunan = 0;
        double hasil = 0;
        int pindahX = 0;
        int pindahY = 0;
        int idxkolom = 0;// idxkolom dan idxBaris berupa penanda pada matrix hasil inputan
        Matrix A = new Matrix(16, 16);
        for (int a = 0; a < 16; a++) {
            for (int b = 0; b < 16; b++) {
                int x;
                if (idxkolom == 0 || idxkolom == 2) {
                    x = 0;
                } else {
                    x = 1;
                }
                int y;
                if (idxkolom == 0 || idxkolom == 1) {
                    y = 0;
                } else {
                    y = 1;
                }
                int j = pindahY;
                int i = pindahX;
                if (turunan == 0) {
                    hasil = (Math.pow(x, i)) * (Math.pow(y, j));
                } else if (turunan == 1) {
                    if (i == 0) {
                        hasil = 0;
                    } else {
                        hasil = i * (Math.pow(x, (i - 1)) * (Math.pow(y, j)));
                    }
                } else if (turunan == 2) {
                    if (j == 0) {
                        hasil = 0;
                    } else {
                        hasil = j * (Math.pow(x, i)) * (Math.pow(y, (j - 1)));
                    }
                } else {
                    if (j == 0 || i == 0) {
                        hasil = 0;
                    } else {
                        hasil = i * j * (Math.pow(x, (i - 1))) * (Math.pow(y, (j - 1)));
                    }
                }
                if (pindahX == kolom - 1) {
                    pindahY += 1;
                    pindahX = -1;
                }
                pindahX += 1;
                A.set_elmt(a, b, hasil);
            }
            pindahY = 0;
            if (idxkolom == kolom - 1) { // Setiap 1 baris sudah terisi pada matrix ekspansi, maka penanda bergeser
                idxkolom = -1;
                turunan += 1;
            }
            idxkolom += 1;
        }
        return A;
    }

    // Fungsi untuk mencari dan menghitung bicubic spline interpolation dengan input
    // dari terminal
    public void read_matrix_bicubic(Scanner scanner) {
        this.set_new_size(4, 4);
        System.out.println("Masukkan matriks 4x4 : ");
        for (int i = 0; i < 4; i++) { // input matriks dari terminal
            for (int j = 0; j < 4; j++) {
                this.data[i][j] = scanner.nextDouble();
            }
        }
        System.out.print("Masukkan a : "); // masukkan a
        double a = scanner.nextDouble();
        System.out.print("Masukkan b : "); // masukkan a
        double b = scanner.nextDouble();
        System.out.println(" ");

        Matrix hasil = new Matrix(4, 4); // mengenerate matriks 16x16
        hasil = generateMatrix();
        // Matriks 16x16 generated dengan fungsi yang sudah ditentukan
        hasil = hasil.find_inverse_obe();
        // Matriks 16x16 yang sudah digenerate di inverse
        Matrix sixteen_row_Matrix = new Matrix(16, 1); // matrix ukuran 16x1
        int row = 0;
        for (int i = 0; i < this.get_row(); i++) { // Mengubah matriks ukuran 4x4 menjadi 16x1
            for (int j = 0; j < this.get_col(); j++) {
                sixteen_row_Matrix.set_elmt(row, 0, this.get_elmt(i, j));
                row++;
            }
        }
        Matrix hasilKoef = Matrix.multiply_matrix(hasil, sixteen_row_Matrix); // Menghasilkan matrix koef
        row = 0;
        Matrix newHasilKoef = new Matrix(4, 4);
        for (int i = 0; i < 4; i++) { // memconvert kembali matriks hasilKoef berukuran 16x1 menjadi 4x4
            for (int j = 0; j < 4; j++) {
                newHasilKoef.set_elmt(j, i, hasilKoef.get_elmt(row, 0));
                row++;
            }
        }
        Matrix MatrixX = new Matrix(1, 4);
        Matrix MatrixY = new Matrix(4, 1);
        double val = 1;
        for (int i = 0; i < 1; i++) { // membuat matriks x baru dengan nilai a
            for (int j = 0; j < 4; j++) {
                MatrixX.set_elmt(i, j, val);
                val = val * a;
            }
        }
        val = 1;

        for (int i = 0; i < 4; i++) { // membuat matriks y baru dengan nilai b
            for (int j = 0; j < 1; j++) {
                MatrixY.set_elmt(i, j, val);
                val = val * b;
            }
        }
        // Mengalikan matrixX dengan hasil koef , lalu dikalikan lagi dengan matrixY
        Matrix hasilMatrixbaru = Matrix.multiply_matrix(Matrix.multiply_matrix(MatrixX, newHasilKoef), MatrixY);
        String output_msg = "";
        String[] data_to_file = new String[0];
        System.out.format("Hasil f(%.2f ,%.2f) = %.2f\n", a, b, hasilMatrixbaru.get_elmt(0, 0));
        output_msg = String.format("Hasil f(%.2f ,%.2f) = %.2f\n", a, b, hasilMatrixbaru.get_elmt(0, 0));
        int input = -1;
        System.out.println("Apakah ingin mengecek hasil taksiran fungsi lain?");
        System.out.println("1. Ya");
        System.out.println("2. Tidak");
        scanner.nextLine();
        input = Main.valid_input_choice(scanner, 1, 2);
        data_to_file = Matrix.push_arr_string(data_to_file, output_msg);

        while (input == 1) { // Looping jika pengguna masih ingin mencari taksiran lain
            System.out.print("Masukkan a : ");
            a = scanner.nextDouble();
            System.out.print("Masukkan b : ");
            b = scanner.nextDouble();
            scanner.nextLine();

            val = 1;
            for (int i = 0; i < 1; i++) {
                for (int j = 0; j < 4; j++) {
                    MatrixX.set_elmt(i, j, val);
                    val = val * a;
                }
            }
            val = 1;
            MatrixX.print_matrix(2);
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 1; j++) {
                    MatrixY.set_elmt(i, j, val);
                    val = val * b;
                }
            }
            hasilMatrixbaru = Matrix.multiply_matrix(Matrix.multiply_matrix(MatrixX, newHasilKoef), MatrixY);
            System.out.format("Hasil f(%.2f ,%.2f) = %.2f\n", a, b, hasilMatrixbaru.get_elmt(0, 0));
            output_msg = String.format("Hasil f(%.2f ,%.2f) = %.2f\n", a, b, hasilMatrixbaru.get_elmt(0, 0));

            data_to_file = Matrix.push_arr_string(data_to_file, output_msg);
            System.out.println("Apakah ingin mengecek hasil taksiran fungsi lain?");
            System.out.println("1. Ya");
            System.out.println("2. Tidak");
            input = Main.valid_input_choice(scanner, 1, 2);
        }
        Matrix.option_output_to_file(data_to_file, scanner);
    }

    // Fungsi untuk mencari dan menghitung bicubic spline interpolation dengan input
    // dari file
    public void read_matrix_bicubic_from_file(Scanner scanner) {
        System.out.print("Masukkan nama file beserta extension (.txt) : ");
        String file_name = scanner.nextLine();
        file_name.strip();
        boolean txt_extension = file_name.endsWith(".txt");
        double a = 0;
        double b = 0;
        if (txt_extension) { // error handling jika nama file bukan txt
            try {
                this.set_new_size(4, 4); // Proses menscan matriks dari file
                File file = new File("../test/" + file_name);
                Scanner file_scanner = new Scanner(file);
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 4; j++) {
                        this.set_elmt(i, j, file_scanner.nextDouble());
                    }
                }
                a = file_scanner.nextDouble(); // Proses menscan nilai A dan B dari file
                b = file_scanner.nextDouble();
                file_scanner.close();

            } catch (FileNotFoundException e) { // error handling jika file tidak ditemukan
                System.out.format("Tidak ditemukan file dengan nama %s\n", file_name);
                return;
            }

            Matrix hasil = new Matrix(4, 4); // mengenerate matriks 16x16
            hasil = generateMatrix();
            // Matriks 16x16 generated dengan fungsi yang sudah ditentukan
            // Proses genenate matriks 16x16 selesai
            hasil = hasil.find_inverse_obe(); // Matriks generated yang berukuran 16x16 di inverse
            Matrix sixteen_row_Matrix = new Matrix(16, 1); // Mengubah matriks berukuran 4x4 menjadi 16x1
            int row = 0;
            for (int i = 0; i < this.get_row(); i++) {
                for (int j = 0; j < this.get_col(); j++) {
                    sixteen_row_Matrix.set_elmt(row, 0, this.get_elmt(i, j));
                    row++;
                }
            }
            Matrix hasilKoef = Matrix.multiply_matrix(hasil, sixteen_row_Matrix); // Menghasilkan matrix koef dengan
                                                                                  // melakukan perkalian terhadap
                                                                                  // matirks hasil inverse dan matriks
                                                                                  // 16x1
            row = 0;
            Matrix newHasilKoef = new Matrix(4, 4);
            for (int i = 0; i < 4; i++) { // memconvert kembali matriks hasilKoef berukuran 16x1 menjadi 4x4
                for (int j = 0; j < 4; j++) {
                    newHasilKoef.set_elmt(j, i, hasilKoef.get_elmt(row, 0));
                    row++;
                }
            }
            Matrix MatrixX = new Matrix(1, 4);
            Matrix MatrixY = new Matrix(4, 1);
            double val = 1;
            for (int i = 0; i < 1; i++) { // membuat matriks x baru dengan nilai a
                for (int j = 0; j < 4; j++) {
                    MatrixX.set_elmt(i, j, val);
                    val = val * a;
                }
            }
            val = 1;

            for (int i = 0; i < 4; i++) { // membuat matriks y baru dengan nilai b
                for (int j = 0; j < 1; j++) {
                    MatrixY.set_elmt(i, j, val);
                    val = val * b;
                }
            }

            // Mengalikan matrixX dengan hasil koef , lalu dikalikan lagi dengan matrixY
            Matrix hasilMatrixbaru = Matrix.multiply_matrix(Matrix.multiply_matrix(MatrixX, newHasilKoef), MatrixY);

            String output_msg = "";
            String[] data_to_file = new String[0];
            System.out.format("Hasil f(%.2f ,%.2f) = %.2f\n", a, b, hasilMatrixbaru.get_elmt(0, 0));
            output_msg = String.format("Hasil f(%.2f ,%.2f) = %.2f\n", a, b, hasilMatrixbaru.get_elmt(0, 0));
            data_to_file = Matrix.push_arr_string(data_to_file, output_msg);
            Matrix.option_output_to_file(data_to_file, scanner);
        } else { // Jika bukan file txt
            System.out.println("Baca file gagal. File bukan file txt.");
        }
    }

    // Mencari solusi dari SPL dengan metode cramer dengan ukuran matriks nRow *
    // (nRow+1)
    public void cramer(Scanner scanner) {
        System.out.println();
        System.out.println("Matriks Augmented dari masukan");
        this.print_matrix(2);
        Matrix x = new Matrix(this.n_row, 1); // Untuk menampung hasil jawaban
        Matrix a = new Matrix(this.n_row, this.n_col - 1); // Untuk menampung koefisien dari tiap variabel
        Matrix temp = new Matrix(this.n_row, this.n_col - 1); // Mengcopy matriks a ke temp lalu diproses sebanyak nCol
                                                              // kali dengan memasukkan elemen dari b dari 0 hingga
                                                              // nCol-1
        double det1; // Determinan dari koefisien variabel
        double det2 = 1; // Determinan ketika elemen dari b dimasukkan ke temp
        Matrix b = new Matrix(this.n_row, 1); // Berisi konstanta tiap persamaan

        // Mengisi matriks b dengan konstanta tiap persamaan
        for (int i = 0; i < this.n_row; i++) {
            b.set_elmt(i, 0, this.get_elmt(i, n_col - 1));
        }

        // Mengisi matriks a dengan koefisien dari matriks input
        for (int i = 0; i < a.n_row; i++) {
            for (int j = 0; j < a.n_col; j++) {
                a.set_elmt(i, j, this.data[i][j]);
            }
        }
        if (a.get_row() != a.get_col()) {
            System.out.println("Matriks tidak dapat dikerjakan dengan menggunakan kaidah Cramer.");
            System.out.println("Karena matriks A bukan matriks persegi.");
            System.out.println("Gunakan cara lain untuk mencari solusi SPL tersebut!");
            return;
        }
        det1 = find_determinant(a);
        if (det1 == 0) {
            System.out.println("Determinan matriks A = 0, SPL tidak memiliki solusi yang unik.");
            System.out.println("Gunakan cara lain untuk mencari solusi SPL tersebut!");
            return;
        } else {
            for (int i = 0; i < this.n_row; i++) {
                b.set_elmt(i, 0, this.get_elmt(i, n_col - 1));
            }

            for (int i = 0; i < a.n_row; i++) {
                for (int j = 0; j < a.n_col; j++) {
                    a.set_elmt(i, j, this.data[i][j]);
                }
            }
            det1 = find_determinant(a);
            System.out.println("Determinan matriks A : " + det1);

            for (int i = 0; i < a.n_col; i++) {
                for (int j = 0; j < a.n_row; j++) {
                    for (int q = 0; q < a.n_col; q++) {
                        temp.set_elmt(j, q, a.data[j][q]);
                    }
                }
                for (int p = 0; p < b.n_row; p++) {
                    temp.set_elmt(p, i, b.data[p][0]);
                }
                det2 = find_determinant(temp);
                System.out.println("Determinan matriks A" + (i + 1) + " : " + det2);
                x.set_elmt(i, 0, (det2 / det1));
            }
            System.out.println("\nBerikut solusi dari kaidah Cramer");
            String[] data_to_file = new String[0];
            for (int i = 0; i < x.n_row; i++) {
                String output_msg = String.format("x%d = %.2f", i + 1, x.data[i][0]);
                System.out.println(output_msg);
                data_to_file = push_arr_string(data_to_file, output_msg);
            }
            option_output_to_file(data_to_file, scanner);
        }
    }

    // Proses untuk menginverse sebuah matriks dengan metode OBE
    public void inverse_obe(Scanner scanner) {
        System.out.println();
        System.out.println("Matriks yang dimasukkan");
        this.print_matrix(2);
        if (this.get_row() != this.get_col()) {
            System.out.println("Matriks masukan bukan matriks persegi!");
            System.out.println("Tidak dapat mencari balikan.");
            System.out.println("Matriks balikan hanya dapat dicari jika matriks adalah matriks persegi.");
            System.out.println("NOTE: Matriks persegi adalah ketika jumlah baris = jumlah kolom.");
            return;
        }
        double det = find_determinant_obe(this);
        if (det == 0) {
            System.out.println("Determinan matriks masukan = 0 sehingga matriks tidak mempunyai balikan");
        } else {
            int row = this.get_row();
            int col = this.get_col();
            Matrix m1 = new Matrix(row, col * 2);
            // Memasukkan elemen m1 dari matriks input dan matriks identitas, dengan posisi
            // matriks identitas di sebelah kanan matriks input
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col * 2; j++) {
                    if (j < col) {
                        m1.set_elmt(i, j, this.get_elmt(i, j));
                    } else if (i == j - col) {
                        m1.set_elmt(i, j, 1);
                    } else {
                        m1.set_elmt(i, j, 0);
                    }
                }
            }
            m1.print_matrix(2);
            m1.eliminasi_gauss_jordan();
            Matrix hasil = new Matrix(row, col);
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    hasil.set_elmt(i, j, m1.get_elmt(i, j + col));
                }
            }
            System.out.println("Matriks balikan");
            hasil.print_matrix(2);
            String[] data_to_file = hasil.to_string_arr();
            option_output_to_file(data_to_file, scanner);
        }
    }

    // Mengembalikan sebuah matriks inverse dengan metode OBE dengan menggunakan
    // fungsi eliminasi_gauss_jordan_silent yang bersih dari output proses
    // pengerjaan
    public Matrix find_inverse_obe() {
        int row = this.get_row();
        int col = this.get_col();
        Matrix hasil = new Matrix(row, col);
        Matrix m1 = new Matrix(row, col * 2);
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col * 2; j++) {
                if (j < col) {
                    m1.set_elmt(i, j, this.get_elmt(i, j));
                } else if (i == j - col) {
                    m1.set_elmt(i, j, 1);
                } else {
                    m1.set_elmt(i, j, 0);
                }
            }
        }
        m1.eliminasi_gauss_jordan_silent();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                hasil.set_elmt(i, j, m1.get_elmt(i, j + col));
            }
        }
        return hasil;
    }

    // Untuk interpolasi polinom
    private double[] spl_solution_to_arr() {
        Matrix b = new Matrix(this.get_row(), 1);
        Matrix A = new Matrix(this.get_row(), this.get_col() - 1);
        for (int i = 0; i < this.get_row(); i++) {
            for (int j = 0; j < this.get_col() - 1; j++) {
                A.set_elmt(i, j, this.get_elmt(i, j));
            }
        }
        for (int i = 0; i < this.get_row(); i++) {
            b.set_elmt(i, 0, this.get_elmt(i, this.get_col() - 1));
        }
        A = A.find_inverse_obe();
        Matrix x = multiply_matrix(A, b);
        double[] solution_arr = new double[x.get_row()];
        for (int i = 0; i < x.get_row(); i++) {
            solution_arr[i] = x.get_elmt(i, 0);
        }
        return solution_arr;
    }

    public static void polynomial_interpolation_file(Scanner scanner) {
        System.out.print("Masukkan nama file beserta extension (.txt) : ");
        String file_name = scanner.nextLine().strip();
        boolean txt_extension = file_name.endsWith(".txt");
        Matrix temp = new Matrix();
        if (txt_extension) {
            try {
                File file = new File("../test/" + file_name);
                Scanner file_scanner = new Scanner(file);
                int row_count = 0;
                int col_count;
                while (file_scanner.hasNextLine()) {
                    file_scanner.nextLine();
                    row_count += 1;
                }
                col_count = row_count;
                row_count -= 1;
                temp.set_new_size(row_count, col_count);
                double[] mem = new double[0];
                file_scanner.close();
                Scanner file_scanner2 = new Scanner(file);
                for (int i = 0; i < temp.get_row(); i++) {
                    int exponent = 0;
                    String[] temp_scan = file_scanner2.nextLine().split(" ");
                    if (temp_scan.length != 2) {
                        System.out.println("Baca file gagal.");
                        System.out.println(
                                "Dalam file terdapat baris (selain baris terakhir) yang tidak terdiri dari dua data!");
                        file_scanner2.close();
                        return;
                    }
                    double[] temp_scan_double = new double[2];
                    for (int t = 0; t < temp_scan.length; t++) {
                        temp_scan_double[t] = Double.parseDouble(temp_scan[t]);
                    }
                    double xi = temp_scan_double[0];
                    if (is_in_array(mem, xi)) {
                        System.out.println("\nBaca file gagal. Tidak dapat membuat interpolasi.");
                        System.out.println("Terdapat data dengan masukan titik dengan x yang sama!");
                        file_scanner2.close();
                        return;
                    } else {
                        mem = push_arr_double(mem, xi);
                    }
                    int j = 0;
                    while (j < temp.get_col() - 1) {
                        temp.data[i][j] = Math.pow(xi, exponent);
                        exponent += 1;
                        j += 1;
                    }
                    double yi = temp_scan_double[1];
                    temp.data[i][j] = yi;
                }
                String[] x_line = file_scanner2.nextLine().split(" ");
                if (x_line.length != 1) {
                    System.out.println("Baca file gagal.");
                    System.out.println(
                            "Baris terakhir hanya boleh terdiri dari satu data yakni data x untuk menginterpolasi suatu y.");
                    file_scanner2.close();
                    return;
                }
                double x = Double.parseDouble(x_line[0]);
                file_scanner2.close();
                System.out.println("File berhasil terbaca.");
                System.out.println("\nBerikut merupakan titik - titik yang terbaca dari file");
                for (int i = 0; i < temp.get_row(); i++) {
                    String msg = String.format("(x%d, y%d) : (%.4f, %.4f)", i, i, temp.get_elmt(i, 1),
                            temp.get_elmt(i, temp.get_col() - 1));
                    System.out.println(msg);
                }
                double[] solution_a = temp.spl_solution_to_arr();
                double result = solution_a[0];
                double temp_double;
                int exp = 1;
                System.out.println();
                System.out.println("Polinom yang didapat :");
                String[] data_to_file = new String[0];
                String poly = String.format("P(x) = %.4f", solution_a[0]);
                System.out.print(poly);
                for (int i = 1; i < solution_a.length; i++) {
                    poly += String.format(" + %.4fx^(%d)", solution_a[i], exp);
                    System.out.print(String.format(" + %.4fx^(%d)", solution_a[i], exp));
                    temp_double = solution_a[i] * Math.pow(x, exp);
                    exp += 1;
                    result += temp_double;
                }
                data_to_file = push_arr_string(data_to_file, poly);
                System.out.println(String.format("\nHasil Interpolasi dari x = %.4f : ", x));
                String output_msg = String.format("y = P(%.4f) = %.4f", x, result);
                System.out.println(output_msg);
                data_to_file = push_arr_string(data_to_file, output_msg);
                option_output_to_file(data_to_file, scanner);
            } catch (FileNotFoundException e) {
                System.out.println("Baca file gagal. File " + file_name + " tidak ditemukan.");
                return;
            } catch (NumberFormatException | InputMismatchException e) {
                System.out.println("Baca file gagal. Terdapat elemen bukan bilangan riil pada file " + file_name + ".");
                return;
            }
        } else {
            System.out.println("Baca file gagal. File bukan file txt.");
            return;
        }
    }

    private static boolean is_in_array(double[] arr, double x) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == x) {
                return true;
            }
        }
        return false;
    }

    private static double[] push_arr_double(double[] original_arr, double value) {
        int array_length = original_arr.length;
        double[] new_arr = new double[array_length + 1];
        for (int i = 0; i < array_length; i++) {
            new_arr[i] = original_arr[i];
        }
        new_arr[array_length] = value;
        return new_arr;
    }

    public static String[] push_arr_string(String[] original_arr, String value) {
        int array_length = original_arr.length;
        String[] new_arr = new String[array_length + 1];
        for (int i = 0; i < array_length; i++) {
            new_arr[i] = original_arr[i];
        }
        new_arr[array_length] = value;
        return new_arr;
    }

    public static void polynomial_interpolation_scan(Scanner scanner) {
        int n = valid_int_input(scanner, "Masukkan berapa banyak titik yang akan digunakan untuk interpolasi : ", 1);
        System.out.println("Masukkan data tiap titik!\n");
        Matrix temp = new Matrix(n, n + 1);
        double[] mem = new double[0];
        for (int i = 0; i < temp.get_row(); i++) {
            int exponent = 0;
            String msg = String.format("x%d : ", i);
            double xi = valid_double_input(scanner, msg);
            if (is_in_array(mem, xi)) {
                System.out.println("\nTidak dapat membuat interpolasi.");
                System.out.println(String.format("Sudah terdapat masukan titik yang x-nya adalah %.4f!", xi));
                return;
            } else {
                mem = push_arr_double(mem, xi);
            }
            int j = 0;
            while (j < temp.get_col() - 1) {
                temp.data[i][j] = Math.pow(xi, exponent);
                exponent += 1;
                j += 1;
            }
            msg = String.format("y%d : ", i);
            double yi = valid_double_input(scanner, msg);
            temp.data[i][j] = yi;
        }
        System.out.println("\nBerikut merupakan titik - titik yang terbaca dari masukan");
        for (int i = 0; i < temp.get_row(); i++) {
            String msg = String.format("(x%d, y%d) : (%.4f, %.4f)", i, i, temp.get_elmt(i, 1),
                    temp.get_elmt(i, temp.get_col() - 1));
            System.out.println(msg);
        }
        double[] solution_a = temp.spl_solution_to_arr();
        double temp_double;
        System.out.println();
        System.out.println("Polinom yang didapat :");
        String[] data_to_file = new String[0];
        String poly = String.format("P(x) = %.4f", solution_a[0]);
        System.out.print(poly);
        for (int i = 1; i < solution_a.length; i++) {
            poly += String.format(" + %.4fx^(%d)", solution_a[i], i);
            System.out.print(String.format(" + %.4fx^(%d)", solution_a[i], i));
        }
        data_to_file = push_arr_string(data_to_file, poly);
        System.out.println("\nApakah anda ingin menginterpolasi suatu y dengan persamaan polinomial tersebut?");
        System.out.println("1. Ya");
        System.out.println("2. Tidak\n");
        int calc_option = Main.valid_input_choice(scanner, 1, 2);
        if (calc_option == 2) {
            option_output_to_file(data_to_file, scanner);
            return;
        }
        while (true) {
            double x = valid_double_input(scanner, "x yang ingin diinterpolasikan y-nya: ");
            double result = solution_a[0];
            int exp = 1;
            for (int i = 1; i < solution_a.length; i++) {
                temp_double = solution_a[i] * Math.pow(x, exp);
                exp += 1;
                result += temp_double;
            }
            System.out.println(String.format("\nHasil Interpolasi dari x = %.4f : ", x));
            String output_msg = String.format("y = P(%.4f) = %.4f", x, result);
            System.out.println(output_msg);
            data_to_file = push_arr_string(data_to_file, output_msg);
            System.out
                    .println("\nApakah anda ingin menginterpolasi suatu y lagi dengan persamaan polinomial tersebut?");
            System.out.println("1. Ya");
            System.out.println("2. Tidak\n");
            int again_option = Main.valid_input_choice(scanner, 1, 2);
            if (again_option == 2) {
                option_output_to_file(data_to_file, scanner);
                return;
            }
        }
    }

    public void read_points_reg(Scanner scanner) {
        int n = valid_int_input(scanner, "Masukkan jumlah peubah x : ", 0);
        int m = valid_int_input(scanner, "Masukkan jumlah sampel : ", 0);
        this.set_new_size(m, n + 1);
        System.out.println();
        for (int i = 0; i < this.get_row(); i++) {
            for (int j = 0; j < this.get_col(); j++) {
                String msg;
                if (j != this.get_col() - 1) {
                    msg = String.format("x%d sampel %d : ", j + 1, i + 1);
                } else {
                    msg = String.format("y  sampel %d : ", i + 1);
                }
                this.data[i][j] = valid_double_input(scanner, msg);
            }
            System.out.println();
        }
    }

    public void multiple_linear_regression(Scanner scanner) {
        Matrix points = this;
        Matrix A = new Matrix(points.get_row(), points.get_col());
        Matrix Y = new Matrix(points.get_row(), 1);
        for (int i = 0; i < points.get_row(); i++) {
            for (int j = 0; j < points.get_col() - 1; j++) {
                A.data[i][j + 1] = points.get_elmt(i, j);
            }
        }
        for (int i = 0; i < A.get_row(); i++) {
            A.data[i][0] = 1;
        }
        for (int i = 0; i < points.get_row(); i++) {
            Y.data[i][0] = points.get_elmt(i, points.get_col() - 1);
        }
        Matrix AT = A.transpose();
        Matrix ATA = multiply_matrix(AT, A);
        Matrix ATY = multiply_matrix(AT, Y);
        Matrix augmented = new Matrix(ATA.get_row(), ATA.get_col() + 1);
        for (int i = 0; i < ATA.get_row(); i++) {
            for (int j = 0; j < ATA.get_col(); j++) {
                augmented.set_elmt(i, j, ATA.get_elmt(i, j));
            }
        }
        for (int i = 0; i < augmented.get_row(); i++) {
            augmented.set_elmt(i, augmented.get_col() - 1, ATY.get_elmt(i, 0));
        }
        System.out.println("\nMenggunakan Normal Estimation Equation for Multiple Linear Regression.");
        System.out.println("Berikut matriks augmented dari persamaan tersebut : ");
        augmented.print_matrix(3);
        augmented.row_eselon_reduction_silent();
        System.out.println("Dengan metode Gauss - Jordan, berikut hasilnya :");
        augmented.print_matrix(4);
        double[] cof_beta = new double[augmented.get_row()];
        for (int i = 0; i < augmented.get_row(); i++) {
            cof_beta[i] = augmented.get_elmt(i, augmented.get_col() - 1);
        }
        System.out.println("Persamaan regresi linier berganda yang didapat :");
        String reg = String.format("f(x) = %.4f", cof_beta[0]);
        for (int i = 1; i < cof_beta.length; i++) {
            reg += String.format(" + %.4f * x%d", cof_beta[i], i);
        }
        String[] data_to_file = new String[0];
        data_to_file = push_arr_string(data_to_file, reg);
        System.out.println(reg);
        System.out.println("\nApakah anda ingin menafsir suatu y dengan regresi tersebut?");
        System.out.println("1. Ya");
        System.out.println("2. Tidak\n");
        int calc_option = Main.valid_input_choice(scanner, 1, 2);
        if (calc_option == 2) {
            option_output_to_file(data_to_file, scanner);
            return;
        }
        while (true) {
            String format_output = new String();
            for (int i = 0; i < augmented.get_row() - 1; i++) {
                format_output += String.format("x%d   ", i + 1);
            }
            format_output += "y";
            data_to_file = push_arr_string(data_to_file, format_output);
            System.out.println("\nMasukkan data tiap peubah X (Xk)!");
            double[] xs = new double[A.get_col() - 1];
            String temp_data = new String();
            for (int i = 0; i < xs.length; i++) {
                String msg = String.format("X%d : ", i + 1);
                xs[i] = valid_double_input(scanner, msg);
                temp_data += Double.toString(xs[i]) + " ";
            }
            System.out.println("\nHasil regresi : ");
            System.out.println(reg);
            String regx = String.format("f(Xk) = %.4f", cof_beta[0]);
            double result = cof_beta[0];
            for (int i = 0; i < xs.length; i++) {
                regx += String.format(" + %.4f * %.4f", cof_beta[i + 1], xs[i]);
                result += cof_beta[i + 1] * xs[i];
            }
            System.out.println(regx);
            System.out.println(String.format("y = f(Xk) = %.4f", result));
            temp_data += String.format("%.4f", result);
            data_to_file = push_arr_string(data_to_file, temp_data);
            System.out.println("\nApakah anda ingin menafsir suatu y lagi dengan regresi tersebut?");
            System.out.println("1. Ya");
            System.out.println("2. Tidak\n");
            int again_option = Main.valid_input_choice(scanner, 1, 2);
            if (again_option == 2) {
                option_output_to_file(data_to_file, scanner);
                return;
            }
        }
    }

    public String[] to_string_arr() {
        String[] lists = new String[0];
        for (int i = 0; i < this.get_row(); i++) {
            String temp_string = new String();
            for (int j = 0; j < this.get_col(); j++) {
                temp_string += String.format("%.3f ", this.get_elmt(i, j));
            }
            lists = push_arr_string(lists, temp_string);
        }
        return lists;
    }

    public static void option_output_to_file(String[] data, Scanner scanner) {
        display_output_option();
        int option = Main.valid_input_choice(scanner, 1, 2);
        if (option == 1) {
            print_to_file(data, scanner);
        }
    }

    public static void display_output_option() {
        System.out.println("\nApakah anda ingin menyimpan luaran ke dalam file?");
        System.out.println("1. Ya");
        System.out.println("2. Tidak\n");
    }

    public static String valid_file_name(Scanner scanner) {
        String file_name;
        while (true) {
            System.out.print("\nMasukkan nama file yang ingin dibuat (tidak perlu mengetik extensionnya) : ");
            file_name = scanner.nextLine().strip();
            File file = new File("../test", file_name + ".txt");
            if (file_name == "") {
                System.out.println("Name file tidak boleh kosong!");
                System.out.println("Ulangi masukan.");
            } else if (file.exists()) {
                System.out.println("Sudah ada file " + file_name + ".txt di folder test!");
                System.out.println("Ulangi masukan nama file selain itu.");
            } else {
                break;
            }
        }
        return file_name;

    }

    public static void print_to_file(String[] data, Scanner scanner) {
        String file_name = valid_file_name(scanner);
        String file_path = "../test/" + file_name + ".txt";
        try {
            FileWriter fileWriter = new FileWriter(file_path);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (int i = 0; i < data.length; i++) {
                bufferedWriter.write(data[i]);
                if (i != data.length - 1) {
                    bufferedWriter.newLine();
                }
            }
            bufferedWriter.close();
            System.out.println("Luaran berhasil tersimpan ke dalam " + file_name + ".txt di folder test!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}