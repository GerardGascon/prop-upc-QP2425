package edu.upc.prop.scrabble.domain.exceptions;

import edu.upc.prop.scrabble.data.exceptions.ScrabbleException;

/**
 * Excepció que es llença quan no hi ha prou fitxes restants a la bossa per completar un moviment.
 * Aquesta excepció assegura que els jugadors no puguin fer un moviment que requereixi més fitxes
 * de les que hi ha disponibles.
 *
 * @author Gerard Gascón
 */
public class NotEnoughPiecesInBagException extends ScrabbleException {
    /**
     * Crea una nova excepció amb un missatge que explica la causa.
     *
     * @param message Missatge detallat que descriu el motiu de l'excepció.
     */
    public NotEnoughPiecesInBagException(String message) {
        super(message);
    }
}
