// import java.io.File;
// import java.io.IOException;
// import java.util.Scanner;
// import java.lang.Math;

public class SPL {

    private double value;
    private double[] array;
    private int isi;

    public SPL(double value, double[] array) {
        this.value = value;
        this.array = array;
        this.isi = 0; // 0 jika tidak ada variabel di belakang, 1 jika ada variabel di belakang
    }

    public double getValue() {
        return this.value;
    }

    public double getArray(int i) {
        return this.array[i];
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setArray(int idx, double value) {
        this.array[idx] = value;
    }

    public void set_isi() {
        this.isi = 1;
    }

    public boolean get_isi_noVar() {
        return (this.isi == 0);
    }

    public void print_out_solution(int get_col, int a) {
        double angka = this.getValue();
        if (angka != 0) {
            System.out.format("%.2f ", this.getValue());
        }
        if (!this.get_isi_noVar() && angka != 0) {
            for (int b = 0; b < get_col - 1; b++) {
                if (this.getArray(b) != 0 && this.getArray(b) > 0) {
                    System.out.format("+ %.2f %c ", this.getArray(b), (char) (b + 65));
                } else if (this.getArray(b) != 0 && this.getArray(b) < 0) {
                    System.out.format("- %.2f %c ", this.getArray(b) * -1, (char) (b + 65));
                }
            }
            System.out.println("");
        } else if (!this.get_isi_noVar() && angka == 0) {
            boolean first = true;
            for (int b = 0; b < get_col - 1; b++) {
                if (this.getArray(b) != 0 && first) {
                    System.out.format(" %.2f %c", this.getArray(b), (char) (b + 65));
                } else if (this.getArray(b) != 0 && !first && this.getArray(b) > 0) {
                    System.out.format("+ %.2 f%c ", this.getArray(b), (char) (b + 65));
                } else if (this.getArray(b) != 0 && !first && this.getArray(b) < 0) {
                    System.out.format("+ %.2 f%c ", this.getArray(b) * -1, (char) (b + 65));
                }
            }
            System.out.println("");
        } else if (angka == 0 && this.get_isi_noVar()) {
            System.out.println((char) (a + 65));
            ;
        }

    }

    public static void gauss_result(Matrix m) {
        int i, j;
        boolean gaAdaSolusi, banyakSolusi;
        gaAdaSolusi = false;
        banyakSolusi = false;

        for (i = 0; i < m.get_row(); i++) {
            if (m.is_baris_i_0(i)) {
                if (m.get_elmt(i, m.get_col() - 1) != 0) {
                    gaAdaSolusi = true;
                    break;
                } else if (m.get_elmt(i, m.get_col() - 1) == 0) {
                    banyakSolusi = true;
                    break;
                }
            }
        }

        if (gaAdaSolusi) {
            System.out.println("Tidak ada solusi.");
        } else if (banyakSolusi) {
            System.out.println("Mempunyai banyak solusi");

            SPL[] arrayOfSpl = new SPL[m.get_col() - 1];
            for (j = 0; j < m.get_col() - 1; j++) {
                double[] maxParametrik = new double[m.get_col() - 1];
                for (i = 0; i < m.get_col() - 1; i++) {
                    maxParametrik[i] = 0;
                }
                arrayOfSpl[j] = new SPL(0, maxParametrik);
            }

            for (i = m.get_row() - 1; i >= 0; i--) {
                int idx = -1;
                for (j = 0; j < m.get_col() - 1; j++) {
                    if (m.get_elmt(i, j) == 1) {
                        idx = j;
                        break;
                    }
                }

                if (idx == -1) {
                    continue;
                }

                arrayOfSpl[idx].setValue(m.get_elmt(i, m.get_col() - 1)); // set value ke array
                for (j = idx + 1; j < m.get_col() - 1; j++) { // mulai dari setelah leading 1
                    if (m.get_elmt(i, j) != 0) { // jika tidak sama dengan 0
                        double temp = arrayOfSpl[idx].getValue();
                        double temp2 = arrayOfSpl[j].getValue();
                        double temp3 = m.get_elmt(i, j);
                        arrayOfSpl[idx].setValue(temp - (temp2) * temp3); // kurang value
                        if (!(arrayOfSpl[j].get_isi_noVar())) {
                            arrayOfSpl[idx].set_isi();
                            for (int a = 0; a < m.get_col() - 1; a++) {
                                // kurang var
                                if (arrayOfSpl[j].getArray(a) != 0) {
                                    double temp5 = arrayOfSpl[idx].getArray(a);
                                    double temp4 = arrayOfSpl[j].getArray(a);
                                    arrayOfSpl[idx].setArray(a, temp5 - temp3 * temp4);
                                }
                            }

                        } else if ((arrayOfSpl[j].get_isi_noVar()) && arrayOfSpl[j].getValue() == 0
                                && arrayOfSpl[idx].getArray(j) != 0) {
                            double temp5 = arrayOfSpl[idx].getArray(j);
                            arrayOfSpl[idx].setArray(j, temp5 - temp3);
                            arrayOfSpl[idx].set_isi();
                        } else if ((arrayOfSpl[j].get_isi_noVar()) && arrayOfSpl[j].getValue() == 0
                                && arrayOfSpl[idx].getArray(j) == 0) {

                            arrayOfSpl[idx].setArray(j, -temp3);
                            arrayOfSpl[idx].set_isi();
                        }

                    }

                }

            }
            for (int a = 0; a < m.get_col() - 1; a++) {
                System.out.format("X%d -> ", a + 1);
                arrayOfSpl[a].print_out_solution(m.get_col() - 1, a);
            }

        } else { // solusi tunggal
            System.out.println("Mempunyai solusi tunggal");
            double[] hasil = new double[m.get_row()];
            for (i = 0; i < m.get_row(); i++) {
                hasil[i] = 0;
            }
            Matrix matriksA = new Matrix(m.get_row(), m.get_col() - 1);
            for (i = 0; i < m.get_row(); i++) {
                for (j = 0; j < m.get_col() - 1; j++) {
                    matriksA.set_elmt(i, j, m.get_elmt(i, j));
                }
            }
            double[] matriksB = new double[m.get_row()];
            for (i = 0; i < m.get_row(); i++) {
                matriksB[i] = m.get_elmt(i, m.get_col() - 1);
            }

            for (i = m.get_row() - 1; i >= 0; i--) {
                hasil[i] = matriksB[i];
                for (j = i + 1; j < m.get_row(); j++) {
                    hasil[i] -= matriksA.get_elmt(i, j) * hasil[j];
                }
                hasil[i] = hasil[i] / matriksA.get_elmt(i, i);
            }

            for (int a = 0; a < m.get_row(); a++) {
                System.out.format("X%d -> ", a + 1);
                System.out.format("%.2f\n", hasil[a]);
            }

        }

    }

}
