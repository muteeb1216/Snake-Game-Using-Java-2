
package com.snakearena.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 650;
    private static final int UNIT = 20;

    private static final int BOARD_X = 24;
    private static final int BOARD_Y = 100;
    private static final int BOARD_W = 752;
    private static final int BOARD_H = 510;

    private static final int COLS = BOARD_W / UNIT;
    private static final int ROWS = BOARD_H / UNIT;
    private static final int MAX = COLS * ROWS;

    private final int[] snakeX = new int[MAX];
    private final int[] snakeY = new int[MAX];

    private int bodyParts = 5;
    private int foodX, foodY;
    private int score = 0;
    private int bestScore = 0;

    private char direction = 'R';
    private boolean running = false;
    private boolean gameOver = false;

    private final Random random = new Random();
    private Timer timer;

    private final Color background = new Color(10, 15, 25);
    private final Color panelColor = new Color(18, 27, 42);
    private final Color neon = new Color(57, 255, 136);
    private final Color muted = new Color(145, 163, 181);

    public GamePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(background);
        setFocusable(true);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                    case KeyEvent.VK_W:
                        if (direction != 'D') direction = 'U';
                        break;

                    case KeyEvent.VK_DOWN:
                    case KeyEvent.VK_S:
                        if (direction != 'U') direction = 'D';
                        break;

                    case KeyEvent.VK_LEFT:
                    case KeyEvent.VK_A:
                        if (direction != 'R') direction = 'L';
                        break;

                    case KeyEvent.VK_RIGHT:
                    case KeyEvent.VK_D:
                        if (direction != 'L') direction = 'R';
                        break;

                    case KeyEvent.VK_R:
                        if (gameOver) restartGame();
                        break;

                    case KeyEvent.VK_SPACE:
                        if (gameOver) restartGame();
                        break;
                }
            }
        });

        startGame();
    }

    private void startGame() {
        bodyParts = 5;
        score = 0;
        direction = 'R';
        gameOver = false;

        for (int i = 0; i < bodyParts; i++) {
            snakeX[i] = (COLS / 2 - i) * UNIT;
            snakeY[i] = (ROWS / 2) * UNIT;
        }

        spawnFood();
        running = true;

        if (timer != null) {
            timer.stop();
        }

        timer = new Timer(100, this);
        timer.start();

        repaint();
        requestFocusInWindow();
    }

    private void spawnFood() {
        boolean occupied;

        do {
            foodX = random.nextInt(COLS) * UNIT;
            foodY = random.nextInt(ROWS) * UNIT;

            occupied = false;

            for (int i = 0; i < bodyParts; i++) {
                if (snakeX[i] == foodX && snakeY[i] == foodY) {
                    occupied = true;
                    break;
                }
            }
        } while (occupied);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON
        );

        drawHeader(g2);
        drawBoard(g2);

        if (running || gameOver) {
            drawFood(g2);
            drawSnake(g2);
        }

        if (gameOver) {
            drawGameOver(g2);
        }

        g2.dispose();
    }

    private void drawHeader(Graphics2D g) {
        g.setColor(neon);
        g.setFont(new Font("SansSerif", Font.BOLD, 27));
        g.drawString("SNAKE", 26, 40);

        g.setColor(Color.WHITE);
        g.drawString("ARENA", 119, 40);

        g.setColor(muted);
        g.setFont(new Font("SansSerif", Font.PLAIN, 12));
        g.drawString("ARCADE EDITION", 28, 59);

        drawCard(g, 500, 15, 125, 60);
        drawCard(g, 638, 15, 138, 60);

        g.setFont(new Font("SansSerif", Font.BOLD, 10));
        g.setColor(muted);
        g.drawString("CURRENT SCORE", 514, 34);
        g.drawString("BEST THIS SESSION", 650, 34);

        g.setFont(new Font("SansSerif", Font.BOLD, 22));
        g.setColor(neon);
        g.drawString(String.valueOf(score), 514, 59);

        g.setColor(Color.WHITE);
        g.drawString(String.valueOf(bestScore), 650, 59);
    }

    private void drawCard(Graphics2D g, int x, int y, int w, int h) {
        g.setColor(panelColor);
        g.fill(new RoundRectangle2D.Double(x, y, w, h, 15, 15));

        g.setColor(new Color(49, 67, 83));
        g.draw(new RoundRectangle2D.Double(x, y, w, h, 15, 15));
    }

    private void drawBoard(Graphics2D g) {
        g.setColor(new Color(13, 21, 33));
        g.fillRoundRect(
            BOARD_X, BOARD_Y, BOARD_W, BOARD_H, 20, 20
        );

        Shape oldClip = g.getClip();

        g.setClip(
            BOARD_X, BOARD_Y, BOARD_W, BOARD_H
        );

        g.setColor(new Color(29, 43, 56));

        for (int x = 0; x <= BOARD_W; x += UNIT) {
            g.drawLine(
                BOARD_X + x, BOARD_Y,
                BOARD_X + x, BOARD_Y + BOARD_H
            );
        }

        for (int y = 0; y <= BOARD_H; y += UNIT) {
            g.drawLine(
                BOARD_X, BOARD_Y + y,
                BOARD_X + BOARD_W, BOARD_Y + y
            );
        }

        g.setClip(oldClip);

        g.setColor(new Color(47, 70, 83));
        g.drawRoundRect(
            BOARD_X, BOARD_Y, BOARD_W, BOARD_H, 20, 20
        );

        g.setFont(new Font("SansSerif", Font.PLAIN, 12));
        g.setColor(muted);
        g.drawString(
            "MOVE  \u2190  \u2191  \u2192  \u2193",
            BOARD_X + 8, 90
        );
    }

    private void drawFood(Graphics2D g) {
        int x = BOARD_X + foodX;
        int y = BOARD_Y + foodY;

        g.setColor(new Color(255, 70, 95, 55));
        g.fillOval(x - 5, y - 5, UNIT + 10, UNIT + 10);

        g.setColor(new Color(255, 76, 100));
        g.fillOval(x + 3, y + 3, UNIT - 6, UNIT - 6);
    }

    private void drawSnake(Graphics2D g) {
        for (int i = bodyParts - 1; i >= 0; i--) {
            int x = BOARD_X + snakeX[i];
            int y = BOARD_Y + snakeY[i];

            if (i == 0) {
                g.setColor(neon);
                g.fillRoundRect(x, y, UNIT, UNIT, 9, 9);

                g.setColor(new Color(10, 50, 32));

                if (direction == 'R' || direction == 'L') {
                    int eyeX = direction == 'R' ? x + 13 : x + 4;
                    g.fillOval(eyeX, y + 5, 3, 3);
                    g.fillOval(eyeX, y + 12, 3, 3);
                } else {
                    int eyeY = direction == 'D' ? y + 13 : y + 4;
                    g.fillOval(x + 5, eyeY, 3, 3);
                    g.fillOval(x + 12, eyeY, 3, 3);
                }
            } else {
                int shade = Math.max(90, 220 - i * 2);
                g.setColor(new Color(0, shade, 100));
                g.fillRoundRect(x + 1, y + 1, UNIT - 2,
                                UNIT - 2, 7, 7);
            }
        }
    }

    private void drawGameOver(Graphics2D g) {
        g.setColor(new Color(0, 0, 0, 175));
        g.fillRoundRect(
            BOARD_X, BOARD_Y, BOARD_W, BOARD_H, 20, 20
        );

        int centerX = BOARD_X + BOARD_W / 2;
        int centerY = BOARD_Y + BOARD_H / 2;

        int cardW = 360;
        int cardH = 255;
        int cardX = centerX - cardW / 2;
        int cardY = centerY - cardH / 2;

        g.setColor(panelColor);
        g.fillRoundRect(cardX, cardY, cardW, cardH, 28, 28);

        g.setColor(new Color(59, 91, 103));
        g.drawRoundRect(cardX, cardY, cardW, cardH, 28, 28);

        g.setFont(new Font("SansSerif", Font.BOLD, 34));
        g.setColor(new Color(255, 90, 110));
        centerText(g, "GAME OVER", centerX, cardY + 55);

        g.setFont(new Font("SansSerif", Font.PLAIN, 14));
        g.setColor(muted);
        centerText(g, "NICE TRY! YOUR RUN HAS ENDED",
                   centerX, cardY + 84);

        g.setColor(neon);
        g.setFont(new Font("SansSerif", Font.BOLD, 13));
        centerText(g, "FINAL SCORE", centerX, cardY + 120);

        g.setFont(new Font("SansSerif", Font.BOLD, 43));
        g.setColor(Color.WHITE);
        centerText(g, String.valueOf(score), centerX, cardY + 169);

        g.setColor(muted);
        g.setFont(new Font("SansSerif", Font.PLAIN, 13));
        centerText(g, "Press R or SPACE to play again",
                   centerX, cardY + 211);
    }

    private void centerText(Graphics2D g, String text,
                            int centerX, int baselineY) {
        FontMetrics fm = g.getFontMetrics();
        g.drawString(text, centerX - fm.stringWidth(text) / 2,
                     baselineY);
    }

    private void move() {
        for (int i = bodyParts - 1; i > 0; i--) {
            snakeX[i] = snakeX[i - 1];
            snakeY[i] = snakeY[i - 1];
        }

        switch (direction) {
            case 'U': snakeY[0] -= UNIT; break;
            case 'D': snakeY[0] += UNIT; break;
            case 'L': snakeX[0] -= UNIT; break;
            case 'R': snakeX[0] += UNIT; break;
        }
    }

    private void checkFood() {
        if (snakeX[0] == foodX && snakeY[0] == foodY) {
            if (bodyParts < MAX) {
                bodyParts++;
            }

            score += 10;
            bestScore = Math.max(bestScore, score);

            if (bodyParts < MAX) {
                spawnFood();
            } else {
                endGame();
            }
        }
    }

    private void checkCollisions() {
        if (snakeX[0] < 0 || snakeX[0] >= BOARD_W
                || snakeY[0] < 0 || snakeY[0] >= BOARD_H) {
            endGame();
            return;
        }

        for (int i = 1; i < bodyParts; i++) {
            if (snakeX[0] == snakeX[i]
                    && snakeY[0] == snakeY[i]) {
                endGame();
                return;
            }
        }
    }

    private void endGame() {
        running = false;
        gameOver = true;

        if (timer != null) {
            timer.stop();
        }

        repaint();
    }

    private void restartGame() {
        startGame();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkFood();

            if (running) {
                checkCollisions();
            }
        }

        repaint();
    }
}
