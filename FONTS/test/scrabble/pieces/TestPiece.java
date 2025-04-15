package scrabble.pieces;

import edu.upc.prop.scrabble.data.pieces.Piece;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestPiece {
    @Test
    public void pieceCreator() {
        Piece piece = new Piece("a", 1);
        assertEquals(piece.letter(), "a");
        assertEquals(piece.value(), 1);
    }

    @Test
    public void pieceComparator() {
        Piece piece1 = new Piece("a", 1);
        Piece piece2 = new Piece("b", 2);
        assertFalse(piece1.equals(piece2));
        assertTrue(piece1.equals(piece1));
        assertTrue(piece2.equals(piece2));
    }

    @Test
    public void pieceBlank() {
        Piece piece = new Piece("a", 1, true);
        assertTrue(piece.isBlank());
    }

    @Test
    public void setLetterBlankPiece() {
        Piece piece = new Piece(" ", 0, true);
        assertEquals(piece.letter(), " ");

        piece.setLetter("a");
        assertEquals(piece.letter(), "a");
    }
}
