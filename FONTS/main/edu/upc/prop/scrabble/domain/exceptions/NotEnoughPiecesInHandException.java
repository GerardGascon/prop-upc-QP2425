package edu.upc.prop.scrabble.domain.exceptions;

public class NotEnoughPiecesInHandException extends RuntimeException {
    public NotEnoughPiecesInHandException(String message) {
        super(message);
    }
}
