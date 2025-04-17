package edu.upc.prop.scrabble.domain.exceptions;

public class WordNotConnectedToOtherWordsException extends RuntimeException {
    public WordNotConnectedToOtherWordsException(String message) {
        super(message);
    }
}
