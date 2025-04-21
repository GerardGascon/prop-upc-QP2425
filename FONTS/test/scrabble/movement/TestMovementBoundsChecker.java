package scrabble.movement;

import edu.upc.prop.scrabble.data.Movement;
import edu.upc.prop.scrabble.data.board.Board;
import edu.upc.prop.scrabble.data.board.JuniorBoard;
import edu.upc.prop.scrabble.data.board.StandardBoard;
import edu.upc.prop.scrabble.data.board.SuperBoard;
import edu.upc.prop.scrabble.domain.movement.MovementBoundsChecker;
import edu.upc.prop.scrabble.domain.pieces.EnglishPiecesConverter;
import edu.upc.prop.scrabble.domain.pieces.PiecesConverter;
import edu.upc.prop.scrabble.utils.Direction;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestMovementBoundsChecker {
    @Test
    public void testExactJuniorHorizontalBounds() {
        Board board = new JuniorBoard();
        PiecesConverter converter = new EnglishPiecesConverter();
        MovementBoundsChecker sut = new MovementBoundsChecker(board, converter);
        Movement m = new Movement("aaaaaaaaaaa", 0, 0, Direction.Horizontal);
        assertTrue(sut.run(m));
    }

    @Test
    public void testExactJuniorVerticalBounds() {
        Board board = new JuniorBoard();
        PiecesConverter converter = new EnglishPiecesConverter();
        MovementBoundsChecker sut = new MovementBoundsChecker(board, converter);
        Movement m = new Movement("aaaaaaaaaaa", 0, 0, Direction.Vertical);
        assertTrue(sut.run(m));
    }

    @Test
    public void testMoreThanJuniorHorizontalBounds() {
        Board board = new JuniorBoard();
        PiecesConverter converter = new EnglishPiecesConverter();
        MovementBoundsChecker sut = new MovementBoundsChecker(board, converter);
        Movement m = new Movement("aaaaaaaaaaaa", 0, 0, Direction.Horizontal);
        assertFalse(sut.run(m));
    }

    @Test
    public void testMoreThanJuniorVerticalBounds() {
        Board board = new JuniorBoard();
        PiecesConverter converter = new EnglishPiecesConverter();
        MovementBoundsChecker sut = new MovementBoundsChecker(board, converter);
        Movement m = new Movement("aaaaaaaaaaaa", 0, 0, Direction.Vertical);
        assertFalse(sut.run(m));
    }

    @Test
    public void testExactStandardHorizontalBounds() {
        Board board = new StandardBoard();
        PiecesConverter converter = new EnglishPiecesConverter();
        MovementBoundsChecker sut = new MovementBoundsChecker(board, converter);
        Movement m = new Movement("aaaaaaaaaaaaaaa", 0, 0, Direction.Horizontal);
        assertTrue(sut.run(m));
    }

    @Test
    public void testExactStandardVerticalBounds() {
        Board board = new StandardBoard();
        PiecesConverter converter = new EnglishPiecesConverter();
        MovementBoundsChecker sut = new MovementBoundsChecker(board, converter);
        Movement m = new Movement("aaaaaaaaaaaaaaa", 0, 0, Direction.Vertical);
        assertTrue(sut.run(m));
    }

    @Test
    public void testMoreThanStandardHorizontalBounds() {
        Board board = new StandardBoard();
        PiecesConverter converter = new EnglishPiecesConverter();
        MovementBoundsChecker sut = new MovementBoundsChecker(board, converter);
        Movement m = new Movement("aaaaaaaaaaaaaaaa", 0, 0, Direction.Horizontal);
        assertFalse(sut.run(m));
    }

    @Test
    public void testMoreThanStandardVerticalBounds() {
        Board board = new StandardBoard();
        PiecesConverter converter = new EnglishPiecesConverter();
        MovementBoundsChecker sut = new MovementBoundsChecker(board, converter);
        Movement m = new Movement("aaaaaaaaaaaaaaaa", 0, 0, Direction.Vertical);
        assertFalse(sut.run(m));
    }

    @Test
    public void testExactSuperHorizontalBounds() {
        Board board = new SuperBoard();
        PiecesConverter converter = new EnglishPiecesConverter();
        MovementBoundsChecker sut = new MovementBoundsChecker(board, converter);
        Movement m = new Movement("aaaaaaaaaaaaaaaaaaaaa", 0, 0, Direction.Horizontal);
        assertTrue(sut.run(m));
    }

    @Test
    public void testExactSuperVerticalBounds() {
        Board board = new SuperBoard();
        PiecesConverter converter = new EnglishPiecesConverter();
        MovementBoundsChecker sut = new MovementBoundsChecker(board, converter);
        Movement m = new Movement("aaaaaaaaaaaaaaaaaaaaa", 0, 0, Direction.Vertical);
        assertTrue(sut.run(m));
    }

    @Test
    public void testMoreThanSuperHorizontalBounds() {
        Board board = new SuperBoard();
        PiecesConverter converter = new EnglishPiecesConverter();
        MovementBoundsChecker sut = new MovementBoundsChecker(board, converter);
        Movement m = new Movement("aaaaaaaaaaaaaaaaaaaaaa", 0, 0, Direction.Horizontal);
        assertFalse(sut.run(m));
    }

    @Test
    public void testMoreThanSuperVerticalBounds() {
        Board board = new SuperBoard();
        PiecesConverter converter = new EnglishPiecesConverter();
        MovementBoundsChecker sut = new MovementBoundsChecker(board, converter);
        Movement m = new Movement("aaaaaaaaaaaaaaaaaaaaaa", 0, 0, Direction.Vertical);
        assertFalse(sut.run(m));
    }
}
