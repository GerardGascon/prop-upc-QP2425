package edu.upc.prop.scrabble.persistence.runtime.interfaces;

/**
 * Interfície per llegir dades desades des d’un fitxer.
 *
 * @author Gerard Gascón
 */
public interface ISaveReader {
    /**
     * Llegeix el fitxer de guardat i el converteix en una cadena.
     *
     * @param fileName Nom del fitxer a llegir.
     * @return null si el fitxer no existeix, String en cas contrari.
     */
    String read(String fileName);

    /**
     * Comprova que el fitxer de guardat existeixi.

     * @param fileName Nom del fitxer de guardat.
     * @return true si el fitxer existeix.
     */
    boolean exists(String fileName);
}
