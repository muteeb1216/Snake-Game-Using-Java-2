
package com.snakearena.game;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Snake {

    private final List<Point> body = new ArrayList<>();

    private int directionX = 1;
    private int directionY = 0;

    public Snake(int startX, int startY) {
        body.add(new Point(startX, startY));
        body.add(new Point(startX - 1, startY));
        body.add(new Point(startX - 2, startY));
    }

    public void move(boolean grow) {
        Point head = body.get(0);

        Point newHead = new Point(
                head.x + directionX,
                head.y + directionY
        );

        body.add(0, newHead);

        if (!grow) {
            body.remove(body.size() - 1);
        }
    }

    public void setDirection(int dx, int dy) {
        // Prevent direct reverse movement
        if (dx == -directionX && dy == -directionY) {
            return;
        }

        directionX = dx;
        directionY = dy;
    }

    public List<Point> getBody() {
        List<Point> copy = new ArrayList<>();

        for (Point point : body) {
            copy.add(new Point(point));
        }

        return copy;
    }

    public Point getHead() {
        return new Point(body.get(0));
    }

    public Point getNextHead() {
        Point head = body.get(0);

        return new Point(
                head.x + directionX,
                head.y + directionY
        );
    }

    public boolean hitsItself() {
        Point head = body.get(0);

        for (int i = 1; i < body.size(); i++) {
            if (head.equals(body.get(i))) {
                return true;
            }
        }

        return false;
    }

    public void reset(int startX, int startY) {
        body.clear();

        body.add(new Point(startX, startY));
        body.add(new Point(startX - 1, startY));
        body.add(new Point(startX - 2, startY));

        directionX = 1;
        directionY = 0;
    }
}
