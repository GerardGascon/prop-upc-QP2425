package scrabble.board;

import edu.upc.prop.scrabble.data.board.Board;
import edu.upc.prop.scrabble.data.board.JuniorBoard;
import edu.upc.prop.scrabble.data.board.StandardBoard;
import edu.upc.prop.scrabble.data.board.SuperBoard;
import edu.upc.prop.scrabble.data.pieces.Piece;
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
    public void boardIsEmptyWhenNoPiecesPlaced() {
        Board sut = new JuniorBoard();

        assertTrue(sut.isEmpty());
    }

    @Test
    public void boardIsNotEmptyWhenPiecesPlaced() {
        Board sut = new JuniorBoard();

        sut.placePiece(new Piece("C", 0), 0, 0);

        assertFalse(sut.isEmpty());
    }

    @Test
    public void placePieceAddsItToBoard() {
        Board sut = new StandardBoard();

        sut.placePiece(new Piece("C", 0), 0, 0);

        assertEquals(new Piece("C", 0), sut.getCellPiece(0, 0));
    }

    @Test
    public void placePiecePlacesCorrectPiece() {
        Board sut = new StandardBoard();

        sut.placePiece(new Piece("D", 0), 0, 0);

        assertEquals(new Piece("D", 0), sut.getCellPiece(0, 0));
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

        sut.placePiece(new Piece("C", 0), 1, 1);

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

        sut.placePiece(new Piece("C", 0), 0, 0);
        sut.placePiece(new Piece("D", 0), 0, 0);

        assertEquals(new Piece("C", 0), sut.getCellPiece(0, 0));
    }

    @Test
    public void rotateBoardRotatesPiece(){
        Board sut = new StandardBoard();
        sut.placePiece(new Piece("A", 0), 0, 0);

        Board res = sut.rotate();

        assertEquals("A", res.getCellPiece(0, 14).letter());
    }

    @Test
    public void rotateBoardRotatesWord(){
        Board sut = new StandardBoard();
        sut.placePiece(new Piece("H", 0), 4, 0);
        sut.placePiece(new Piece("O", 0), 4, 1);
        sut.placePiece(new Piece("L", 0), 4, 2);
        sut.placePiece(new Piece("A", 0), 4, 3);

        Board res = sut.rotate();

        assertEquals("H", res.getCellPiece(0, 10).letter());
        assertEquals("O", res.getCellPiece(1, 10).letter());
        assertEquals("L", res.getCellPiece(2, 10).letter());
        assertEquals("A", res.getCellPiece(3, 10).letter());
    }
}