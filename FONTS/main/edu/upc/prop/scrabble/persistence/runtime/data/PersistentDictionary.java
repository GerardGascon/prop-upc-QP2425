package edu.upc.prop.scrabble.persistence.runtime.data;

import java.util.HashMap;
import java.util.Map;

/**
 * Representa un objecte persistent que conté un diccionari d'objectes persistents.
 * <p>
 * Aquesta classe permet emmagatzemar un conjunt de parells clau-valor,
 * on la clau és una cadena i el valor és un objecte persistent.
 * </p>
 *
 * @author Gerard Gascón
 */
public class PersistentDictionary extends PersistentObject {
    /** Diccionari intern que conté els objectes persistents associats a una clau */
    private final Map<String, PersistentObject> dictionary;

    /**
     * Retorna el diccionari intern de parells clau-valor.
     *
     * @return El diccionari d'objectes persistents.
     */
    public Map<String, PersistentObject> getDictionary() {
        return dictionary;
    }

    /**
     * Constructor per defecte que inicialitza un diccionari sense nom i buit.
     */
    public PersistentDictionary() {
        this(null);
    }

    /**
     * Constructor que inicialitza un diccionari buit amb un nom específic.
     *
     * @param name Nom associat a aquest diccionari persistent.
     */
    public PersistentDictionary(String name) {
        this(name, new HashMap<>());
    }

    /**
     * Constructor que inicialitza un diccionari amb un nom i un mapa de contingut específic.
     *
     * @param name       Nom associat a aquest diccionari persistent.
     * @param dictionary Mapa que conté els elements del diccionari.
     */
    public PersistentDictionary(String name, Map<String, PersistentObject> dictionary) {
        super(name, null);
        this.dictionary = dictionary;
    }

    /**
     * Mètode no suportat per a aquest tipus d'objecte.
     * Llança una excepció si es prova de convertir el diccionari a un altre tipus.
     *
     * @param type Tipus al qual es vol parsejar (no suportat).
     * @param <T>  Tipus genèric.
     * @throws RuntimeException Sempre llança excepció perquè no es pot parsejar un diccionari.
     */
    @Override
    public <T> T parse(Class<T> type) throws RuntimeException {
        throw new RuntimeException("Cannot parse a dictionary");
    }

    /**
     * Afegeix un nou objecte persistent al diccionari, associat amb el seu nom.
     *
     * @param value Objecte persistent que s'afegirà al diccionari.
     */
    public void add(PersistentObject value) {
        dictionary.put(value.getName(), value);
    }

    /**
     * Retorna l'objecte persistent associat a una clau determinada.
     *
     * @param key Clau que identifica l'objecte.
     * @return Objecte persistent associat a la clau, o null si no existeix.
     */
    public PersistentObject get(String key) {
        return dictionary.get(key);
    }
}
