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
        if (gb.getMoves()[row][col] == 1 && i == 0){
            gb.getButtons()[i][j].setIcon(gb.getBlackKing());
            gb.setMoves(row, col, -1);
        }else if (gb.getMoves()[row][col] == 2 && i == 7){
            gb.getButtons()[i][j].setIcon(gb.getWhiteKing());
            gb.setMoves(row, col, -2);
        }

        //set the new position as occupied for black
        if (gb.getMoves()[row][col] == 1 || gb.getMoves()[row][col] == -1) {

            //which black we play, normal or king
            if (gb.getMoves()[row][col] == -1)
                gb.getButtons()[i][j].setIcon(gb.getBlackKing());
            else
                gb.getButtons()[i][j].setIcon(gb.getBlack());

            //if it kill as black
            if (gb.getMoves()[row][col] == 1) {
                if (row - 2 == i && col - 2 == j) {
                    gb.getButtons()[row - 1][col - 1].setIcon(null);
                    gb.setMoves(row - 1, col - 1, 0);

                } else if (row - 2 == i && col + 2 == j) {
                    gb.getButtons()[row - 1][col + 1].setIcon(null);
                    gb.setMoves(row - 1, col + 1, 0);
                }
            }
            // because both killing in the same way
            else if (gb.getMoves()[row][col] == -1) {
                kingKill(row,col,i,j);
            }

            // remove the icons
            gb.getButtons()[row][col].setIcon(null);

            if (gb.getMoves()[row][col] == 1)
                gb.setMoves(i, j, 1);
            else  gb.setMoves(i, j, -1);
        }

        //set the new position as occupied for white
        else {
            //which black we play, normal or king
            if (gb.getMoves()[row][col] == -2)
                gb.getButtons()[i][j].setIcon(gb.getWhiteKing());
            else
                gb.getButtons()[i][j].setIcon(gb.getWhite());

            //if it kill as white
            if (gb.getMoves()[row][col] == 2) {
                if (row + 2 == i && col - 2 == j) {
                    gb.getButtons()[row + 1][col - 1].setIcon(null);
                    gb.setMoves(row + 1, col - 1, 0);

                } else if (row + 2 == i && col + 2 == j) {
                    gb.getButtons()[row + 1][col + 1].setIcon(null);
                    gb.setMoves(row + 1, col + 1, 0);
                }
                // because both killing in the same way
            }else if (gb.getMoves()[row][col] == - 2) {
              kingKill(row,col,i,j);
            }

            // remove the icons
            gb.getButtons()[row][col].setIcon(null);

            //update the move matrix
            if (gb.getMoves()[row][col] == 2)
                gb.setMoves(i, j, 2);
            else
                gb.setMoves(i, j, -2);
        }

        //set the old place as free
        gb.setMoves(row, col, 0);

        //update the row and the column
        gb.setRow(i);
        gb.setColumn(j);

        //-------------------- start two steps checking ---------------------
        if (Math.abs(i-row) == 2 ) {

            //checking for black
            if (gb.getMoves()[i][j] == 1 && i > 1 ) {
                if (j < 2){
                    if (!isValidMove(i - 2, j + 2))
                        gb.setBlack(!gb.getIsBlack());
                }else if (j > 6){
                    if (!isValidMove(i - 2, j - 2))
                        gb.setBlack(!gb.getIsBlack());
                }else if (!isValidMove(i - 2, j - 2) && !isValidMove(i - 2, j + 2))
                    gb.setBlack(!gb.getIsBlack());

               //checking fot white
            } else if (gb.getMoves()[i][j] == 2 && i < 6) {
                if (j < 2 ){
                    if (!isValidMove(i + 2, j + 2))
                        gb.setBlack(!gb.getIsBlack());
                }else if (j > 6){
                    if (!isValidMove(i + 2, j - 2))
                        gb.setBlack(!gb.getIsBlack());
                }else
                    if (!isValidMove(i + 2, j - 2) && !isValidMove(i + 2, j + 2))
                        gb.setBlack(!gb.getIsBlack());

                //checking fot king
            } else if ((gb.getMoves()[i][j] == -1 || gb.getMoves()[i][j] == -2) && i < 6 && i > 1) {
                if ( j < 2) {
                    if (!isValidMove(i - 2, j + 2) && !isValidMove(i + 2, j + 2))
                        gb.setBlack(!gb.getIsBlack());
                }
                else if (j > 6) {
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
            gb.setMoves(row - 1, col - 1, 0);

        }else if (row + 2 == i && col - 2 == j){
            gb.getButtons()[row + 1][col - 1].setIcon(null);
            gb.setMoves(row + 1, col - 1, 0);
        }
        else if (row + 2 == i && col + 2 == j) {
            gb.getButtons()[row + 1][col + 1].setIcon(null);
            gb.setMoves(row + 1, col + 1, 0);
        }else if (row - 2 == i && col + 2 == j){
            gb.getButtons()[row - 1][col + 1].setIcon(null);
            gb.setMoves(row - 1, col + 1, 0);
        }
    }


    /** check if the move is valid or not */
    private boolean isValidMove(int i, int j) {
        int r = gb.getRow();
        int c = gb.getColumn();

        if (!gb.getIsBlack() && gb.getMoves()[r][c] == 1 || !gb.getIsBlack() && gb.getMoves()[r][c] == -1
                || gb.getIsBlack() && gb.getMoves()[r][c] == 2 || gb.getIsBlack() && gb.getMoves()[r][c] == -2)
            return false;

        // --------------------- check move for normal piece --------------------------------

        // check move for black
        if (gb.getMoves()[r][c] == 1 && gb.getMoves()[i][j] == 0) {
            if (r - i == 1 && Math.abs(j - c) == 1)
                return true;
            if (r - 2 == i && c - 2 == j)
                if (gb.getMoves()[r - 1][c - 1] == 2 || gb.getMoves()[r - 1][c - 1] == -2)
                    return true;
            if (r - 2 == i && c + 2 == j)
                if(gb.getMoves()[r - 1][c + 1] == 2 || gb.getMoves()[r - 1][c + 1] == -2)
                    return true;

            //check move for white
        } else if (gb.getMoves()[r][c] == 2 && gb.getMoves()[i][j] == 0) {
            if (i - r == 1 && Math.abs(j - c) == 1)
                return true;
            if (r + 2 == i && c - 2 == j)
                if(gb.getMoves()[r + 1][c - 1] == 1 || gb.getMoves()[r + 1][c - 1] == -1)
                    return true;
            if (r + 2 == i && c + 2 == j)
                if (gb.getMoves()[r + 1][c + 1] == 1 || gb.getMoves()[r + 1][c + 1] == -1)
                    return true;

        // --------------------- check move for king piece --------------------------------

            // check move for kingBlack
        } else if (gb.getMoves()[r][c] == -1 && gb.getMoves()[i][j] == 0) {
            //checking for normal move
            if (Math.abs(r - i) == 1 && Math.abs(j - c) == 1)
                return true;
            //checking for kill move
            if (r - 2 == i && c - 2 == j){
                if (gb.getMoves()[r - 1][c - 1] == 2 || gb.getMoves()[r - 1][c - 1] == -2)
                    return true;
            }else if (r - 2 == i && c + 2 == j){
                if (gb.getMoves()[r - 1][c + 1] == 2 || gb.getMoves()[r - 1][c + 1] == -2)
                    return true;
            }else if (r + 2 == i && c - 2 == j){
               if (gb.getMoves()[r + 1][c - 1] == 2 || gb.getMoves()[r + 1][c - 1] == -2)
                   return true;
            }else if (r + 2 == i && c + 2 == j){
                if (gb.getMoves()[r + 1][c + 1] == 2 || gb.getMoves()[r + 1][c + 1] == -2)
                    return true;
            }

            // check move for whiteKing
        } else if (gb.getMoves()[r][c] == -2 && gb.getMoves()[i][j] == 0) {

            //checking for normal move
            if (Math.abs(i - r) == 1 && Math.abs(j - c) == 1)
                return true;
            //checking for kill move
            if (r - 2 == i && c - 2 == j){
                if (gb.getMoves()[r - 1][c - 1] == 1 || gb.getMoves()[r - 1][c - 1] == -1)
                    return true;
            }else if (r - 2 == i && c + 2 == j){
                if (gb.getMoves()[r - 1][c + 1] == 1 || gb.getMoves()[r - 1][c + 1] == -1)
                    return true;
            }else if (r + 2 == i && c - 2 == j){
                if (gb.getMoves()[r + 1][c - 1] == 1 || gb.getMoves()[r + 1][c - 1] == -1)
                    return true;
            }else if (r + 2 == i && c + 2 == j){
                if (gb.getMoves()[r + 1][c + 1] == 1 || gb.getMoves()[r + 1][c + 1] == -1)
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
                if(gb.getMoves()[i][j] == 1 || gb.getMoves()[i][j] == -1)
                    black ++;
                else if(gb.getMoves()[i][j] == 2 || gb.getMoves()[i][j] == -2)
                    white++;
        if (white == 0){
            answer= JOptionPane.showOptionDialog(null,
                    "Black is the winner\n Would you play again?", "Winner",
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
                    "White is the winner\n Would you play again?", "Winner",
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
                    if (gb.getMoves()[i][j] == 0)
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