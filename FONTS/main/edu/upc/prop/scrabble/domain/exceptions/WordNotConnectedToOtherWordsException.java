package edu.upc.prop.scrabble.domain.exceptions;

/**
 * Exception thrown to indicate that a word is not connected to any other words on the board.
 * <p>
 * This exception is thrown when a player attempts to place a word on the board that is not
 * connected to any previously placed words, violating the rule that all new words must be
 * connected to existing words on the board.
 * </p>
 *
 * @author Gerard Gascón
 */
public class WordNotConnectedToOtherWordsException extends ScrabbleException {
    /**
     * Constructs a WordNotConnectedToOtherWordsException with the specified detail message.
     *
     * @param message The detail message explaining the cause of the exception.
     */
    public WordNotConnectedToOtherWordsException(String message) {
        super(message);
    }
}
