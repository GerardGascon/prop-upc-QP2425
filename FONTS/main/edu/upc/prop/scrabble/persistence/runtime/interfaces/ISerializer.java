package edu.upc.prop.scrabble.persistence.runtime.interfaces;

import edu.upc.prop.scrabble.persistence.runtime.data.PersistentObject;

public interface ISerializer {
    String serialize(PersistentObject object);
}
