package edu.upc.prop.scrabble.persistence.runtime.controllers;

import edu.upc.prop.scrabble.persistence.runtime.data.PersistentDictionary;
import edu.upc.prop.scrabble.persistence.runtime.data.PersistentObject;
import edu.upc.prop.scrabble.persistence.runtime.interfaces.IPersistableObject;

import java.util.ArrayList;
import java.util.List;

public class DataRestorer {
    private final List<IPersistableObject> persistableObject = new ArrayList<>();

    public void addPersistableObject(final IPersistableObject persistableObject) {
        this.persistableObject.add(persistableObject);
    }

    public void addPersistableObjects(final IPersistableObject... persistableObject) {
        this.persistableObject.addAll(List.of(persistableObject));
    }

    public void run(PersistentDictionary dictionary) {
        for (IPersistableObject persistableObject : persistableObject) {
            PersistentObject object = dictionary.get(persistableObject.getClass().getSimpleName());
            persistableObject.decode((PersistentDictionary) object);
        }
    }
}
