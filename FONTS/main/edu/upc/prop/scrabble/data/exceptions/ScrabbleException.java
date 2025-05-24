package edu.upc.prop.scrabble.data.exceptions;

/**
 * Classe base abstracta per a totes les excepcions específiques del joc de Scrabble.
 * <p>
 * Aquesta classe és la mare de totes les excepcions que es llancen en el context d’una partida de Scrabble.
 * Estén {@link RuntimeException}, cosa que permet utilitzar excepcions no comprovades que poden ser llançades
 * durant el joc quan hi ha infraccions de les regles o accions de joc no vàlides.
 * </p>
 *
 * @author Gerard Gascón
 */
public abstract class ScrabbleException extends RuntimeException {
    /**
     * Construeix una excepció de tipus Scrabble amb el missatge de detall especificat.
     *
     * @param message El missatge explicatiu de la causa de l’excepció.
     */
    public ScrabbleException(String message) {
        super(message);
    }
}
