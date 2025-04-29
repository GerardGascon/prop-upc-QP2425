package scrabble.persistence;

import edu.upc.prop.scrabble.data.board.Board;
import edu.upc.prop.scrabble.data.board.StandardBoard;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.persistence.runtime.data.PersistentDictionary;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class TestBoardPersistence {
    @Test
    public void boardGetsLoadedWithCorrectPieces() {
        Board board = new StandardBoard();
        board.placePiece(new Piece("A", 1), 0, 0);
        board.placePiece(new Piece("Z", 6), 3, 7);

        PersistentDictionary dict = board.encode();
        Board sut = new StandardBoard();
        sut.decode(dict);

        assertEquals("A", sut.getCellPiece(0, 0).letter());
        assertEquals(1, sut.getCellPiece(0, 0).value());
        assertEquals("Z", sut.getCellPiece(3, 7).letter());
        assertEquals(6, sut.getCellPiece(3, 7).value());
    }

    @Test
    public void boardGetsLoadedAndNotEmpty() {
        Board board = new StandardBoard();
        board.placePiece(new Piece("A", 1), 0, 0);

        PersistentDictionary dict = board.encode();
        Board sut = new StandardBoard();
        sut.decode(dict);

        assertFalse(sut.isEmpty());
    }
}
