package edu.upc.prop.scrabble.persistence.runtime.controllers;

import edu.upc.prop.scrabble.persistence.runtime.data.PersistentDictionary;
import edu.upc.prop.scrabble.persistence.runtime.data.PersistentObject;
import edu.upc.prop.scrabble.persistence.runtime.interfaces.IPersistableObject;

import java.util.ArrayList;
import java.util.List;

public class DataCollector {
    private final List<IPersistableObject> persistableObject = new ArrayList<>();

    public void addPersistableObject(final IPersistableObject persistableObject) {
        this.persistableObject.add(persistableObject);
    }

    public void addPersistableObjects(final IPersistableObject... persistableObject) {
        this.persistableObject.addAll(List.of(persistableObject));
    }

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
