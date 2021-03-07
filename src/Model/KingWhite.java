package Model;

import View.GameBoardGUI;

public class KingWhite extends Pieces {


    public KingWhite(GameBoardGUI gb) {
        super(gb);
    }

    @Override
    public boolean isValidMove(int i, int j) {
        int r = gb.getRow();
        int c = gb.getColumn();

        if (!isRightPiece(r,c))
            return false;

        if (gb.getMoves()[r][c] == PiecesType.KING_WHITE && gb.getMoves()[i][j] == null) {

            //checking for normal move
            if (Math.abs(i - r) == 1 && Math.abs(j - c) == 1)
                return true;
            //checking for kill move
            if (r - 2 == i && c - 2 == j){
                return gb.getMoves()[r - 1][c - 1] == PiecesType.BLACK || gb.getMoves()[r - 1][c - 1] == PiecesType.KING_BLACK;
            }else if (r - 2 == i && c + 2 == j){
                return gb.getMoves()[r - 1][c + 1] == PiecesType.BLACK || gb.getMoves()[r - 1][c + 1] == PiecesType.KING_BLACK;
            }else if (r + 2 == i && c - 2 == j){
                return gb.getMoves()[r + 1][c - 1] == PiecesType.BLACK || gb.getMoves()[r + 1][c - 1] == PiecesType.KING_BLACK;
            }else if (r + 2 == i && c + 2 == j){
                return gb.getMoves()[r + 1][c + 1] == PiecesType.BLACK || gb.getMoves()[r + 1][c + 1] == PiecesType.KING_BLACK;
            }
        }

        return false;
    }
}
