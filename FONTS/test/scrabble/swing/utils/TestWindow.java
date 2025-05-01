package scrabble.swing.utils;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;

public class TestWindow<T extends JPanel> {
    private T panel;

    public TestWindow(int width, int height, T panel) {
        this.panel = panel;

        try {
            SwingUtilities.invokeAndWait(() -> {
                JFrame window = new JFrame("Test Window");
                window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                window.setSize(width, height);
                window.setLocationRelativeTo(null);
                this.panel = panel;
                window.add(panel);
                window.setVisible(true);
            });
        } catch (InterruptedException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public T getPanel() {
        return panel;
    }
}
