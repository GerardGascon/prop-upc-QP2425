package scrabble.swing.utils;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;

public class TestWindow<T extends JPanel> {
    private T panel;
    private JDialog window;

    public TestWindow(int width, int height, T panel) {
        this.panel = panel;

        String title = getCallingTestMethodName();

        try {
            SwingUtilities.invokeAndWait(() -> {
                window = new JDialog();
                window.setTitle(title);
                window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                window.setSize(width, height);
                window.setResizable(false);
                window.setLocationRelativeTo(null);
                this.panel = panel;
                window.add(panel);
                window.setVisible(true);
            });
        } catch (InterruptedException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private String getCallingTestMethodName() {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        for (StackTraceElement element : stack) {
            String methodName = element.getMethodName();
            if (!methodName.equals("getStackTrace") &&
                    !methodName.equals("getCallingTestMethodName") &&
                    !methodName.equals("<init>") &&
                    !element.getClassName().equals(this.getClass().getName())) {
                return methodName;
            }
        }
        return "TestWindow";
    }

    public T getPanel() {
        return panel;
    }

    public <TComponent extends Component> TComponent getComponentOfType(Class<TComponent> clazz, int index) {
        int count = 0;
        for (Component comp : panel.getComponents()) {
            if (clazz.isInstance(comp)) {
                if (count == index)
                    return clazz.cast(comp);
                count++;
            }
        }
        throw new RuntimeException("No component found for " + clazz.getName());
    }

    public void close() {
        window.dispose();
    }
}
