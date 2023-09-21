import java.util.Scanner;
import java.util.InputMismatchException;

public class Main {

    public static void display_menu() {
        System.out.println("MENU");
        System.out.println("1. Sistem Persamaaan Linier");
        System.out.println("2. Determinan");
        System.out.println("3. Matriks Balikan");
        System.out.println("4. Interpolasi Polinom");
        System.out.println("5. Interpolasi Bicubic Spline");
        System.out.println("6. Regresi Linier Berganda");
        System.out.println("7. Keluar");
    }

    public static void display_submenu_1() {
        System.out.println("Sistem Persamaan Linier :");
        System.out.println("1. Metode Eliminasi Gauss");
        System.out.println("2. Metode Eliminasi Gauss-Jordan");
        System.out.println("3. Metode Matriks Balikan");
        System.out.println("4. Kaidah Cramer");
        System.out.println("5. Balik ke Menu Utama");
    }

    public static void display_submenu_2() {
        System.out.println("Determinan :");
        System.out.println("1. Metode Reduksi Baris");
        System.out.println("2. Metode Ekpansi Kofaktor");
        System.out.println("3. Balik ke Menu Utama");
    }

    public static void display_submenu_3() {
        System.out.println("Matriks Balikan :");
        System.out.println("1. Metode Matriks Adjoin");
        System.out.println("2. Metode Transformasi Baris Elementer");
        System.out.println("3. Balik ke Menu Utama");
    }

    public static int valid_input_choice(Scanner scanner, int range_from, int range_to) {
        String err_msg = String.format("!! Input tidak valid. Hanya input angka dari %d hingga %d !!", range_from, range_to);

        while (true) {
            try {
                System.out.print("Ketik Pilihan : ");
                int num = scanner.nextInt();
                if (num >= range_from && num <= range_to) {
                    return num;
                }
                System.out.println(err_msg);
            } catch (InputMismatchException e) {
                System.out.println(err_msg);
                scanner.next();
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean program_on = true;
        while (program_on == true) {
            display_menu();
            int choice = valid_input_choice(scanner, 1, 7);
            if (choice == 1) {
                display_submenu_1();
                int sub_choice = valid_input_choice(scanner, 1, 5);
                if (sub_choice == 1) {

                } else if (sub_choice == 2) {

                } else if (sub_choice == 3) {

                } else if (sub_choice == 4) {

                } else {
                    System.out.println("===============================");
                    continue;
                }

            } else if (choice == 2) {
                display_submenu_2();
                int sub_choice = valid_input_choice(scanner, 1, 3);
                if (sub_choice == 1) {

                } else if (sub_choice == 2) {

                } else {
                    System.out.println("===============================");
                    continue;
                }

            } else if (choice == 3) {
                display_submenu_3();
                int sub_choice = valid_input_choice(scanner, 1, 3);
                if (sub_choice == 1) {

                } else if (sub_choice == 2) {

                } else {
                    System.out.println("===============================");
                    continue;
                }
            } else if (choice == 4) {

            } else if (choice == 5) {

            } else if (choice == 6) {

            } else {
                program_on = false;
            }
        }
        scanner.close();
        
        
        // //Get nRow + nCol
        // System.out.print("Enter the number of rows: ");
        // int numRows = scanner.nextInt();
        // System.out.print("Enter the number of columns: ");
        // int numCols = scanner.nextInt();

        // // Initialize matrix
        // float[][] matrix = new float[numRows][numCols];


        // System.out.println("(TEST) Enter the elements of the matrix:");
        // for (int i = 0; i < numRows; i++) {
        //     for (int j = 0; j < numCols; j++) {
        //         matrix[i][j] = scanner.nextFloat();
        //     }
        // }

        // scanner.close();

        // //Output Matrix
        // System.out.println("Input Matrix:");
        // for (int i = 0; i < numRows; i++) {
        //     for (int j = 0; j < numCols; j++) {
        //         System.out.print(matrix[i][j] + " ");
        //     }
        //     System.out.println();
        // }
    }
}
