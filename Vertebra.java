package snake;

import java.awt.*;

public class Vertebra extends Point{

	int direction;

	Vertebra (int a, int b, int d) {
		this.x = a;
		this.y = b;
		this.direction = d;
	}

	void rotate(int direction)
	{
		this.direction = direction;
	}

	int getDirection()
	{
		return this.direction;
	}
}