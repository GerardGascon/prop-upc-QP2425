package edu.upc.prop.scrabble.persistence.runtime.controllers;

import edu.upc.prop.scrabble.persistence.runtime.data.PersistentDictionary;
import edu.upc.prop.scrabble.persistence.runtime.data.PersistentObject;
import edu.upc.prop.scrabble.persistence.runtime.interfaces.IPersistableObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe encarregada de recopilar objectes persistibles i generar un diccionari persistent.
 * <p>
 * Permet afegir objectes que implementen la interfície IPersistableObject i,
 * mitjançant el mètode run(), genera un PersistentDictionary que conté
 * la representació persistent de tots aquests objectes.
 * </p>
 *
 * @author Gerard Gascón
 */
public class DataCollector {
    private final List<IPersistableObject> persistableObject = new ArrayList<>();

    /**
     * Afegeix un objecte persistible a la col·lecció.
     *
     * @param persistableObject Objecte que implementa IPersistableObject per afegir.
     */
    public void addPersistableObject(final IPersistableObject persistableObject) {
        this.persistableObject.add(persistableObject);
    }

    /**
     * Afegeix diversos objectes persistibles a la col·lecció.
     *
     * @param persistableObject Array d'objectes que implementen IPersistableObject per afegir.
     */
    public void addPersistableObjects(final IPersistableObject... persistableObject) {
        this.persistableObject.addAll(List.of(persistableObject));
    }

    /**
     * Genera un diccionari persistent amb la representació de tots els objectes persistibles afegits.
     * <p>
     * Cada objecte persistent s'afegeix al diccionari amb el seu nom de classe com a clau.
     * </p>
     *
     * @return Un PersistentDictionary amb tots els objectes persistibles codificats.
     */
    public PersistentDictionary run() {
        PersistentDictionary data = new PersistentDictionary(null);
        for (IPersistableObject persistableObject : persistableObject) {
            PersistentObject object = persistableObject.encode();
            object.setName(persistableObject.getClass().getSimpleName());
            data.add(object);
        }
        return data;
    }
}
