package scrabble;

import edu.upc.prop.scrabble.data.Piece;
import edu.upc.prop.scrabble.data.board.Board;
import edu.upc.prop.scrabble.data.board.StandardBoard;
import edu.upc.prop.scrabble.domain.PointCalculator;
import edu.upc.prop.scrabble.utils.Vector2;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestPointCalculator {
    @Test
    public void calculateStandardPointsWithNoPremium() {
        Board board = new StandardBoard();
        board.placePiece(new Piece("T", 2), 5, 4);
        board.placePiece(new Piece("E", 1), 6, 4);
        board.placePiece(new Piece("S", 1), 7, 4);
        board.placePiece(new Piece("T", 2), 8, 4);

        Vector2[] positions = new Vector2[]{
                new Vector2(5, 4), new Vector2(6, 4), new Vector2(7, 4), new Vector2(8, 4)
        };

        PointCalculator sut = new PointCalculator(board);

        int points = sut.run(positions);

        assertEquals(6, points);
    }

    @Test
    public void calculateStandardPointsWithPremiumLetter() {
        Board board = new StandardBoard();
        board.placePiece(new Piece("T", 2), 5, 5); // This is on a x3 cell
        board.placePiece(new Piece("E", 1), 6, 5);
        board.placePiece(new Piece("S", 1), 7, 5);
        board.placePiece(new Piece("T", 2), 8, 5);

        Vector2[] positions = new Vector2[]{
                new Vector2(5, 5), new Vector2(6, 5), new Vector2(7, 5), new Vector2(8, 5)
        };

        PointCalculator sut = new PointCalculator(board);

        int points = sut.run(positions);

        assertEquals(10, points);
    }

    @Test
    public void calculateStandardPointsWithPremiumWord() {
        Board board = new StandardBoard();
        board.placePiece(new Piece("T", 2), 4, 4); // This is on a x2 word cell
        board.placePiece(new Piece("E", 1), 5, 4);
        board.placePiece(new Piece("S", 1), 6, 4);
        board.placePiece(new Piece("T", 2), 7, 4);

        Vector2[] positions = new Vector2[]{
                new Vector2(4, 4), new Vector2(5, 4), new Vector2(6, 4), new Vector2(7, 4)
        };

        PointCalculator sut = new PointCalculator(board);

        int points = sut.run(positions);

        assertEquals(12, points);
    }

    @Test
    public void calculateBingoBonus() {
        Board board = new StandardBoard();
        board.placePiece(new Piece("D", 3), 7, 1);
        board.placePiece(new Piece("E", 1), 7, 2);
        board.placePiece(new Piece("S", 1), 7, 3); // x2 letter
        board.placePiece(new Piece("S", 2), 7, 4);
        board.placePiece(new Piece("E", 1), 7, 5);
        board.placePiece(new Piece("R", 2), 7, 6);
        board.placePiece(new Piece("T", 2), 7, 7); // x2 word

        Vector2[] positions = new Vector2[]{
                new Vector2(7, 1),
                new Vector2(7, 2),
                new Vector2(7, 3),
                new Vector2(7, 4),
                new Vector2(7, 5),
                new Vector2(7, 6),
                new Vector2(7, 7),
        };

        PointCalculator sut = new PointCalculator(board);

        int points = sut.run(positions);

        assertEquals(76, points);
    }
}
