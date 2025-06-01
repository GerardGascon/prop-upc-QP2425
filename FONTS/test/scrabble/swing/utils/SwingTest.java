package scrabble.swing.utils;

import org.junit.Assume;
import org.junit.BeforeClass;

import javax.swing.*;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;

public abstract class SwingTest {
    protected static final boolean IS_RUN_TESTS;
    private static Queue<CountDownLatch> latch = new LinkedList<>();

    static {
        IS_RUN_TESTS = "runTests".equals(System.getProperty("run.origin"));
    }

    @BeforeClass
    public static void skipIfHeadless() {
        Assume.assumeTrue("Skipping GUI test in headless mode", !java.awt.GraphicsEnvironment.isHeadless());
    }

    protected static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void clickButton() {
        Objects.requireNonNull(latch.poll()).countDown();
    }

    protected static void queueClick(AbstractButton button) {
        if (!IS_RUN_TESTS)
            return;
        latch.add(new CountDownLatch(1));
        button.addActionListener(_ -> clickButton());
    }

    protected static void doClick(AbstractButton button) {
        if (IS_RUN_TESTS) {
            try {
                Objects.requireNonNull(latch.peek()).await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } else {
            button.doClick();
        }
    }

    protected static void finish() {
        if (IS_RUN_TESTS)
            JOptionPane.showMessageDialog(null, "Test Passed", "Test Passed", JOptionPane.INFORMATION_MESSAGE);
    }
}
