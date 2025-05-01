package scrabble.swing;

import edu.upc.prop.scrabble.presenter.swing.screens.game.board.BoardView;
import edu.upc.prop.scrabble.presenter.swing.screens.game.board.tiles.BoardTile;
import org.junit.Test;
import scrabble.swing.stubs.HandViewStub;
import scrabble.swing.utils.SwingTest;
import scrabble.swing.utils.TestWindow;

import static org.junit.Assert.*;

public class TestBoardView extends SwingTest {
    private static int getTileIndex(int x, int y, int boardSize) {
        return x * boardSize + y;
    }

    private static BoardTile getTile(TestWindow<BoardView> view, int x, int y, int boardSize) {
        return view.getComponentOfType(BoardTile.class, getTileIndex(x, y, boardSize));
    }

    @Test
    public void testCellClickAsksHandForSelectedPiece() {
        HandViewStub handViewStub = new HandViewStub();
        TestWindow<BoardView> view = new TestWindow<>(500, 500, new BoardView(21, handViewStub));
        BoardTile c = getTile(view, 10, 10, 21);

        c.doClick();

        assertTrue("Hand view getPiece() was not requested", handViewStub.getGetPieceRequestCalled());
    }
}
