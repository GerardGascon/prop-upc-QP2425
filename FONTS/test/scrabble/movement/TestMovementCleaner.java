package scrabble.movement;

import edu.upc.prop.scrabble.data.Movement;
import edu.upc.prop.scrabble.data.Player;
import edu.upc.prop.scrabble.data.board.Board;
import edu.upc.prop.scrabble.data.board.JuniorBoard;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.domain.board.PointCalculator;
import edu.upc.prop.scrabble.domain.board.WordGetter;
import edu.upc.prop.scrabble.domain.board.WordPlacer;
import edu.upc.prop.scrabble.domain.movement.MovementCleaner;
import edu.upc.prop.scrabble.domain.pieces.EnglishPiecesConverter;
import edu.upc.prop.scrabble.domain.pieces.PiecesConverter;
import edu.upc.prop.scrabble.utils.Direction;
import edu.upc.prop.scrabble.utils.Pair;
import edu.upc.prop.scrabble.utils.Vector2;
import org.junit.Test;
import scrabble.stubs.BoardViewStub;

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;

public class TestMovementCleaner {
    private Piece[] extractNecessaryPieces(Pair<Piece, Vector2>[] necessaryPiecesPositions) {
        return Arrays.stream(necessaryPiecesPositions).map(Pair::first).toArray(Piece[]::new);
    }

    @Test
    public void test1PieceCleanerVertical() {
        Board board = new JuniorBoard();
        PiecesConverter piecesConverter = new EnglishPiecesConverter();
        Player player = new Player("nom", false);
        BoardViewStub mock = new BoardViewStub();
        WordGetter wordGetter = new WordGetter(board);
        PointCalculator pointCalculator = new PointCalculator(board);
        WordPlacer wordPlacer = new WordPlacer(player, board, mock, pointCalculator);
        Piece[] pieces = new Piece[] {new Piece("T", 0)};
        wordPlacer.run(pieces, 0, 0, Direction.Vertical);
        MovementCleaner sut = new MovementCleaner(board, piecesConverter);
        Movement movement = new Movement("TEST", 0, 0, Direction.Vertical);
        Pair<Piece, Vector2>[] sutPieces = sut.run(movement);

        Piece[] estimatedPieces = new Piece[] {new Piece("E", 0), new Piece("S", 0), new Piece("T", 0)};
        assertArrayEquals(estimatedPieces, extractNecessaryPieces(sutPieces));
    }

    @Test
    public void test2PiecesCleanerVertical() {
        Board board = new JuniorBoard();
        PiecesConverter piecesConverter = new EnglishPiecesConverter();
        Player player = new Player("nom", false);
        BoardViewStub mock = new BoardViewStub();
        WordGetter wordGetter = new WordGetter(board);
        PointCalculator pointCalculator = new PointCalculator(board);
        WordPlacer wordPlacer = new WordPlacer(player, board, mock, pointCalculator);
        Piece[] pieces = new Piece[] {
                new Piece("T", 0),
                new Piece("E", 0)
        };
        wordPlacer.run(pieces, 0, 0, Direction.Vertical);
        MovementCleaner sut = new MovementCleaner(board, piecesConverter);
        Movement movement = new Movement("TEST", 0, 0, Direction.Vertical);
        Pair<Piece, Vector2>[] sutPieces = sut.run(movement);

        Piece[] estimatedPieces = new Piece[] {
                new Piece("S", 0),
                new Piece("T", 0)
        };
        assertArrayEquals(estimatedPieces, extractNecessaryPieces(sutPieces));
    }

    @Test
    public void test3PiecesCleanerVertical() {
        Board board = new JuniorBoard();
        PiecesConverter piecesConverter = new EnglishPiecesConverter();
        Player player = new Player("nom", false);
        BoardViewStub mock = new BoardViewStub();
        WordGetter wordGetter = new WordGetter(board);
        PointCalculator pointCalculator = new PointCalculator(board);
        WordPlacer wordPlacer = new WordPlacer(player, board, mock, pointCalculator);
        Piece[] pieces = new Piece[] {
                new Piece("T", 0),
                new Piece("E", 0),
                new Piece("S", 0)
        };
        wordPlacer.run(pieces, 0, 0, Direction.Vertical);
        MovementCleaner sut = new MovementCleaner(board, piecesConverter);
        Movement movement = new Movement("TEST", 0, 0, Direction.Vertical);
        Pair<Piece, Vector2>[] sutPieces = sut.run(movement);

        Piece[] estimatedPieces = new Piece[] {
                new Piece("T", 0)
        };
        assertArrayEquals(estimatedPieces, extractNecessaryPieces(sutPieces));
    }
    @Test
    public void testAllPiecesCleanerVertical() {
        Board board = new JuniorBoard();
        PiecesConverter piecesConverter = new EnglishPiecesConverter();
        Player player = new Player("nom", false);
        BoardViewStub mock = new BoardViewStub();
        WordGetter wordGetter = new WordGetter(board);
        PointCalculator pointCalculator = new PointCalculator(board);
        WordPlacer wordPlacer = new WordPlacer(player, board, mock, pointCalculator);
        Piece[] pieces = new Piece[] {
                new Piece("T", 0),
                new Piece("E", 0),
                new Piece("S", 0),
                new Piece("T", 0)
        };
        wordPlacer.run(pieces, 0, 0, Direction.Vertical);
        MovementCleaner sut = new MovementCleaner(board, piecesConverter);
        Movement movement = new Movement("TEST", 0, 0, Direction.Vertical);
        Pair<Piece, Vector2>[] sutPieces = sut.run(movement);

        Piece[] estimatedPieces = new Piece[0];
        assertArrayEquals(estimatedPieces, extractNecessaryPieces(sutPieces));
    }



    @Test
    public void test1PieceCleanerHorizontal() {
        Board board = new JuniorBoard();
        PiecesConverter piecesConverter = new EnglishPiecesConverter();
        Player player = new Player("nom", false);
        BoardViewStub mock = new BoardViewStub();
        WordGetter wordGetter = new WordGetter(board);
        PointCalculator pointCalculator = new PointCalculator(board);
        WordPlacer wordPlacer = new WordPlacer(player, board, mock, pointCalculator);
        Piece[] pieces = new Piece[] {new Piece("T", 0)};
        wordPlacer.run(pieces, 0, 0, Direction.Horizontal);
        MovementCleaner sut = new MovementCleaner(board, piecesConverter);
        Movement movement = new Movement("TEST", 0, 0, Direction.Horizontal);
        Pair<Piece, Vector2>[] sutPieces = sut.run(movement);

        Piece[] estimatedPieces = new Piece[] {new Piece("E", 0), new Piece("S", 0), new Piece("T", 0)};
        assertArrayEquals(estimatedPieces, extractNecessaryPieces(sutPieces));
    }

    @Test
    public void test2PiecesCleanerHorizontal() {
        Board board = new JuniorBoard();
        PiecesConverter piecesConverter = new EnglishPiecesConverter();
        Player player = new Player("nom", false);
        BoardViewStub mock = new BoardViewStub();
        WordGetter wordGetter = new WordGetter(board);
        PointCalculator pointCalculator = new PointCalculator(board);
        WordPlacer wordPlacer = new WordPlacer(player, board, mock, pointCalculator);
        Piece[] pieces = new Piece[] {
                new Piece("T", 0),
                new Piece("E", 0)
        };
        wordPlacer.run(pieces, 0, 0, Direction.Horizontal);
        MovementCleaner sut = new MovementCleaner(board, piecesConverter);
        Movement movement = new Movement("TEST", 0, 0, Direction.Horizontal);
        Pair<Piece, Vector2>[] sutPieces = sut.run(movement);

        Piece[] estimatedPieces = new Piece[] {
                new Piece("S", 0),
                new Piece("T", 0)
        };
        assertArrayEquals(estimatedPieces, extractNecessaryPieces(sutPieces));
    }

    @Test
    public void test3PiecesCleanerHorizontal() {
        Board board = new JuniorBoard();
        PiecesConverter piecesConverter = new EnglishPiecesConverter();
        Player player = new Player("nom", false);
        BoardViewStub mock = new BoardViewStub();
        WordGetter wordGetter = new WordGetter(board);
        PointCalculator pointCalculator = new PointCalculator(board);
        WordPlacer wordPlacer = new WordPlacer(player, board, mock, pointCalculator);
        Piece[] pieces = new Piece[] {
                new Piece("T", 0),
                new Piece("E", 0),
                new Piece("S", 0)
        };
        wordPlacer.run(pieces, 0, 0, Direction.Horizontal);
        MovementCleaner sut = new MovementCleaner(board, piecesConverter);
        Movement movement = new Movement("TEST", 0, 0, Direction.Horizontal);
        Pair<Piece, Vector2>[] sutPieces = sut.run(movement);

        Piece[] estimatedPieces = new Piece[] {
                new Piece("T", 0)
        };
        assertArrayEquals(estimatedPieces, extractNecessaryPieces(sutPieces));
    }
    @Test
    public void testAllPiecesCleanerHorizontal() {
        Board board = new JuniorBoard();
        PiecesConverter piecesConverter = new EnglishPiecesConverter();
        Player player = new Player("nom", false);
        BoardViewStub mock = new BoardViewStub();
        WordGetter wordGetter = new WordGetter(board);
        PointCalculator pointCalculator = new PointCalculator(board);
        WordPlacer wordPlacer = new WordPlacer(player, board, mock, pointCalculator);
        Piece[] pieces = new Piece[] {
                new Piece("T", 0),
                new Piece("E", 0),
                new Piece("S", 0),
                new Piece("T", 0)
        };
        wordPlacer.run(pieces, 0, 0, Direction.Horizontal);
        MovementCleaner sut = new MovementCleaner(board, piecesConverter);
        Movement movement = new Movement("TEST", 0, 0, Direction.Horizontal);
        Pair<Piece, Vector2>[] sutPieces = sut.run(movement);

        Piece[] estimatedPieces = new Piece[0];
        assertArrayEquals(estimatedPieces, extractNecessaryPieces(sutPieces));
    }
}