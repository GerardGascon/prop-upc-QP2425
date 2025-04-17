package edu.upc.prop.scrabble.domain.exceptions;

public class InitialMoveNotInCenterException extends RuntimeException {
    public InitialMoveNotInCenterException(String message) {
        super(message);
    }
}
