package edu.upc.prop.scrabble.persistence.runtime.interfaces;

import edu.upc.prop.scrabble.persistence.runtime.data.PersistentDictionary;

/**
 * Interfície que defineix un objecte persistible.
 * <p>
 * Aquest objecte pot codificar-se en un PersistentDictionary per a la seva serialització,
 * i pot ser decodificat a partir d'un PersistentDictionary per restaurar el seu estat.
 * </p>
 *
 * @author Gerard Gascón
 */
public interface IPersistableObject {
    /**
     * Codifica l'objecte actual en un PersistentDictionary.
     *
     * @return PersistentDictionary que representa l'estat serialitzat de l'objecte.
     */
    PersistentDictionary encode();

    /**
     * Decodifica i restaura l'estat de l'objecte a partir del PersistentDictionary proporcionat.
     *
     * @param data PersistentDictionary que conté l'estat serialitzat per restaurar.
     */
    void decode(PersistentDictionary data);
}
