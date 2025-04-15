package edu.upc.prop.scrabble.domain.exceptions;

/**
 * Thrown to indicate that a word doesn't exist in a dictionary during a move
 */
public class WordDoesNotExistException extends RuntimeException {
    public WordDoesNotExistException(String message) {
        super(message);
    }
}
