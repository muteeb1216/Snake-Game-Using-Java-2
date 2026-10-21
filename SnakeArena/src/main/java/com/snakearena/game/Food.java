package com.snakearena.game;

import java.awt.Point;
import java.util.List;
import java.util.Random;

public class Food {

    private final Random random = new Random();
    private Point position;

    public Food() {
        position = new Point(10, 10);
    }

    public Point getPosition() {
        return position == null
                ? null
                : new Point(position);
    }

    public void spawn(int columns, int rows,
                      List<Point> snakeBody) {

        // Board completely filled
        if (snakeBody.size() >= columns * rows) {
            position = null;
            return;
        }

        Point newPosition;

        do {
            int x = random.nextInt(columns);
            int y = random.nextInt(rows);

            newPosition = new Point(x, y);

        } while (snakeBody.contains(newPosition));

        position = newPosition;
    }
}

