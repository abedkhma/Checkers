import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonHandler implements ActionListener {

    GameBoardGUI gb;

    public ButtonHandler(GameBoardGUI gb) {
        this.gb = gb;
    }


    /** process the click that received */
    public void processClick(int i, int j) {
        int row = gb.getRow();
        int col = gb.getColumn();

        if (!isValidMove(i, j))
            return;

        // set the king piece if the enemy reach the end
        if (gb.getMoves()[row][col] == PiecesType.BLACK && i == 0){
            gb.getButtons()[i][j].setIcon(gb.getBlackKing());
            gb.setMoves(row, col, PiecesType.KING_BLACK);

        }else if (gb.getMoves()[row][col] == PiecesType.WHITE && i == 7){
            gb.getButtons()[i][j].setIcon(gb.getWhiteKing());
            gb.setMoves(row, col, PiecesType.KING_WHITE);
        }

        //set the new position as occupied for black
        if (gb.getMoves()[row][col] == PiecesType.BLACK || gb.getMoves()[row][col] == PiecesType.KING_BLACK) {

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

        //set the new position as occupied for white
        else {
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

        //set the old place as free
        gb.setMoves(row, col, null);

        //update the row and the column
        gb.setRow(i);
        gb.setColumn(j);

        //-------------------- start two steps checking ---------------------
        if (Math.abs(i-row) == 2 ) {

            //checking for black
            if (gb.getMoves()[i][j] == PiecesType.BLACK && i > 1 ) {
                if (j < 2){
                    if (!isValidMove(i - 2, j + 2))
                        gb.setBlack(!gb.getIsBlack());
                }else if (j > 5){
                    if (!isValidMove(i - 2, j - 2))
                        gb.setBlack(!gb.getIsBlack());
                }else if (!isValidMove(i - 2, j - 2) && !isValidMove(i - 2, j + 2))
                    gb.setBlack(!gb.getIsBlack());

               //checking fot white
            } else if (gb.getMoves()[i][j] == PiecesType.WHITE && i < 6) {
                if (j < 2 ){
                    if (!isValidMove(i + 2, j + 2))
                        gb.setBlack(!gb.getIsBlack());
                }else if (j > 5){
                    if (!isValidMove(i + 2, j - 2))
                        gb.setBlack(!gb.getIsBlack());
                }else
                    if (!isValidMove(i + 2, j - 2) && !isValidMove(i + 2, j + 2))
                        gb.setBlack(!gb.getIsBlack());

                //checking fot king
            } else if ((gb.getMoves()[i][j] == PiecesType.KING_BLACK || gb.getMoves()[i][j] == PiecesType.KING_WHITE) && i < 6 && i > 1) {
                if ( j < 2) {
                    if (!isValidMove(i - 2, j + 2) && !isValidMove(i + 2, j + 2))
                        gb.setBlack(!gb.getIsBlack());
                }
                else if (j > 5) {
                    if (!isValidMove(i - 2, j - 2) && !isValidMove(i + 2, j - 2))
                        gb.setBlack(!gb.getIsBlack());
                }else
                    if (!isValidMove(i - 2, j - 2) && !isValidMove(i + 2, j - 2) &&
                            !isValidMove(i - 2, j + 2) && !isValidMove(i + 2, j + 2))
                        gb.setBlack(!gb.getIsBlack());


                // if the piece kills another piece but dose not have another move
            }else gb.setBlack(!gb.getIsBlack());
            //------------------- end start two steps checking ---------------------

            //whose turn is it (first blach then white)
        }else gb.setBlack(!gb.getIsBlack());

    }

    /** for the king to kill the enemy */
    private void kingKill(int row, int col, int i, int j){

        if (row - 2 == i && col - 2 == j) {
            gb.getButtons()[row - 1][col - 1].setIcon(null);
            gb.setMoves(row - 1, col - 1, null);

        }else if (row + 2 == i && col - 2 == j){
            gb.getButtons()[row + 1][col - 1].setIcon(null);
            gb.setMoves(row + 1, col - 1, null);
        }
        else if (row + 2 == i && col + 2 == j) {
            gb.getButtons()[row + 1][col + 1].setIcon(null);
            gb.setMoves(row + 1, col + 1, null);
        }else if (row - 2 == i && col + 2 == j){
            gb.getButtons()[row - 1][col + 1].setIcon(null);
            gb.setMoves(row - 1, col + 1, null);
        }
    }


    /** check if the move is valid or not */
    private boolean isValidMove(int i, int j) {
        int r = gb.getRow();
        int c = gb.getColumn();


        if (!gb.getIsBlack() && gb.getMoves()[r][c] == PiecesType.BLACK || !gb.getIsBlack() && gb.getMoves()[r][c] == PiecesType.KING_BLACK
                || gb.getIsBlack() && gb.getMoves()[r][c] == PiecesType.WHITE || gb.getIsBlack() && gb.getMoves()[r][c] == PiecesType.KING_WHITE)
            return false;

        // --------------------- check move for normal piece --------------------------------

        // check move for black
        if (gb.getMoves()[r][c] == PiecesType.BLACK && gb.getMoves()[i][j] == null) {
            if (r - i == 1 && Math.abs(j - c) == 1)
                return true;
            if (r - 2 == i && c - 2 == j)
                if (gb.getMoves()[r - 1][c - 1] == PiecesType.WHITE || gb.getMoves()[r - 1][c - 1] == PiecesType.KING_WHITE)
                    return true;
            if (r - 2 == i && c + 2 == j)
                if(gb.getMoves()[r - 1][c + 1] == PiecesType.WHITE || gb.getMoves()[r - 1][c + 1] == PiecesType.KING_WHITE)
                    return true;

            //check move for white
        } else if (gb.getMoves()[r][c] == PiecesType.WHITE && gb.getMoves()[i][j] == null) {
            if (i - r == 1 && Math.abs(j - c) == 1)
                return true;
            if (r + 2 == i && c - 2 == j)
                if(gb.getMoves()[r + 1][c - 1] == PiecesType.BLACK || gb.getMoves()[r + 1][c - 1] == PiecesType.KING_BLACK)
                    return true;
            if (r + 2 == i && c + 2 == j)
                if (gb.getMoves()[r + 1][c + 1] == PiecesType.BLACK || gb.getMoves()[r + 1][c + 1] == PiecesType.KING_BLACK)
                    return true;

        // --------------------- check move for king piece --------------------------------

            // check move for kingBlack
        } else if (gb.getMoves()[r][c] == PiecesType.KING_BLACK && gb.getMoves()[i][j] == null) {
            //checking for normal move
            if (Math.abs(r - i) == 1 && Math.abs(j - c) == 1)
                return true;
            //checking for kill move
            if (r - 2 == i && c - 2 == j){
                if (gb.getMoves()[r - 1][c - 1] == PiecesType.WHITE || gb.getMoves()[r - 1][c - 1] == PiecesType.KING_WHITE)
                    return true;
            }else if (r - 2 == i && c + 2 == j){
                if (gb.getMoves()[r - 1][c + 1] == PiecesType.WHITE || gb.getMoves()[r - 1][c + 1] == PiecesType.KING_WHITE)
                    return true;
            }else if (r + 2 == i && c - 2 == j){
               if (gb.getMoves()[r + 1][c - 1] == PiecesType.WHITE || gb.getMoves()[r + 1][c - 1] == PiecesType.KING_WHITE)
                   return true;
            }else if (r + 2 == i && c + 2 == j){
                if (gb.getMoves()[r + 1][c + 1] == PiecesType.WHITE || gb.getMoves()[r + 1][c + 1] == PiecesType.KING_WHITE)
                    return true;
            }

            // check move for whiteKing
        } else if (gb.getMoves()[r][c] == PiecesType.KING_WHITE && gb.getMoves()[i][j] == null) {

            //checking for normal move
            if (Math.abs(i - r) == 1 && Math.abs(j - c) == 1)
                return true;
            //checking for kill move
            if (r - 2 == i && c - 2 == j){
                if (gb.getMoves()[r - 1][c - 1] == PiecesType.BLACK || gb.getMoves()[r - 1][c - 1] == PiecesType.KING_BLACK)
                    return true;
            }else if (r - 2 == i && c + 2 == j){
                if (gb.getMoves()[r - 1][c + 1] == PiecesType.BLACK || gb.getMoves()[r - 1][c + 1] == PiecesType.KING_BLACK)
                    return true;
            }else if (r + 2 == i && c - 2 == j){
                if (gb.getMoves()[r + 1][c - 1] == PiecesType.BLACK || gb.getMoves()[r + 1][c - 1] == PiecesType.KING_BLACK)
                    return true;
            }else if (r + 2 == i && c + 2 == j){
                if (gb.getMoves()[r + 1][c + 1] == PiecesType.BLACK || gb.getMoves()[r + 1][c + 1] == PiecesType.KING_BLACK)
                    return true;
            }
        }
        return false;
    }

    /** checking of there any winner in a move */
    private void winner (){
        int black = 0, white = 0 , answer;
        ImageIcon ic ;
        String[] strings = {"YES","NO"};

        ic = new ImageIcon("icons/winner.png");
        Image image = ic.getImage();
        Image newimg = image.getScaledInstance(40, 40,  java.awt.Image.SCALE_SMOOTH);
        ic = new ImageIcon(newimg);

        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                if(gb.getMoves()[i][j] == PiecesType.BLACK || gb.getMoves()[i][j] == PiecesType.KING_BLACK)
                    black ++;
                else if(gb.getMoves()[i][j] == PiecesType.WHITE || gb.getMoves()[i][j] == PiecesType.KING_WHITE)
                    white++;
        if (white == 0){
            answer= JOptionPane.showOptionDialog(null,
                    "Congratulations, The winner is \"Black\"\n Would you like to play again?", "Winner",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    ic,strings,0);
            if (answer == JOptionPane.NO_OPTION)
                System.exit(0);
            else {
                new ButtonHandler(new GameBoardGUI());
            }

        }else  if (black == 0) {
             answer = JOptionPane.showOptionDialog(null,
                    "Congratulations, The winner is \"White\"\n Would you like to play again?", "Winner",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    ic, strings, 0);
            if (answer == JOptionPane.NO_OPTION)
                System.exit(0);
            else {
                new ButtonHandler(new GameBoardGUI());
            }
        }

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