package snake;

import java.awt.*;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class Snake {

    final int LEFT = 37;
    final int UP = 38;
    final int RIGHT = 39;
    final int DOWN = 40;

    static boolean isUniform;

    Field owner;
    int direction;
    int speed = 1;
    int teasingTime = 15;
    boolean isDead = false;

    private final Color paleGreen = new Color (129, 159, 0);
    private Color bodyColor = paleGreen;
    private final static InputStream CRUNCH = ClassLoader.getSystemResourceAsStream("snake/items/crunch.wav");
    static InputStream bufferedIn = new BufferedInputStream(CRUNCH);
    static SnakeSound eating = new SnakeSound(bufferedIn);

    ArrayList <Vertebra> body = new ArrayList<>();
    int tailLength = 2;
    int headSize = 30;

    int START_X = 60;
    int START_Y = 30;

    int x = 60;
    int y = 30;

    Snake(Field owner)
    {
        super();
        this.owner = owner;
        this.direction = RIGHT;

        body.add(new Vertebra(START_X, START_Y, RIGHT));
        body.add(new Vertebra(START_X, START_Y, RIGHT));
    }

    void correctDirectionAndStep(int direction)
    {
        int step = 30;
        if (Math.abs(direction - body.get(tailLength - 1).direction) == 2)
        {
            this.direction = body.get(tailLength - 1).direction;
            teasingTime = 0;
        }
        if (this.direction == body.get(tailLength - 1).direction) step = 1;

        switch (this.direction)
        {
            case UP:
                y -= step;
                x = step == 30 ? body.get(tailLength - 1).x : x;
                break;
            case DOWN:
                y += step;
                x = step == 30 ? body.get(tailLength - 1).x : x;
                break;
            case RIGHT:
                x += step;
                y = step == 30 ? body.get(tailLength - 1).y : y;
                break;
            case LEFT:
                x -= step;
                y = step == 30 ? body.get(tailLength - 1).y : y;
                break;
            case 32:
                //змея останавливается
        }
    }

    boolean crossBorder () {
        return (y > owner.getHeight() - headSize || y < 0 || x > owner.getWidth() - headSize || x <  0);
    }

    boolean crossTail() {
        for (int i = 0; i < tailLength - 1; i++) {
            if (Point.distance(x, y, body.get(i).x, body.get(i).y) < 30) {
                return true;
            }
        }
        return false;
    }

    private void drawHead(Graphics2D g2D) {
        if (isDead)
        {
            x = body.get(tailLength - 1).x;
            y = body.get(tailLength - 1).y;

            g2D.setColor(bodyColor);
            g2D.fillRect(x, y, 30, 30);
            g2D.setColor(Color.BLACK);
            g2D.drawRect(x + 5, y + 5, 20, 20);
            g2D.drawLine(x + 10, y + 11, x + 14, y + 15);
            g2D.drawLine(x + 16, y + 11, x + 20, y + 15);
            g2D.drawLine(x + 14, y + 11, x + 10, y + 15);
            g2D.drawLine(x + 20, y + 11, x + 16, y + 15);
        }
        else {
            g2D.setColor(bodyColor);
            g2D.fillRect(x, y, 30, 30);
            g2D.setColor(Color.BLACK);
            g2D.drawRect(x + 5, y + 5, 20, 20);
            g2D.drawRect(x + 10, y + 11, 4, 4);
            g2D.drawRect(x + 16, y + 11, 4, 4);
        }
        g2D.setColor(bodyColor);
    }

    void drawSnake(Graphics2D g2D) {
        g2D.setColor(bodyColor);
        drawTail(g2D);
        for (int i = 1; i < tailLength; i++) {

            int vertebraX = body.get(i).x;
            int vertebraY = body.get(i).y;
            g2D.fillRect(vertebraX, vertebraY, 30, 30);
            g2D.setColor(Color.BLACK);
            g2D.drawRect(vertebraX + 5, vertebraY + 5, 20, 20);
            g2D.drawRect(vertebraX + 10, vertebraY + 10, 10, 10);
            g2D.drawRect(vertebraX + 13, vertebraY + 13, 4, 4);
            g2D.setColor(bodyColor);
        }
        drawHead(g2D);
        if (teasingTime < 15) showTongue(g2D);
    }

    private void drawTail (Graphics g) {
        int n = 3;
        int direction = body.get(1).direction;

        int tailX = body.get(1).x;
        int tailY = body.get(1).y;

        switch (direction) {
            case (DOWN):
                int[] x = {tailX, tailX + 30, tailX + 15};
                int[] y = {tailY, tailY, tailY - 40};
                g.fillPolygon(new Polygon (x, y, n));
                g.setColor(Color.BLACK);
                g.drawLine(x[0] + 1, y[0] - 10, x[1] - 1, y[1] - 10);
                g.drawLine(x[0] + 1, y[0] - 20, x[1] - 1, y[1] - 20);
                break;
            case (LEFT):
                x = new int[] {tailX + 30, tailX + 30, tailX + 70};
                y = new int[] {tailY, tailY + 30, tailY + 30};
                g.fillPolygon(new Polygon (x, y, n));
                g.setColor(Color.BLACK);
                g.drawLine(x[0] + 10, y[0] + 1, x[1] + 10, y[1] - 1);
                g.drawLine(x[0] + 20, y[0] + 1, x[1] + 20, y[1] - 1);
                break;
            case (UP):
                x = new int[] {tailX + 30, tailX, tailX + 15};
                y = new int[] {tailY + 30, tailY + 30, tailY + 70};
                g.fillPolygon(new Polygon (x, y, n));
                g.setColor(Color.BLACK);
                g.drawLine(x[0] - 1, y[0] + 10, x[1] + 1, y[1] + 10);
                g.drawLine(x[0] - 1, y[0] + 20, x[1] + 1, y[1] + 20);
                break;
            case (RIGHT):
                x = new int[] {tailX, tailX, tailX - 40};
                y = new int[] {tailY + 30, tailY, tailY + 30};
                g.fillPolygon(new Polygon (x, y, n));
                g.setColor(Color.BLACK);
                g.drawLine(x[0] - 10, y[0] - 1, x[1] - 10, y[1] + 1);
                g.drawLine(x[0] - 20, y[0] - 1, x[1] - 20, y[1] + 1);
                break;
        }
        g.setColor(bodyColor);
    }

    void grow()
    {
        x = owner.apple.getLocation().x;
        y = owner.apple.getLocation().y;
        body.add(new Vertebra(x, y, this.direction));
        tailLength++;
    }

    void increaseSpeed() {
        speed++;
    }

    boolean isEating(Food food)
    {
        return food.getLocation().distance(x, y) <= 29;
    }

    void move ()
    {
        correctDirectionAndStep(direction);
        if ((int)(body.get(tailLength - 1).distance(x, y)) == 30) {

            if (crossBorder() || crossTail()) {
                Game.over = true;
                isDead = true;
                bodyColor = Color.red;
                owner.repaint();

            } else {
                for (int i = 0; i < body.size(); i++)
                {
                    if (i == body.size() - 1)
                    {
                        body.get(i).move(x, y);
                        body.get(i).rotate(direction);
                    } else
                    {
                        body.get(i).move(body.get(i + 1).x, body.get(i + 1).y);
                        body.get(i).rotate(body.get(i + 1).getDirection());
                    }
                }
            }
        }
        if (isEating(owner.apple))
        {
            eating.play();
            grow();
            owner.apple.reincarnation();
            owner.game.scoring();
        }
    }

    private void showTongue(Graphics g) {

        g.setColor(Color.RED);
        if (direction == RIGHT) {
            g.drawLine(x + 25, y + 20, x + 30, y + 20);
            g.drawLine(x + 30, y + 20, x + 35, y + 18);
            g.drawLine(x + 30, y + 20, x + 35, y + 22);
        }
        if (direction == LEFT) {
            g.drawLine(x + 5, y + 20, x, y + 20);
            g.drawLine(x, y + 20, x - 5, y + 18);
            g.drawLine(x, y + 20, x - 5, y + 22);
        }
        if (direction == UP) {
            g.drawLine(x + 15, y + 6, x + 15, y);
            g.drawLine(x + 15, y, x + 13, y - 5);
            g.drawLine(x + 15, y, x + 17, y - 5);
        }
        if (direction == DOWN) {
            g.drawLine(x + 15, y + 24, x + 15, y + 30);
            g.drawLine(x + 15, y + 30, x + 13, y + 35);
            g.drawLine(x + 15, y + 30, x + 17, y + 35);
        }
        g.setColor(bodyColor);
    }
}