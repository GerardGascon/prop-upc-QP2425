package edu.upc.prop.scrabble.domain.board;

import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.data.board.Board;
import edu.upc.prop.scrabble.utils.Direction;
import edu.upc.prop.scrabble.utils.Vector2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * This class is responsible for identifying and retrieving the pieces that form a new word on the Scrabble board
 * after placing new pieces. It handles cases where the new pieces extend existing words or form new words.
 *
 * @author Gerard Gascón
 */
public class WordGetter {
    private final Board board;

    /**
     * Constructs a WordGetter instance that operates on the provided board.
     *
     * @param board The board where pieces are placed and words are formed.
     */
    public WordGetter(Board board) {
        this.board = board;
    }

    /**
     * Retrieves the pieces that form a new word on the board after placing new pieces.
     * <p>
     * This method is useful when placing pieces that extend an existing word or create new words.
     * It checks both the newly added pieces and their positions on the board, and returns all the pieces that
     * form a continuous word either horizontally or vertically.
     *
     * @param newPieces An array of the new pieces being added to the board.
     * @param newPositions An array of positions for the new pieces on the board.
     * @param direction The direction (horizontal or vertical) in which the new word is formed.
     * @return An array of pieces that form the new word created on the board.
     * @see Piece
     * @see Direction
     */
    public Piece[] run(Piece[] newPieces, Vector2[] newPositions, Direction direction) {
        List<Piece> pieces = new ArrayList<>();
        List<Vector2> positions = new ArrayList<>();

        for (int i = 0; i < board.getSize(); i++) {
            int x = getXPosition(newPositions[0], direction, i);
            int y = getYPosition(newPositions[0], direction, i);

            if (!board.isCellEmpty(x, y)) {
                positions.add(new Vector2(x, y));
                pieces.add(board.getCellPiece(x, y));
                continue;
            }

            int positionInNewList = Arrays.asList(newPositions).indexOf(new Vector2(x, y));
            boolean containsPosition = positionInNewList != -1;
            if (containsPosition) {
                positions.add(newPositions[positionInNewList]);
                pieces.add(newPieces[positionInNewList]);
                continue;
            }

            if (new HashSet<>(positions).containsAll(Arrays.asList(newPositions)))
                return pieces.toArray(new Piece[0]);

            pieces.clear();
        }

        return pieces.toArray(new Piece[0]);
    }

    private static int getYPosition(Vector2 position, Direction direction, int offset) {
        return direction == Direction.Horizontal ? position.y : offset;
    }

    private static int getXPosition(Vector2 position, Direction direction, int offset) {
        return direction == Direction.Horizontal ? offset : position.x;
    }
}
