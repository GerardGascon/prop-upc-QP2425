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

        assertEquals("C", sut.getCellPiece(0, 0));
    }

    @Test
    public void placePiecePlacesCorrectPiece() {
        Board sut = new StandardBoard();

        sut.placePiece("D", 0, 0);

        assertEquals("D", sut.getCellPiece(0, 0));
    }

    @Test
    public void readEmptyCellFromBoardReturnsNull() {
        Board sut = new StandardBoard();

        assertNull(sut.getCellPiece(1, 1));
    }

    @Test
    public void centerTileIsNotCorrectWhenNotCenter() {
        Board sut = new StandardBoard();

        assertFalse(sut.isCenter(7, 0));
    }

    @Test
    public void centerTileIsCorrectInStandardBoard() {
        Board sut = new StandardBoard();

        assertTrue(sut.isCenter(7, 7));
    }

    @Test
    public void centerTileIsCorrectInSuperBoard() {
        Board sut = new SuperBoard();

        assertTrue(sut.isCenter(10, 10));
    }

    @Test
    public void centerTileIsCorrectInJuniorBoard() {
        Board sut = new JuniorBoard();

        assertTrue(sut.isCenter(5, 5));
    }

    @Test
    public void emptyCellReturnsTrueWhenCellIsEmpty() {
        Board sut = new StandardBoard();

        assertTrue(sut.isCellEmpty(1, 1));
    }

    @Test
    public void emptyCellReturnsFalseWhenCellIsNotEmpty() {
        Board sut = new StandardBoard();

        sut.placePiece("C", 1, 1);

        assertFalse(sut.isCellEmpty(1, 1));
    }

    @Test
    public void isPremiumTileReturnsTrueWhenCellIsPremium() {
        Board sut = new StandardBoard();

        assertTrue(sut.isPremiumTile(0, 0));
    }

    @Test
    public void isPremiumTileReturnsFalseWhenCellIsNotPremium() {
        Board sut = new StandardBoard();

        assertFalse(sut.isPremiumTile(1, 0));
    }

    @Test
    public void tryPlacingToAlreadyPlacedCellReturnsFalse() {
        Board sut = new StandardBoard();

        sut.placePiece("C", 0, 0);
        sut.placePiece("D", 0, 0);

        assertEquals("C", sut.getCellPiece(0, 0));
    }

    @Test
    public void placeWordVerticalPlacesWord() {
        Board board = new StandardBoard();
        BoardViewMock mock = new BoardViewMock();
        WordPlacer sut = new WordPlacer(board, mock);

        sut.run("TEST", 0, 0, Direction.Vertical);

        assertEquals("T", board.getCellPiece(0, 0));
        assertEquals("E", board.getCellPiece(0, 1));
        assertEquals("S", board.getCellPiece(0, 2));
        assertEquals("T", board.getCellPiece(0, 3));
    }

    @Test
    public void placeWordHorizontalPlacesWord() {
        Board board = new StandardBoard();
        BoardViewMock mock = new BoardViewMock();
        WordPlacer sut = new WordPlacer(board, mock);

        sut.run("TEST", 0, 0, Direction.Horizontal);

        assertEquals("T", board.getCellPiece(0, 0));
        assertEquals("E", board.getCellPiece(1, 0));
        assertEquals("S", board.getCellPiece(2, 0));
        assertEquals("T", board.getCellPiece(3, 0));
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