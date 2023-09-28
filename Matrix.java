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
        Matrix m2 = new Matrix(this.n_col, this.n_row);
        for (int i = 0; i < this.get_row(); i++) {
            for (int j = 0; j < this.get_col(); j++) {
                m2.data[j][i] = this.data[i][j];
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

    public void spl_inverse() {
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
        if (find_determinant(A) == 0) {
            System.out.println("Determinan matriks A = 0. Matriks A tidak memiliki balikan.");
            System.out.println("Gunakan cara lain untuk mencari solusi SPL!");
            return;
        }
        System.out.println("Invers Matriks A");
        A = A.find_inverse();
        A.print_matrix(2);
        System.out.println("Matriks b");
        b.print_matrix(2);
        System.out.println("x = (Invers A) * b");
        Matrix x = multiply_matrix(A, b);
        System.out.println("Matriks x");
        x.print_matrix(2);

        for (int i = 0; i < x.get_row(); i++) {
            System.out.println(String.format("x%d : %.2f", i + 1, x.get_elmt(i, 0)));
        }
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

    public void eliminasi_gauss() {
        this.row_eselon();
        this.print_matrix(2);
    }

    public void eliminasi_gauss_jordan() {
        this.row_eselon_reduction();
        this.print_matrix(2);
    }

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

    public void row_eselon() {
        int i, j, baris, kolom;
        baris = this.get_row();
        kolom = this.get_col();
        this.atur_baris_rapi();
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
                    set_elmt(k, j, round_x_decimals(get_elmt(k, j) + pengurang * this.get_elmt(i, j), 7));
                }
            }
        }
        this.atur_baris_rapi();
    }

    public void row_eselon_reduction() {
        this.row_eselon();
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
                    this.set_elmt(k, j, round_x_decimals(this.get_elmt(k, j) + pengurang * this.get_elmt(i, j), 7));
                }
            }
        }
    }

    // #akhirAlbert

    // Ivan
    public void cramer() {
        Matrix x = new Matrix(this.n_row, 1);
        Matrix a = new Matrix(this.n_row, this.n_col - 1);
        Matrix temp = new Matrix(this.n_row, this.n_col - 1);
        double det1;
        double det2 = 1;
        Matrix b = new Matrix(this.n_row, 1);

        for (int i = 0; i < this.n_row; i++) {
            b.set_elmt(i, 0, this.get_elmt(i, n_col - 1));
        }

        for (int i = 0; i < a.n_row; i++) {
            for (int j = 0; j < a.n_col; j++) {
                a.set_elmt(i, j, this.data[i][j]);
            }
        }
        det1 = find_determinant(a);
        if (det1 == 0) {
            System.out.println("Determinan matriks = 0, SPL tidak memiliki solusi yang unik.\n");
            this.row_eselon();
            SPL.gauss_result(this);
        }
        else {
            for (int i = 0; i < this.n_row; i++) {
                b.set_elmt(i, 0, this.get_elmt(i, n_col - 1));
            }

            for (int i = 0; i < a.n_row; i++) {
                for (int j = 0; j < a.n_col; j++) {
                    a.set_elmt(i, j, this.data[i][j]);
                }
            }
            det1 = find_determinant(a);
            System.out.println("\nDeterminan matriks A: " + det1);

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
                System.out.println("Determinan matriks A" + i + " : " + det2);
                x.set_elmt(i, 0, (det2 / det1));
            }
            System.out.println("\nBerikut solusi dari kaidah Cramer");
            for (int i = 0; i < x.n_row; i++) {
                System.out.println("x" + i + " = " + x.data[i][0]);
            }
        }
    }

    //Untuk interpolasi polinom
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
        A = A.find_inverse();
        Matrix x = multiply_matrix(A, b);
        double[] solution_arr = new double[x.get_row()];
        for (int i = 0; i < x.get_row(); i++) {
            solution_arr[i] = x.get_elmt(i, 0);
        }
        return solution_arr;
    }

    public static void polynomial_interpolation_file(Scanner scanner) {
        System.out.print("Masukkan nama file beserta extension (.txt) : ");
        String file_name = scanner.nextLine();
        file_name.strip();
        boolean txt_extension = file_name.endsWith(".txt");
        Matrix temp = new Matrix();   
        if (txt_extension) {
            try {
                File file = new File(file_name);
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
                file_scanner.close();
                Scanner file_scanner2 = new Scanner(file);
                for (int i = 0; i < temp.get_row(); i++) {
                    int exponent = 0;
                    double xi = file_scanner2.nextDouble();
                    
                    int j = 0;
                    while (j < temp.get_col() - 1) {
                        temp.data[i][j] = Math.pow(xi, exponent);
                        exponent += 1;
                        j += 1;
                    }
                    double yi = file_scanner2.nextDouble();
                    temp.data[i][j] = yi;
                }
                double x = file_scanner2.nextDouble();
                file_scanner2.close();
                System.out.println("File berhasil terbaca.");
                System.out.println("\nBerikut merupakan titik - titik yang terbaca dari file");
                for (int i = 0; i < temp.get_row(); i++) {
                    String msg = String.format("(x%d, y%d) : (%.4f, %.4f)", i, i, temp.get_elmt(i, 1), temp.get_elmt(i, temp.get_col() - 1));
                    System.out.println(msg);
                }
                double[] solution_a = temp.spl_solution_to_arr();
                double result = solution_a[0];
                double temp_double;
                int exp = 1;
                System.out.println();
                System.out.println("Polinom yang didapat :");
                System.out.print(String.format("P(x) = %.4f", solution_a[0]));
                for (int i = 1; i < solution_a.length; i++) {
                    System.out.print(String.format(" + %.4fx^(%d)", solution_a[i], exp));
                    temp_double = solution_a[i] * Math.pow(x, exp);
                    exp += 1;
                    result += temp_double;
                }
                System.out.println(String.format("\nHasil Interpolasi dari x = %.4f : ", x));
                System.out.println(String.format("y = P(%.4f) = %.4f", x, result));
            } catch (FileNotFoundException e) {
                System.out.println("Baca file gagal. File " + file_name + " tidak ditemukan.");
                return;
            }
        } else {
            System.out.println("Baca file gagal. File bukan file txt.");
            return;
        }
    }
    
    public static void polynomial_interpolation_scan(Scanner scanner) {
        int n = valid_int_input(scanner, "Masukkan berapa banyak titik yang akan digunakan untuk interpolasi : ", 0);
        System.out.println("Masukkan data tiap titik!\n");
        Matrix temp = new Matrix(n, n + 1);    
        for (int i = 0; i < temp.get_row(); i++) {
            int exponent = 0;
            String msg = String.format("x%d : ", i);
            double xi = valid_double_input(scanner, msg);
            
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
        double x = valid_double_input(scanner, "x yang ingin diinterpolasikan y-nya: ");
        System.out.println("\nBerikut merupakan titik - titik yang terbaca dari masukan");
        for (int i = 0; i < temp.get_row(); i++) {
            String msg = String.format("(x%d, y%d) : (%.4f, %.4f)", i, i, temp.get_elmt(i, 1), temp.get_elmt(i, temp.get_col() - 1));
            System.out.println(msg);
        }
        double[] solution_a = temp.spl_solution_to_arr();
        double result = solution_a[0];
        double temp_double;
        int exp = 1;
        System.out.println();
        System.out.println("Polinom yang didapat :");
        System.out.print(String.format("P(x) = %.4f", solution_a[0]));
        for (int i = 1; i < solution_a.length; i++) {
            System.out.print(String.format(" + %.4fx^(%d)", solution_a[i], exp));
            temp_double = solution_a[i] * Math.pow(x, exp);
            exp += 1;
            result += temp_double;
        }
        System.out.println(String.format("\nHasil Interpolasi dari x = %.4f : ", x));
        System.out.println(String.format("y = P(%.4f) = %.4f", x, result));
    }

    public void read_points_reg(Scanner scanner) {
        int n = valid_int_input(scanner, "Masukkan jumlah peubah x : ", 0);
        int m = valid_int_input(scanner, "Masukkan jumlah sampel : ", 0);
        this.set_new_size(m + 1, n + 1);
        for (int i = 0; i < this.get_row() - 1; i++) {
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
        System.out.println("\nMasukkan data tiap peubah x, untuk mencari y!");
        for (int j = 0; j < this.get_col(); j++) {
            if (j != this.get_col() - 1) {
                String msg = String.format("x%d : ", j + 1);
                this.data[this.get_row() - 1][j] = valid_double_input(scanner, msg);
            } else {
                this.data[this.get_row() - 1][j] = 999999;
            }
        }
    }

    public void read_points_from_file(Scanner scanner) {
        /*
         * Asumsi bentuk data tiap titik dalam txt adalah sebagai berikut
         * (n = jumlah peubah x)
         * (i = sampel ke - i)
         * (k = data x untuk mencari y-nya)
         * x1i x2i x3i .. xni yi
         * x1i x2i x3i .. xni yi
         * x1k x2k x3k .. xnk
         */
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
                        if (i != this.get_row() - 1 || j != this.get_col() - 1) {
                            this.set_elmt(i, j, file_scanner.nextDouble());
                        } else {
                            this.set_elmt(i, j, 999999);
                        }
                    }
                }
                file_scanner.close();
                System.out.println("File berhasil terbaca.");
            } catch (FileNotFoundException e) {
                return;
            }
        } else {
            System.out.println("Baca file gagal. File bukan file txt.");
        }
    }

    public void multiple_linear_regression() {
        Matrix points = this;
        Matrix A = new Matrix(points.get_row() - 1, points.get_col());
        Matrix Y = new Matrix(points.get_row() - 1, 1);
        double[] xs = new double[points.get_col() - 1];
        for (int j = 0; j < xs.length; j++) {
            xs[j] = points.get_elmt(points.get_row() - 1, j);
        }
        for (int i = 0; i < points.get_row() - 1; i++) {
            for (int j = 0; j < points.get_col() - 1; j++) {
                A.data[i][j + 1] = points.get_elmt(i, j);
            }
        }
        for (int i = 0; i < A.get_row(); i++) {
            A.data[i][0] = 1;
        }
        for (int i = 0; i < points.get_row() - 1; i++) {
            Y.data[i][0] = points.get_elmt(i, points.get_col() - 1);
        }
        //B = (At * A)^(-1) * (At * Y)
        Matrix AT = A.transpose();
        Matrix ATA = multiply_matrix(AT, A);
        ATA = ATA.find_inverse();
        Matrix ATY = multiply_matrix(AT, Y);
        Matrix B = multiply_matrix(ATA, ATY);
        double[] cof_beta = new double[B.get_row()];
        for (int i = 0; i < B.get_row(); i++) {
            cof_beta[i] = B.get_elmt(i, 0);
        }
        System.out.println();
        System.out.println("Persamaan regresi linier berganda yang didapat :");
        System.out.print(String.format("f(x) = %.4f", cof_beta[0]));
        double result = cof_beta[0];
        for (int i = 1; i < cof_beta.length; i++) {
            System.out.print(String.format(" + %.4f * x%d", cof_beta[i], i));
            double temp_double = cof_beta[i] * xs[i - 1] ;
            result += temp_double;
        }
        System.out.println();
        System.out.println("\nHasil regresi : ");
        System.out.println("Kumpulan X yang digunakan untuk mencari y dari regresi linier berganda (Xk):");
        for (int i = 0; i < xs.length; i++) {
            System.out.println(String.format("X%d : %.4f", i + 1, xs[i]));
        }
        System.out.println(String.format("y = f(Xk) = %.4f", result));
    }
}