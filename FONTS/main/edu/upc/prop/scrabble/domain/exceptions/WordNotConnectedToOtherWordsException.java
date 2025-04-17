package edu.upc.prop.scrabble.domain.exceptions;

public class WordNotConnectedToOtherWordsException extends ScrabbleException {
    public WordNotConnectedToOtherWordsException(String message) {
        super(message);
    }
}
