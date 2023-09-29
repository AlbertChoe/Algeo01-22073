public class SPL {

    private double value;
    private double[] array;
    private boolean isi;

    public SPL(double value, double[] array) {
        this.value = value;
        this.array = array;
        this.isi = false; // false jika angka adalah bebas , true jika angka adalah 0
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
        this.isi = true;
    }

    public boolean get_isi_noVar() {
        return (this.isi == false);
    }

    public void print_out_solution(int get_col, int a) {
        double angka = Matrix.round_x_decimals(this.getValue(), 2);
        if (!this.get_isi_noVar() && angka != 0) {
            System.out.format(" %.2f ", this.getValue());
        }
        boolean printed = false;
        // for (int b = 0; b < get_col - 1; b++) {
        // System.out.format("%.2f ", this.getArray(b));
        // }
        if (angka != 0) {
            for (int b = 0; b < get_col - 1; b++) {
                if (Matrix.round_x_decimals(this.getArray(b), 2) != 0
                        && Matrix.round_x_decimals(this.getArray(b), 2) > 0) {
                    System.out.format("+ %.2f%c ", Matrix.round_x_decimals(this.getArray(b), 2), (char) (b + 65));
                } else if (Matrix.round_x_decimals(this.getArray(b), 2) != 0
                        && Matrix.round_x_decimals(this.getArray(b), 2) < 0) {
                    System.out.format("- %.2f%c ", Matrix.round_x_decimals(this.getArray(b), 2) * (-1),
                            (char) (b + 65));
                }
            }

        } else if (angka == 0 && this.get_isi_noVar()) {
            boolean first = true;
            for (int b = 0; b < get_col - 1; b++) {
                if (Matrix.round_x_decimals(this.getArray(b), 2) != 0 && first) {
                    System.out.format("%.2f%c ", Matrix.round_x_decimals(this.getArray(b), 2), (char) (b + 65));
                    printed = true;
                    first = false;
                } else if (Matrix.round_x_decimals(this.getArray(b), 2) != 0 && !first
                        && Matrix.round_x_decimals(this.getArray(b), 2) > 0) {
                    System.out.format(" + %.2f%c ", Matrix.round_x_decimals(this.getArray(b), 2), (char) (b + 65));
                    printed = true;
                } else if (Matrix.round_x_decimals(this.getArray(b), 2) != 0 && !first
                        && Matrix.round_x_decimals(this.getArray(b), 2) < 0) {
                    System.out.format(" - %.2f%c ", Matrix.round_x_decimals(this.getArray(b), 2) * (-1),
                            (char) (b + 65));
                    printed = true;
                }
            }
            if (!printed) {
                System.out.format(" %c", (char) (a + 65));
            }

        } else if (angka == 0 && !this.get_isi_noVar()) {
            boolean first = true;
            for (int b = 0; b < get_col - 1; b++) {
                if (Matrix.round_x_decimals(this.getArray(b), 2) != 0 && first) {
                    System.out.format(" %.2f%c", Matrix.round_x_decimals(this.getArray(b), 2), (char) (b + 65));
                    printed = true;
                    first = false;
                } else if (Matrix.round_x_decimals(this.getArray(b), 2) != 0 && !first
                        && Matrix.round_x_decimals(this.getArray(b), 2) > 0) {
                    System.out.format(" + %.2f%c ", Matrix.round_x_decimals(this.getArray(b), 2), (char) (b + 65));
                    printed = true;
                } else if (Matrix.round_x_decimals(this.getArray(b), 2) != 0 && !first
                        && Matrix.round_x_decimals(this.getArray(b), 2) < 0) {
                    System.out.format(" - %.2f%c ", Matrix.round_x_decimals(this.getArray(b), 2) * (-1),
                            (char) (b + 65));
                    printed = true;
                }
            }
            if (!printed) {
                System.out.format(" %.2f", 0.00);
            }

        }
        System.out.println(" ");
    }

    public static void gauss_result(Matrix m) {
        int i, j;
        boolean gaAdaSolusi;
        gaAdaSolusi = false;
        for (i = 0; i < m.get_row(); i++) {
            if (m.is_baris_i_0(i)) {
                if (m.get_elmt(i, m.get_col() - 1) != 0) {
                    gaAdaSolusi = true;
                    break;
                }
            }
        }

        if (gaAdaSolusi) {
            System.out.println("Tidak ada solusi.");
        } else {
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

                arrayOfSpl[idx].setValue(m.get_elmt(i, m.get_col() - 1));
                arrayOfSpl[idx].set_isi(); // set value ke array
                for (j = idx + 1; j < m.get_col() - 1; j++) { // mulai dari setelah leading 1
                    if (m.get_elmt(i, j) != 0) { // jika tidak sama dengan 0
                        double temp = arrayOfSpl[idx].getValue();
                        double temp2 = arrayOfSpl[j].getValue();
                        double temp3 = m.get_elmt(i, j);
                        arrayOfSpl[idx].setValue(temp - (temp2) * temp3); // kurang value
                        boolean allVar0 = true;
                        for (int a = 0; a < m.get_col() - 1; a++) {
                            if (arrayOfSpl[j].getArray(a) != 0) {
                                allVar0 = false;
                                break;
                            }
                        }
                        if (!allVar0) {

                            for (int a = 0; a < m.get_col() - 1; a++) {
                                // kurang var
                                if (arrayOfSpl[j].getArray(a) != 0) {
                                    double temp5 = arrayOfSpl[idx].getArray(a);
                                    double temp4 = arrayOfSpl[j].getArray(a);
                                    arrayOfSpl[idx].setArray(a, temp5 - temp3 * temp4);
                                }
                            }

                        } else if (allVar0 && arrayOfSpl[idx].getArray(j) != 0) {
                            double temp5 = arrayOfSpl[idx].getArray(j);
                            arrayOfSpl[idx].setArray(j, temp5 - temp3);

                        } else if (allVar0 && arrayOfSpl[idx].getArray(j) == 0 && arrayOfSpl[j].get_isi_noVar()) {
                            arrayOfSpl[idx].setArray(j, -temp3);
                        } else if (allVar0 && arrayOfSpl[idx].getArray(j) == 0 && !arrayOfSpl[j].get_isi_noVar()) {
                            arrayOfSpl[idx].setArray(j, 0);
                        }

                    }
                }
                // for (int a = 0; a < m.get_col() - 1; a++) {
                // System.out.format("%.2f ", arrayOfSpl[a].getValue());
                // for (int b = 0; b < m.get_col() - 1; b++) {
                // System.out.format("%.2f ", arrayOfSpl[a].getArray(b));
                // }
                // System.out.println("");
                // }
                // System.out.println("");
            }
            for (int a = 0; a < m.get_col() - 1; a++) {
                System.out.format("X%d -> ", a + 1);
                arrayOfSpl[a].print_out_solution(m.get_col(), a);
            }

        }

    }

    public static void gauss_jordan_result(Matrix m) {
        int i, j;
        boolean gaAdaSolusi;
        gaAdaSolusi = false;
        for (i = 0; i < m.get_row(); i++) {
            if (m.is_baris_i_0(i)) {
                if (m.get_elmt(i, m.get_col() - 1) != 0) {
                    gaAdaSolusi = true;
                    break;
                }
            }
        }

        if (gaAdaSolusi) {
            System.out.println("Tidak ada solusi.");
        } else {
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

                arrayOfSpl[idx].setValue(m.get_elmt(i, m.get_col() - 1));
                arrayOfSpl[idx].set_isi(); // set value ke array
                for (j = idx + 1; j < m.get_col() - 1; j++) { // mulai dari setelah leading 1
                    if (m.get_elmt(i, j) != 0) { // jika tidak sama dengan 0
                        double temp = arrayOfSpl[idx].getValue();
                        double temp2 = arrayOfSpl[j].getValue();
                        double temp3 = m.get_elmt(i, j);
                        arrayOfSpl[idx].setValue(temp - (temp2) * temp3); // kurang value
                        boolean allVar0 = true;
                        for (int a = 0; a < m.get_col() - 1; a++) {
                            if (arrayOfSpl[j].getArray(a) != 0) {
                                allVar0 = false;
                                break;
                            }
                        }
                        if (!allVar0) {

                            for (int a = 0; a < m.get_col() - 1; a++) {
                                // kurang var
                                if (arrayOfSpl[j].getArray(a) != 0) {
                                    double temp5 = arrayOfSpl[idx].getArray(a);
                                    double temp4 = arrayOfSpl[j].getArray(a);
                                    arrayOfSpl[idx].setArray(a, temp5 - temp3 * temp4);
                                }
                            }

                        } else if (allVar0 && arrayOfSpl[idx].getArray(j) != 0) {
                            double temp5 = arrayOfSpl[idx].getArray(j);
                            arrayOfSpl[idx].setArray(j, temp5 - temp3);

                        } else if (allVar0 && arrayOfSpl[idx].getArray(j) == 0 && arrayOfSpl[j].get_isi_noVar()) {
                            arrayOfSpl[idx].setArray(j, -temp3);
                        } else if (allVar0 && arrayOfSpl[idx].getArray(j) == 0 && !arrayOfSpl[j].get_isi_noVar()) {
                            arrayOfSpl[idx].setArray(j, 0);
                        }

                    }
                }
                // for (int a = 0; a < m.get_col() - 1; a++) {
                // System.out.format("%.2f ", arrayOfSpl[a].getValue());
                // for (int b = 0; b < m.get_col() - 1; b++) {
                // System.out.format("%.2f ", arrayOfSpl[a].getArray(b));
                // }
                // System.out.println("");
                // }
                // System.out.println("");
            }
            for (int a = 0; a < m.get_col() - 1; a++) {
                System.out.format("X%d -> ", a + 1);
                arrayOfSpl[a].print_out_solution(m.get_col(), a);
            }

        }
    }

}