package edu.upc.prop.scrabble.domain.exceptions;

import edu.upc.prop.scrabble.data.exceptions.ScrabbleException;

/**
 * Excepció llençada quan una paraula no està connectada a cap altra paraula al tauler.
 * <p>
 * Aquesta excepció es llença quan un jugador intenta col·locar una paraula al tauler que no està
 * connectada a cap paraula col·locada prèviament, violant la regla que totes les paraules noves
 * han d’estar connectades a paraules ja existents al tauler.
 * </p>
 *
 * @author Gerard Gascón
 */
public class WordNotConnectedToOtherWordsException extends ScrabbleException {
    /**
     * Crea una instància de WordNotConnectedToOtherWordsException amb un missatge detallat.
     *
     * @param message El missatge que explica la causa de l’excepció.
     */
    public WordNotConnectedToOtherWordsException(String message) {
        super(message);
    }
}
