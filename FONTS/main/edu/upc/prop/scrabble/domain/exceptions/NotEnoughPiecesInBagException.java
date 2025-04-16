package edu.upc.prop.scrabble.domain.exceptions;

public class NotEnoughPiecesInBagException extends RuntimeException {
  public NotEnoughPiecesInBagException(String message) {
    super(message);
  }
}