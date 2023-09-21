public class Matrix {
    private int n_row;
    private int n_col;
    private float[][] data;

    public Matrix(int n_row, int n_col) {
        this.n_row= n_row;
        this.n_col = n_col;
        this.data = new float[n_row][n_col];
    }
}
