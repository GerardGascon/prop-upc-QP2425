package edu.upc.prop.scrabble.data;
import java.lang.reflect.InaccessibleObjectException;
import java.util.*;

public class Bag {
    // atributs de la bossa
    private final List<Piece> bag;

    // constructora per defecte d'una bossa
    public Bag() {
        this.bag = new ArrayList<>();
    }

    // Booleà per comprovar si la bossa está buida
    public boolean isEmpty() {
        return bag.isEmpty();
    }

    //funcions getter
    public Piece getPiece(int i) {
        Piece p = bag.get(i);
        this.bag.remove(i);
        return p;
    }

    public int getSize() {
        return bag.size();
    }

    // s'afegeix la fitxa a la bossa
    public void addPiece(Piece piece) {
        bag.add(piece);
    }
}
