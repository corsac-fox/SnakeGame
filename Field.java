package snake;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.Vector;

class GameField extends JPanel {

    static int fieldWidth = 607, fieldHeight = 600;
    static Color[] tool = {Color.GREEN, Color.YELLOW, Color.RED};

    Random random = new Random();
    double foodExist;
    int x = 30, y = 30;
    int foodX = 30*random.nextInt(20), foodY = 30*random.nextInt(20);
    int score, foodCount;
    boolean isCount, isFood;
    Color foodColor;
    Color bodyColor = new Color (115, 174, 255);
    int foodType = random.nextInt(3) + 1;
    int bodyCount = 1;
    Vector <Vertebra> body = new Vector <Vertebra>();
    static int tongueExist = 15;

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (body.size() == 0) {

            body.add(new Vertebra(x, y, Snake.direction));
            x = 60;
            body.add(new Vertebra(x, y, Snake.direction));
        }

        if (Snake.GameOver) {
            bodyColor = Color.RED;
            drawBody(g);
            drawDeadHead(g);

        } else {

            g.setColor(bodyColor);

            switch (Snake.direction) {
                case ("left"):

                    if (body.get(bodyCount).direction.equals("left")) {
                        x = --x;
                    } else if (body.get(bodyCount).direction.equals("right")){
                        Snake.direction = "right";
                        tongueExist = 0;
                    } else {
                        x = x - 30;
                        y = (int)body.get(bodyCount).y;
                    }
                    break;
                case ("up"):

                    if (body.get(bodyCount).direction.equals("up")) {
                        y = --y;
                    } else if  (body.get(bodyCount).direction.equals("down")){
                        Snake.direction = "down";
                        tongueExist = 0;
                    } else {
                        y = y - 30;
                        x = (int)body.get(bodyCount).x;
                    }
                    break;
                case ("right"):

                    if (body.get(bodyCount).direction.equals("right")) {
                        x = ++x;
                    } else if (body.get(bodyCount).direction.equals("left")){
                        Snake.direction = "left";
                        tongueExist = 0;
                    } else {
                        x = x + 30;
                        y = (int)body.get(bodyCount).y;
                    }
                    break;
                case ("down"):

                    if (body.get(bodyCount).direction.equals("down")) {
                        y = ++y;
                    } else if (body.get(bodyCount).direction.equals("up")){
                        Snake.direction = "up";
                        tongueExist = 0;
                    } else {
                        y = y + 30;
                        x = (int)body.get(bodyCount).x;
                    }
                    break;

            }

            if(body.get(bodyCount).p.distance(x, y) > 29) {
                if ((touchBorder()&&(crossBorder()))|| crossTail()) {
                    Snake.GameOver = true;
                    repaint();
                } else {
                    body.add(new Vertebra(x, y, Snake.direction));
                    body.remove(0);
                }
            }

            if (!isCount) {
                scoreCount();
            }

            drawBody(g);
            drawHead(g);

            if (tongueExist < 15) {
                drawTongue(g);
                tongueExist++;
            }

            drawFood(g);

        }
    }

    private boolean crossBorder () {
        if (y > fieldHeight - 60 || y < 0 || x > fieldWidth - 60 || x <  0)
            return true;
        else return false;
    }

    private boolean touchBorder () {
        if (y >= fieldHeight - 30|| y <= 0 || x >= fieldWidth - 30 || x <=  0)
            return true;
        else return false;
    }

    private void drawFood(Graphics g) {
        if (foodColor == null) {
            foodColor = tool[foodType - 1];
        }

        if (foodExist > 3000 || isCount) {
            foodType = random.nextInt(3) + 1;
            foodPlace();
            isCount = false;
            foodColor = null;
            isFood = false;
        }

        g.setColor(foodColor);
        g.fillOval(foodX, foodY, 30, 30);
        g.setColor(Color.BLACK);
        g.drawLine(foodX + 10, foodY + 7, foodX + 14, foodY + 11);
        g.drawLine(foodX + 14, foodY + 7, foodX + 10, foodY + 11);
        foodExist += (2 * foodType) * 10.0 / Snake.getSpeed();
    }

    private void drawDeadHead(Graphics g) {
        x = body.get(bodyCount).x;
        y = body.get(bodyCount).y;
        g.setColor(Color.RED);
        g.fillRect(x, y, 30, 30);
        g.setColor(Color.BLACK);
        g.drawRect(x + 5, y + 5, 20, 20);
        g.drawLine(x + 10, y + 11, x + 14, y + 15);
        g.drawLine(x + 16, y + 11, x + 20, y + 15);
        g.drawLine(x + 14, y + 11, x + 10, y + 15);
        g.drawLine(x + 20, y + 11, x + 16, y + 15);
    }

    private void drawHead(Graphics g) {
        g.setColor(bodyColor);
        g.fillRect(x, y, 30, 30);
        g.setColor(Color.BLACK);
        g.drawRect(x + 5, y + 5, 20, 20);
        g.drawRect(x + 10, y + 11, 4, 4);
        g.drawRect(x + 16, y + 11, 4, 4);

    }

    private void drawTongue(Graphics g) {
        g.setColor(Color.RED);
        if (Snake.direction.equals("right")) {
            g.drawLine(x + 25, y + 20, x + 30, y + 20);
            g.drawLine(x + 30, y + 20, x + 35, y + 18);
            g.drawLine(x + 30, y + 20, x + 35, y + 22);
        }
        if (Snake.direction.equals("left")) {
            g.drawLine(x + 5, y + 20, x, y + 20);
            g.drawLine(x, y + 20, x - 5, y + 18);
            g.drawLine(x, y + 20, x - 5, y + 22);
        }
        if (Snake.direction.equals("up")) {
            g.drawLine(x + 15, y + 6, x + 15, y);
            g.drawLine(x + 15, y, x + 13, y - 5);
            g.drawLine(x + 15, y, x + 17, y - 5);
        }
        if (Snake.direction.equals("down")) {
            g.drawLine(x + 15, y + 24, x + 15, y + 30);
            g.drawLine(x + 15, y + 30, x + 13, y + 35);
            g.drawLine(x + 15, y + 30, x + 17, y + 35);
        }
        g.setColor(bodyColor);
    }

    private void drawBody(Graphics g) {
        g.setColor(bodyColor);
        for (int i = 0; i < body.size(); i++) { //

            if (i == 0 ) {
                drawTail(g);

            } else {
                g.fillRect(body.get(i).x, body.get(i).y, 30, 30);
                g.setColor(Color.BLACK);
                g.drawRect(body.get(i).x + 5, body.get(i).y + 5, 20, 20);
                g.drawRect(body.get(i).x + 10, body.get(i).y + 10, 10, 10);
                g.drawRect(body.get(i).x + 13, body.get(i).y + 13, 4, 4);
                g.setColor(bodyColor);
            }
        }
    }

    public void foodPlace() {
        foodExist = 0;
        foodX = 30*random.nextInt(19);
        foodY = 30*random.nextInt(18);
        for (int i = 0; i < body.size(); i++) {
            if (Point.distance(body.get(i).x, body.get(i).y, foodX, foodY) < 30) {
                foodPlace();
            }
        }
    }

    public int scoreCount() {
        if (Point.distance(x, y, foodX, foodY) < 30) {

            score += foodType * 10;
            isFood = true;
            foodCount++;
            body.add(new Vertebra(foodX, foodY, Snake.direction));
            x = foodX;
            y = foodY;
            isCount = true;
            bodyCount++;
            repaint();
        }
        return score;
    }

    public boolean crossTail() {
        for (int i = 0; i < bodyCount; i++) {
            if (Point.distance(x, y, body.get(i).x, body.get(i).y) < 30) {
                return true;
            }
        }
        return false;
    }

    private void drawTail (Graphics g) {
        int n = 3;
        String direction = body.get(1).direction;

        switch (direction) {
            case ("down"):
                int[] x = {body.get(1).x, body.get(1).x + 30, body.get(1).x + 15};
                int[] y = {body.get(1).y, body.get(1).y, body.get(1).y - 40};
                g.fillPolygon(new Polygon (x, y, n));
                g.setColor(Color.BLACK);
                g.drawLine(x[0], y[0] - 10, x[1], y[1] - 10);
                g.drawLine(x[0], y[0] - 20, x[1], y[1] - 20);
                break;
            case ("left"):
                x = new int[] {body.get(1).x + 30, body.get(1).x + 30, body.get(1).x + 70};
                y = new int[] {body.get(1).y, body.get(1).y + 30, body.get(1).y + 30};
                g.fillPolygon(new Polygon (x, y, n));
                g.setColor(Color.BLACK);
                g.drawLine(x[0] + 10, y[0], x[1] + 10, y[1]);
                g.drawLine(x[0] + 20, y[0], x[1] + 20, y[1]);
                break;
            case ("up"):
                x = new int[] {body.get(1).x + 30, body.get(1).x, body.get(1).x + 15};
                y = new int[]{body.get(1).y + 30, body.get(1).y + 30, body.get(1).y + 70};
                g.fillPolygon(new Polygon (x, y, n));
                g.setColor(Color.BLACK);
                g.drawLine(x[0], y[0] + 10, x[1], y[1] + 10);
                g.drawLine(x[0], y[0] + 20, x[1], y[1] + 20);
                break;
            case ("right"):
                x = new int[] {body.get(1).x, body.get(1).x, body.get(1).x - 40};
                y = new int[]{body.get(1).y + 30, body.get(1).y, body.get(1).y + 30};
                g.fillPolygon(new Polygon (x, y, n));
                g.setColor(Color.BLACK);
                g.drawLine(x[0] - 10, y[0], x[1] - 10, y[1]);
                g.drawLine(x[0] - 20, y[0], x[1] - 20, y[1]);
                break;
        }
        g.setColor(bodyColor);
    }

    GameField() {
        setSize(fieldWidth, fieldHeight);
        setBackground(Color.black);
        setVisible(true);
    }
}