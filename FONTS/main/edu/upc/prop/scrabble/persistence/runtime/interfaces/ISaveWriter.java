package edu.upc.prop.scrabble.persistence.runtime.interfaces;

/**
 * Interfície per escriure dades a un fitxer de guardat.
 *
 * @author Gerard Gascón
 */
public interface ISaveWriter {
    /**
     * Escriu les dades al fitxer especificat.
     *
     * @param data Dades a escriure.
     * @param fileName Nom del fitxer on escriure les dades.
     */
    void write(String data, String fileName);
}
