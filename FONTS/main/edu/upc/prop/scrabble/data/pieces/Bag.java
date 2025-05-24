package edu.upc.prop.scrabble.data.pieces;
import edu.upc.prop.scrabble.persistence.runtime.data.PersistentDictionary;
import edu.upc.prop.scrabble.persistence.runtime.data.PersistentObject;

import java.util.*;

/***
 * Representa una bossa de peces del joc de taula Scrabble.
 * @author Gina Escofet González
 */
public class Bag {
    private List<Piece> bag;

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
    public PersistentDictionary encode() {
        PersistentDictionary data = new PersistentDictionary();

        data.add(new PersistentObject("bag", bag.toArray()));

        return data;
    }

    /**
     * Decodifica les dades d'un diccionari persistent per reconstruir l'estat de la bossa.
     * Si les dades són invàlides o buides, inicialitza una bossa buida.
     * @param data Diccionari persistent que conté les dades de la bossa serialitzades.
     */
    public void decode(PersistentDictionary data) {
        PersistentObject bagData = data.get("bag");
        if (bagData != null) {
            Object[] parsedObjects = bagData.parse(Object[].class);

            if (parsedObjects != null) {
                this.bag = new ArrayList<>();
                for (Object obj : parsedObjects) {
                    if (obj instanceof Piece) {
                        this.bag.add((Piece) obj);
                    } else {
                        System.err.println("Error: Found non-Piece object in decoded bag: " + obj);
                    }
                }
            } else {
                this.bag = new ArrayList<>();
            }
        }
        else {
            this.bag = new ArrayList<>();
        }
    }
}
