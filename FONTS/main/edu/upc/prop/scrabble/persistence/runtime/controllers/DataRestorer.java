package edu.upc.prop.scrabble.persistence.runtime.controllers;

import edu.upc.prop.scrabble.persistence.runtime.data.PersistentDictionary;
import edu.upc.prop.scrabble.persistence.runtime.data.PersistentObject;
import edu.upc.prop.scrabble.persistence.runtime.interfaces.IPersistableObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe encarregada de restaurar l'estat dels objectes persistibles a partir d'un diccionari persistent.
 * <p>
 * Permet afegir objectes que implementen la interfície IPersistableObject i,
 * mitjançant el mètode run(), restaura la seva informació codificada a partir
 * d'un PersistentDictionary.
 * </p>
 *
 * @author Gerard Gascón
 */
public class DataRestorer {
    /**
     * Llista de tots els objectes amb dades serialitzables.
     */
    private final List<IPersistableObject> persistableObject = new ArrayList<>();

    /**
     * Crea una instància del restaurador de dades.
     */
    public DataRestorer() {
    }

    /**
     * Afegeix un objecte persistible a la col·lecció per a restaurar.
     *
     * @param persistableObject Objecte que implementa IPersistableObject per afegir.
     */
    public void addPersistableObject(final IPersistableObject persistableObject) {
        this.persistableObject.add(persistableObject);
    }

    /**
     * Afegeix diversos objectes persistibles a la col·lecció per a restaurar.
     *
     * @param persistableObject Array d'objectes que implementen IPersistableObject per afegir.
     */
    public void addPersistableObjects(final IPersistableObject... persistableObject) {
        this.persistableObject.addAll(List.of(persistableObject));
    }

    /**
     * Restaura l'estat dels objectes persistibles a partir d'un diccionari persistent.
     * <p>
     * Cerca en el diccionari persistent la informació corresponent a cada objecte per
     * nom de classe i crida el seu mètode decode() per carregar l'estat.
     * </p>
     *
     * @param dictionary El PersistentDictionary que conté l'estat codificat dels objectes.
     */
    public void run(PersistentDictionary dictionary) {
        for (IPersistableObject persistableObject : persistableObject) {
            Class<?> clazz = getSuperclass(persistableObject);
            PersistentObject object = dictionary.get(clazz.getSimpleName());
            if (object == null)
                continue;
            persistableObject.decode(object.toDictionary());
        }
    }

    /**
     * Retorna el nom de la superclasse d'una classe.
     * <p>
     * Si una classe no és filla de cap, retorna la mateixa classe.
     *
     * @param persistableObject la classe de la qual obtenir la superclasse.
     * @return El tipus de la superclasse.
     */
    private static Class<?> getSuperclass(IPersistableObject persistableObject) {
        Class<?> clazz = persistableObject.getClass();
        Class<?> next = clazz.getSuperclass();
        while (next != null && !next.equals(Object.class)) {
            clazz = next;
            next = clazz.getSuperclass();
        }
        return clazz;
    }
}
