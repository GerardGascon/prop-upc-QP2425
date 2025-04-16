package edu.upc.prop.scrabble.domain.movement;

import edu.upc.prop.scrabble.data.board.Board;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.data.Movement;
import edu.upc.prop.scrabble.domain.pieces.PiecesConverter;
import edu.upc.prop.scrabble.utils.Direction;
import edu.upc.prop.scrabble.utils.Pair;
import edu.upc.prop.scrabble.utils.Vector2;

import java.util.ArrayList;

/***
 * Filters pieces from a new word placement that overlap with existing board pieces.
 * It handles both vertical and horizontal word orientations.
 * @author Gina Escofet GonzÃ¡lez
 */
public class MovementCleaner {
    private final Board board;
    private final PiecesConverter piecesConverter;

    /***
     * Constructs a MovementCleaner with the game board and pieces converter.
     * @param board The current board state.
     * @param piecesConverter Converts between string representations and Piece arrays.
     * @throws IllegalArgumentException if either parameter is null.
     */
    public MovementCleaner(Board board, PiecesConverter piecesConverter) {
        if (board == null) {
            throw new IllegalArgumentException("Board cannot be null");
        }
        if (piecesConverter == null) {
            throw new IllegalArgumentException("PiecesConverter cannot be null");
        }
        this.board = board;
        this.piecesConverter = piecesConverter;
    }

    /***
     * Filters and returns only the new pieces needed for a word placement.
     * @param movement The proposed word placement containing: the word to place, the starting coordinates (x,y), and
     * the placement direction (horizontal/vertical).
     * @return Array of pieces needed to complete the word placement.
     * @throws IllegalArgumentException if movement is null
     */
    public Pair<Piece, Vector2>[] run(Movement movement) {
        if (movement == null) {
            throw new IllegalArgumentException("Movement cannot be null");
        }
        Direction direction = movement.direction();
        if (direction == Direction.Vertical) {
            return CleanVertical(movement);
        }
        else {
            return CleanHorizontal(movement);
        }
    }

    private Pair<Piece, Vector2>[] CleanHorizontal(Movement movement) {
        ArrayList<Pair<Piece, Vector2>> requiredPieces= new ArrayList<>();
        int x = movement.x();
        int y = movement.y();
        Piece[] allPieces = piecesConverter.run(movement.word());
        int n = allPieces.length;
        for (int i = 0; i < n; ++i) {
            if (board.isCellEmpty(x + i, y)) {
                requiredPieces.add(new Pair<>(allPieces[i], new Vector2(x + i, y)));
            }
            else {
                Piece pieceInCell = board.getCellPiece(x, y + i);
                if (pieceInCell == allPieces[i]) {
                    continue;
                }
                // else throw exception
            }
        }
        return requiredPieces.toArray(Pair[]::new);

    }

    private Pair<Piece, Vector2>[] CleanVertical(Movement movement) {
        ArrayList<Pair<Piece, Vector2>> requiredPieces = new ArrayList<>();
        int x = movement.x();
        int y = movement.y();
        Piece[] allPieces = piecesConverter.run(movement.word());
        int n = allPieces.length;
        for (int i = 0; i < n; ++i) {
            if (board.isCellEmpty(x, y + i)) {
                requiredPieces.add(new Pair<>(allPieces[i], new Vector2(x, y + i)));
            }
            else {
                Piece pieceInCell = board.getCellPiece(x + i, y);
                if (pieceInCell == allPieces[i]) {
                    continue;
                }
                // else throw exception
            }
        }
        return requiredPieces.toArray(Pair[]::new);
    }
}