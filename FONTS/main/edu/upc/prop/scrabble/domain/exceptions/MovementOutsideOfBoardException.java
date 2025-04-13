package edu.upc.prop.scrabble.domain.exceptions;

public class MovementOutsideOfBoardException extends RuntimeException {
    public MovementOutsideOfBoardException(String message) {
        super(message);
    }
}
