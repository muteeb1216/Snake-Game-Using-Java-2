
package com.snakearena;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import com.snakearena.game.SnakeArena;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            JFrame frame = new JFrame("Snake Arena");

            frame.setDefaultCloseOperation(
                    JFrame.EXIT_ON_CLOSE
            );

            frame.setResizable(false);
            frame.add(new SnakeArena());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

        });
    }
}
