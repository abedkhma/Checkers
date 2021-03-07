package View;

import View.GameBoardGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class StartFrame extends JFrame implements ActionListener {

    class OpenUrlAction implements ActionListener {
        @Override public void actionPerformed(ActionEvent e) {
            open(uri);
        }
    }

    private static void open(URI uri) {
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(uri);
            } catch (IOException ee) {
                System.out.println(ee.getMessage() + " open Methon");
            }
        }
    }

    JButton button, uriBurron;
    JLabel text;
    final URI uri = new URI("https://www.ducksters.com/games/checkers_rules.php");

    public StartFrame() throws URISyntaxException {


        text = new JLabel();
        text.setText("<html> <br/> To get familiar with the game rules please press \"HELP\" <br/><br/>" +
                "  <br/>" +
                "  To start the game press \"Start\"" +
                "</html");
        text.setOpaque(true);
        text.setFont(new Font("MV Boli", Font.PLAIN,15));

        button = new JButton("Start");
        button.setBounds(250,130,150,50);
        button.addActionListener(this);


        uriBurron = new JButton();
        uriBurron.addActionListener(new OpenUrlAction());
        uriBurron.setText("<HTML> <FONT color=\"#000099\"><U>HELP</U></FONT>"
                + " </HTML>");
        uriBurron.setBounds(50,130,150,50);

        this.add(button);
        this.add(uriBurron);

        this.setTitle("Checkers");
        this.add(text);
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(450,230);
        this.setLocation(520,150);
        this.setLayout(null);
        this.setResizable(false);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button) {
            this.dispose(); // after opining the new window the frame will be closed
            new GameBoardGUI();
        }
    }
}
