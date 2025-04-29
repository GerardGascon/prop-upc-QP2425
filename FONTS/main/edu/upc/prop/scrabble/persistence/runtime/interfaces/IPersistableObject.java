package edu.upc.prop.scrabble.persistence.runtime.interfaces;

import edu.upc.prop.scrabble.persistence.runtime.data.PersistentDictionary;

public interface IPersistableObject {
    PersistentDictionary encode();
    void decode(PersistentDictionary data);
}
