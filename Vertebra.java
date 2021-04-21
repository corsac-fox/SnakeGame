package snake;

import java.awt.*;

public class Vertebra {
    int x, y;
    Point p;
    String direction;

    Vertebra (int a, int b, String d) {
        x = a;
        y = b;
        p = new Point(x, y);
        direction = d;
    }
}
