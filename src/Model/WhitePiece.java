package Model;

import View.GameBoardGUI;

public class WhitePiece extends Pieces {

    public WhitePiece(GameBoardGUI gb) {
        super(gb);
    }

    @Override
    public boolean isValidMove(int i, int j) {
        int r = gb.getRow();
        int c = gb.getColumn();

        if (!isRightPiece(r,c))
            return false;

        if (gb.getMoves()[r][c] == PiecesType.WHITE && gb.getMoves()[i][j] == null) {
            if (i - r == 1 && Math.abs(j - c) == 1)
                return true;
            if (r + 2 == i && c - 2 == j)
                return gb.getMoves()[r + 1][c - 1] == PiecesType.BLACK || gb.getMoves()[r + 1][c - 1] == PiecesType.KING_BLACK;
            if (r + 2 == i && c + 2 == j)
                return gb.getMoves()[r + 1][c + 1] == PiecesType.BLACK || gb.getMoves()[r + 1][c + 1] == PiecesType.KING_BLACK;
        }
        return false;
    }

    public void kill(int i, int j){
        int row = gb.getRow();
        int col = gb.getColumn();

        //which black we play, normal or king
        if (gb.getMoves()[row][col] == PiecesType.KING_WHITE)
            gb.getButtons()[i][j].setIcon(gb.getWhiteKing());
        else
            gb.getButtons()[i][j].setIcon(gb.getWhite());

        //if it kill as white
        if (gb.getMoves()[row][col] == PiecesType.WHITE) {
            if (row + 2 == i && col - 2 == j) {
                gb.getButtons()[row + 1][col - 1].setIcon(null);
                gb.setMoves(row + 1, col - 1, null);

            } else if (row + 2 == i && col + 2 == j) {
                gb.getButtons()[row + 1][col + 1].setIcon(null);
                gb.setMoves(row + 1, col + 1, null);
            }
            // because both killing in the same way
        }else if (gb.getMoves()[row][col] == PiecesType.KING_WHITE) {
            kingKill(row,col,i,j);
        }

        // remove the icons
        gb.getButtons()[row][col].setIcon(null);

        //update the move matrix
        if (gb.getMoves()[row][col] == PiecesType.WHITE)
            gb.setMoves(i, j, PiecesType.WHITE);
        else
            gb.setMoves(i, j, PiecesType.KING_WHITE);
    }
}
