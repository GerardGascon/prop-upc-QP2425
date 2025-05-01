package scrabble.swing;

import edu.upc.prop.scrabble.presenter.swing.screens.game.board.BoardView;
import org.junit.Test;
import scrabble.swing.utils.SwingTest;
import scrabble.swing.utils.TestWindow;

import javax.swing.*;
import java.awt.*;

public class TestBoardView extends SwingTest {
    private static int getTileIndex(int x, int y, int boardSize) {
        return x * boardSize + y;
    }

    @Test
    public void test() {
        TestWindow<BoardView> view = new TestWindow<>(500, 500, new BoardView(21));
//        sleep(500);
        Component c = view.getPanel().getComponent(getTileIndex(5, 0, 21));
        ((JButton)c).doClick();
        System.out.println(c);
    }
}
