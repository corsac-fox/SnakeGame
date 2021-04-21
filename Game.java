package snake;

public class Game extends Thread {

	static boolean over;
	Snake player;
	Field owner;
	Food food;

	int score;
	HighScorePage highScore;

	int delay = 19;
	boolean isPaused;

	Game(Field owner, Snake player)
	{
		this.player = player;
		this.owner = owner;
		food = owner.apple;
		highScore = owner.owner.highScore;
	}

	boolean isHighScore(int key)
	{
		return (highScore.highScore.size() < 1 || key > highScore.highScore.firstKey());
	}

	@Override
	public void run() {
		do {
			if (!isPaused)
			{
				player.move();
				owner.repaint();
				food.lifetime--;
			}
			try {
				sleep(delay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		while (!over);
		owner.owner.setTitle("");
	}

	void scoring() {
		int temp = score;
		score += food.getType() * 10;

		if (temp / 150 < score / 150 && !Snake.isUniform && delay > 7) {
			player.increaseSpeed();
			delay -= 2;
		}
		owner.owner.setTitle(String.format("очки: %d    длина хвоста: %d    скорость: %d", score, player.tailLength,
				player.speed));
	}

	void updateHighScore(String name)
	{
		if (name.equals("")) name = "змея";
		if (highScore.highScore.containsKey((double) player.tailLength))
			highScore.highScore.put(player.tailLength - 0.1, name);
		else highScore.highScore.put((double) player.tailLength, name);

		highScore.table.setEnabled(true);
		highScore.fillTable();
		highScore.table.setEnabled(false);
		highScore.writeData();
	}
}