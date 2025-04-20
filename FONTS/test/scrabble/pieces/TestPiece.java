package scrabble.pieces;

import edu.upc.prop.scrabble.data.pieces.Piece;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestPiece {
    @Test
    public void pieceCreator() {
        Piece sut = new Piece("a", 1);
        assertEquals("a", sut.letter());
        assertEquals(1, sut.value());
    }

    @Test
    public void pieceComparator() {
        Piece sut1 = new Piece("a", 1);
        Piece sut2 = new Piece("b", 2);
        assertNotEquals(sut1, sut2);
    }

    @Test
    public void pieceBlank() {
        Piece sut = new Piece("a", 1, true);
        assertTrue(sut.isBlank());
    }

    @Test
    public void setLetterBlankPiece() {
        Piece sut = new Piece(" ", 0, true);
        assertEquals(" ", sut.letter());

        sut.setLetter("a");
        assertEquals("a", sut.letter());
    }
}
