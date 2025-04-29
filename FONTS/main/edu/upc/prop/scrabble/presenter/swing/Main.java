package edu.upc.prop.scrabble.presenter.swing;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to the Scrabble");

        SwingUtilities.invokeLater(() -> {
            GraphicsEnvironment env = GraphicsEnvironment
                    .getLocalGraphicsEnvironment();
            GraphicsDevice device = env.getDefaultScreenDevice();
            JFrame startWindow = new JFrame("Scrabble");
            startWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            startWindow.setUndecorated(true);
            startWindow.setExtendedState(JFrame.MAXIMIZED_BOTH);
            startWindow.pack();
            startWindow.setResizable(false);
            startWindow.validate();

            device.setFullScreenWindow(startWindow);
            startWindow.setVisible(true);
        });

        System.out.println("Bye.");
    }
}
