package snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class SettingsPage extends Content {

    JCheckBox turnOff;
    JCheckBox speedRise;
    JSlider sound;
    JLabel sliderName;
    JButton resetTable;

    Font reducedFont = new Font(Font.DIALOG_INPUT, Font.BOLD, 18);

    SettingsPage(GameWindow owner) {
        super(owner);

        buttonSize.setSize(260, 50);

        turnOff = new JCheckBox(" отключить звуки", false);
        speedRise = new JCheckBox(" повышать скорость", true);
        sound = new JSlider(SwingConstants.HORIZONTAL, 0, 10, 10);
        sliderName = new JLabel("громкость звука");
        resetTable = new JButton("сброс рекордов");

        setButtonSettings(turnOff);
        setButtonSettings(speedRise);
        setComponentSettings(sliderName, null, reducedFont, null, null);
        setComponentSettings(sound, buttonSize, null, backgroundColor, buttonColor);
        setButtonSettings(resetTable);
        setButtonSettings(mainMenu);

        sound.addChangeListener(e -> {
            owner.hissing.setVolume(((JSlider) e.getSource()).getValue());
            Snake.eating.setVolume(((JSlider) e.getSource()).getValue());
            owner.hissing.play();
        });

        {
            layout.putConstraint(SpringLayout.WEST , turnOff, 70,
                    SpringLayout.WEST , this);
            layout.putConstraint(SpringLayout.NORTH, turnOff, 50,
                    SpringLayout.NORTH, this);
            layout.putConstraint(SpringLayout.WEST , speedRise, 70,
                    SpringLayout.WEST , this);
            layout.putConstraint(SpringLayout.NORTH, speedRise, 55,
                    SpringLayout.NORTH, turnOff);
            layout.putConstraint(SpringLayout.WEST , sliderName, 120,
                    SpringLayout.WEST , this);
            layout.putConstraint(SpringLayout.NORTH, sliderName, 50,
                    SpringLayout.NORTH, speedRise);
            layout.putConstraint(SpringLayout.WEST , sound, 70,
                    SpringLayout.WEST , this);
            layout.putConstraint(SpringLayout.NORTH, sound, 80,
                    SpringLayout.NORTH, speedRise);
            layout.putConstraint(SpringLayout.WEST , resetTable, 70,
                    SpringLayout.WEST , this);
            layout.putConstraint(SpringLayout.NORTH, resetTable, 70,
                    SpringLayout.NORTH, sound);
            layout.putConstraint(SpringLayout.WEST, mainMenu, 70,
                    SpringLayout.WEST, this);
            layout.putConstraint(SpringLayout.NORTH, mainMenu, 55,
                    SpringLayout.NORTH, resetTable);
        }

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        switch (e.getActionCommand())
        {
            case "главное меню":
                owner.setContentPane(owner.lobby);
                newGame.setVisible(true);
                owner.repaint();
                break;

            case " повышать скорость":
                Snake.isUniform = !speedRise.isSelected();
                break;

            case " отключить звуки":
                if (turnOff.isSelected())
                {
                    SnakeSound.isMute = true;
                    owner.hissing.stop();
                }
                else
                {
                    SnakeSound.isMute = false;
                    owner.hissing.play();
                }
                break;
            case "сброс рекордов":
                owner.highScore.highScore.clear();
                owner.highScore.clearTable();
                owner.highScore.writeData();
                break;
        }
    }
}