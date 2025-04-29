package edu.upc.prop.scrabble.persistence.runtime.data;

import java.util.ArrayList;
import java.util.List;

public class PersistentArray extends PersistentObject {
    private final List<PersistentObject> elements;

    public PersistentArray(String name) {
        this(name, new ArrayList<>());
    }

    public PersistentArray(String name, List<PersistentObject> elements) {
        super(name, null);
        this.elements = elements;
    }

    public int getLength() {
        return elements.size();
    }

    @Override
    public Object getValue() {
        return elements;
    }

    @Override
    public <T> T parse(Class<T> type) throws RuntimeException {
        throw new RuntimeException("Cannot parse an array");
    }

    public void add(PersistentObject value) {
        elements.add(value);
    }

    public PersistentObject get(int index) {
        return elements.get(index);
    }
}
