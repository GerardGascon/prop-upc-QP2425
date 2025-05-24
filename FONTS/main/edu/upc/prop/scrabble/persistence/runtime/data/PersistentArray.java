package edu.upc.prop.scrabble.persistence.runtime.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa un objecte persistent que conté una llista d'altres objectes persistents.
 * <p>
 * Aquesta classe permet emmagatzemar un array d'objectes persistents sota un mateix nom,
 * proporcionant accés a cada element i a la mida de l'array.
 * </p>
 *
 * @author Gerard Gascón
 */
public class PersistentArray extends PersistentObject {
    /** Llista d'elements continguts en aquest array persistent */
    private final List<PersistentObject> elements;

    /**
     * Constructor que inicialitza l'array amb un nom i una llista buida d'elements.
     *
     * @param name Nom associat a aquest array persistent.
     */
    public PersistentArray(String name) {
        this(name, new ArrayList<>());
    }

    /**
     * Constructor que inicialitza l'array amb un nom i una llista d'elements específica.
     *
     * @param name     Nom associat a aquest array persistent.
     * @param elements Llista d'objectes persistents que contindrà l'array.
     */
    public PersistentArray(String name, List<PersistentObject> elements) {
        super(name, null);
        this.elements = elements;
    }

    /**
     * Retorna la quantitat d'elements que conté l'array persistent.
     *
     * @return Nombre d'elements de l'array.
     */
    public int getLength() {
        return elements.size();
    }

    /**
     * Retorna el valor associat a aquest objecte persistent.
     * En aquest cas, retorna la llista d'elements.
     *
     * @return La llista d'objectes persistents que formen l'array.
     */
    @Override
    public Object getValue() {
        return elements;
    }

    /**
     * Mètode no suportat per a aquest tipus d'objecte.
     * Llança una excepció si es prova de convertir l'array a un altre tipus.
     *
     * @param type Tipus al qual es vol parsejar (no suportat).
     * @param <T>  Tipus genèric.
     * @throws RuntimeException Sempre llança excepció perquè no es pot parsejar un array.
     */
    @Override
    public <T> T parse(Class<T> type) throws RuntimeException {
        throw new RuntimeException("Cannot parse an array");
    }

    /**
     * Afegeix un nou element a l'array persistent.
     *
     * @param value Objecte persistent a afegir.
     */
    public void add(PersistentObject value) {
        elements.add(value);
    }

    /**
     * Retorna l'element situat en una posició concreta de l'array.
     *
     * @param index Índex de l'element a retornar.
     * @return Objecte persistent que es troba en la posició indicada.
     */
    public PersistentObject get(int index) {
        return elements.get(index);
    }
}
