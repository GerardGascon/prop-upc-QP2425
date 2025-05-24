package edu.upc.prop.scrabble.domain.exceptions;

import edu.upc.prop.scrabble.data.exceptions.ScrabbleException;

/**
 * Excepció que es llença quan una paraula no existeix al diccionari durant un moviment.
 * <p>
 * Aquesta excepció es llença quan un jugador intenta col·locar una paraula al tauler que no és
 * vàlida segons el diccionari de paraules acceptades. Assegura que només es facin servir paraules
 * vàlides en el joc.
 * </p>
 *
 * @author Gerard Gascón
 */
public class WordDoesNotExistException extends ScrabbleException {
    /**
     * Crea una instància de WordDoesNotExistException amb un missatge detallat.
     *
     * @param message El missatge que explica la causa de l'excepció.
     */
    public WordDoesNotExistException(String message) {
        super(message);
    }
}
