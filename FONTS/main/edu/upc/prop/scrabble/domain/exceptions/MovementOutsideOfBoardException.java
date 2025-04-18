package edu.upc.prop.scrabble.domain.exceptions;

/**
 * Exception thrown when a movement is attempted outside the boundaries of the Scrabble board.
 * This exception ensures that players cannot place pieces beyond the playable area of the board.
 *
 * @author Gerard Gascón
 */
public class MovementOutsideOfBoardException extends ScrabbleException {
    /**
     * Constructs a MovementOutsideOfBoardException with the specified detail message.
     *
     * @param message The detail message explaining the cause of the exception.
     */
    public MovementOutsideOfBoardException(String message) {
        super(message);
    }
}
