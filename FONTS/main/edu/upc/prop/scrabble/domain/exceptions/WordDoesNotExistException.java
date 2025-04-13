package edu.upc.prop.scrabble.domain.exceptions;

public class WordDoesNotExistException extends RuntimeException {
    public WordDoesNotExistException(String message) {
        super(message);
    }
}
