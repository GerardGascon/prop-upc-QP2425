package scrabble;

import edu.upc.prop.scrabble.data.board.Board;
import edu.upc.prop.scrabble.data.board.JuniorBoard;
import edu.upc.prop.scrabble.data.board.StandardBoard;
import edu.upc.prop.scrabble.data.board.SuperBoard;
import edu.upc.prop.scrabble.domain.WordPlacer;
import edu.upc.prop.scrabble.utils.Direction;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestBoard {
    @Test
    public void standardBoardHasSize15() {
        Board sut = new StandardBoard();

        assertEquals(15, sut.getSize());
    }
    @Test
    public void superBoardHasSize21() {
        Board sut = new SuperBoard();

        assertEquals(21, sut.getSize());
    }
    @Test
    public void jrBoardHasSize11() {
        Board sut = new JuniorBoard();

        assertEquals(11, sut.getSize());
    }

    @Test
    public void placePieceAddsItToBoard() {
        Board sut = new StandardBoard();

        sut.placePiece("C", 0, 0);

        assertEquals("C", sut.getCell(0, 0));
    }

    @Test
    public void placePiecePlacesCorrectPiece() {
        Board sut = new StandardBoard();

        sut.placePiece("D", 0, 0);

        assertEquals("D", sut.getCell(0, 0));
    }

    @Test
    public void readEmptyCellFromBoardReturnsNull() {
        Board sut = new StandardBoard();

        assertNull(sut.getCell(1, 1));
    }

    @Test
    public void tryPlacingToAlreadyPlacedCellReturnsFalse() {
        Board sut = new StandardBoard();

        sut.placePiece("C", 0, 0);
        sut.placePiece("D", 0, 0);

        assertEquals("C", sut.getCell(0, 0));
    }

    @Test
    public void placeWordVerticalPlacesWord() {
        Board board = new StandardBoard();
        BoardViewMock mock = new BoardViewMock();
        WordPlacer sut = new WordPlacer(board, mock);

        sut.run("TEST", 0, 0, Direction.Vertical);

        assertEquals("T", board.getCell(0, 0));
        assertEquals("E", board.getCell(0, 1));
        assertEquals("S", board.getCell(0, 2));
        assertEquals("T", board.getCell(0, 3));
    }

    @Test
    public void placeWordHorizontalPlacesWord() {
        Board board = new StandardBoard();
        BoardViewMock mock = new BoardViewMock();
        WordPlacer sut = new WordPlacer(board, mock);

        sut.run("TEST", 0, 0, Direction.Horizontal);

        assertEquals("T", board.getCell(0, 0));
        assertEquals("E", board.getCell(1, 0));
        assertEquals("S", board.getCell(2, 0));
        assertEquals("T", board.getCell(3, 0));
    }

    @Test
    public void noPlaceActionDoesntUpdateBoard(){
        Board board = new StandardBoard();
        BoardViewMock mock = new BoardViewMock();
        WordPlacer _ = new WordPlacer(board, mock);

        assertFalse(mock.getUpdateCallReceived());
    }

    @Test
    public void placeActionUpdatesBoard(){
        Board board = new StandardBoard();
        BoardViewMock mock = new BoardViewMock();
        WordPlacer sut = new WordPlacer(board, mock);

        sut.run("TEST", 0, 0, Direction.Horizontal);

        assertTrue(mock.getUpdateCallReceived());
    }
}