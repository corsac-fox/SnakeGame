package snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;

public abstract class Content extends JPanel implements ActionListener {
    Dimension buttonSize = new Dimension(200, 65);
    static Color buttonColor = new Color(214, 250, 63);
    static Color backgroundColor = new Color(224, 250, 113);
    Font font = new Font(Font.DIALOG_INPUT, Font.BOLD, 22);
    JLabel label;
    JLabel pause;
    InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream("snake/items/HoneyScript.otf");

    static JButton newGame = new JButton("новая игра");
    JButton settings = new JButton("настройки");
    JButton highScoreTable = new JButton("рекорды");
    JButton mainMenu = new JButton("главное меню");
    JButton exit = new JButton("выход");
    GameWindow owner;
    SpringLayout layout = new SpringLayout();

    Content (GameWindow owner)
    {
        try
        {
            Font cursive = Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont(60f);
            label = new JLabel("Змейка");
            pause = new JLabel("пауза");

            label.setFont(cursive);
            pause.setFont(cursive);

        } catch (Exception e) {
            e.printStackTrace();
        }

        this.owner = owner;
        setPreferredSize(new Dimension(400, 450));
        setLayout(layout);
        setBackground(backgroundColor);
    }

    void setButtonSettings(AbstractButton b)
    {
        b.setActionCommand(b.getText());
        b.addActionListener(this);
        b.setBackground(buttonColor);
        b.setPreferredSize(buttonSize);
        b.setFont(font);
        add(b);
    }

    void setComponentSettings(JComponent c, Dimension size, Font font, Color foreground, Color background)
    {
        c.setFont(font);
        c.setBackground(background);
        c.setForeground(foreground);
        c.setPreferredSize(size);
        add(c);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand())
        {
            case "выход":
                System.exit(0);

            case "настройки":
                newGame.setVisible(false);
                owner.setContentPane(owner.settingsPage);
                break;

            case "новая игра":
                owner.setContentPane(owner.field);
                owner.field.init();
                owner.hissing.play();
                owner.pack();
                break;

            case "рекорды":
                owner.setContentPane(owner.highScore);
                break;
        }
    }
}