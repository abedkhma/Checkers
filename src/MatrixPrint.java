public class MatrixPrint {

    PiecesType[][] x ;

    public MatrixPrint(PiecesType [][] m) {
        this.x = m;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print(x[i][j] + ", ");
            }
            System.out.println();
        }
    }
}
