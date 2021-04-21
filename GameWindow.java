package snake;

import javax.swing.*;
import java.io.BufferedInputStream;
import java.io.InputStream;

public class GameWindow extends JFrame {

    Lobby lobby = new Lobby(this);
    Field field = new Field(this);

    InputStream entryHiss = ClassLoader.getSystemResourceAsStream("snake/items/snakeHissing.wav");

    InputStream bufferedIn = new BufferedInputStream(entryHiss);
    SnakeSound hissing = new SnakeSound(bufferedIn);

    HighScorePage highScore = new HighScorePage(this);
    SettingsPage settingsPage = new SettingsPage(this);

    GameWindow() {
        super();
        hissing.play();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(400, 450);

        setLocation(430, 80);
        setResizable(false);
        setContentPane(lobby);
        pack();

        setVisible(true);
    }
}