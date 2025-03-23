package edu.upc.prop.scrabble.data.pieces;
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
    public int getSize() {
        return bag.size();
    }

    public Piece getPiece(int index) {
        Piece piece = bag.get(index);
        this.bag.remove(index);
        return piece;
    }

    // s'afegeix la fitxa a la bossa
    public void addPiece(Piece piece) {
        bag.add(piece);
    }
}
