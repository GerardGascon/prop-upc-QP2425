package edu.upc.prop.scrabble.domain.exceptions;

/**
 * Thrown to indicate that a movement is outside the board
 * @author Gerard Gascón
 */
public class MovementOutsideOfBoardException extends ScrabbleException {
    public MovementOutsideOfBoardException(String message) {
        super(message);
    }
}
