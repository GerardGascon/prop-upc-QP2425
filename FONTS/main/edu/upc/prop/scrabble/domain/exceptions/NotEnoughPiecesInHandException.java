package edu.upc.prop.scrabble.domain.exceptions;

/**
 * Exception thrown when a player does not have enough pieces in their hand to make a move.
 * This ensures that the game logic prevents invalid moves due to a lack of available pieces.
 *
 * @author Gerard Gascón
 */
public class NotEnoughPiecesInHandException extends ScrabbleException {
    /**
     * Constructs a NotEnoughPiecesInHandException with the specified detail message.
     *
     * @param message The detail message explaining the cause of the exception.
     */
    public NotEnoughPiecesInHandException(String message) {
        super(message);
    }
}
