package Model;

import Controller.ButtonHandler;
import View.GameBoardGUI;

import javax.swing.*;
import java.awt.*;

public abstract class  Pieces {

    protected GameBoardGUI gb;

    public Pieces (GameBoardGUI gb){
        this.gb = gb;
    }

    public abstract boolean isValidMove(int i, int j);


    protected boolean isRightPiece(int r, int c){
        if (!gb.getIsBlack() && gb.getMoves()[r][c] == PiecesType.BLACK || !gb.getIsBlack() && gb.getMoves()[r][c] == PiecesType.KING_BLACK
                || gb.getIsBlack() && gb.getMoves()[r][c] == PiecesType.WHITE || gb.getIsBlack() && gb.getMoves()[r][c] == PiecesType.KING_WHITE)
            return false;
        return true;
    }

    public void setIcon(int i, int j) {

        if (gb.getMoves()[gb.getRow()][gb.getColumn()] == PiecesType.BLACK && i == 0){
            gb.getButtons()[i][j].setIcon(gb.getBlackKing());
            gb.setMoves(gb.getRow(), gb.getColumn(), PiecesType.KING_BLACK);

        }else if (gb.getMoves()[gb.getRow()][gb.getColumn()] == PiecesType.WHITE && i == 7){
            gb.getButtons()[i][j].setIcon(gb.getWhiteKing());
            gb.setMoves(gb.getRow(), gb.getColumn(), PiecesType.KING_WHITE);
        }
    }

    /** for the king to kill the enemy */
    protected void kingKill(int row, int col, int i, int j){

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

    protected void checkMultiSteps (int i, int j){
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
    }

    /** checking of there any winner in a move */
    protected void winner (){
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
}
