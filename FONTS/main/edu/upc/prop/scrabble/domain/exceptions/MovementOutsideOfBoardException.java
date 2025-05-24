package edu.upc.prop.scrabble.domain.exceptions;

import edu.upc.prop.scrabble.data.exceptions.ScrabbleException;

/**
 * Excepció que es llença quan s'intenta fer un moviment fora dels límits del tauler de Scrabble.
 * Aquesta excepció garanteix que els jugadors no puguin col·locar fitxes fora de l'àrea jugable del tauler,
 * evitant així jugades invàlides o errors durant la partida.
 *
 * @author Gerard Gascón
 */
public class MovementOutsideOfBoardException extends ScrabbleException {
    /**
     * Crea una nova excepció amb un missatge que explica la causa.
     *
     * @param message Missatge detallat que descriu el motiu de l'excepció.
     */
    public MovementOutsideOfBoardException(String message) {
        super(message);
    }
}
