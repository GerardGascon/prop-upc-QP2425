package scrabble.board;

import edu.upc.prop.scrabble.data.Player;
import edu.upc.prop.scrabble.data.board.Board;
import edu.upc.prop.scrabble.data.board.StandardBoard;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.domain.board.PointCalculator;
import edu.upc.prop.scrabble.domain.board.WordGetter;
import edu.upc.prop.scrabble.domain.board.WordPlacer;
import edu.upc.prop.scrabble.utils.Direction;
import org.junit.Test;
import scrabble.stubs.BoardViewStub;

import static org.junit.Assert.*;

public class TestWordPlacer {
    @Test
    public void placeWordVerticalPlacesWord() {
        Board board = new StandardBoard();
        BoardViewStub mock = new BoardViewStub();
        Piece[] pieces = new Piece[]{
                new Piece("T", 1),
                new Piece("E", 1),
                new Piece("S", 1),
                new Piece("T", 1)
        };
        WordGetter wordGetter = new WordGetter(board);
        PointCalculator pointCalculator = new PointCalculator(board);
        Player player = new Player("Test", false);
        WordPlacer sut = new WordPlacer(player, board, mock, pointCalculator);

        sut.run(pieces, 0, 0, Direction.Vertical);

        assertEquals(new Piece("T", 1), board.getCellPiece(0, 0));
        assertEquals(new Piece("E", 1), board.getCellPiece(0, 1));
        assertEquals(new Piece("S", 1), board.getCellPiece(0, 2));
        assertEquals(new Piece("T", 1), board.getCellPiece(0, 3));
    }

    @Test
    public void placeWordHorizontalPlacesWord() {
        Board board = new StandardBoard();
        BoardViewStub mock = new BoardViewStub();
        Piece[] pieces = new Piece[]{
                new Piece("T", 1),
                new Piece("E", 1),
                new Piece("S", 1),
                new Piece("T", 1)
        };
        WordGetter wordGetter = new WordGetter(board);
        PointCalculator pointCalculator = new PointCalculator(board);
        Player player = new Player("Test", false);
        WordPlacer sut = new WordPlacer(player, board, mock, pointCalculator);

        sut.run(pieces, 0, 0, Direction.Horizontal);

        assertEquals(new Piece("T", 1), board.getCellPiece(0, 0));
        assertEquals(new Piece("E", 1), board.getCellPiece(1, 0));
        assertEquals(new Piece("S", 1), board.getCellPiece(2, 0));
        assertEquals(new Piece("T", 1), board.getCellPiece(3, 0));
    }

    @Test
    public void noPlaceActionDoesntUpdateBoard() {
        Board board = new StandardBoard();
        BoardViewStub mock = new BoardViewStub();
        WordGetter wordGetter = new WordGetter(board);
        PointCalculator pointCalculator = new PointCalculator(board);
        Player player = new Player("Test", false);
        WordPlacer _ = new WordPlacer(player, board, mock, pointCalculator);

        assertFalse(mock.getUpdateCallReceived());
    }

    @Test
    public void placeActionUpdatesBoard() {
        Board board = new StandardBoard();
        BoardViewStub mock = new BoardViewStub();
        Piece[] pieces = new Piece[]{
                new Piece("T", 1),
                new Piece("E", 1),
                new Piece("S", 1),
                new Piece("T", 1)
        };
        WordGetter wordGetter = new WordGetter(board);
        PointCalculator pointCalculator = new PointCalculator(board);
        Player player = new Player("Test", false);
        WordPlacer sut = new WordPlacer(player, board, mock, pointCalculator);

        sut.run(pieces, 0, 0, Direction.Horizontal);

        assertTrue(mock.getUpdateCallReceived());
    }
}
