package edu.upc.prop.scrabble.presenter.swing;

import edu.upc.prop.scrabble.presenter.swing.screens.game.GameMock;
import edu.upc.prop.scrabble.presenter.swing.screens.game.MenuMock;

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
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("windows"))
            return createMainWindow_Windows();
        else
            return createMainWindow_Linux();
    }

    private static JFrame createMainWindow_Windows() {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        window.setUndecorated(true);
        window.setResizable(false);
        window.setSize(gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight());
        window.setExtendedState(JFrame.MAXIMIZED_BOTH);
        window.setLocationRelativeTo(null);
        return window;
    }

    private static JFrame createMainWindow_Linux() {
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
