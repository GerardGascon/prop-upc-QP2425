package edu.upc.prop.scrabble.domain.exceptions;

import edu.upc.prop.scrabble.data.exceptions.ScrabbleException;

/**
 * Excepció que es llença quan el primer moviment d'un jugador no es col·loca al centre del tauler.
 * Aquesta excepció garanteix que la primera paraula jugada en la partida comenci a la casella central
 * del tauler de Scrabble, que és una regla estàndard del joc.
 *
 * @author Gerard Gascón
 */
public class InitialMoveNotInCenterException extends ScrabbleException {
    /**
     * Crea una nova excepció amb un missatge que explica la causa.
     *
     * @param message Missatge de detall que descriu el motiu de l'excepció.
     */
    public InitialMoveNotInCenterException(String message) {
        super(message);
    }
}
