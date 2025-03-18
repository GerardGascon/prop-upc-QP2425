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

    // pre: s'ha eliminat la piece del taulell
    // post: s'afegeix de nou la fitxa a la bossa
    public void addPiece(Piece piece) {
        bag.add(piece);
    }

    // pre: es vol posar una fitxa al taulell
    // post: agafar una fitxa random de la bossa
    public Piece drawPiece() {
        if (bag.isEmpty()) {
            throw new InaccessibleObjectException("Bag is empty.");
        }
        Random rand = new Random();
        int i = rand.nextInt(bag.size());
        Piece piece = bag.get(i);
        bag.remove(i);
        return piece;
    }
}
