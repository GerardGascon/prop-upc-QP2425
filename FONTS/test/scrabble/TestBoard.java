package scrabble;

import edu.upc.prop.scrabble.data.Board;
import edu.upc.prop.scrabble.domain.CtrlBoard;
import org.junit.Test;

import static org.junit.Assert.*;

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

    @Test
    public void tryPlacingToAlreadyPlacedCellReturnsFalse() {
        Board sut = new Board(4);

        assertTrue(sut.placePiece("C", 0, 0));
        assertFalse(sut.placePiece("D", 0, 0));

        assertEquals("C", sut.getCell(0, 0));
    }

    @Test
    public void placeWordVerticalPlacesWord() {
        CtrlBoard sut = new CtrlBoard(4);

        sut.placeWordVertical("TEST", 0, 0);

        assertEquals("T", sut.getCell(0, 0));
        assertEquals("E", sut.getCell(0, 1));
        assertEquals("S", sut.getCell(0, 2));
        assertEquals("T", sut.getCell(0, 3));
    }

    @Test
    public void placeWordHorizontalPlacesWord() {
        CtrlBoard sut = new CtrlBoard(4);

        sut.placeWordHorizontal("TEST", 0, 0);

        assertEquals("T", sut.getCell(0, 0));
        assertEquals("E", sut.getCell(1, 0));
        assertEquals("S", sut.getCell(2, 0));
        assertEquals("T", sut.getCell(3, 0));
    }
}