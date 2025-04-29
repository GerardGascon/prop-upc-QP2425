package edu.upc.prop.scrabble.persistence.runtime.data;

import java.util.HashMap;
import java.util.Map;

public class PersistentDictionary extends PersistentObject{
    private final Map<String, PersistentObject> dictionary;

    public Map<String, PersistentObject> getDictionary() {
        return dictionary;
    }

    public PersistentDictionary(String name) {
        this(name, new HashMap<>());
    }

    public PersistentDictionary(String name, Map<String, PersistentObject> dictionary) {
        super(name, null);
        this.dictionary = dictionary;
    }

    @Override
    public <T> T parse(Class<T> type) throws RuntimeException {
        throw new RuntimeException("Cannot parse a dictionary");
    }

    public void add(PersistentObject value) {
        dictionary.put(value.getName(), value);
    }

    public PersistentObject get(String key) {
        return dictionary.get(key);
    }
}
