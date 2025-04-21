package scrabble.ai;

import edu.upc.prop.scrabble.data.Anchors;
import edu.upc.prop.scrabble.data.Movement;
import edu.upc.prop.scrabble.data.board.Board;
import edu.upc.prop.scrabble.data.board.StandardBoard;
import edu.upc.prop.scrabble.domain.ai.AnchorUpdater;
import edu.upc.prop.scrabble.domain.pieces.EnglishPiecesConverter;
import edu.upc.prop.scrabble.domain.pieces.PiecesConverter;
import edu.upc.prop.scrabble.utils.Direction;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestAnchors {
    private Anchors anchors;
    private AnchorUpdater anchorUpdater;
    private int boardsize;
    @Before
    public void setUp() {
        Board board = new StandardBoard();
        boardsize = board.getSize();
        PiecesConverter converter = new EnglishPiecesConverter();
        anchors = new Anchors();
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

    @Test
    public void rotateAnchors() {
        Movement move = new Movement("PAN", 6, 7, Direction.Horizontal);

        anchorUpdater.run(move);

        Anchors rotated = anchors.rotate(boardsize);

        assertFalse(rotated.exists(5, 7));
        assertTrue(rotated.exists(7, 5));
        assertTrue(rotated.exists(7, 9));
        assertTrue(rotated.exists(6, 7));
        assertTrue(rotated.exists(6, 6));
        assertTrue(rotated.exists(6, 8));
        assertTrue(rotated.exists(8, 6));
        assertTrue(rotated.exists(8, 7));
        assertTrue(rotated.exists(8, 8));
    }
}
