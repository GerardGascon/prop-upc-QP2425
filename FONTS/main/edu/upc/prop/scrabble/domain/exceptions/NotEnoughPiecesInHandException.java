package edu.upc.prop.scrabble.domain.exceptions;

/**
 * Thrown to indicate that a player doesn't have enough pieces in their hand for making a move
 * @author Gerard Gascón
 */
public class NotEnoughPiecesInHandException extends RuntimeException {
    public NotEnoughPiecesInHandException(String message) {
        super(message);
    }
}
