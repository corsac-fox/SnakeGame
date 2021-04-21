package snake;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Field extends Content {

    Game game;
    Snake snake;
    Food apple;

    JTextField name = new JTextField();
    JButton write = new JButton("записать");
    JLabel enter;
    LineBorder border;

    Field(GameWindow owner) {
        super(owner);
        enter = new JLabel("введите ваше имя");
        setComponentSettings(enter, null, font, null, null);
        Dimension fieldSize = new Dimension(600, 600);
        setPreferredSize(fieldSize);
        setBackground(Color.black);

        label.setText("новый рекорд!");
        label.setForeground(Color.WHITE);
        pause.setForeground(Color.WHITE);

        add(label);
        add(pause);

        setComponentSettings(name, buttonSize, font, null, Color.WHITE);
        setButtonSettings(mainMenu);
        setButtonSettings(exit);
        setButtonSettings(write);

        {
            layout.putConstraint(SpringLayout.WEST, mainMenu, 195, SpringLayout.WEST, this);
            layout.putConstraint(SpringLayout.NORTH, mainMenu, 170, SpringLayout.NORTH, this);
            layout.putConstraint(SpringLayout.WEST, exit, 195, SpringLayout.WEST, this);
            layout.putConstraint(SpringLayout.NORTH, exit, 70, SpringLayout.NORTH, mainMenu);

            layout.putConstraint(SpringLayout.WEST, label, 195, SpringLayout.WEST, this);
            layout.putConstraint(SpringLayout.NORTH, label, 70, SpringLayout.NORTH, this);

            layout.putConstraint(SpringLayout.WEST, pause, 265, SpringLayout.WEST, this);
            layout.putConstraint(SpringLayout.NORTH, pause, 240, SpringLayout.NORTH, this);

            layout.putConstraint(SpringLayout.WEST, enter, 193, SpringLayout.WEST, this);
            layout.putConstraint(SpringLayout.NORTH, enter, 140, SpringLayout.NORTH, this);
            layout.putConstraint(SpringLayout.WEST, name, 195, SpringLayout.WEST, this);
            layout.putConstraint(SpringLayout.NORTH, name, 170, SpringLayout.NORTH, this);
            layout.putConstraint(SpringLayout.WEST, write, 195, SpringLayout.WEST, this);
            layout.putConstraint(SpringLayout.NORTH, write, 70, SpringLayout.NORTH, name);
        }

        name.setAlignmentY(CENTER_ALIGNMENT);
        name.setHorizontalAlignment(SwingConstants.CENTER);

        enter.setVisible(false);
        exit.setVisible(false);
        label.setVisible(false);
        mainMenu.setVisible(false);
        name.setVisible(false);
        pause.setVisible(false);
        write.setVisible(false);

        border = (LineBorder) LineBorder.createGrayLineBorder();
        setControlSetting();
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "выход":
                System.exit(0);
            case "главное меню":
                owner.setContentPane(owner.lobby);
                owner.setSize(400, 450);
                mainMenu.setVisible(false);
                exit.setVisible(false);
                owner.hissing.play();
                owner.repaint();
                break;
            case "записать":
                game.updateHighScore(name.getText());
                name.setVisible(false);
                write.setVisible(false);
                label.setVisible(false);
                enter.setVisible(false);
                owner.setSize(400, 450);
                owner.setContentPane(owner.highScore);
                owner.hissing.play();
        }
    }

    void init() {
        snake = new Snake(this);
        apple = new Food(this);
        game = new Game(this, snake);

        setFocusable(true);
        requestFocusInWindow();

        Game.over = false;
        owner.setTitle(String.format("очки: %d    длина хвоста: %d    скорость: %d", 0, snake.tailLength, snake.speed));
        game.start();
    }

    void setControlSetting() {
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode())
                {
                    case 37:
                    case 38:
                    case 39:
                    case 40:
                        if (!game.isPaused) snake.direction = e.getKeyCode();
                        break;
                    case 32:
                        game.isPaused = !game.isPaused;
                        repaint();
                        break;
                }
            }
        });
    }

    void showMenu(Graphics2D g2D) {
        g2D.setColor(backgroundColor);
        g2D.fillRect(180, 155, 230, 165);

        mainMenu.setVisible(true);
        exit.setVisible(true);
    }

    void showNameEdit(Graphics2D g2D)
    {
        g2D.setColor(backgroundColor);
        g2D.fillRect(180, 135, 230, 185);

        label.setVisible(true);
        enter.setVisible(true);
        name.setVisible(true);
        write.setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        super.paintComponent(g2D);
        border.paintBorder(this, g2D, 0,0,600, 600);

        snake.drawSnake(g2D);
        snake.teasingTime++;

        pause.setVisible(game.isPaused);

        if (Game.over)
        {
            if (game.isHighScore(snake.tailLength))
            {
                showNameEdit(g2D);
            }
            else showMenu(g2D);
        }
        else apple.draw(g2D);
    }
}