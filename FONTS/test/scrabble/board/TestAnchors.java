package scrabble.board;

import edu.upc.prop.scrabble.data.Anchors;
import edu.upc.prop.scrabble.data.Movement;
import edu.upc.prop.scrabble.data.board.Board;
import edu.upc.prop.scrabble.data.board.StandardBoard;
import edu.upc.prop.scrabble.domain.AnchorUpdater;
import edu.upc.prop.scrabble.domain.board.WordGetter;
import edu.upc.prop.scrabble.domain.dawg.WordAdder;
import edu.upc.prop.scrabble.domain.pieces.PiecesConverter;
import edu.upc.prop.scrabble.utils.Direction;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestAnchors {
    private Anchors anchors;
    private AnchorUpdater anchorUpdater;

    @Before
    public void setUp() {
        Board board = new StandardBoard();
        PiecesConverter converter = new PiecesConverter();
        anchors = new Anchors(board);
        anchorUpdater = new AnchorUpdater(anchors, board, converter);
    }

    @Test
    public void initialAnchor() {
        assertFalse(anchors.exists(0, 0));
        assertTrue(anchors.exists(7, 7));
    }

    @Test
    public void updateAnchorHor() {
        Movement move = new Movement("PAN", 6, 7, Direction.Horizontal);

        anchorUpdater.run(move);

        assertTrue(anchors.exists(5, 7));
        assertTrue(anchors.exists(9, 7));
        assertTrue(anchors.exists(6, 6));
        assertTrue(anchors.exists(7, 6));
        assertTrue(anchors.exists(8, 6));
        assertTrue(anchors.exists(6, 8));
        assertTrue(anchors.exists(7, 8));
        assertTrue(anchors.exists(8, 8));
    }

    @Test
    public void updateAnchorVer() {
        Movement move = new Movement("PAN", 7, 6, Direction.Vertical);

        anchorUpdater.run(move);

        assertTrue(anchors.exists(7, 5));
        assertTrue(anchors.exists(7, 9));
        assertTrue(anchors.exists(6, 6));
        assertTrue(anchors.exists(6, 7));
        assertTrue(anchors.exists(6, 8));
        assertTrue(anchors.exists(8, 6));
        assertTrue(anchors.exists(8, 7));
        assertTrue(anchors.exists(8, 8));
    }
}
