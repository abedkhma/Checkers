package Model;

import View.GameBoardGUI;

public class KingBlack extends Pieces {


    public KingBlack(GameBoardGUI gb) {
        super(gb);
    }

    @Override
    public boolean isValidMove(int i, int j) {
        int r = gb.getRow();
        int c = gb.getColumn();

        if (!isRightPiece(r,c))
            return false;

        if (gb.getMoves()[r][c] == PiecesType.KING_BLACK && gb.getMoves()[i][j] == null) {
            //checking for normal move
            if (Math.abs(r - i) == 1 && Math.abs(j - c) == 1)
                return true;
            //checking for kill move
            if (r - 2 == i && c - 2 == j){
                return gb.getMoves()[r - 1][c - 1] == PiecesType.WHITE || gb.getMoves()[r - 1][c - 1] == PiecesType.KING_WHITE;
            }else if (r - 2 == i && c + 2 == j){
                return gb.getMoves()[r - 1][c + 1] == PiecesType.WHITE || gb.getMoves()[r - 1][c + 1] == PiecesType.KING_WHITE;
            }else if (r + 2 == i && c - 2 == j){
                return gb.getMoves()[r + 1][c - 1] == PiecesType.WHITE || gb.getMoves()[r + 1][c - 1] == PiecesType.KING_WHITE;
            }else if (r + 2 == i && c + 2 == j){
                return gb.getMoves()[r + 1][c + 1] == PiecesType.WHITE || gb.getMoves()[r + 1][c + 1] == PiecesType.KING_WHITE;
            }
        }
        return false;
    }
}
