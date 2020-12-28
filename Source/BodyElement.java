package SnakeGame;

import java.awt.*;

public class BodyElement {
	int x, y;
	Point p;
	String direction;
	
	BodyElement (int a, int b, String d) {
		x = a;
		y = b;
		p = new Point(x, y);
		direction = d;
	}
}