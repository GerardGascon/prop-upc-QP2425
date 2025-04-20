package edu.upc.prop.scrabble.domain.exceptions;

import edu.upc.prop.scrabble.data.exceptions.ScrabbleException;

/**
 * Exception thrown when there are not enough pieces remaining in the bag to complete a move.
 * This ensures that players cannot make a move that requires more pieces than are available.
 *
 * @author Gerard Gascón
 */
public class NotEnoughPiecesInBagException extends ScrabbleException {
    /**
     * Constructs a NotEnoughPiecesInBagException with the specified detail message.
     *
     * @param message The detail message explaining the cause of the exception.
     */
    public NotEnoughPiecesInBagException(String message) {
        super(message);
    }
}