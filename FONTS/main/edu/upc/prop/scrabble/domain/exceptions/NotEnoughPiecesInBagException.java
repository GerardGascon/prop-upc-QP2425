package edu.upc.prop.scrabble.domain.exceptions;

public class NotEnoughPiecesInBagException extends ScrabbleException {
  public NotEnoughPiecesInBagException(String message) {
    super(message);
  }
}