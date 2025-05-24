package edu.upc.prop.scrabble.persistence.runtime.interfaces;

import edu.upc.prop.scrabble.persistence.runtime.data.PersistentObject;

/**
 * Interfície per serialitzar un PersistentObject a String.
 *
 * @author Gerard Gascón
 */
public interface ISerializer {
    /**
     * Serialitza un PersistentObject a una representació en String.
     *
     * @param object Objecte persistent a serialitzar.
     * @return La representació en String de l’objecte.
     */
    String serialize(PersistentObject object);
}
