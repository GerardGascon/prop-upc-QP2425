package edu.upc.prop.scrabble.domain.exceptions;

/**
 * Abstract base class for all exceptions specific to the Scrabble game.
 * <p>
 * This class is the parent for all exceptions that are thrown in the context of a Scrabble game.
 * It extends {@link RuntimeException}, allowing for unchecked exceptions that can be thrown during the game
 * whenever there are rule violations or invalid game actions.
 * </p>
 *
 * @author Gerard Gascón
 */
public abstract class ScrabbleException extends RuntimeException {
    /**
     * Constructs a ScrabbleException with the specified detail message.
     *
     * @param message The detail message explaining the cause of the exception.
     */
    public ScrabbleException(String message) {
        super(message);
    }
}
