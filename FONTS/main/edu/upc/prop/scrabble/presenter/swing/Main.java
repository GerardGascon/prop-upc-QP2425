package edu.upc.prop.scrabble.presenter.swing;

import edu.upc.prop.scrabble.presenter.swing.screens.game.GameMock;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame window = createMainWindow();
            window.add(new GameMock());
            window.setVisible(true);
        });
    }

    private static JFrame createMainWindow() {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setUndecorated(true);

        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

        if (gd.isFullScreenSupported()) {
            gd.setFullScreenWindow(window);
        } else {
            window.setSize(gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight());
            window.setLocationRelativeTo(null);
        }

        return window;
    }
}
