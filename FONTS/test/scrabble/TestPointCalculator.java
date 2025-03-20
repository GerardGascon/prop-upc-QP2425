package scrabble;

import edu.upc.prop.scrabble.data.Piece;
import edu.upc.prop.scrabble.data.board.Board;
import edu.upc.prop.scrabble.data.board.StandardBoard;
import edu.upc.prop.scrabble.domain.PointCalculator;
import edu.upc.prop.scrabble.domain.WordGetter;
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

        WordGetter wordGetter = new WordGetter(board);
        PointCalculator sut = new PointCalculator(board, wordGetter);

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

        WordGetter wordGetter = new WordGetter(board);
        PointCalculator sut = new PointCalculator(board, wordGetter);

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

        WordGetter wordGetter = new WordGetter(board);
        PointCalculator sut = new PointCalculator(board, wordGetter);

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

        WordGetter wordGetter = new WordGetter(board);
        PointCalculator sut = new PointCalculator(board, wordGetter);

        int points = sut.run(positions);

        assertEquals(76, points);
    }

    @Test
    public void calculatePointsForTwoWordsSharingOnePiece() {
        Board board = new StandardBoard();
        board.placePiece(new Piece("O", 1), 0, 1);
        board.placePiece(new Piece("N", 1), 0, 2);
        board.placePiece(new Piece("E", 1), 0, 3);

        board.placePiece(new Piece("T", 1), 0, 0); // 3W
        board.placePiece(new Piece("E", 1), 1, 0);
        board.placePiece(new Piece("S", 1), 2, 0);
        board.placePiece(new Piece("T", 1), 3, 0); // 2L

        Vector2[] positions = new Vector2[]{
                new Vector2(0, 0),
                new Vector2(1, 0),
                new Vector2(2, 0),
                new Vector2(3, 0)
        };

        WordGetter wordGetter = new WordGetter(board);
        PointCalculator sut = new PointCalculator(board, wordGetter);

        int points = sut.run(positions);

        assertEquals(27, points);
    }

    @Test
    public void calculatePointsForTwoWordsSharingOnePieceNotPlaced() {
        Board board = new StandardBoard();
        board.placePiece(new Piece("O", 1), 0, 1);
        board.placePiece(new Piece("N", 1), 0, 2);
        board.placePiece(new Piece("E", 1), 0, 3);

        Vector2[] positions = new Vector2[]{
                new Vector2(0, 0),
                new Vector2(1, 0),
                new Vector2(2, 0),
                new Vector2(3, 0)
        };

        Piece[] piecesArray = new Piece[]{
                new Piece("T", 1), // 3W
                new Piece("E", 1),
                new Piece("S", 1),
                new Piece("T", 1), // 2L
        };

        WordGetter wordGetter = new WordGetter(board);
        PointCalculator sut = new PointCalculator(board, wordGetter);

        int points = sut.run(positions, piecesArray);

        assertEquals(27, points);
    }

    @Test
    public void calculatePointsForThreeWordsWithDifferentMultipliers() {
        Board board = new StandardBoard();
        board.placePiece(new Piece("O", 1), 0, 1);
        board.placePiece(new Piece("N", 1), 0, 2);
        board.placePiece(new Piece("E", 1), 0, 3);

        board.placePiece(new Piece("T", 1), 3, 0);
        board.placePiece(new Piece("E", 1), 3, 1);
        board.placePiece(new Piece("A", 1), 3, 2);

        board.placePiece(new Piece("T", 1), 0, 0); // 3W
        board.placePiece(new Piece("I", 1), 1, 0);
        board.placePiece(new Piece("T", 1), 2, 0);
        board.placePiece(new Piece("E", 1), 4, 0);
        board.placePiece(new Piece("R", 1), 5, 0);
        board.placePiece(new Piece("E", 1), 6, 0);
        board.placePiece(new Piece("D", 2), 7, 0); // 3W

        Vector2[] positions = new Vector2[]{
                new Vector2(0, 0),
                new Vector2(1, 0),
                new Vector2(2, 0),
                new Vector2(4, 0),
                new Vector2(5, 0),
                new Vector2(6, 0),
                new Vector2(7, 0),
        };

        WordGetter wordGetter = new WordGetter(board);
        PointCalculator sut = new PointCalculator(board, wordGetter);

        int points = sut.run(positions);

        assertEquals(143, points);
    }

    @Test
    public void calculatePointsForThreeWordsWithDifferentMultipliersNotPlaced() {
        Board board = new StandardBoard();
        board.placePiece(new Piece("O", 1), 0, 1);
        board.placePiece(new Piece("N", 1), 0, 2);
        board.placePiece(new Piece("E", 1), 0, 3);

        board.placePiece(new Piece("T", 1), 3, 0);
        board.placePiece(new Piece("E", 1), 3, 1);
        board.placePiece(new Piece("A", 1), 3, 2);

        Piece[] piecesArray = new Piece[]{
                new Piece("T", 1), // 3W
                new Piece("I", 1),
                new Piece("T", 1),
                new Piece("E", 1),
                new Piece("R", 1),
                new Piece("E", 1),
                new Piece("D", 2), // 3W
        };

        Vector2[] positions = new Vector2[]{
                new Vector2(0, 0),
                new Vector2(1, 0),
                new Vector2(2, 0),
                new Vector2(4, 0),
                new Vector2(5, 0),
                new Vector2(6, 0),
                new Vector2(7, 0),
        };

        WordGetter wordGetter = new WordGetter(board);
        PointCalculator sut = new PointCalculator(board, wordGetter);

        int points = sut.run(positions, piecesArray);

        assertEquals(143, points);
    }

    @Test
    public void calculatePointsForThreeWordsWithGaps() {
        Board board = new StandardBoard();
        board.placePiece(new Piece("O", 1), 0, 1);
        board.placePiece(new Piece("N", 1), 0, 2);
        board.placePiece(new Piece("E", 1), 0, 3);

        board.placePiece(new Piece("T", 1), 3, 0);
        board.placePiece(new Piece("E", 1), 3, 1);
        board.placePiece(new Piece("A", 1), 3, 2);

        board.placePiece(new Piece("T", 1), 0, 0); // 3W
        board.placePiece(new Piece("E", 1), 1, 0);
        board.placePiece(new Piece("S", 1), 2, 0);
        board.placePiece(new Piece("S", 1), 4, 0);

        Vector2[] positions = new Vector2[]{
                new Vector2(0, 0),
                new Vector2(1, 0),
                new Vector2(2, 0),
                new Vector2(4, 0)
        };

        WordGetter wordGetter = new WordGetter(board);
        PointCalculator sut = new PointCalculator(board, wordGetter);

        int points = sut.run(positions);

        assertEquals(27, points);
    }

    @Test
    public void calculatePointsForThreeWordsWithGapsNotPlaced() {
        Board board = new StandardBoard();
        board.placePiece(new Piece("O", 1), 0, 1);
        board.placePiece(new Piece("N", 1), 0, 2);
        board.placePiece(new Piece("E", 1), 0, 3);

        board.placePiece(new Piece("T", 1), 3, 0);
        board.placePiece(new Piece("E", 1), 3, 1);
        board.placePiece(new Piece("A", 1), 3, 2);

        Piece[] piecesArray = new Piece[]{
                new Piece("T", 1), // 3W
                new Piece("E", 1),
                new Piece("S", 1),
                new Piece("S", 1),
        };

        Vector2[] positions = new Vector2[]{
                new Vector2(0, 0),
                new Vector2(1, 0),
                new Vector2(2, 0),
                new Vector2(4, 0)
        };

        WordGetter wordGetter = new WordGetter(board);
        PointCalculator sut = new PointCalculator(board, wordGetter);

        int points = sut.run(positions, piecesArray);

        assertEquals(27, points);
    }

    @Test
    public void calculatePointsForThreeWords() {
        Board board = new StandardBoard();
        board.placePiece(new Piece("T", 1), 0, 0);
        board.placePiece(new Piece("E", 1), 1, 0);
        board.placePiece(new Piece("S", 1), 2, 0);
        board.placePiece(new Piece("T", 1), 3, 0);

        board.placePiece(new Piece("A", 1), 2, 3);
        board.placePiece(new Piece("N", 1), 3, 3);
        board.placePiece(new Piece("T", 1), 4, 3);
        board.placePiece(new Piece("S", 1), 5, 3);

        board.placePiece(new Piece("L", 1), 1, 1);
        board.placePiece(new Piece("E", 1), 1, 2);
        board.placePiece(new Piece("P", 3), 1, 3);
        board.placePiece(new Piece("H", 4), 1, 4);
        board.placePiece(new Piece("A", 1), 1, 5);
        board.placePiece(new Piece("N", 1), 1, 6);
        board.placePiece(new Piece("T", 1), 1, 7);

        Vector2[] positions = new Vector2[]{
                new Vector2(1, 1),
                new Vector2(1, 2),
                new Vector2(1, 3),
                new Vector2(1, 4),
                new Vector2(1, 5),
                new Vector2(1, 6),
                new Vector2(1, 7)
        };

        WordGetter wordGetter = new WordGetter(board);
        PointCalculator sut = new PointCalculator(board, wordGetter);

        int points = sut.run(positions);

        assertEquals(87, points);
    }

    @Test
    public void calculatePointsForThreeWordsNotPlaced() {
        Board board = new StandardBoard();
        board.placePiece(new Piece("T", 1), 0, 0);
        board.placePiece(new Piece("E", 1), 1, 0);
        board.placePiece(new Piece("S", 1), 2, 0);
        board.placePiece(new Piece("T", 1), 3, 0);

        board.placePiece(new Piece("A", 1), 2, 3);
        board.placePiece(new Piece("N", 1), 3, 3);
        board.placePiece(new Piece("T", 1), 4, 3);
        board.placePiece(new Piece("S", 1), 5, 3);

        Piece[] piecesArray = new Piece[]{
                new Piece("L", 1),
                new Piece("E", 1),
                new Piece("P", 3),
                new Piece("H", 4),
                new Piece("A", 1),
                new Piece("N", 1),
                new Piece("T", 1),
        };

        Vector2[] positions = new Vector2[]{
                new Vector2(1, 1),
                new Vector2(1, 2),
                new Vector2(1, 3),
                new Vector2(1, 4),
                new Vector2(1, 5),
                new Vector2(1, 6),
                new Vector2(1, 7)
        };

        WordGetter wordGetter = new WordGetter(board);
        PointCalculator sut = new PointCalculator(board, wordGetter);

        int points = sut.run(positions, piecesArray);

        assertEquals(87, points);
    }
}
