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
    private final List<IPersistableObject> persistableObject = new ArrayList<>();

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
            PersistentObject object = dictionary.get(persistableObject.getClass().getSimpleName());
            if (object == null)
                continue;
            persistableObject.decode(object.toDictionary());
        }
    }
}
