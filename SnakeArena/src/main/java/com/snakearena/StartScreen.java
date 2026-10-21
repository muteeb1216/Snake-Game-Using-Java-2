
package com.snakearena;

import javax.swing.*;
import java.awt.*;

public class StartScreen extends JFrame {

    private final Color background = new Color(10, 15, 25);
    private final Color neonGreen = new Color(57, 255, 136);

    public StartScreen() {
        setTitle("SnakeArena");
        setSize(800, 650);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        setContentPane(createStartScreen());

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                quitGame();
            }
        });

        setVisible(true);
    }

    private JPanel createStartScreen() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Graphics2D g2 = (Graphics2D) g;
                g2.setPaint(new GradientPaint(
                    0, 0, new Color(10, 15, 25),
                    getWidth(), getHeight(),
                    new Color(20, 45, 42)
                ));

                g2.fillRect(0, 0, getWidth(), getHeight());

                // Decorative grid
                g2.setColor(new Color(57, 255, 136, 20));

                for (int x = 0; x < getWidth(); x += 35) {
                    g2.drawLine(x, 0, x, getHeight());
                }

                for (int y = 0; y < getHeight(); y += 35) {
                    g2.drawLine(0, y, getWidth(), y);
                }
            }
        };

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(
            75, 80, 60, 80
        ));

        JLabel badge = new JLabel("THE ARCADE EDITION");
        badge.setFont(new Font("SansSerif", Font.BOLD, 13));
        badge.setForeground(neonGreen);
        badge.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel title = new JLabel("SNAKE ARENA");
        title.setFont(new Font("SansSerif", Font.BOLD, 48));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel(
            "EAT • GROW • SURVIVE"
        );
        subtitle.setFont(new Font("SansSerif", Font.BOLD, 15));
        subtitle.setForeground(neonGreen);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel snakeArt = new JLabel("■ ■ ■ ■ ●");
        snakeArt.setFont(new Font("Monospaced", Font.BOLD, 32));
        snakeArt.setForeground(neonGreen);
        snakeArt.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel instructions = new JLabel(
            "Use arrow keys or W A S D to move"
        );
        instructions.setFont(new Font("SansSerif", Font.PLAIN, 14));
        instructions.setForeground(new Color(180, 195, 205));
        instructions.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton startButton = createButton("START GAME", neonGreen);
        JButton quitButton = createButton(
            "QUIT GAME", new Color(255, 100, 115)
        );

        startButton.addActionListener(e -> startGame());
        quitButton.addActionListener(e -> quitGame());

        panel.add(badge);
        panel.add(Box.createVerticalStrut(15));
        panel.add(title);
        panel.add(Box.createVerticalStrut(12));
        panel.add(subtitle);
        panel.add(Box.createVerticalStrut(45));
        panel.add(snakeArt);
        panel.add(Box.createVerticalStrut(35));
        panel.add(startButton);
        panel.add(Box.createVerticalStrut(15));
        panel.add(quitButton);
        panel.add(Box.createVerticalStrut(30));
        panel.add(instructions);

        return panel;
    }

    private JButton createButton(String text, Color color) {
        JButton button = new JButton(text);

        button.setFont(new Font("SansSerif", Font.BOLD, 16));
        button.setForeground(color);
        button.setBackground(new Color(20, 30, 42));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(
            color, 2
        ));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(240, 48));
        button.setPreferredSize(new Dimension(240, 48));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return button;
    }

    private void startGame() {
        dispose();

        SwingUtilities.invokeLater(() -> {
            new GameFrame();
        });
    }

    private void quitGame() {
        int choice = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to quit SnakeArena?",
            "Quit Game",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );

        if (choice == JOptionPane.YES_OPTION) {
            dispose();
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StartScreen::new);
    }
}
