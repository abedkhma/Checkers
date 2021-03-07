package Model;

import View.GameBoardGUI;

public class BlackPiece extends Pieces {

    public BlackPiece(GameBoardGUI gb) {
        super(gb);
    }

    @Override
    public boolean isValidMove(int i, int j) {
        int r = gb.getRow();
        int c = gb.getColumn();

        if (!isRightPiece(r,c))
            return false;

        // check move for black
        if (gb.getMoves()[r][c] == PiecesType.BLACK && gb.getMoves()[i][j] == null) {
            if (r - i == 1 && Math.abs(j - c) == 1)
                return true;
            if (r - 2 == i && c - 2 == j)
                return gb.getMoves()[r - 1][c - 1] == PiecesType.WHITE || gb.getMoves()[r - 1][c - 1] == PiecesType.KING_WHITE;
            if (r - 2 == i && c + 2 == j)
                return gb.getMoves()[r - 1][c + 1] == PiecesType.WHITE || gb.getMoves()[r - 1][c + 1] == PiecesType.KING_WHITE;
        }

        return false;
    }

    public void kill(int i, int j){
        int row = gb.getRow();
        int col = gb.getColumn();
        //which black we play, normal or king
        if (gb.getMoves()[row][col] == PiecesType.KING_BLACK)
            gb.getButtons()[i][j].setIcon(gb.getBlackKing());
        else
            gb.getButtons()[i][j].setIcon(gb.getBlack());

        //if it kill as black
        if (gb.getMoves()[row][col] == PiecesType.BLACK) {
            if (row - 2 == i && col - 2 == j) {
                gb.getButtons()[row - 1][col - 1].setIcon(null);
                gb.setMoves(row - 1, col - 1, null);

            } else if (row - 2 == i && col + 2 == j) {
                gb.getButtons()[row - 1][col + 1].setIcon(null);
                gb.setMoves(row - 1, col + 1, null);
            }
        }
        // because both killing in the same way
        else if (gb.getMoves()[row][col] == PiecesType.KING_BLACK) {
            kingKill(row,col,i,j);
        }
        // remove the icons
        gb.getButtons()[row][col].setIcon(null);

        if (gb.getMoves()[row][col] == PiecesType.BLACK)
            gb.setMoves(i, j, PiecesType.BLACK);
        else  gb.setMoves(i, j, PiecesType.KING_BLACK);
    }
}
