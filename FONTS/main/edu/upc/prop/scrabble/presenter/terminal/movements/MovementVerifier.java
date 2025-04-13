package edu.upc.prop.scrabble.presenter.terminal.movements;
import edu.upc.prop.scrabble.data.Movement;
import edu.upc.prop.scrabble.data.board.Board;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.domain.pieces.PiecesConverter;
import edu.upc.prop.scrabble.utils.Direction;


public class MovementVerifier {
    private final Board board;
    private final PiecesConverter piecesConverter;

    public MovementVerifier(Board board, PiecesConverter piecesConverter) {
        this.board = board;
        this.piecesConverter = piecesConverter;
    }
    
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
        for (int i = 0; i < n; ++i) {
            if (!board.isCellValid(x, y + i)) return false;
        }
        return true;
    }

    private boolean VerifyVertical(Movement movement) {
        int x = movement.x();
        int y = movement.y();
        Piece[] pieces = piecesConverter.run(movement.word());
        int n = pieces.length;
        for (int i = 0; i < n; ++i) {
            if (!board.isCellValid(x + i, y)) return false;
        }
        return true;
    }
}
