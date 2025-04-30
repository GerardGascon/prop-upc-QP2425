package edu.upc.prop.scrabble.presenter.swing;

import edu.upc.prop.scrabble.presenter.swing.screens.game.GameMock;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to the Scrabble");

        SwingUtilities.invokeLater(() -> {
            JFrame window = createMainWindow();
            window.add(new GameMock());
            window.setVisible(true);
        });

        System.out.println("Bye.");
    }

    private static JFrame createMainWindow() {
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
}
