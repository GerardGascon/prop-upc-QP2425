package edu.upc.prop.scrabble.domain.exceptions;

public abstract class ScrabbleException extends RuntimeException {
    public ScrabbleException(String message) {
        super(message);
    }
}
