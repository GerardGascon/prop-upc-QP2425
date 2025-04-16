package scrabble.pieces;

import edu.upc.prop.scrabble.data.pieces.Piece;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestPiece {
    @Test
    public void pieceCreator() {
        Piece sut = new Piece("a", 1);
        assertEquals(sut.letter(), "a");
        assertEquals(sut.value(), 1);
    }

    @Test
    public void pieceComparator() {
        Piece sut1 = new Piece("a", 1);
        Piece sut2 = new Piece("b", 2);
        assertFalse(sut1.equals(sut2));
        assertTrue(sut1.equals(sut1));
        assertTrue(sut2.equals(sut2));
    }

    @Test
    public void pieceBlank() {
        Piece sut = new Piece("a", 1, true);
        assertTrue(sut.isBlank());
    }

    @Test
    public void setLetterBlankPiece() {
        Piece sut = new Piece(" ", 0, true);
        assertEquals(sut.letter(), " ");

        sut.setLetter("a");
        assertEquals(sut.letter(), "a");
    }
}
