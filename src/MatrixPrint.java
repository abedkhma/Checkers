public class MatrixPrint {

    int[][] x ;

    public MatrixPrint(int[][] m) {
        this.x = m;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print(x[i][j] + ", ");
            }
            System.out.println();
        }
    }
}
