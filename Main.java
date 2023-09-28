import java.util.Scanner;
import java.io.IOException;

public class Main {
    public static void clear_terminal() {
        String os = System.getProperty("os.name").toLowerCase();

        try {
            if (os.contains("win")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else if (os.contains("mac") || os.contains("nix") || os.contains("nux") || os.contains("bsd")) {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            } else {
                System.out.println();
            }
        } catch (IOException | InterruptedException e) {
            System.out.println();
        }
    }

    public static void press_to_menu(Scanner scanner) {
        System.out.println();
        System.out.print("Tekan Enter untuk balik ke menu utama!");
        scanner.nextLine();
    }

    public static void display_menu() {
        System.out.println("# MENU");
        System.out.println("-----------------------------");
        System.out.println("1. Sistem Persamaaan Linier");
        System.out.println("2. Determinan");
        System.out.println("3. Matriks Balikan");
        System.out.println("4. Interpolasi Polinom");
        System.out.println("5. Interpolasi Bicubic Spline");
        System.out.println("6. Regresi Linier Berganda");
        System.out.println("7. Keluar Program");
        System.out.println("-----------------------------");
    }

    public static void display_submenu_1() {
        System.out.println("* Sistem Persamaan Linier");
        System.out.println("--------------------------------");
        System.out.println("1. Metode Eliminasi Gauss");
        System.out.println("2. Metode Eliminasi Gauss-Jordan");
        System.out.println("3. Metode Matriks Balikan");
        System.out.println("4. Kaidah Cramer");
        System.out.println("5. Balik ke Menu Utama");
        System.out.println("--------------------------------");
    }

    public static void display_submenu_2() {
        System.out.println("*  Determinan");
        System.out.println("--------------------------");
        System.out.println("1. Metode Reduksi Baris");
        System.out.println("2. Metode Ekpansi Kofaktor");
        System.out.println("3. Balik ke Menu Utama");
        System.out.println("--------------------------");
    }

    public static void display_submenu_3() {
        System.out.println("*  Matriks Balikan");
        System.out.println("--------------------------------------");
        System.out.println("1. Metode Matriks Adjoin");
        System.out.println("2. Metode Transformasi Baris Elementer");
        System.out.println("3. Balik ke Menu Utama");
        System.out.println("--------------------------------------");
    }

    public static void display_input_options() {
        System.out.println("*  Opsi Memasukkan Matriks");
        System.out.println("--------------------------");
        System.out.println("1. Dari Command Line");
        System.out.println("2. Dari File .txt");
        System.out.println("--------------------------");
    }

    public static void display_input_options_iplpol() {
        System.out.println("*  Opsi Memasukkan Data");
        System.out.println("--------------------------");
        System.out.println("1. Dari Command Line");
        System.out.println("2. Dari File .txt");
        System.out.println("3. Balik ke Menu Utama");
        System.out.println("--------------------------");
    }

    public static int valid_input_choice(Scanner scanner, int range_from, int range_to) {
        String err_msg = String.format(">> Tidak valid. Hanya menerima input angka dari %d hingga %d!", range_from,
                range_to);

        while (true) {
            System.out.print("Ketik Pilihan : ");
            String input = scanner.nextLine();

            try {
                int num = Integer.parseInt(input);
                if (num >= range_from && num <= range_to) {
                    return num;
                }
                System.out.println(err_msg);
            } catch (NumberFormatException e) {
                System.out.println(err_msg);
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean program_on = true;
        while (program_on == true) {
            clear_terminal();
            display_menu();
            int choice = valid_input_choice(scanner, 1, 7);
            clear_terminal();
            if (choice == 1) {
                display_submenu_1();
                int sub_choice = valid_input_choice(scanner, 1, 5);
                if (sub_choice >= 1 && sub_choice <= 4) {
                    clear_terminal();
                    // input Matrix
                    Matrix matrix = new Matrix();
                    display_input_options();
                    int input_option = valid_input_choice(scanner, 1, 2);
                    clear_terminal();
                    if (input_option == 1) {
                        matrix.read_matrix_scan(scanner);
                    } else {
                        matrix.read_matrix_from_file(scanner);
                    }
                    if (matrix.is_not_empty()) {
                        if (sub_choice == 1) { // done
                            matrix.eliminasi_gauss();
                            SPL.gauss_result(matrix);
                        } else if (sub_choice == 2) { // done
                            matrix.eliminasi_gauss_jordan();
                            SPL.gauss_result(matrix);
                        } else if (sub_choice == 3) {
                            matrix.spl_inverse();
                        } else {
                            // Bila input dengan file, prekondisi ukuran matriks adalah nRow x (nRow + 1)
                            matrix.cramer();
                        }
                    }
                    press_to_menu(scanner);
                } else {
                    continue;
                }
            } else if (choice == 2) {
                display_submenu_2();
                int sub_choice = valid_input_choice(scanner, 1, 3);
                if (sub_choice == 1 || sub_choice == 2) {
                    clear_terminal();

                    // input matrix
                    Matrix matrix = new Matrix();
                    display_input_options();
                    int input_option = valid_input_choice(scanner, 1, 2);
                    clear_terminal();
                    if (input_option == 1) {
                        matrix.read_square_matrix_scan(scanner, 1);
                    } else {
                        matrix.read_matrix_from_file(scanner);
                    }

                    if (matrix.is_not_empty()) {
                        if (sub_choice == 1) {
                            matrix.determinant_row_reduction();
                        } else {
                            matrix.determinant_cofactor_expansion();
                        }
                    }

                    press_to_menu(scanner);
                } else {
                    continue;
                }

            } else if (choice == 3) {
                display_submenu_3();
                int sub_choice = valid_input_choice(scanner, 1, 3);
                if (sub_choice == 1 || sub_choice == 2) {
                    clear_terminal();

                    // input matrix
                    Matrix matrix = new Matrix();
                    display_input_options();
                    int input_option = valid_input_choice(scanner, 1, 2);
                    clear_terminal();
                    if (input_option == 1) {
                        matrix.read_square_matrix_scan(scanner, 2);
                    } else {
                        matrix.read_matrix_from_file(scanner);
                    }

                    if (matrix.is_not_empty()) {
                        if (sub_choice == 1) {
                            matrix.inverse_adjoint();
                        } else {
                            // Matriks balikan metode transformasi baris el.
                            int row = matrix.get_row();
                            int col = matrix.get_col();
                            Matrix m1 = new Matrix(row, col * 2);
                            for (int i = 0; i < row; i++) {
                                for (int j = 0; j < col * 2; j++) {
                                    if (j < col) {
                                        m1.set_elmt(i, j, matrix.get_elmt(i, j));
                                    } else if (i == j - col) {
                                        m1.set_elmt(i, j, 1);
                                    } else {
                                        m1.set_elmt(i, j, 0);
                                    }
                                }
                            }
                            m1.print_matrix(2);
                            m1.eliminasi_gauss_jordan();
                            m1.print_matrix(2);
                            Matrix hasil = new Matrix(row, col);
                            for (int i = 0; i < row; i++) {
                                for (int j = 0; j < col; j++) {
                                    hasil.set_elmt(i, j, m1.get_elmt(i, j + col));
                                }
                            }
                            hasil.print_matrix(3);
                        }
                    }

                    press_to_menu(scanner);
                } else {
                    continue;
                }
            } else if (choice == 4) {
                display_input_options_iplpol();
                int input_option = valid_input_choice(scanner, 1, 3);
                clear_terminal();
                if (input_option == 1) {
                    Matrix.polynomial_interpolation_scan(scanner);
                } else if (input_option == 2) {
                    Matrix.polynomial_interpolation_file(scanner);
                } else {
                    continue;
                }

                press_to_menu(scanner);
            } else if (choice == 5) {
                // Interpolasi bicubic
            } else if (choice == 6) {
                // Regresi lsinier
            } else {
                program_on = false;
            }
        }
        scanner.close();
    }
}