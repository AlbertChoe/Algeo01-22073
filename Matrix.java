import java.io.File;
import java.io.FileNotFoundException;
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

    // Scanner matriks untuk matriks persegi
    public void read_square_matrix_scan(Scanner scanner, int min_dimension) {
        int dimension = valid_int_input(scanner, "Masukkan dimensi matriks persegi : ", min_dimension - 1);
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
        boolean txt_extension = file_name.endsWith(".txt");
        if (txt_extension) {
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

    public void determinant_row_reduction() {
        System.out.println();
        System.out.println("Matriks yang dimasukkan");
        this.print_matrix(2);
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

    public void determinant_cofactor_expansion() {
        System.out.println();
        System.out.println("Matriks yang dimasukkan");
        this.print_matrix(2);
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
    }

    public boolean is_matrix_upper_triangle() {
        boolean triangle = true;
        for (int i = 1; i < this.get_row(); i++) {
            for (int j = 0; j < i; j++) {
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

    private Matrix transpose() {
        Matrix m2 = new Matrix(this.n_row, this.n_col);
        for (int i = 0; i < this.get_row(); i++) {
            for (int j = 0; j < this.get_col(); j++) {
                m2.data[i][j] = this.data[j][i];
            }
        }
        return m2;
    }

    public void inverse_adjoint() {
        System.out.println();
        System.out.println("Matriks yang dimasukkan");
        this.print_matrix(2);
        double determinant = find_determinant(this);

        if (determinant == 0) {
            System.out.println("Determinan matriks adalah 0.");
            System.out.println("Sehingga matriks tidak mempunyai matriks balikan.");
            System.out.println("Matriks masukan adalah matriks tunggal(singular).");
            return;
        }
        System.out.println(String.format("Determinan matriks masukan : %.2f", determinant));
        System.out.println();
        Matrix cofactor = this.find_cofactor_matrix();
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
    }

    /*
     * Return invers dari matrix.
     * Prekondisi : Matriks bukan singular (determinan != 0) dan dimensi >= 2
     */
    private Matrix find_inverse() {
        double determinant = find_determinant(this);
        Matrix cofactor = this.find_cofactor_matrix();
        Matrix adjoin = cofactor.transpose();
        double factor = 1 / determinant;
        for (int i = 0; i < adjoin.get_row(); i++) {
            for (int j = 0; j < adjoin.get_col(); j++) {
                adjoin.set_elmt(i, j, factor * adjoin.get_elmt(i, j));
            }
        }
        return adjoin;
    }

    // #Albert
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

    // Membuat matriks agar tidak ada baris yang 0 semua di tengah tengah
    // contoh
    // 0 0 0 0 0 6
    // 0 0 0 menjadi 0 0 0
    // 0 0 6 0 0 0
    // 0 0 0 0 6 6
    // 0 6 6 menjadi 0 0 6
    // 0 0 6 0 0 0
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
                    System.out.println(String.format("Menukar baris %d dengan bairs %d", j + 1, j + 2));
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

    public void eliminasi_gauss() {
        this.row_eselon();
    }

    public void eliminasi_gauss_jordan() {
        this.row_eselon_reduction();
    }

    public static Matrix multiply_matrix(Matrix m1, Matrix m2) {
        Matrix hasil = new Matrix(m1.get_row(), m2.get_col());
        int i, j;
        for (i = 0; i < hasil.get_row(); i++) {
            for (j = 0; j < hasil.get_col(); j++) {
                int x, jumlah = 0;
                for (x = 0; x < m1.get_col(); x++) {
                    jumlah += m1.get_elmt(i, x) * m2.get_elmt(x, j);
                }
                hasil.set_elmt(i, j, jumlah);

            }
        }
        return hasil;
    }

    public void row_eselon() {
        int i, j, baris, kolom;
        baris = this.get_row();
        kolom = this.get_col();
        this.atur_baris_rapi();
        for (i = 0; i < baris; i++) {
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
            double value = this.get_elmt(i, idx);
            for (j = 0; j < kolom; j++) {
                set_elmt(i, j, round_x_decimals(this.get_elmt(i, j) / value, 7));
            }

            for (int k = i + 1; k < baris; k++) {
                double pengurang = -this.get_elmt(k, idx);
                for (j = 0; j < kolom; j++) {
                    set_elmt(k, j, round_x_decimals(get_elmt(k, j) + pengurang * this.get_elmt(i, j), 7));
                }
            }
        }
    }

    public void row_eselon_reduction() {
        this.row_eselon();
        int i, j, baris, kolom;
        baris = this.get_row();
        kolom = this.get_col();

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
                    this.set_elmt(k, j, round_x_decimals(this.get_elmt(k, j) + pengurang * this.get_elmt(i, j), 7));
                }
            }
        }
    }
    // #akhirAlbert

    // Ivan
    public void cramer(Matrix b) {
        Matrix x = new Matrix(this.n_row, 1);
        Matrix a = new Matrix(this.n_row, this.n_col);
        Matrix temp = new Matrix(this.n_row, this.n_col);
        double det1;
        double det2 = 1;

        for (int i = 0; i < a.n_row; i++) {
            for (int j = 0; j < a.n_col; j++) {
                a.set_elmt(i, j, this.data[i][j]);
            }
        }
        det1 = find_determinant(a);

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
            x.set_elmt(i, 0, (det2 / det1));
        }
        System.out.println("Berikut solusi dari kaidah Cramer");
        x.print_matrix(3);
    }
}
