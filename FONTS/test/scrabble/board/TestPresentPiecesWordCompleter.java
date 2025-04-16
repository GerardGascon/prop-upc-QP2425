package scrabble.board;

import edu.upc.prop.scrabble.data.Movement;
import edu.upc.prop.scrabble.data.board.Board;
import edu.upc.prop.scrabble.data.board.StandardBoard;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.domain.board.PointCalculator;
import edu.upc.prop.scrabble.domain.board.PresentPiecesWordCompleter;
import edu.upc.prop.scrabble.domain.board.WordGetter;
import edu.upc.prop.scrabble.utils.Vector2;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestPresentPiecesWordCompleter {
    @Test
    public void checkWordCompleterReturnsExtendedWord() {
        Board board = new StandardBoard();
        board.placePiece(new Piece("T", 1), 1, 0);
        board.placePiece(new Piece("E", 1), 2, 0);
        board.placePiece(new Piece("S", 1), 3, 0);
        board.placePiece(new Piece("T", 1), 4, 0);

        Piece[] piecesArray = new Piece[]{
                new Piece("S", 2),
        };

        Vector2[] positions = new Vector2[]{
                new Vector2(5, 0),
        };

        WordGetter wordGetter = new WordGetter(board);
        PresentPiecesWordCompleter sut = new PresentPiecesWordCompleter(board, wordGetter);

        String[] words = sut.run(positions, piecesArray);

        assertEquals(1, words.length);
        assertEquals("TESTS", words[0]);
    }

    @Test
    public void checkWordCompleterReturnsCrossedWord() {
        Board board = new StandardBoard();
        board.placePiece(new Piece("T", 1), 1, 0);
        board.placePiece(new Piece("E", 1), 2, 0);
        board.placePiece(new Piece("S", 1), 3, 0);
        board.placePiece(new Piece("T", 1), 4, 0);

        Piece[] piecesArray = new Piece[]{
                new Piece("E", 2),
                new Piece("S", 2),
                new Piece("T", 2),
        };

        Vector2[] positions = new Vector2[]{
                new Vector2(1, 1),
                new Vector2(1, 2),
                new Vector2(1, 3),
        };

        WordGetter wordGetter = new WordGetter(board);
        PresentPiecesWordCompleter sut = new PresentPiecesWordCompleter(board, wordGetter);

        String[] words = sut.run(positions, piecesArray);

        assertEquals(1, words.length);
        assertEquals("TEST", words[0]);
    }
}
