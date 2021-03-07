package Controller;

import Model.Pieces;
import Model.PiecesType;
import View.GameBoardGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonHandler extends Pieces implements ActionListener {

    public ButtonHandler(GameBoardGUI gb) {
        super(gb);
    }

    /** process the click that received */
    public void processClick(int i, int j) {
        int row = gb.getRow();
        int col = gb.getColumn();

        if (!isValidMove(i,j))
            return;

        setIcon(i,j);

        //set the new position as occupied for black
        if (gb.getMoves()[row][col] == PiecesType.BLACK || gb.getMoves()[row][col] == PiecesType.KING_BLACK)
            gb.getBlackPiece().kill(i,j);

            //set the new position as occupied for white
        else {
            gb.getWhitePiece().kill(i,j);
        }

        //set the old place as free
        gb.setMoves(row, col, null);

        //update the row and the column
        gb.setRow(i);
        gb.setColumn(j);

        //------------- Multi steps checking -------------
        if (Math.abs(i-row) == 2 )
            checkMultiSteps(i,j);

            //whose turn is it (first black then white)
        else gb.setBlack(!gb.getIsBlack());
    }


    @Override
    /** check if the move is valid or not */
    public boolean isValidMove(int i, int j) {

        if (gb.getBlackPiece().isValidMove(i, j))
            return true;
        if (gb.getWhitePiece().isValidMove(i, j))
            return true;
        if (gb.getKingBlack().isValidMove(i,j))
            return true;
        if (gb.getKingWhite().isValidMove(i, j))
            return true;
        return false;
    }

    /** check from which button do we have received the click */
    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (e.getSource() == gb.getButtons()[i][j]) {
                    if (gb.getMoves()[i][j] == null)
                        processClick(i, j);

                    gb.setColumn(j);
                    gb.setRow(i);

                    winner();
                    return;
                }
            }
        }
    }
}