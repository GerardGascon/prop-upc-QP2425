package edu.upc.prop.scrabble.domain.exceptions;

import edu.upc.prop.scrabble.data.exceptions.ScrabbleException;

/**
 * Exception thrown when a player's initial move is not placed in the center of the board.
 * This exception ensures that the first word played in the game starts at the center tile of the Scrabble board.
 *
 * @author Gerard Gascón
 */
public class InitialMoveNotInCenterException extends ScrabbleException {
    /**
     * Constructs an InitialMoveNotInCenterException with the specified detail message.
     *
     * @param message The detail message explaining the cause of the exception.
     */
    public InitialMoveNotInCenterException(String message) {
        super(message);
    }
}
