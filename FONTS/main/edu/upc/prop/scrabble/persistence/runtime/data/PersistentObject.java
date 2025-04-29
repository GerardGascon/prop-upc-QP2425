package edu.upc.prop.scrabble.persistence.runtime.data;

public class PersistentObject {
    private String name;
    private final Object value;

    public PersistentObject(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

    public <T> T parse(Class<T> type) throws ClassCastException {
        if (type.isInstance(value)) {
            return type.cast(value);
        } else {
            throw new ClassCastException("Cannot cast object of type "
                    + value.getClass().getName() + " to " + type.getName());
        }
    }
}
