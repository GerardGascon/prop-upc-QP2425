package edu.upc.prop.scrabble.persistence.runtime.data;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * Representa un objecte persistent genèric que conté un nom i un valor.
 * <p>
 * Aquesta classe serveix com a contenidor bàsic per a dades que es poden serialitzar
 * i deserialitzar, associant un nom amb un valor genèric.
 * </p>
 *
 * @author Gerard Gascón
 */
public class PersistentObject {
    /**
     * Nom identificador de l'objecte persistent
     */
    private String name;
    /**
     * Serialitzador de JSON
     */
    private static final Gson gson = new Gson();

    /**
     * Valor associat a l'objecte persistent, pot ser de qualsevol tipus
     */
    private final Object value;

    /**
     * Constructor que inicialitza l'objecte persistent amb un nom i un valor.
     *
     * @param name  Nom de l'objecte persistent.
     * @param value Valor associat a l'objecte.
     */
    public PersistentObject(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    /**
     * Assigna un nou nom a l'objecte persistent.
     *
     * @param name Nou nom que es vol assignar.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retorna el nom associat a aquest objecte persistent.
     *
     * @return El nom de l'objecte.
     */
    public String getName() {
        return name;
    }

    /**
     * Retorna el valor associat a aquest objecte persistent.
     *
     * @return Valor de l'objecte.
     */
    public Object getValue() {
        return value;
    }

    /**
     * Intenta convertir el valor a un tipus específic.
     *
     * @param <T>  Tipus al qual es vol convertir el valor.
     * @param type Classe del tipus al qual es vol fer el càsting.
     * @return El valor convertit al tipus especificat.
     * @throws ClassCastException Si el valor no pot ser convertit al tipus especificat.
     */
    public <T> T parse(Class<T> type) throws ClassCastException {
        return parse(value, type);
    }

    /**
     * Intenta convertir el valor a un tipus específic.
     *
     * @param value valor a convertir.
     * @param type Classe del tipus al qual es vol fer el càsting.
     * @return El valor convertit al tipus especificat.
     * @param <T> Classe del tipus al qual es vol fer el càsting.
     */
    protected <T> T parse(Object value, Class<T> type) {
        if (value == null)
            return null;

        if (type.isInstance(value)) {
            return type.cast(value);
        }

        try {
            String json = gson.toJson(value);
            return gson.fromJson(json, type);
        } catch (Exception e) {
            throw new ClassCastException("Cannot cast object of type "
                    + value.getClass().getName() + " to " + type.getName()
                    + ". Conversion failed: " + e.getMessage());
        }
    }

    /**
     * Converteix l'objecte en un diccionari.
     *
     * @return Una versió del mateix objecte convertit en un diccionari.
     */
    public PersistentDictionary toDictionary() {
        if (value instanceof PersistentDictionary) {
            return (PersistentDictionary) value;
        }

        Map<String, PersistentObject> map = new HashMap<>();
        map.put(((PersistentObject) this.value).name, (PersistentObject) ((PersistentObject) this.value).value);
        return new PersistentDictionary(name, map);
    }
}
