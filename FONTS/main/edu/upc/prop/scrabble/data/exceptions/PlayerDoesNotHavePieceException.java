package edu.upc.prop.scrabble.data.exceptions;

public class PlayerDoesNotHavePieceException extends RuntimeException {
    public PlayerDoesNotHavePieceException(String message) {
        super(message);
    }
}