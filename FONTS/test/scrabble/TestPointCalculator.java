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
}
