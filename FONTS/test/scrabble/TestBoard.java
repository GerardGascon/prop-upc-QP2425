package scrabble;

import edu.upc.prop.scrabble.data.Board;
import edu.upc.prop.scrabble.domain.WordPlacer;
import edu.upc.prop.scrabble.utils.Direction;
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

        sut.placePiece("C", 0, 0);
        sut.placePiece("D", 0, 0);

        assertEquals("C", sut.getCell(0, 0));
    }

    @Test
    public void placeWordVerticalPlacesWord() {
        Board board = new Board(4);
        WordPlacer sut = new WordPlacer(board);

        sut.run("TEST", 0, 0, Direction.Vertical);

        assertEquals("T", board.getCell(0, 0));
        assertEquals("E", board.getCell(0, 1));
        assertEquals("S", board.getCell(0, 2));
        assertEquals("T", board.getCell(0, 3));
    }

    @Test
    public void placeWordHorizontalPlacesWord() {
        Board board = new Board(4);
        WordPlacer sut = new WordPlacer(board);

        sut.run("TEST", 0, 0, Direction.Horizontal);

        assertEquals("T", board.getCell(0, 0));
        assertEquals("E", board.getCell(1, 0));
        assertEquals("S", board.getCell(2, 0));
        assertEquals("T", board.getCell(3, 0));
    }
}