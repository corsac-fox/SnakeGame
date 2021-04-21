package snake;

import javax.swing.*;
import java.awt.event.*;

public class Snake extends JFrame {

    static GameField gamefield = new GameField();
    static boolean GameOver;
    public static int speed = 10;
    final int LEFT = 37;
    final int UP = 38;
    final int RIGHT = 39;
    final int DOWN = 40;

    static String direction = "right";
    Menu choice = new Menu(this);

    public static void main(String[] args) {
        new Snake().go();
    }

    public Snake() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(607, 600);

        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (!GameOver) {
                    if (e.getKeyCode() == DOWN) {
                        direction = "down";
                    }
                    if (e.getKeyCode() == UP) {
                        direction = "up";
                    }
                    if (e.getKeyCode() == LEFT) {
                        direction = "left";
                    }
                    if (e.getKeyCode() == RIGHT) {
                        direction = "right";
                    }
                }
                gamefield.repaint();
            }
        });

        add(gamefield);
        setResizable(false);
        setVisible(true);

    }

    public void go() {
        do {
            try {
                Thread.sleep(100 / speed);

            } catch (Exception e) { e.printStackTrace(); }

            setTitle("Змейка   Счёт: " + gamefield.score + "   Длина хвоста: " + gamefield.body.size());
            setSpeed(gamefield.score, speed);

            if(GameOver) {

                direction = "right";
                choice.setVisible(true);

                if (!choice.getNewGame() && choice.buttonPressed) {
                    dispose();
                    System.exit(0);
                }

                if (choice.getNewGame() && choice.buttonPressed) {
                    remove(gamefield);
                    GameOver = false;
                    gamefield = new GameField();
                    add(gamefield);

                    speed = 10;
                    choice.buttonPressed = false;
                    choice.setNewGame(false);
                }
            }

            gamefield.repaint();

        } while (true);
    }

    private void setSpeed(int score, int sp) {
        if (score > 99) {
            speed = 10 + (int)(score / 100);
        }
    }

    public static int getSpeed() {
        return speed;
    }
}