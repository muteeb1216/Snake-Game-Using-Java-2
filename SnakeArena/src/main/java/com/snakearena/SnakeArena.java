
package com.snakearena.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;

public class SnakeArena extends JPanel {

    private static final int CELL_SIZE = 20;
    private static final int COLUMNS = 30;
    private static final int ROWS = 30;

    private Snake snake;
    private Food food;

    private int score = 0;
    private boolean gameOver = false;
    private boolean won = false;

    private final Timer timer;

    public SnakeArena() {
        setPreferredSize(new Dimension(
                COLUMNS * CELL_SIZE,
                ROWS * CELL_SIZE
        ));

        setBackground(Color.BLACK);

        snake = new Snake(COLUMNS / 2, ROWS / 2);
        food = new Food();
        food.spawn(COLUMNS, ROWS, snake.getBody());

        setupControls();

        timer = new Timer(120, e -> gameLoop());
        timer.start();
    }

    private void setupControls() {
        bindKey("UP", "up",
                () -> snake.setDirection(0, -1));

        bindKey("DOWN", "down",
                () -> snake.setDirection(0, 1));

        bindKey("LEFT", "left",
                () -> snake.setDirection(-1, 0));

        bindKey("RIGHT", "right",
                () -> snake.setDirection(1, 0));

        bindKey("R", "restart", this::restartGame);
    }

    private void bindKey(String key, String name,
                         Runnable action) {

        getInputMap(
                JComponent.WHEN_IN_FOCUSED_WINDOW
        ).put(KeyStroke.getKeyStroke(key), name);

        getActionMap().put(name, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                action.run();
            }
        });
    }

    private void gameLoop() {
        if (gameOver) {
            return;
        }

        Point nextHead = snake.getNextHead();

        // Wall collision
        if (nextHead.x < 0 || nextHead.x >= COLUMNS
                || nextHead.y < 0 || nextHead.y >= ROWS) {
            endGame(false);
            return;
        }

        // Check whether food will be eaten
        boolean eating = food.getPosition() != null
                && nextHead.equals(food.getPosition());

        snake.move(eating);

        // Self collision
        if (snake.hitsItself()) {
            endGame(false);
            return;
        }

        if (eating) {
            score += 10;

            food.spawn(
                    COLUMNS, ROWS, snake.getBody()
            );

            if (food.getPosition() == null) {
                endGame(true);
                return;
            }
        }

        repaint();
    }

    private void endGame(boolean victory) {
        gameOver = true;
        won = victory;
        timer.stop();
        repaint();
    }

    private void restartGame() {
        snake.reset(COLUMNS / 2, ROWS / 2);

        score = 0;
        gameOver = false;
        won = false;

        food.spawn(
                COLUMNS, ROWS, snake.getBody()
        );

        timer.start();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw food
        Point foodPosition = food.getPosition();

        if (foodPosition != null) {
            g.setColor(Color.RED);

            g.fillOval(
                    foodPosition.x * CELL_SIZE,
                    foodPosition.y * CELL_SIZE,
                    CELL_SIZE,
                    CELL_SIZE
            );
        }

        // Draw snake
        List<Point> body = snake.getBody();

        for (int i = 0; i < body.size(); i++) {
            Point part = body.get(i);

            g.setColor(i == 0
                    ? Color.GREEN
                    : new Color(0, 170, 0));

            g.fillRoundRect(
                    part.x * CELL_SIZE,
                    part.y * CELL_SIZE,
                    CELL_SIZE - 1,
                    CELL_SIZE - 1,
                    5, 5
            );
        }

        // Display score
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString("Score: " + score, 10, 22);

        // Game-over screen
        if (gameOver) {
            String message = won
                    ? "YOU WIN!"
                    : "GAME OVER";

            g.setFont(new Font("Arial", Font.BOLD, 32));

            int messageWidth =
                    g.getFontMetrics().stringWidth(message);

            g.drawString(
                    message,
                    (getWidth() - messageWidth) / 2,
                    getHeight() / 2
            );

            g.setFont(new Font("Arial", Font.PLAIN, 16));

            String restart = "Press R to Restart";

            int restartWidth =
                    g.getFontMetrics().stringWidth(restart);

            g.drawString(
                    restart,
                    (getWidth() - restartWidth) / 2,
                    getHeight() / 2 + 35
            );
        }
    }
}
