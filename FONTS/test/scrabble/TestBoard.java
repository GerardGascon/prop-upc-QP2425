package scrabble;

import edu.upc.prop.scrabble.data.Board;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class TestBoard {
    @Test
    public void createBoardSetsProperSize() {
        Board sut = new Board(4);

        assertEquals(4, sut.getSize());
    }

    @Test
    public void placePieceAddsItToBoard() {
        Board sut = new Board(4);

        sut.placePiece("C", 0, 0);

        assertEquals("C", sut.getCell(0, 0));
    }

    @Test
    public void placePiecePlacesCorrectPiece() {
        Board sut = new Board(4);

        sut.placePiece("D", 0, 0);

        assertEquals("D", sut.getCell(0, 0));
    }

    @Test
    public void readEmptyCellFromBoardReturnsNull() {
        Board sut = new Board(4);

        assertNull(sut.getCell(1, 1));
    }
}