package edu.upc.prop.scrabble.persistence.runtime.interfaces;

import edu.upc.prop.scrabble.persistence.runtime.data.PersistentObject;

public interface IDeserializer {
    public <T extends PersistentObject> T deserialize(String data, Class<T> clazz);
}
