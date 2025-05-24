package edu.upc.prop.scrabble.persistence.runtime.interfaces;

import edu.upc.prop.scrabble.persistence.runtime.data.PersistentObject;

/**
 * Interfície que defineix el contracte per a la deserialització de dades.
 * <p>
 * Implementacions d'aquesta interfície han de convertir una cadena de text (normalment JSON o similar)
 * a un objecte PersistentObject o alguna subclasse d'aquest.
 * </p>
 *
 * @author Gerard Gascón
 */
public interface IDeserializer {
    /**
     * Deserialitza una cadena de dades en un objecte PersistentObject del tipus especificat.
     *
     * @param <T>   Tipus d'objecte PersistentObject o subclasse que es vol obtenir.
     * @param data  Dades en format de cadena (per exemple JSON) que es volen deserialitzar.
     * @param clazz Classe de l'objecte PersistentObject esperat.
     * @return Objecte deserialitzat de tipus T.
     */
    <T extends PersistentObject> T deserialize(String data, Class<T> clazz);
}
