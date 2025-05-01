package scrabble.swing;

import edu.upc.prop.scrabble.presenter.swing.screens.game.board.BoardView;
import edu.upc.prop.scrabble.presenter.swing.screens.game.board.tiles.BoardTile;
import org.junit.Test;
import scrabble.swing.utils.SwingTest;
import scrabble.swing.utils.TestWindow;

public class TestBoardView extends SwingTest {
    private static int getTileIndex(int x, int y, int boardSize) {
        return x * boardSize + y;
    }

    @Test
    public void testCellClick() {
        TestWindow<BoardView> view = new TestWindow<>(500, 500, new BoardView(21));
        BoardTile c = view.getComponentOfType(BoardTile.class, getTileIndex(5, 0, 21));
        c.doClick();
    }
}
