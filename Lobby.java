package snake;

import javax.swing.*;
import java.util.Arrays;

public class Lobby extends Content {

	Lobby (GameWindow owner) {
		super(owner);
		add(label);
		for (JButton jButton : Arrays.asList(newGame, settings, highScoreTable)) {
			setButtonSettings(jButton);
		}

		{
			layout.putConstraint(SpringLayout.WEST , label, 135,
					SpringLayout.WEST , this);
			layout.putConstraint(SpringLayout.NORTH, label, 50,
					SpringLayout.NORTH, this);
			layout.putConstraint(SpringLayout.WEST , newGame, 100,
					SpringLayout.WEST , this);
			layout.putConstraint(SpringLayout.NORTH, newGame, 65,
					SpringLayout.NORTH, label);
			layout.putConstraint(SpringLayout.WEST, settings, 100,
					SpringLayout.WEST, this);
			layout.putConstraint(SpringLayout.NORTH, settings, 70,
					SpringLayout.NORTH, newGame);
			layout.putConstraint(SpringLayout.WEST, highScoreTable, 100,
					SpringLayout.WEST, this);
			layout.putConstraint(SpringLayout.NORTH, highScoreTable, 70,
					SpringLayout.NORTH, settings);
		}
		setVisible(true);
	}


}