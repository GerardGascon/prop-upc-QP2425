package edu.upc.prop.scrabble.domain.movement;
import edu.upc.prop.scrabble.data.Movement;
import edu.upc.prop.scrabble.data.board.Board;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.domain.pieces.PiecesConverter;
import edu.upc.prop.scrabble.utils.Direction;

/***
 * Class to verify if a given movement is within the bounds of a board.
 * It handles both vertical and horizontal word orientations.
 * @author Gina Escofet González
 */
public class MovementBoundsChecker {
    private final Board board;
    private final PiecesConverter piecesConverter;

    public MovementBoundsChecker(Board board, PiecesConverter piecesConverter) {
        this.board = board;
        this.piecesConverter = piecesConverter;
    }

    /***
     * Verifies whether a given movement is within the bounds of the game board.
     * @param movement The movement to validate, containing the word, starting position (x, y),
     *                  and direction (vertical/horizontal).
     * @return True if the entire word fits within board bounds in the specified
 *              direction, False otherwise (including null input or null word).
     */
    public boolean run(Movement movement) {
        if (movement == null || movement.word() == null) return false;
        Direction direction = movement.direction();
        if (direction == Direction.Vertical) {
            return VerifyVertical(movement);
        }
        else {
            return VerifyHorizontal(movement);
        }
    }

    private boolean VerifyHorizontal(Movement movement) {
        int x = movement.x();
        int y = movement.y();
        Piece[] pieces = piecesConverter.run(movement.word());
        int n = pieces.length;
        return board.isCellValid(x, y) && board.isCellValid(x + n - 1, y);
    }

    private boolean VerifyVertical(Movement movement) {
        int x = movement.x();
        int y = movement.y();
        Piece[] pieces = piecesConverter.run(movement.word());
        int n = pieces.length;
        return board.isCellValid(x, y) && board.isCellValid(x, y + n - 1);
    }
}
