import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Request;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public abstract class Runner {
    protected static void openTestSuite(Class<?> testClass) {
        SwingUtilities.invokeLater(() -> {
            createUI(testClass);
        });
    }

    private static void createUI(Class<?> testClass) {
        JDialog frame = new JDialog();
        frame.setTitle(testClass.getSimpleName());
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        Method[] methods = testClass.getDeclaredMethods();
        List<String> methodNames = new ArrayList<>();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Test.class))
                methodNames.add(method.getName());
        }
        methodNames.sort(Comparator.naturalOrder());

        String[] methodArray = methodNames.toArray(String[]::new);
        JList<String> methodList = new JList<>(methodArray);
        methodList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        methodList.setCellRenderer(new DefaultListCellRenderer() {
            private int hoveredIndex = -1;

            {
                methodList.addMouseMotionListener(new MouseMotionAdapter() {
                    @Override
                    public void mouseMoved(MouseEvent e) {
                        int index = methodList.locationToIndex(e.getPoint());
                        if (index != hoveredIndex) {
                            hoveredIndex = index;
                            methodList.repaint();
                        }
                    }
                });
                methodList.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseExited(MouseEvent e) {
                        hoveredIndex = -1;
                        methodList.repaint();
                    }
                });
            }

            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (index == hoveredIndex && !isSelected) {
                    c.setBackground(new Color(220, 240, 255));
                }
                return c;
            }
        });

        JScrollPane scrollPane = new JScrollPane(methodList);

        methodList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selectedMethodName = methodList.getSelectedValue();
                if (selectedMethodName != null) {
                    testMethod(testClass, selectedMethodName, frame, methodList);
                }
            }
        });

        frame.setLayout(new BorderLayout());
        frame.add(scrollPane, BorderLayout.CENTER);

        int itemHeight = methodList.getFixedCellHeight();
        if (itemHeight == -1) {
            itemHeight = methodList.getFontMetrics(methodList.getFont()).getHeight();
        }
        int maxVisibleItems = 10;
        int visibleItems = Math.min(methodArray.length, maxVisibleItems);
        int height = 75 + (itemHeight * visibleItems);
        frame.setSize(400, Math.max(height, 150));

        frame.setVisible(true);
    }

    private static void testMethod(Class<?> testClass, String methodName, JDialog frame, JList<String> methodList) {
        try {
            SwingUtilities.invokeLater(() -> {
                methodList.setEnabled(false);
                frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            });

            Thread t = new Thread(() -> {
                String message;
                String title;
                int messageType;

                try {
                    JUnitCore junit = new JUnitCore();
                    Result result = junit.run(Request.method(testClass, methodName));

                    boolean testPassed = true;
                    String failureMessage = "";
                    for (Failure failure : result.getFailures()) {
                        if (failure.getDescription().getMethodName().equals(methodName)) {
                            testPassed = false;
                            failureMessage = failure.toString();
                            break;
                        }
                    }

                    if (testPassed) {
                        title = "Test Passed";
                        message = "Test '" + methodName + "' passed successfully.";
                        messageType = JOptionPane.INFORMATION_MESSAGE;
                    } else {
                        title = "Test Failed";
                        message = "Test '" + methodName + "' failed:\n" + failureMessage;
                        messageType = JOptionPane.ERROR_MESSAGE;
                    }

                } catch (Exception ex) {
                    title = "Error";
                    message = "An error occurred while running test:\n" + ex.getMessage();
                    messageType = JOptionPane.ERROR_MESSAGE;
                }

                String finalMessage = message;
                String finalTitle = title;
                int finalMessageType = messageType;
                SwingUtilities.invokeLater(() -> {
                    methodList.setEnabled(true);
                    frame.setCursor(Cursor.getDefaultCursor());

                    JOptionPane.showMessageDialog(frame, finalMessage, finalTitle, finalMessageType);
                });
            });

            t.start();

        } catch (Exception e) {
            SwingUtilities.invokeLater(() -> {
                methodList.setEnabled(true);
                frame.setCursor(Cursor.getDefaultCursor());
                JOptionPane.showMessageDialog(frame,
                        "Unexpected error: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            });
        }
    }
}
