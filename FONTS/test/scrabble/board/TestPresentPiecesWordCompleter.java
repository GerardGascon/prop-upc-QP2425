package scrabble.board;

import edu.upc.prop.scrabble.data.board.Board;
import edu.upc.prop.scrabble.data.board.StandardBoard;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.domain.board.PresentPiecesWordCompleter;
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

        PresentPiecesWordCompleter sut = new PresentPiecesWordCompleter(board);

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

        PresentPiecesWordCompleter sut = new PresentPiecesWordCompleter(board);

        String[] words = sut.run(positions, piecesArray);

        assertEquals(1, words.length);
        assertEquals("TEST", words[0]);
    }

    @Test
    public void checkWordCompleterReturnsTwoCompletedWordsWhenPlacingOnePiece() {
        Board board = new StandardBoard();
        board.placePiece(new Piece("E", 1), 2, 0);
        board.placePiece(new Piece("S", 1), 3, 0);
        board.placePiece(new Piece("T", 1), 4, 0);
        board.placePiece(new Piece("E", 1), 1, 1);
        board.placePiece(new Piece("S", 1), 1, 2);
        board.placePiece(new Piece("T", 1), 1, 3);

        Piece[] piecesArray = new Piece[]{
                new Piece("T", 2),
        };

        Vector2[] positions = new Vector2[]{
                new Vector2(1, 0),
        };

        PresentPiecesWordCompleter sut = new PresentPiecesWordCompleter(board);

        String[] words = sut.run(positions, piecesArray);

        assertEquals(2, words.length);
        assertEquals("TEST", words[0]);
        assertEquals("TEST", words[1]);
    }

    @Test
    public void checkWordCompleterReturnsTwoCompletedWordsWhenCompletingMorePieces() {
        Board board = new StandardBoard();
        board.placePiece(new Piece("E", 1), 2, 0);
        board.placePiece(new Piece("S", 1), 3, 0);
        board.placePiece(new Piece("T", 1), 4, 0);

        Piece[] piecesArray = new Piece[]{
                new Piece("T", 2),
                new Piece("E", 2),
                new Piece("S", 2),
                new Piece("T", 2),
        };

        Vector2[] positions = new Vector2[]{
                new Vector2(1, 0),
                new Vector2(1, 1),
                new Vector2(1, 2),
                new Vector2(1, 3),
        };

        PresentPiecesWordCompleter sut = new PresentPiecesWordCompleter(board);

        String[] words = sut.run(positions, piecesArray);

        assertEquals(2, words.length);
        assertEquals("TEST", words[0]);
        assertEquals("TEST", words[1]);
    }

    @Test
    public void checkWordCompleterReturnsAllAdjacentPiecesOfTwoParallelWords() {
        Board board = new StandardBoard();
        board.placePiece(new Piece("H", 1), 1, 0);
        board.placePiece(new Piece("O", 1), 2, 0);
        board.placePiece(new Piece("L", 1), 3, 0);
        board.placePiece(new Piece("A", 1), 4, 0);

        Piece[] piecesArray = new Piece[]{
                new Piece("C", 2),
                new Piece("A", 2),
                new Piece("S", 2),
                new Piece("A", 2),
        };

        Vector2[] positions = new Vector2[]{
                new Vector2(1, 1),
                new Vector2(2, 1),
                new Vector2(3, 1),
                new Vector2(4, 1),
        };

        PresentPiecesWordCompleter sut = new PresentPiecesWordCompleter(board);

        String[] words = sut.run(positions, piecesArray);

        assertEquals(5, words.length);
        assertEquals("CASA", words[0]);
        assertEquals("HC", words[1]);
        assertEquals("OA", words[2]);
        assertEquals("LS", words[3]);
        assertEquals("AA", words[4]);
    }
}
