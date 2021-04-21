package snake;

import java.awt.*;
import java.util.Random;

public class Food {

    static private final Color[] COLORS = {Color.GREEN, Color.YELLOW, Color.RED};

    Field owner;
    Snake snake;

    Random random = new Random();
    private final Point location = new Point(30 * random.nextInt(20), 30 * random.nextInt(20));
    private Color color;
    private int type;
    int lifetime;

    Food (Field owner) {
        this.owner = owner;
        snake = owner.snake;
        type = random.nextInt(3);
        color = COLORS[type];
    }

    void draw(Graphics g) {

        if (lifetime == 0) {
            reincarnation();
        }

        g.setColor(color);
        int x = (int) location.getX();
        int y = (int) location.getY();
        g.fillOval(x, y, 30, 30);
        g.setColor(Color.BLACK);
        g.drawLine(x + 10, y + 7, x + 14, y + 11);
        g.drawLine(x + 14, y + 7, x + 10, y + 11);
    }

    void reincarnation()
    {
        type = random.nextInt(3);
        location.move(30 * random.nextInt(20), 30 * random.nextInt(20));
        for (int i = 0; i < snake.body.size(); i++) {
            if (snake.body.get(i).distance(location) <= 30) reincarnation();
        }
        lifetime = 700 - type * 100;
        color = COLORS[type];
        owner.repaint();
    }

    int getType()
    {
        return this.type + 1;
    }

    Point getLocation()
    {
        return location;
    }
}