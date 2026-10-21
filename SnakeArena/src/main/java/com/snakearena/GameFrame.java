
package com.snakearena;

import com.snakearena.game.GamePanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameFrame extends JFrame {

    private final Color background = new Color(10, 15, 25);
    private final Color red = new Color(220, 53, 69);

    public GameFrame() {
        setTitle("Snake Arena");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(false);

        GamePanel gamePanel = new GamePanel();

        // Quit button
        JButton quitButton = new JButton("QUIT GAME");

        quitButton.setFont(
            new Font("SansSerif", Font.BOLD, 14)
        );
        quitButton.setForeground(Color.WHITE);
        quitButton.setBackground(red);
        quitButton.setOpaque(true);
        quitButton.setContentAreaFilled(true);
        quitButton.setBorder(
            BorderFactory.createEmptyBorder(12, 25, 12, 25)
        );
        quitButton.setFocusPainted(false);
        quitButton.setPreferredSize(new Dimension(180, 48));
        quitButton.setCursor(
            new Cursor(Cursor.HAND_CURSOR)
        );

        // Hover effect
        quitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                quitButton.setBackground(
                    new Color(255, 75, 90)
                );
            }

            @Override
            public void mouseExited(MouseEvent e) {
                quitButton.setBackground(red);
            }
        });

        quitButton.addActionListener(e -> quitGame());

        // Bottom bar
        JPanel bottomPanel = new JPanel(
            new FlowLayout(FlowLayout.RIGHT, 20, 10)
        );
        bottomPanel.setBackground(background);
        bottomPanel.add(quitButton);

        // Main layout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(background);
        mainPanel.add(gamePanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);

        // Handle window close button
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                quitGame();
            }
        });

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        SwingUtilities.invokeLater(
            gamePanel::requestFocusInWindow
        );
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
}
