import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameBoardGUI extends JFrame implements ActionListener {

    private JButton[][] buttons;
    private int [][] moves;
    private int row = 0, column = 0;
    private ImageIcon black, blackKing;
    private ImageIcon white, whiteKing;
    private boolean isBlack = true;

    public GameBoardGUI() throws HeadlessException {

        moves = new int [8][8];
        //because the icon is to big we have to resize it to fit in the our scale
        black = new ImageIcon("icons/unnamed.png");
        Image image = black.getImage();
        Image newimg = image.getScaledInstance(60, 60,  java.awt.Image.SCALE_SMOOTH);
        black = new ImageIcon(newimg);

        white = new ImageIcon("icons/IMG_1534.png");
        Image image2 = white.getImage();
        Image newimg2 = image2.getScaledInstance(60, 60,  java.awt.Image.SCALE_SMOOTH);
        white = new ImageIcon(newimg2);

        blackKing = new ImageIcon("icons/blackKing.png");
        Image imagekingb = blackKing.getImage();
        Image newimgkingb = imagekingb.getScaledInstance(60, 60,  java.awt.Image.SCALE_SMOOTH);
        blackKing = new ImageIcon(newimgkingb);

        whiteKing = new ImageIcon("icons/whiteKing.png");
        Image image2king = whiteKing.getImage();
        Image newimg2king = image2king.getScaledInstance(60, 60,  java.awt.Image.SCALE_SMOOTH);
        whiteKing = new ImageIcon(newimg2king);


        buttons = new JButton[8][8];

        //initialize the buttons and there colors
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setOpaque(true);
                buttons[i][j].setBorderPainted(false);

                if ((i + j) % 2 == 0)
                    buttons[i][j].setBackground(new Color(33, 35, 35));
                else
                    buttons[i][j].setBackground(new Color(216, 220, 221));


                this.add(buttons[i][j]);
                buttons[i][j].addActionListener(new ButtonHandler(this));
            }
        }

        //put the icons of the checkers each one to there right place
        for (int i = 5; i < 8; i++)
            for (int j = 0; j < 8; j++)
                if ((i+j) % 2 != 0) {
                    buttons[i][j].setIcon(black);
                    moves[i][j] = 1;
                }

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 8; j++)
                if ((i+j) % 2 != 0) {
                    buttons[i][j].setIcon(white);
                    moves[i][j] = 2;
                }


        //default setup for the frame to close when we close the widow, size etc...
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new GridLayout(8,8,0,0));
        this.setSize(650,650);
        this.setLocation(320,30);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }


    public JButton[][] getButtons() {
        return buttons;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public ImageIcon getBlack() {
        return black;
    }

    public ImageIcon getWhite() {
        return white;
    }

    public ImageIcon getBlackKing() {
        return blackKing;
    }

    public ImageIcon getWhiteKing() {
        return whiteKing;
    }

    public int[][] getMoves() {
        return moves;
    }

    public boolean getIsBlack() {
        return isBlack;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setMoves(int i , int j, int va) {
        this.moves[i][j] = va;
    }

    public void setButtons(int i, int j) {
        buttons[i][j].setBorder(BorderFactory.createLineBorder(Color.green));
    }

    public void setBlack(boolean black) {
        isBlack = black;
    }
}
