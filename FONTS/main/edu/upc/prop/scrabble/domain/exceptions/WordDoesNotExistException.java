package edu.upc.prop.scrabble.domain.exceptions;

/**
 * Exception thrown to indicate that a word does not exist in the dictionary during a move.
 * <p>
 * This exception is thrown when a player tries to place a word on the board that is not valid
 * according to the dictionary of accepted words. It ensures that only valid words are used in the game.
 * </p>
 *
 * @author Gerard Gascón
 */
public class WordDoesNotExistException extends ScrabbleException {
    /**
     * Constructs a WordDoesNotExistException with the specified detail message.
     *
     * @param message The detail message explaining the cause of the exception.
     */
    public WordDoesNotExistException(String message) {
        super(message);
    }
}
