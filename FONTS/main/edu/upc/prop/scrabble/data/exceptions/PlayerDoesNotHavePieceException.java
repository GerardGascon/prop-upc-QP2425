package edu.upc.prop.scrabble.data.exceptions;

/**
 * Classe per tractar excepcións quan un jugador/a no té una peça en específic.
 * Filla de ScrabbleException
 * @author Albert Usero
 * @see ScrabbleException
 */
public class PlayerDoesNotHavePieceException extends ScrabbleException {
    /**
     * Construiex una excepció amb el missatge especificat.
     * Seran sobre la manca d'alguna peça a la mà.
     * @param message Missatge explicant el motiu de l'excepció.
     */
    public PlayerDoesNotHavePieceException(String message) {
        super(message);
    }
}