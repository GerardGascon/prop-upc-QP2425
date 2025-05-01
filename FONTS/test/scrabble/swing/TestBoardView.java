package scrabble.swing;

import edu.upc.prop.scrabble.presenter.swing.screens.game.board.BoardView;
import edu.upc.prop.scrabble.presenter.swing.screens.game.board.tiles.BoardCell;
import edu.upc.prop.scrabble.presenter.swing.screens.game.board.tiles.BoardEmptyTile;
import edu.upc.prop.scrabble.presenter.swing.screens.game.board.tiles.BoardPieceTile;
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
        return view.getComponentOfType(BoardCell.class, getTileIndex(x, y, boardSize)).getTile();
    }

    @Test
    public void testCellClickAsksHandForSelectedPiece() {
        HandViewStub handViewStub = new HandViewStub();
        TestWindow<BoardView> view = new TestWindow<>(500, 500, new BoardView(21, handViewStub));
        BoardTile c = getTile(view, 10, 10, 21);

        c.doClick();

        assertTrue("Hand view getPiece() was not requested", handViewStub.getGetPieceRequestCalled());
    }

    @Test
    public void testCellTileChangeReplacesTile() {
        HandViewStub handViewStub = new HandViewStub();
        TestWindow<BoardView> view = new TestWindow<>(500, 500, new BoardView(21, handViewStub));

        view.getPanel().changeTile(new BoardPieceTile(0, 0, handViewStub), 0, 0);

        assertTrue(getTile(view, 0, 0, 21) instanceof BoardPieceTile);
    }

    @Test
    public void testCellTileIsEmptyByDefault() {
        HandViewStub handViewStub = new HandViewStub();
        TestWindow<BoardView> view = new TestWindow<>(500, 500, new BoardView(21, handViewStub));

        assertTrue(getTile(view, 0, 0, 21) instanceof BoardEmptyTile);
    }
}
