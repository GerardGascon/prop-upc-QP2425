package edu.upc.prop.scrabble.data.pieces;

import edu.upc.prop.scrabble.persistence.runtime.data.PersistentArray;
import edu.upc.prop.scrabble.persistence.runtime.data.PersistentDictionary;
import edu.upc.prop.scrabble.persistence.runtime.interfaces.IPersistableObject;

import java.util.ArrayList;
import java.util.List;

/***
 * Representa una bossa de peces del joc de taula Scrabble.
 * @author Gina Escofet González
 */
public class Bag implements IPersistableObject {
    /**
     * La llista de les fitxes presents dins seu
     */
    private final List<Piece> bag;

    /***
     * Creadora d'una bossa buida.
     */
    public Bag() {
        this.bag = new ArrayList<>();
    }

    /***
     * Revisa l'estat de la bossa.
     * @return True si està buida, False altrament.
     */
    public boolean isEmpty() {
        return bag.isEmpty();
    }

    /***
     * Revista l'estat de la bossa.
     * @return La quantitat de peces que hi ha la bossa.
     */
    public int getSize() {
        return bag.size();
    }

    /**
     * Roba i elimina una peça de la bossa.
     * @param index La posició de la peça a agafar (ha de ser vàlida), començant des de zero.
     * @return La peça en la posició especificada.
     * @throws IndexOutOfBoundsException si l'index no és vàlid.
     * @throws IllegalStateException si la bossa està buida.
     */
    public Piece draw(int index) {
        if (bag.isEmpty()) {
            throw new IllegalStateException("Cannot get any piece from an empty bag");
        }
        return bag.remove(index);
    }

    /**
     * Afegeix una peça a la bossa.
     * @param piece La peça que s'afegirà a la bossa.
     * @throws IllegalArgumentException si la peça és nul·la.
     */
    public void add(Piece piece) {
        if (piece == null) {
            throw new IllegalArgumentException("Cannot add null piece");
        }
        bag.add(piece);
    }

    /**
     * Codifica l'estat actual de la bossa en un diccionari persistent.
     * @return Diccionari persistent amb les dades de la bossa serialitzades.
     */
    @Override
    public PersistentDictionary encode() {
        PersistentDictionary data = new PersistentDictionary();

        PersistentArray bag = new PersistentArray("bag");
        for (int y = 0; y < getSize(); y++) {
            bag.add(this.bag.get(y));
        }
        data.add(bag);

        return data;
    }

    /**
     * Decodifica les dades d'un diccionari persistent per reconstruir l'estat de la bossa.
     * Si les dades són invàlides o buides, inicialitza una bossa buida.
     * @param data Diccionari persistent que conté les dades de la bossa serialitzades.
     */
    @Override
    public void decode(PersistentDictionary data) {
        PersistentArray bag = (PersistentArray)data.get("bag");
        int size = bag.getLength();
        for (int i = 0; i < size; i++) {
            this.bag.add(bag.get(i, Piece.class));
        }
    }
}
