package snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Menu extends JDialog {

    static private boolean newGame;
    static boolean buttonPressed;
    Menu(JFrame owner) {
        super(owner, "Dialog", true);
        setSize(200, 95);
        setLocation(200, 250);
        setUndecorated(true);

        Label question = new Label("НАЧАТЬ ЗАНОВО?");
        Font font = new Font("SansSerif", Font.BOLD, 17);

        question.setFont(font);
        question.setAlignment(1);
        JButton yes = new JButton("ДА");
        JButton no = new JButton("НЕТ");

        no.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                buttonPressed = true;
                setVisible(false);
            }
        });

        yes.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                buttonPressed = true;
                newGame = true;
                setVisible(false);
            }
        });
        font = new Font("SansSerif", Font.BOLD, 15);
        yes.setPreferredSize(new Dimension(75, 40));
        yes.setFont(font);
        yes.setBackground(Color.BLACK);
        yes.setForeground(Color.WHITE);

        no.setPreferredSize(new Dimension(75, 40));
        no.setFont(font);
        no.setForeground(Color.WHITE);
        no.setBackground(Color.BLACK);

        JPanel contents = new JPanel();
        contents.add(question);
        contents.add(yes);
        contents.add(no);
        contents.setBackground(new Color (115, 174, 255));
        setContentPane(contents);
    }

    static boolean getNewGame() {
        return newGame;
    }

    public static void setNewGame(boolean b) {
        if (b == true) newGame = true;
        else newGame = false;
    }
}
